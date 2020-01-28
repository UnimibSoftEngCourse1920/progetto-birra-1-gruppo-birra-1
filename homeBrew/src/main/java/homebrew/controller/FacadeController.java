package Birra.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import Birra.model.Attrezzatura;
import Birra.model.Ingrediente;
import Birra.model.Nota;
import Birra.model.Ricetta;
import Birra.model.TipoIngrediente;

public class FacadeController {

	private ControllerRicetta cr;
	private ControllerIngrediente ci;
	private RicetteDisponibili rd;
	private ControllerAttrezzatura ca;

	public FacadeController() {
		ci = new ControllerIngrediente();
		ca = new ControllerAttrezzatura();
		cr = new ControllerRicetta(ci, new ControllerAttrezzatura());
		rd = new RicetteDisponibili(cr, ci, ca);
	}

	public Attrezzatura getStrumento(String nome) throws SQLException {
		return ca.getStrumento(nome);
	}
	
	public HashSet<String> getNomiStrumenti(String nomeBirra) throws SQLException {
		return ca.getNomiStrumenti(nomeBirra);
	}
	
	public HashSet<String> getNomiStrumenti() throws SQLException {
		return ca.getNomiStrumenti();
	}

	// metodi per richiamare ingredienti
	public Ingrediente creaIngrediente(String nome, String quantita, boolean bloccato, String tipo) {
		checkString(nome, "Nome dell'ingrediente vuoto");
		double quant = parseDouble(quantita);

		if (quant < 0)
			throw new IllegalArgumentException("Quantità dell'ingrediente " + nome + " minore di zero");

		return new Ingrediente(nome, quant, bloccato, TipoIngrediente.valueOf(tipo));
	}

	public boolean aggiungiIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException, SQLException {
		Ingrediente ingr = creaIngrediente(nome, quantita, bloccato, tipo);

		if (ci.getIngrediente(nome) != null)
			return false;

		ci.aggiungiIngrediente(ingr);
		return true;
	}

	public void eliminaIngrediente(String nome) throws SQLException {
		ci.eliminaIngrediente(nome);
	}

	public boolean modificaIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException, SQLException {
		Ingrediente newIngr = creaIngrediente(nome, quantita, bloccato, tipo);
		Ingrediente oldIngr = ci.getIngrediente(nome);

		if (oldIngr == null || oldIngr.equals(newIngr))
			return false;

		ci.modificaIngrediente(newIngr);
		return true;
	}

	public Ingrediente getIngrediente(String nome) throws SQLException {
		return ci.getIngrediente(nome);
	}

	// metodi per il controllo delle ricette
	private Nota creaNota(String titolo, String descrizione) throws NullPointerException {
		if (titolo == null)
			return null;

		Objects.requireNonNull(descrizione, "Nessuna descrizione nella nota " + titolo);
		checkString(titolo, "Titolo della nota vuoto");
		checkString(descrizione, "La nota " + titolo + " ha descrizione vuota");

		return new Nota(titolo, descrizione);
	}

	private Ricetta creaRicetta(String nomeBirra, String tempo, String procedimento, Set<String> strumenti,
			Map<Ingrediente, Double> ingredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException, NullPointerException {
		checkString(nomeBirra, "Nome della birra vuoto");
		checkString(procedimento, "Procedimento per la ricetta " + nomeBirra + " vuoto");
		double time = parseDouble(tempo);

		if (time <= 0)
			throw new IllegalArgumentException("Tempo per la ricetta " + nomeBirra + " minore o uguale a 0");

		if (strumenti.isEmpty())
			throw new IllegalArgumentException("Nessuno strumento per la birra " + nomeBirra);

		if (ingredienti.isEmpty())
			throw new IllegalArgumentException("Nessun ingrediente per la birra " + nomeBirra);

		HashMap<String, Double> percIngredienti = new HashMap<>();
		double sommaPerc = 0;

		for (Entry<Ingrediente, Double> coppia : ingredienti.entrySet()) {
			String nome = coppia.getKey().getNome();
			double perc = coppia.getValue();

			if (perc <= 0)
				throw new IllegalArgumentException(
						"Percentuale dell'ingrediente " + nome + " minore o uguale a 0");

			sommaPerc += perc;

			if (sommaPerc > 1.001)
				break;

			percIngredienti.put(nome, coppia.getValue());
		}

		if (Math.abs(sommaPerc - 1) > 0.001)
			throw new IllegalArgumentException(
					"Somma delle percentuali della ricetta " + nomeBirra + " diversa da 100");

		return new Ricetta(nomeBirra, time, procedimento, strumenti, percIngredienti,
				creaNota(titoloNota, descrizioneNota));
	}

	public boolean aggiungiRicetta(String nomeBirra, String tempo, String procedimento, Set<String> strumenti,
			Map<Ingrediente, Double> ingredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException, SQLException {
		Ricetta ricetta = creaRicetta(nomeBirra, tempo, procedimento, strumenti, ingredienti, titoloNota,
				descrizioneNota);

		if (cr.getRicetta(nomeBirra) != null)
			return false;

		for (Ingrediente ingr : ingredienti.keySet())
			ci.aggiungiIngrediente(ingr);

		cr.aggiungiRicetta(ricetta);
		return true;
	}

	public void eliminaRicetta(String nome) throws SQLException {
		cr.eliminaRicetta(nome);
	}

	public boolean modificaRicetta(String nomeBirra, String tempo, String procedimento, Set<String> strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException, SQLException {
		Ricetta newRicetta = creaRicetta(nomeBirra, tempo, procedimento, strumenti, percentualiIngredienti, titoloNota,
				descrizioneNota);
		Ricetta oldRicetta = cr.getRicetta(nomeBirra);

		if (oldRicetta == null || oldRicetta.equals(newRicetta))
			return false;

		for (Ingrediente ingr : percentualiIngredienti.keySet()) {
			Ingrediente oldIngr = ci.getIngrediente(ingr.getNome());

			if (oldIngr == null)
				ci.aggiungiIngrediente(ingr);
			else if (!ingr.equals(oldIngr))
				ci.modificaIngrediente(ingr);
		}

		cr.modificaRicetta(newRicetta);
		return true;
	}

	public Ricetta getRicetta(String nomeBirra) throws SQLException {
		return cr.getRicetta(nomeBirra);
	}

	public Nota getNota(String nomeBirra) throws SQLException {
		Ricetta ric = cr.getRicetta(nomeBirra);
		return ric == null ? null : ric.getNota();
	}

	public boolean aggiungiNota(String nomeBirra, String titolo, String descrizione)
			throws IllegalArgumentException, NullPointerException, SQLException {
		Nota nota = creaNota(titolo, descrizione);
		Ricetta ricetta = cr.getRicetta(nomeBirra);

		if (ricetta == null || Objects.equals(nota, ricetta.getNota()))
			return false;

		cr.aggiungiNota(nomeBirra, nota);
		return true;
	}

	// creo i metodi di controllo per le ricette disponibili
	public Ricetta cosaDovreiPreparareOggi() throws SQLException {
		return rd.cosaDovreiPreparareOggi();
	}

	public double getQuantitaBirra() {
		return rd.getQuantitaBirra();
	}

	public double getMaxQuantita(String nomeBirra) throws SQLException {
		Ricetta ricetta = cr.getRicetta(nomeBirra);
		return ricetta == null ? 0 : rd.getMaxQuantita(ricetta);
	}

	private void checkString(String str, String messaggio) {
		if (str.isEmpty())
			throw new IllegalArgumentException(messaggio);

	}

	private double parseDouble(String str) {
		double x = 0;

		try {
			x = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}

		return x;
	}

}

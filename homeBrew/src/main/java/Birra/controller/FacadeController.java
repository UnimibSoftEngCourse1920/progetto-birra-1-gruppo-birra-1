package Birra.controller;

import java.util.Map;
import java.util.Map.Entry;

import Birra.model.Attrezzatura;
import Birra.model.Ingrediente;
import Birra.model.Nota;
import Birra.model.Ricetta;
import Birra.model.TipoIngrediente;

public class FacadeController {
	private ControllerRicetta cr;
	private ControllerIngrediente ci;
	private RicetteDisponibili rd;

	// metodi per richiamare ingredienti
	public Ingrediente creaIngrediente(String nome, String quantita, boolean bloccato, String tipo) {
		checkString(nome, "Nome dell'ingrediente vuoto");
		double quant = parseDouble(quantita);

		if (quant < 0)
			throw new IllegalArgumentException("Quantità dell'ingrediente " + nome + " minore di zero");

		return new Ingrediente(nome, quant, bloccato, TipoIngrediente.valueOf(tipo));
	}

	public boolean aggiungiIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException {
		Ingrediente ingr = creaIngrediente(nome, quantita, bloccato, tipo);
		
		if (ci.getIngrediente(nome) != null)
			return false;

		ci.aggiungiIngrediente(ingr);
		return true;

	}

	public void eliminaIngrediente(String nome) {
		ci.eliminaIngrediente(nome);
	}

	public boolean modificaIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException {
		Ingrediente ingr = creaIngrediente(nome, quantita, bloccato, tipo);
		
		if (ci.getIngrediente(nome) == null)
			return false;

		ci.modificaIngrediente(ingr);
		return true;
	}

	public Ingrediente getIngrediente(String nome) {
		return ci.getIngrediente(nome);
	}

	// metodi per il controllo delle ricette
	private Nota creaNota(String titolo, String descrizione) throws NullPointerException {
		if (titolo == null)
			return null;
		
		if (descrizione == null)
			throw new NullPointerException("Nessuna descrizione nella nota " + titolo);

		checkString(titolo, "Titolo della nota vuoto");
		checkString(descrizione, "La nota " + titolo + " ha descrizione vuota");

		return new Nota(titolo, descrizione);
	}
	
	private Ricetta creaRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException, NullPointerException {
		checkString(nomeBirra, "Nome della birra vuoto");
		checkString(procedimento, "Procedimento per la ricetta " + nomeBirra + " vuoto");
		double time = parseDouble(tempo);

		if (time <= 0)
			throw new IllegalArgumentException("Tempo per la ricetta " + nomeBirra + " minore o uguale a 0");
		
		if (strumenti.length == 0)
			throw new IllegalArgumentException("Nessuno strumento per la birra " + nomeBirra);
		
		if (percentualiIngredienti.isEmpty())
			throw new IllegalArgumentException("Nessun ingrediente per la birra " + nomeBirra);
		
		double sommaPerc = 0;
		
		for (Entry<Ingrediente, Double> coppia : percentualiIngredienti.entrySet()) {
			double perc = coppia.getValue();
			
			if (perc <= 0)
				throw new IllegalArgumentException("Percentuale dell'ingrediente " + coppia.getKey().getNome() + " minore o uguale a 0");
			
			sommaPerc += perc;
		}
		
		if (Math.abs(sommaPerc - 1) > 0.001)
			throw new IllegalArgumentException("Somma delle percentuali della ricetta " + nomeBirra + "diversa da 100");

		return new Ricetta(nomeBirra, time, procedimento, strumenti, percentualiIngredienti,
				creaNota(titoloNota, descrizioneNota));
	}

	public boolean aggiungiRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException {
		Ricetta ricetta = creaRicetta(nomeBirra, tempo, procedimento, strumenti, percentualiIngredienti, titoloNota,
				descrizioneNota);

		if (cr.getRicetta(nomeBirra) != null)
			return false;

		cr.aggiungiRicetta(ricetta);
		return true;
	}

	public void eliminaRicetta(String nome) {
		cr.eliminaRicetta(nome);
	}

	public boolean modificaRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException {
		Ricetta ricetta = creaRicetta(nomeBirra, tempo, procedimento, strumenti, percentualiIngredienti, titoloNota,
				descrizioneNota);

		if (cr.getRicetta(nomeBirra) == null)
			return false;

		cr.modificaRicetta(ricetta);

		return true;
	}

	public boolean aggiungiNota(String nomeBirra, String titolo, String descrizione) throws IllegalArgumentException, NullPointerException {
		Nota nota = creaNota(titolo, descrizione);

		if (cr.getRicetta(nomeBirra) == null)
			return false;

		cr.aggiungiNota(nomeBirra, nota);
		return true;
	}

	public Ricetta getRicetta(String nome) {
		return cr.getRicetta(nome);
	}

	// creo i metodi di controllo per le ricette disponibili
	public Ricetta cosaDovreiPreparareOggi() {
		return rd.cosaDovreiPreparareOggi();
	}

	public double getMaxQuantita(String nomeBirra) {
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

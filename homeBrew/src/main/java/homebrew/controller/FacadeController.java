package homebrew.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import homebrew.model.Attrezzatura;
import homebrew.model.Ingrediente;
import homebrew.model.Nota;
import homebrew.model.QuantitaRicetta;
import homebrew.model.Ricetta;
import homebrew.model.TipoIngrediente;

/*
 * La classe FacadeController funge da interfaccia di accesso ai metodi delle classi
 * controller. Attraverso questa classe viene implementato il design pattern facade.
 * Questa classe permette, inoltre di definire dei controlli sull'input inserito dallo
 * utente ed eventualmente di lanciare una opportuna eccezione (gestita nella gui).
 */
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

	/*
	 * Viene restituito uno strumento dato il suo nome.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public Attrezzatura getStrumento(String nome) throws SQLException {
		return ca.getStrumento(nome);
	}
	
	/*
	 * Vengono restituiti tutti gli strumenti associati ad una ricetta, identificata 
	 * dal nome della birra che permette di produrre.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public HashSet<String> getNomiStrumenti(String nomeBirra) throws SQLException {
		return ca.getNomiStrumenti(nomeBirra);
	}
	
	/*
	 * Restituisce tutti gli strumenti salvati nel database.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public HashSet<String> getNomiStrumenti() throws SQLException {
		return ca.getNomiStrumenti();
	}

	/*
	 * Viene creato una istanza di Ingrediente, dati i suoi attributi
	 */
	public Ingrediente creaIngrediente(String nome, String quantita, boolean bloccato, String tipo) {
		checkString(nome, "Nome dell'ingrediente vuoto");
		double quant = parseDouble(quantita);

		if (quant < 0)
			throw new IllegalArgumentException("Quantità dell'ingrediente " + nome + " minore di zero");

		return new Ingrediente(nome, quant, bloccato, TipoIngrediente.valueOf(tipo));
	}
	
	/*
	 * Dati il valore degli attributi di un ingrediente, ne viene creata una istanza e, se non già 
	 * inserito nel database in precedenza, viene aggiunto, restituendo true,
	 *  altrimenti non viene aggiunto e viene  restituito false.
	 *  Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public boolean aggiungiIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException, SQLException {
		Ingrediente ingr = creaIngrediente(nome, quantita, bloccato, tipo);

		if (ci.getIngrediente(nome) != null) //Ingrediente già presente nel db
			return false;

		ci.aggiungiIngrediente(ingr);
		return true;
	}

	/*
	 * Viene eliminato un ingrediente dal database.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public void eliminaIngrediente(String nome) throws SQLException {
		ci.eliminaIngrediente(nome);
	}

	/*
	 * Dati in input, gli attributi di un ingrediente, se esso è già presente nel database e non è 
	 * uguale all'ingrediente identificato dagli attributi ricevuti in input, viene modficato (e il metodo
	 * restituisce true), altrimenti il metodo restituisce false e l'ingrediente non viene modificato.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public boolean modificaIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException, SQLException {
		Ingrediente newIngr = creaIngrediente(nome, quantita, bloccato, tipo);
		Ingrediente oldIngr = ci.getIngrediente(nome);

		if (oldIngr == null || oldIngr.equals(newIngr))
			return false;

		ci.modificaIngrediente(newIngr);
		return true;
	}

	/*
	 * Dato il nome di un ingrediente, esso viene restituito, prelevandolo dal db
	 */
	public Ingrediente getIngrediente(String nome) throws SQLException {
		return ci.getIngrediente(nome);
	}

	/*
	 * Viene restituito un istanza di tipo Nota, dati in input il titolo e la
	 * descrizione della nota. Se il titolo, oppure la descrizione della nota sono null (ovvero
	 * non sono state inserite dall'utente), allora viene lanciata una null NullPointerException
	 */
	private Nota creaNota(String titolo, String descrizione) throws NullPointerException {
		if (titolo == null)
			return null;

		Objects.requireNonNull(descrizione, "Nessuna descrizione nella nota " + titolo);
		checkString(titolo, "Titolo della nota vuoto");
		checkString(descrizione, "La nota " + titolo + " ha descrizione vuota");

		return new Nota(titolo, descrizione);
	}

	/*
	 * Dati il nome, il tempo, il procedimento, gli strumenti associati, gli ingredineti associati (con le 
	 * relative percentuali), il titolo della nota, la descrizione della nota, viene creato un oggetto di tipo ricetta.
	 * Viene lanciata una IllegalArgumentException se il tempo è minore di zero, se non ci sono strumenti associati alla ricetta,
	 * se non ci sono ingredienti associati alla ricetta, se le percentuali associate agli ingredienti sono minori o uguali a 0,
	 * oppure se la somma delle percentuali è diversa da 1. Viene lanciata una nullPointerException se il titolo o la descrizione
	 * della ricetta sono null.
	 */
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

	/* Dati il nome, il tempo, il procedimento, gli strumenti associati, gli ingredineti associati (con le 
	 * relative percentuali), il titolo della nota, la descrizione della nota, viene creata una istanza di una ricetta 
	 * e, se non già presente nel db, viene inserita e restituito true. Se invece la ricetta è già presente nel database,
	 * non viene aggiunta e viene restituito false. Può lanciare IllegalArgumentException se il tempo è minore di zero, 
	 * se non ci sono strumenti associati alla ricetta, se non ci sono ingredienti associati alla ricetta, 
	 * se le percentuali associate agli ingredienti sono minori o uguali a 0,
	 * oppure se la somma delle percentuali è diversa da 1. Lancia una SQLException se la query di inserimento non va
	 * a buon fine.
	 */
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

	/*
	 * Viene eliminata una ricetta dato il suo nome. 
	 * Lancia SQLException nel caso in cui la query non vada a buon fine
	 */
	public void eliminaRicetta(String nome) throws SQLException {
		cr.eliminaRicetta(nome);
	}
	
	/* Dati il nome, il tempo, il procedimento, gli strumenti associati, gli ingredineti associati (con le 
	 * relative percentuali), il titolo della nota, la descrizione della nota, viene modificata la ricetta se già
	 * presente nel db, resttuendo true, altrimenti viene restituito false. Può lanciare una SQLException se la query non
	 * dovesse andare a buon fine, oppure una IllegaArgumentException se 
	 * il tempo è minore di zero, se non ci sono strumenti associati alla ricetta, se non ci sono ingredienti associati alla ricetta, 
	 * se le percentuali associate agli ingredienti sono minori o uguali a 0,
	 * oppure se la somma delle percentuali è diversa da 1
	 */
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

	/*
	 * Viene resttuita la ricetta identificata dal nome della birra, che permette di produrre.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public Ricetta getRicetta(String nomeBirra) throws SQLException {
		return cr.getRicetta(nomeBirra);
	}
	
	/*
	 * Viene resttuita la nota della ricetta identificata dal nome della birra, che permette
	 * di produrre. 
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public Nota getNota(String nomeBirra) throws SQLException {
		Ricetta ric = cr.getRicetta(nomeBirra);
		return ric == null ? null : ric.getNota();
	}

	/*
	 * Dato il nome della birra, il titolo e la descrizione della nota, viene aggiunta una nota alla
	 * ricetta. 
	 * Può lanciare IllegalArgumentException se il titolo o la descrizione della nota dovessero essere 
	 * null, e una SQLException se la query non dovesse andare a buon fine.
	 */
	public boolean aggiungiNota(String nomeBirra, String titolo, String descrizione)
			throws IllegalArgumentException, NullPointerException, SQLException {
		Nota nota = creaNota(titolo, descrizione);
		Ricetta ricetta = cr.getRicetta(nomeBirra);

		if (ricetta == null || Objects.equals(nota, ricetta.getNota()))
			return false;

		cr.aggiungiNota(nomeBirra, nota);
		return true;
	}

	/*
	 * In base alle ricette inserite nel db, restituisce un oggetto di tipo QuantitaRicetta, che indica tutti i campi della ricetta e 
	 * la quantità di birra che essa permette di produrre, relativo alla ricetta che massimizza la quantità di birra producibile 
	 * tra quelle inserite nel database.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public QuantitaRicetta cosaDovreiPreparareOggi() throws SQLException {
		return rd.cosaDovreiPreparareOggi();
	}
	
	/*
	 * restituisce un oggetto di tipo QuantitaRicetta, che indica tutti i campi della ricetta e 
	 * la quantità di birra che essa permette di produrre, relativo alla ricetta identificata dal nome della birra che permette 
	 * di produrre.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public QuantitaRicetta getQuantitaRicetta(String nomeBirra) throws SQLException {
		Ricetta ricetta = cr.getRicetta(nomeBirra);
		return ricetta == null ? null : new QuantitaRicetta(ricetta, rd.getQuantita(ricetta));
	}

	/*
	 * Lancia una IllegalArgumentException se la stringa è vuota
	 */
	private void checkString(String str, String messaggio) {
		if (str.isEmpty())
			throw new IllegalArgumentException(messaggio);

	}

	/*
	 * Viene modificata una stringa in double, se la stringa non rappresenta 
	 * un numero viene lanciata IllegalArgumentException.
	 */
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

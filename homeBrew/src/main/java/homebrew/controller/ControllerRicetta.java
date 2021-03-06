package homebrew.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import homebrew.model.*;

/*
 * La classe ControllerRicetta gestisce le ricette nel database
 */
public class ControllerRicetta {

	private ControllerIngrediente controllerIngr;
	private ControllerAttrezzatura controllerAttr;

	public ControllerRicetta(ControllerIngrediente controllerIngr, ControllerAttrezzatura controllerAttr) {
		this.controllerIngr = controllerIngr;
		this.controllerAttr = controllerAttr;
	}

	/*
	 * Aggiungo una ricetta nel db.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public void aggiungiRicetta(Ricetta ricetta) throws SQLException {
		DBUtils.update(sqlAggiungiRicetta(ricetta));

		String nomeBirra = ricetta.getNomeBirra();
		HashMap<String, Double> ingredienti = ricetta.getIngredienti();
		
		controllerIngr.associaRicetta(nomeBirra, ingredienti);
		controllerAttr.associaRicetta(nomeBirra, ricetta.getStrumenti());
	}

	/*
	 * Dato il nome della birra che una ricetta permette di produrre, viene restituito
	 * l'oggetto Ricetta corrispondente.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public Ricetta getRicetta(String nomeBirra) throws SQLException {
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(sqlGetRicetta(nomeBirra));
		return rows.isEmpty() ? null : parseRicetta(rows.get(0));
	}

	/*
	 * Dato il risultato della query che preleva dal database una ricetta,
	 * restituisce un oggetto di tipo ricetta.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	private Ricetta parseRicetta(HashMap<String, Object> row) throws SQLException {
		String nomeBirra = (String) row.get("nomeBirra");
		String titolo = (String) row.get("titoloNota");
		Nota nota = titolo == null ? null : new Nota(titolo, (String) row.get("descrizioneNota"));

		return new Ricetta(nomeBirra, (double) row.get("tempo"), (String) row.get("procedimento"),
				controllerAttr.getNomiStrumenti(nomeBirra), controllerIngr.getQuantitaIngredienti(nomeBirra), nota);
	}

	/*
	 * Viene eliminata la ricetta identificata dal nome ricevuto in input.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public void eliminaRicetta(String nomeBirra) throws SQLException {
		controllerIngr.disassociaRicetta(nomeBirra);
		controllerAttr.disassociaRicetta(nomeBirra);
		DBUtils.update(sqlEliminaRicetta(nomeBirra));
	}

	/*
	 * Viene modificata una ricetta presente nel database.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public void modificaRicetta(Ricetta ricetta) throws SQLException {
		eliminaRicetta(ricetta.getNomeBirra());
		aggiungiRicetta(ricetta);
	}

	/*
	 * Viene aggiunta una nota alla ricetta identificata dal parametro nomeBirra.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	public void aggiungiNota(String nomeBirra, Nota nota) throws SQLException {
		Ricetta ricetta = getRicetta(nomeBirra);
		modificaRicetta(new Ricetta(nomeBirra, ricetta.getTempo(), ricetta.getProcedimento(), ricetta.getStrumenti(),
				ricetta.getIngredienti(), nota));
	}

	/*
	 * Metodo che crea la query necessaria per eliminare la ricetta dal db.
	 */
	private String sqlEliminaRicetta(String nomeBirra) {
		return "delete from ricetta where nomeBirra = '" + nomeBirra + "'";
	}

	/*
	 * Metodo che restituisce la query necessaria per prelevare la ricetta dal db,
	 * dato l'attributo nomeBirra.
	 */
	private String sqlGetRicetta(String nomeBirra) {
		return "select * from ricetta where nomeBirra = '" + nomeBirra + "'";
	}

	/*
	 * Metodo che crea la query necessaria per inserire la ricetta nel db
	 */
	private String sqlAggiungiRicetta(Ricetta ricetta) {
		String query;
		Nota nota = ricetta.getNota();

		if (nota == null)
			query = "insert into ricetta (nomeBirra, tempo, procedimento) values ('" + ricetta.getNomeBirra()
					+ "', " + ricetta.getTempo() + ", '" + ricetta.getProcedimento() + "')";
		else
			query = "insert into ricetta (nomeBirra, tempo, procedimento, titoloNota, descrizioneNota) values ('"
					+ ricetta.getNomeBirra() + "', " + ricetta.getTempo() + ", '" + ricetta.getProcedimento() + "', '"
					+ nota.getTitolo() + "', '" + nota.getDescrizione() + "')";

		return query;
	}
}

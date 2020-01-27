package Birra.controller;

import java.util.ArrayList;
import java.util.HashMap;

import Birra.model.*;

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
	 * Aggiungo una ricetta nel db
	 */
	public void aggiungiRicetta(Ricetta ricetta) {
		DBUtils.update(sqlAggiungiRicetta(ricetta));

		String nomeBirra = ricetta.getNomeBirra();
		HashMap<Ingrediente, Double> ingredienti = ricetta.getIngredienti();

		controllerIngr.aggiungiIngredienti(ingredienti.keySet());
		controllerIngr.associaRicetta(nomeBirra, ingredienti);
		controllerAttr.associaRicetta(nomeBirra, ricetta.getStrumenti());
	}

	/*
	 * Questo metodo dato il nome della birra che una ricetta permette di produrre,
	 * restituisce l'oggetto Ricetta
	 */
	public Ricetta getRicetta(String nomeBirra) {
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(sqlGetRicetta(nomeBirra));
		return rows.isEmpty() ? null : parseRicetta(rows.get(0));
	}

	/*
	 * Dato il risultato della query che preleva dal database una ricetta,
	 * restituisce un oggetto di tipo ricetta
	 */
	private Ricetta parseRicetta(HashMap<String, Object> row) {
		String nomeBirra = (String) row.get("nomeBirra");
		String titolo = (String) row.get("titoloNota");
		Nota nota = titolo == null ? null : new Nota(titolo, (String) row.get("descrizioneNota"));

		return new Ricetta(nomeBirra, (double) row.get("tempo"), (String) row.get("procedimento"),
				controllerAttr.getStrumenti(nomeBirra), controllerIngr.getIngredienti(nomeBirra), nota);
	}

	/*
	 * Viene eliminata la ricetta identificata dal nome ricevuto in input
	 */
	public void eliminaRicetta(String nomeBirra) {
		controllerIngr.disassociaRicetta(nomeBirra);
		controllerAttr.disassociaRicetta(nomeBirra);
		DBUtils.update(sqlEliminaRicetta(nomeBirra));
	}

	/*
	 * Viene modificata una ricetta presente nel database
	 */
	public void modificaRicetta(Ricetta ricetta) {
		for (Ingrediente ingr : ricetta.getIngredienti().keySet()) {
			Ingrediente oldIngr = controllerIngr.getIngrediente(ingr.getNome());
			
			if (oldIngr == null)
				controllerIngr.aggiungiIngrediente(ingr);
			else if (!ingr.equals(oldIngr))
				controllerIngr.modificaIngrediente(ingr);
		}
		
		eliminaRicetta(ricetta.getNomeBirra());
		aggiungiRicetta(ricetta);
	}

	/*
	 * Viene aggiunta una nota alla ricetta identificata dal parametro nomeBirra
	 */
	public void aggiungiNota(String nomeBirra, Nota nota) {
		Ricetta ricetta = getRicetta(nomeBirra);
		modificaRicetta(new Ricetta(nomeBirra, ricetta.getTempo(), ricetta.getProcedimento(), ricetta.getStrumenti(),
				ricetta.getIngredienti(), nota));
	}

	/*
	 * Metodo che crea la query necessaria per eliminare la ricetta dal db
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
					+ "', '" + ricetta.getTempo() + "', '" + ricetta.getProcedimento() + "')";
		else
			query = "insert into ricetta (nomeBirra, tempo, procedimento, titoloNota, descrizioneNota) values ('"
					+ ricetta.getNomeBirra() + "', '" + ricetta.getTempo() + "', '" + ricetta.getProcedimento() + "', '"
					+ nota.getTitolo() + "', '" + nota.getDescrizione() + "')";

		return query;
	}
}

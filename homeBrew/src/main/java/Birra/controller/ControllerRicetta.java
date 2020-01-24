package Birra.controller;

import java.util.HashMap;

import Birra.model.*;

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
	 * restituisce una stringa contenente le informazioni della ricetta come
	 * nomeBirra, tempo, procedimento, il titolo di eventauli note e il testo di
	 * eventauli note restituisce null se la ricetta non è salvat nel database
	 */
	public Ricetta getRicetta(String nomeBirra) {
		return parseRicetta(DBUtils.getRows(sqlGetRicetta(nomeBirra)).get(0));
	}

	public Ricetta parseRicetta(HashMap<String, String> row) {
		String nomeBirra = row.get("nomeBirra");
		return new Ricetta(nomeBirra, Double.parseDouble(row.get("tempo")), row.get("procedimento"),
				controllerAttr.getStrumenti(nomeBirra), controllerIngr.getIngredienti(nomeBirra),
				new Nota(row.get("titoloNota"), row.get("descrizioneNota")));
	}

	/*
	 * Elimino la ricetta identificata dal nome ricevuto in input
	 */
	public void eliminaRicetta(String nomeBirra) {
		controllerIngr.disassociaRicetta(nomeBirra);
		controllerAttr.disassociaRicetta(nomeBirra);
		DBUtils.update(sqlEliminaRicetta(nomeBirra));
	}

	public void modificaRicetta(Ricetta ricetta) {
		eliminaRicetta(ricetta.getNomeBirra());
		aggiungiRicetta(ricetta);
	}

	/*
	 * Aggiungo la nota ricevuta in input alla ricetta ricevuta anch'essa in input
	 */
	public void aggiungiNota(String nomeBirra, Nota nota) {
		Ricetta ricetta = getRicetta(nomeBirra);
		modificaRicetta(new Ricetta(nomeBirra, ricetta.getTempo(), ricetta.getProcedimento(), ricetta.getStrumenti(),
				ricetta.getIngredienti(), nota));
	}

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
	 * Metodo che crea le query necessarie per inserire la ricetta e l'attrezzatura
	 * nel db
	 */
	private String sqlAggiungiRicetta(Ricetta ricetta) {
		String query;
		Nota nota = ricetta.getNota();

		if (nota == null)
			query = "insert ignore into ricetta (nomeBirra, tempo, procedimento) values ('" + ricetta.getNomeBirra()
					+ "', '" + ricetta.getTempo() + "', '" + ricetta.getProcedimento() + "')";
		else
			query = "insert ignore into ricetta (nomeBirra, tempo, procedimento, titoloNota, descrizioneNota) values ('"
					+ ricetta.getNomeBirra() + "', '" + ricetta.getTempo() + "', '" + ricetta.getProcedimento() + "', '"
					+ nota.getTitolo() + "', '" + nota.getDescrizione() + "')";

		return query;
	}
}

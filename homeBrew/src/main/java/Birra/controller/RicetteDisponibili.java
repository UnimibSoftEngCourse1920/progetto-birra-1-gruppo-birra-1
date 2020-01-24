package Birra.controller;

import java.util.*;

import Birra.model.Ricetta;

public class RicetteDisponibili {
	
	private ControllerRicetta controllerRic;

	public Ricetta[] cosaDovreiPreparareOggi() {
		String query = "select * from ricetta as ric where not exists (select nomeIngrediente from (ingrediente join ricettaIngrediente) as ingr where ingr.nomeBirra = ric.nomeBirra AND (bloccato OR quantita = 0))";
		ArrayList<HashMap<String, String>> rows = DBUtils.getRows(query);
		Ricetta[] ricette = new Ricetta[rows.size()];

		for (int i = 0; i < ricette.length; i++)
			ricette[i] = controllerRic.parseRicetta(rows.get(i));
		
		return ricette;
	}

}

package Birra.controller;

import java.util.HashMap;

import Birra.model.*;

public class main {

	public static void main(String[] args) {
		TipoAttrezzatura tipo = TipoAttrezzatura.TUBO;
		TipoIngrediente tipoI = TipoIngrediente.MALTO;
		Attrezzatura strumento = new Attrezzatura("tubo1", 10.10, tipo);
		Attrezzatura[] strumenti = { strumento };

		Ingrediente i = new Ingrediente("malto", 3.20, false, tipoI);
		HashMap<Ingrediente, Double> ingredienti = new HashMap<>();
		ingredienti.put(i, 0.5);

		Nota nota = new Nota("nota1", "blablabla...");
		Ricetta r = new Ricetta("stout", 3.30, "fare...", strumenti, ingredienti, null);
		ControllerRicetta cr = new ControllerRicetta(new ControllerIngrediente(), new ControllerAttrezzatura());
		// System.out.println(cr.getRicetta(r.getNomeBirra()));
		// System.out.println(cr.getNota(r.getNomeBirra()));
		cr.eliminaRicetta(r.getNomeBirra());
	}

}

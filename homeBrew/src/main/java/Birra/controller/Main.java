package Birra.controller;

import java.util.HashMap;

import Birra.model.*;
import static Birra.model.TipoAttrezzatura.*;
import static Birra.model.TipoIngrediente.*;

public class Main {
	
	public static void main(String[] args) {
		Attrezzatura tubo1 = new Attrezzatura("tubo1", 10.1, TUBO);
		Attrezzatura[] strumenti = { tubo1 };

		HashMap<Ingrediente, Double> ingredienti = new HashMap<>();
		ingredienti.put(new Ingrediente("malto", 3.2, false, MALTO), 1d);
		//ingredienti.put(new Ingrediente("luppoli", 5, false, LUPPOLI), .5);

		Nota nota = new Nota("nota1", "blablabla...");
		Ricetta r = new Ricetta("stout", 3.3, "fare...", strumenti, ingredienti, nota);
		FacadeController fc = new FacadeController();
		
		//fc.aggiungiRicetta("stout", "3.3", "fare...", strumenti, ingredienti, "nota1", "blablabla...");
		//fc.aggiungiRicetta("Moretti", "10", "boh", strumenti, ingredienti, "notaMoretti", "buona");
		//System.out.println(fc.getRicetta("stout"));
		System.out.println(fc.cosaDovreiPreparareOggi());
		System.out.println(fc.getQuantitaBirra());
		//fc.eliminaRicetta("stout");
		//DBUtils.update("insert into attrezzatura(nomeAttrezzatura, portata, tipo) values ('tubo1', 10.1, 'TUBO')");
		//DBUtils.update("delete from attrezzatura");
	}

}

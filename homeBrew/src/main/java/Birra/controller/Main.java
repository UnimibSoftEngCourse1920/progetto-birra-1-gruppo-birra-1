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
		ingredienti.put(new Ingrediente("malto", 3.2, false, MALTO), .6);
		ingredienti.put(new Ingrediente("luppoli", 5, false, LUPPOLI), .4);

		Nota nota = new Nota("nota1", "blablabla...");
		Ricetta r = new Ricetta("stout", 3.3, "fare...", strumenti, ingredienti, nota);
		FacadeController fc = new FacadeController();
		
		//fc.modificaRicetta("stout", "4", "fare...", strumenti, ingredienti, "nota1", "blablabla...");
		//fc.aggiungiRicetta("Moretti", "10", "boh", strumenti, ingredienti, "notaMoretti", "buona");
		//System.out.println(fc.getRicetta("stout"));
		//System.out.println(fc.cosaDovreiPreparareOggi());
		//System.out.println(fc.getMaxQuantita("Moretti"));
		//fc.eliminaRicetta("stout");
		//fc.modificaIngrediente("luppoli", "5", false, "LUPPOLI");
		System.out.println(fc.getIngrediente("luppoli"));
		//DBUtils.update("insert into attrezzatura(nomeAttrezzatura, portata, tipo) values ('tubo1', 10.1, 'TUBO')");
		//DBUtils.update("delete from ingrediente where nomeIngrediente = 'luppoli'");
	}

}

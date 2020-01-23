package Birra.controller;
import Birra.model.*;

public class main 
{
	public static void main(String[] args)
	{
		TipoAttrezzatura tipo = TipoAttrezzatura.TUBO;
		TipoIngrediente tipoI = TipoIngrediente.MALTO;
		Attrezzatura strumento=new Attrezzatura("tuobo1", 10.10, tipo);
		Attrezzatura[] strumenti= {strumento};
		Ingrediente i=new Ingrediente("malto", 3.20, false, tipoI);
		Ingrediente[] ingredienti = {i};
		Nota nota =new Nota("nota1", "blablabla...");
		Ricetta r=new Ricetta("stout", 3.30, "fare...", strumenti, ingredienti, null);
		ControllerRicetta cr=new ControllerRicetta();
		cr.aggiungiRicetta(r);
	}
}

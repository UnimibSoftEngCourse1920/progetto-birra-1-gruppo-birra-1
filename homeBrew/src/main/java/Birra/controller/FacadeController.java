package Birra.controller;

import java.util.Map;

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
		checkString(nome);
		return new Ingrediente(nome, parseDouble(quantita), bloccato, TipoIngrediente.valueOf(tipo));
	}

	public boolean aggiungiIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException {
		if (ci.getIngrediente(nome) != null)
			return false;

		ci.aggiungiIngrediente(creaIngrediente(nome, quantita, bloccato, tipo));
		return true;

	}

	public void eliminaIngrediente(String nome) {
		ci.eliminaIngrediente(nome);
	}

	public boolean modificaIngrediente(String nome, String quantita, boolean bloccato, String tipo)
			throws IllegalArgumentException {
		if (ci.getIngrediente(nome) == null)
			return false;
		ci.modificaIngrediente(creaIngrediente(nome, quantita, bloccato, tipo));
		return true;
	}

	public Ingrediente getIngrediente(String nome) {
		return ci.getIngrediente(nome);
	}

	// metodi per il controllo delle ricette
	private Ricetta creaRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException {
		checkString(nomeBirra);
		return new Ricetta(nomeBirra, parseDouble(tempo), procedimento, strumenti, percentualiIngredienti,
				new Nota(titoloNota, descrizioneNota));
	}

	public boolean aggiungiRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException {
		if (cr.getRicetta(nomeBirra) != null)
			return false;
		cr.aggiungiRicetta(creaRicetta(nomeBirra, tempo, procedimento, strumenti, percentualiIngredienti, titoloNota,
				descrizioneNota));
		return true;
	}

	public void eliminaRicetta(String nome) {
		cr.eliminaRicetta(nome);
	}

	public boolean modificaRicetta(String nomeBirra, String tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, String titoloNota, String descrizioneNota)
			throws IllegalArgumentException {

		if (cr.getRicetta(nomeBirra) == null)
			return false;

		cr.modificaRicetta(creaRicetta(nomeBirra, tempo, procedimento, strumenti, percentualiIngredienti, titoloNota,
				descrizioneNota));

		return true;
	}

	public boolean aggiungiNota(String nomeBirra, String titolo, String descrizione) throws IllegalArgumentException {

		checkString(nomeBirra);
		checkString(titolo);
		checkString(descrizione);

		if (cr.getRicetta(nomeBirra) == null)
			return false;
		cr.aggiungiNota(nomeBirra, new Nota(titolo, descrizione));
		return true;
	}

	public Ricetta getRicetta(String nome) {
		return cr.getRicetta(nome);
	}

	// creo i metodi di controllo per le ricette disponibili
	public Ricetta cosaDovreiPreparareOggi() {
		return rd.cosaDovreiPreparareOggi();
	}

	public double getMaxQuantita(String nomeBirra) throws IllegalArgumentException {
		return rd.getMaxQuantita(cr.getRicetta(nomeBirra));
	}

	private void checkString(String str) {
		if (str.isEmpty())
			throw new IllegalArgumentException("Stringa vuota");

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

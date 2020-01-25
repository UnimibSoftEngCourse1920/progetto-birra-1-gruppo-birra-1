package Birra.controller;

import java.util.*;
import java.util.Map.Entry;

import Birra.model.Attrezzatura;
import Birra.model.Ingrediente;
import Birra.model.Ricetta;

public class RicetteDisponibili {

	private ControllerRicetta controllerRic;
	private double quantitaBirra;
	
	public RicetteDisponibili(ControllerRicetta controllerRic) {
		quantitaBirra = -1;
		this.controllerRic = controllerRic;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	public Ricetta cosaDovreiPreparareOggi() {
		String[] nomiBirre = nomiBirreDisponibili();
		
		if (nomiBirre.length == 0)
			return null;
		
		Ricetta ricDaPrep = controllerRic.getRicetta(nomiBirre[0]);
		quantitaBirra = getMaxQuantita(ricDaPrep);
		
		for (int i = 1; i < nomiBirre.length; i++) {
			Ricetta ric = controllerRic.getRicetta(nomiBirre[i]);
			double quantita = getMaxQuantita(ric);
			
			if (quantita > quantitaBirra) {
				ricDaPrep = ric;
				quantitaBirra = quantita;
			}
		}
		
		return ricDaPrep;
	}
	
	private String[] nomiBirreDisponibili() {
		ArrayList<HashMap<String, String>> rows = DBUtils.getRows(sqlNomiBirreDisponibili());
		String[] nomi = new String[rows.size()];
		
		for (int i = 0; i < nomi.length; i++)
			nomi[i] = rows.get(i).get("nomebirra");
		
		return nomi;
	}
	
	private String sqlNomiBirreDisponibili() {
		return "select nomebirra"
				+ "from ricetta as ric"
				+ "where not exists"
					+ "(select * "
					+ "from (ingrediente join ricettaIngrediente) as ingr"
					+ "where ingr.nomeBirra = ric.nomeBirra AND (bloccato OR quantita = 0)"
					+ ")";
	}
	
	public double getMaxQuantita(Ricetta r) {
		double[][] tab = creaTableau(r);
		new Simplesso(tab).esegui();
		return tab[0][0];
	}
	
	/*
	 * funzione obiettivo: quantita di birra producibile
	 * vincoli ingredienti: ogni ingrediente non può eccedere la sua quatita disponibile
	 * vincolo birra: la birra non può essere in eccesso rispetto alla minima tra le capienze dei suoi strumenti
	 */
	private double[][] creaTableau(Ricetta r) {
		final ArrayList<Entry<Ingrediente, Double>> ingr = new ArrayList<>(r.getIngredienti().entrySet());
		final Attrezzatura[] strum = r.getStrumenti();
		
		final int m = 2 + ingr.size(); //(funzione obiettivo) + (vincoli ingredienti) + (vincolo birra)
		final int n = 2 + (ingr.size() << 1); //(termine noto) + (var ingredienti) + (slack ingredienti) + (slack birra)
		
		final double[][] tab = new double[m][n];
		
		//riga funzione obiettivo
		int j = 1; //termine noto funzione obiettivo = 0
		for (Entry<Ingrediente, Double> coppia : ingr) {
			tab[0][j] = -coppia.getValue(); //coeff funzione obiettivo negati (percentuali ingredienti negate)
			j++;
		}
		
		//righe vincoli ingredienti
		int i = 1;
		for (Entry<Ingrediente,Double> coppia : ingr) {
			tab[i][0] = coppia.getKey().getQuantita(); //termine noto (quantita ingrediente)
			tab[i][i] = 1; // coeff var ingrediente
			tab[i][i + ingr.size()] = 1; //coeff var slack
		}
		
		//riga vincolo birra
		final int last = m - 1;
		tab[last][0] = minCapienza(strum); //termine noto (min capienza)
		
		j = 1;
		for (Entry<Ingrediente, Double> coppia : ingr) {
			tab[last][j] = coppia.getValue(); //coeff var ingrediente (percentuali ingredienti)
			j++;
		}
		
		tab[last][n - 1] = 1; //coeff var slack birra
		
		return tab;
	}
	
	private double minCapienza(Attrezzatura[] strum) {
		return Arrays.stream(strum).mapToDouble(Attrezzatura::getPortata).min().getAsDouble();
	}
}

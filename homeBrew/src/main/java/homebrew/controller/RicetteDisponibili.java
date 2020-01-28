package homebrew.controller;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import homebrew.model.Ricetta;

public class RicetteDisponibili {

	private ControllerRicetta cr;
	private ControllerIngrediente ci;
	private ControllerAttrezzatura ca;
	private double quantitaBirra;
	
	public RicetteDisponibili(ControllerRicetta cr, ControllerIngrediente ci, ControllerAttrezzatura ca) {
		quantitaBirra = -1;
		this.cr = cr;
		this.ci = ci;
		this.ca = ca;
	}

	public double getQuantitaBirra() {
		return quantitaBirra;
	}

	public Ricetta cosaDovreiPreparareOggi() throws SQLException {
		String[] nomiBirre = nomiBirreDisponibili();
		
		if (nomiBirre.length == 0) {
			quantitaBirra = 0;
			return null;
		}
		
		Ricetta ricDaPrep = cr.getRicetta(nomiBirre[0]);
		quantitaBirra = getMaxQuantita(ricDaPrep);
		
		for (int i = 1; i < nomiBirre.length; i++) {
			Ricetta ric = cr.getRicetta(nomiBirre[i]);
			double quantita = getMaxQuantita(ric);
			
			if (quantita > quantitaBirra) {
				ricDaPrep = ric;
				quantitaBirra = quantita;
			}
		}
		
		return ricDaPrep;
	}
	
	private String[] nomiBirreDisponibili() throws SQLException {
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(sqlNomiBirreDisponibili());
	String[] nomi = new String[rows.size()];
		
		for (int i = 0; i < nomi.length; i++)
			nomi[i] = (String) rows.get(i).get("nomeBirra");
		
		return nomi;
	}
	
	private String sqlNomiBirreDisponibili() {
		return "select nomeBirra "
				+ "from ricetta as ric "
				+ "where not exists("
					+ "select * "
					+ "from ingrediente natural join ricettaIngrediente as ingr "
					+ "where ingr.nomeBirra = ric.nomeBirra AND (bloccato OR quantita = 0))";
	}
	
	public double getMaxQuantita(Ricetta ricetta) throws SQLException {
		double[][] tab = creaTableau(ricetta);
		new Simplesso(tab).esegui();
		return tab[0][0];
	}
	
	/*
	 * funzione obiettivo: quantita di birra da produrre
	 * vincoli ingredienti: non eccedere la quantità disponibile, rispettare percentuale ricetta
	 * vincolo birra: non eccedere la minima tra le capienze dei suoi strumenti
	 */
	private double[][] creaTableau(Ricetta ric) throws SQLException {
		final ArrayList<Entry<String, Double>> ingr = new ArrayList<>(ric.getIngredienti().entrySet());
		
		final int m = (ingr.size() + 1) << 1; //(funzione obiettivo) + (vincoli quantità) + (vincoli percentuali) + (vincolo birra)
		final int n = (ingr.size() << 1) + 3; //(termine noto) + (var ingredienti) + (slack ingredienti) + (var birra) + (slack birra)
		final int jVarBirra = n - 2;
		final double[][] tab = new double[m][n];
		
		//riga funzione obiettivo
		tab[0][jVarBirra] = -1; //coeff var birra negato
		
		//righe vincoli quantità (quantità ingrediente + slack ingrediente = quantità disponibile)
		int i = 1;
		for (Entry<String, Double> coppia : ingr) {
			tab[i][0] = ci.getIngrediente(coppia.getKey()).getQuantita(); //termine noto (quantità disponibile)
			tab[i][i] = 1; // coeff var ingrediente
			tab[i][i + ingr.size()] = 1; //coeff var slack
			i++;
		}
		
		//righe vincoli percentuali (percentuale ricetta * quantità birra - quantità ingrediente = 0)
		int j = 1;
		for (Entry<String, Double> coppia : ingr) {
			tab[i][j] = -1; // sottrarre var ingrediente
			tab[i][jVarBirra] = coppia.getValue(); //coeff var birra (percentuale ricetta)
			i++;
			j++;
		}
		
		//riga vincolo birra (quantità birra + slack birra = min capienza)
		final int last = m - 1;
		tab[last][0] = minCapienza(ric); //termine noto (min capienza)
		tab[last][jVarBirra] = 1; // coeff var birra
		tab[last][n - 1] = 1; //coeff var slack birra
		
		return tab;
	}
	
	private double minCapienza(Ricetta ric) throws SQLException {
		return Collections.min(ca.getStrumenti(ca.getNomiStrumenti(ric.getNomeBirra()))).getPortata();
	}
}

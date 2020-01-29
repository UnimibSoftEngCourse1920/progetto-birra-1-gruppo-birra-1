package homebrew.controller;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import homebrew.model.QuantitaRicetta;
import homebrew.model.Ricetta;

/*
 * La classe ricette disponibili permette di consigliare al birraio la ricetta 
 * che, date le ricette inserite nel db, massimizza la quantità di birra producibile.
 */
public class RicetteDisponibili {

	private ControllerRicetta cr;
	private ControllerIngrediente ci;
	private ControllerAttrezzatura ca;
	
	public RicetteDisponibili(ControllerRicetta cr, ControllerIngrediente ci, ControllerAttrezzatura ca) {
		this.cr = cr;
		this.ci = ci;
		this.ca = ca;
	}

	/*
	 * Viene restituito un oggetto di tipo QuantitaRicetta, che indica tutti i campi della ricetta e 
	 * la quantità di birra che essa permette di produrre, relativo alla ricetta che massimizza la quantità di birra producibile 
	 * tra quelle inserite nel database.
	 * Può lanciare SQLException se le query che esegue non dovessero andare a buon fine.
	 */
	public QuantitaRicetta cosaDovreiPreparareOggi() throws SQLException {
		String[] nomiBirre = nomiBirreDisponibili();
		
		if (nomiBirre.length == 0)
			return null;
		
		Ricetta ricettaDaPrep = cr.getRicetta(nomiBirre[0]);
		double quantitaBirra = getQuantita(ricettaDaPrep);
		
		for (int i = 1; i < nomiBirre.length; i++) {
			Ricetta ric = cr.getRicetta(nomiBirre[i]);
			double quantita = getQuantita(ric);
			
			if (quantita > quantitaBirra) {
				ricettaDaPrep = ric;
				quantitaBirra = quantita;
			}
		}
		
		return new QuantitaRicetta(ricettaDaPrep, quantitaBirra);
	}
	
	/*
	 * Restituisce un array di stringhe, contenente tutti i nomi delle birre disponibili.
	 * Può lanciare SQLException se la query non dovesse andare a buon fine.
	 */
	private String[] nomiBirreDisponibili() throws SQLException {
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(sqlNomiBirreDisponibili());
	String[] nomi = new String[rows.size()];
		
		for (int i = 0; i < nomi.length; i++)
			nomi[i] = (String) rows.get(i).get("nomeBirra");
		
		return nomi;
	}
	
	/*
	 * Restituisce la query necessaria per prelevare dal db i nomi delle ricette disponibili 
	 */
	private String sqlNomiBirreDisponibili() {
		return "select nomeBirra "
				+ "from ricetta as ric "
				+ "where not exists("
					+ "select * "
					+ "from ingrediente natural join ricettaIngrediente as ingr "
					+ "where ingr.nomeBirra = ric.nomeBirra AND (bloccato OR quantita = 0))";
	}
	
	/*
	 * Restituisce la quantità di birra prodotta dalla ricetta passata in input.
	 * Può lanciare una SQLException
	 */
	public double getQuantita(Ricetta ricetta) throws SQLException {
		double[][] tab = creaTableau(ricetta);
		new Simplesso(tab).esegui();
		return tab[0][0];
	}
	
	/*
	 * Viene eseguito il simplesso:
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

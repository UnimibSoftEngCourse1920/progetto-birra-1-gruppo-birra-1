package homebrew.controller;

import java.util.Arrays;

/*
 * La classe simplesso permette di eseguire il meotodo del simplesso,
 * al fine di determinare, tra quelle salvate nel db, la ricetta che massimizza
 * la quantità di birra prodotta.
 */
public class Simplesso {
	
	private double[][] tableau;

	public Simplesso(double[][] tableau) {
		this.tableau = tableau;
	}
	
	public void esegui() {
		final int n = tableau[0].length;
		final int m = tableau.length;
		
		int jPivot;
		while ((jPivot = colonnaPivot()) != -1) {
			final int iPivot = rigaPivot(jPivot);
			final double pivot = tableau[iPivot][jPivot];
			
			if (pivot != 1)
				for (int j = 0; j < n; j++)
					tableau[iPivot][j] /= pivot;
			
			for (int i = 0; i < iPivot; i++)
				sottraiMultiploRigaPivot(i, iPivot, jPivot);
			
			for (int i = iPivot + 1; i < m; i++)
				sottraiMultiploRigaPivot(i, iPivot, jPivot);
		}
	}
	
	private void sottraiMultiploRigaPivot(int i, int iPivot, int jPivot) {
		double[] riga = tableau[i];
		double coeff = riga[jPivot];
		
		if (coeff != 0) {
			double[] rigaPivot = tableau[iPivot];
			
			for (int j = 0; j < riga.length; j++)
				riga[j] -= coeff * rigaPivot[j];
		}
	}
	
	private int rigaPivot(int jPivot) {
		double minRapp = Double.POSITIVE_INFINITY;
		int riga = -1;
		
		for (int i = 1; i < tableau.length; i++) {
			double coeff = tableau[i][jPivot];
			
			if (coeff > 0) {
				double rapp = tableau[i][0] / coeff;
				
				if (rapp < minRapp) {
					minRapp = rapp;
					riga = i;
				}
			}
		}
		
		return riga;
	}
	
	private int colonnaPivot() {
		double[] c = tableau[0];
		
		for (int j = 1; j < c.length; j++)
			if (c[j] < 0)
				return j;
		
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(tableau.length * tableau[0].length);
		
		for (int i = 0; i < tableau.length; i++)
			sb.append(Arrays.toString(tableau[i])).append("\n");
		
		return sb.toString();
	}
}

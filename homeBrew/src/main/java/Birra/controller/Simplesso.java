package Birra.controller;

public class Simplesso {
	
	private double[][] tableau;

	public Simplesso(double[][] tableau) {
		this.tableau = tableau;
	}
	
	public void esegui() {
		final int n = tableau[0].length, m = tableau.length;
		
		int jPivot;
		while ((jPivot = colonnaPivot()) != -1) {
			final int iPivot = rigaPivot(jPivot);
			final double pivot = tableau[iPivot][jPivot];
			
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
		double[] rigaPivot = tableau[iPivot];
		
		for (int j = 0; j < rigaPivot.length; j++)
			riga[j] -= coeff * rigaPivot[j];
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
}

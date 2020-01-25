package Birra.controller;

public class Simplesso {
	
	private double[][] tableau;

	public Simplesso(double[][] tableau) {
		this.tableau = tableau;
	}
	
	public void esegui() {
		final int n = tableau[0].length, m = tableau.length;
		
		final int colonnaPivot;
		while ((colonnaPivot = colonnaPivot()) != -1) {
			final int rigaPivot = rigaPivot(colonnaPivot);
			final double pivot = tableau[rigaPivot][colonnaPivot];
			
			for (int j = 0; j < n; j++)
				tableau[rigaPivot][j] /= pivot;
			
			for (int i = 0; i < rigaPivot; i++)
				sottraiMultiploRigaPivot(i, rigaPivot, colonnaPivot);
			
			for (int i = rigaPivot + 1; i < m; i++)
				sottraiMultiploRigaPivot(i, rigaPivot, colonnaPivot);
		}
	}
	
	private void sottraiMultiploRigaPivot(int i, int rigaPivot, int colonnaPivot) {
		double[] ai = tableau[i];
		double coeff = ai[colonnaPivot];
		
		for (int j = 0; j < ai.length; j++)
			ai[j] -= coeff * tableau[rigaPivot][j];
	}
	
	private int rigaPivot(int colonnaPivot) {
		double minRapp = Double.POSITIVE_INFINITY;
		int riga = -1;
		
		for (int i = 1; i < tableau.length; i++) {
			double coeff = tableau[i][colonnaPivot];
			
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

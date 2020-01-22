package Birra.model;

public class Ricetta {
	
	private String nomeBirra; // Nome della birra che permette di produrre la ricetta, identifica univocamente una ricetta
	private double tempo; // Tempo necessario per eseguire la ricetta
	private String procedimento; // Procedimento della ricetta
	private Attrezzatura[] strumenti; // Indica l'attrezzatura necessaria per mettere in atto la ricetta
	private Ingrediente[] ingredienti; // é l'insieme di ingredienti necessari per la ricetta
	private Nota nota; // Nota a cui c'è scritto appunti sulla ricetta di questa birra
	
	public Ricetta(String nomeBirra, double tempo, String procedimento, Attrezzatura[] strumenti,
			Ingrediente[] ingredienti, Nota nota) {
		this.nomeBirra = nomeBirra;
		this.tempo = tempo;
		this.procedimento = procedimento;
		this.strumenti = strumenti;
		this.ingredienti = ingredienti;
		this.nota = nota;
	}
	
	public String getNomeBirra() {
		return nomeBirra;
	}

	public double getTempo() {
		return tempo;
	}

	public String getProcedimento() {
		return procedimento;
	}

	public Attrezzatura[] getStrumenti() {
		return strumenti.clone();
	}

	public Ingrediente[] getIngredienti() {
		return ingredienti.clone();
	}
	
	public Nota getNota() {
		return nota;
	}
}

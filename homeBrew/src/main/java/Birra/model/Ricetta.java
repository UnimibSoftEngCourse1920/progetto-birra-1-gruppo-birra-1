package Birra.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Ricetta {

	private String nomeBirra; // Nome della birra che permette di produrre la ricetta, identifica univocamente
								// una ricetta
	private double tempo; // Tempo necessario per eseguire la ricetta
	private String procedimento; // Procedimento della ricetta
	private Attrezzatura[] strumenti; // Indica l'attrezzatura necessaria per mettere in atto la ricetta
	private HashMap<Ingrediente, Double> percentualiIngredienti; // é l'insieme di ingredienti necessari per la ricetta
																	// con le relative percentuali
	private Nota nota; // Nota a cui c'è scritto appunti sulla ricetta di questa birra

	public Ricetta(String nomeBirra, double tempo, String procedimento, Attrezzatura[] strumenti,
			Map<Ingrediente, Double> percentualiIngredienti, Nota nota) {
		this.nomeBirra = nomeBirra;
		this.tempo = tempo;
		this.procedimento = procedimento;
		this.strumenti = strumenti;
		this.percentualiIngredienti = new HashMap<>(percentualiIngredienti);
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

	public HashMap<Ingrediente, Double> getIngredienti() {
		return new HashMap<>(percentualiIngredienti);
	}

	public Nota getNota() {
		return nota;
	}

	@Override
	public String toString() {
		return "[nomeBirra=" + nomeBirra + ", tempo=" + tempo + ", procedimento=" + procedimento + ", strumenti="
				+ Arrays.toString(strumenti) + ", ingredienti=" + percentualiIngredienti + ", nota=" + nota + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + percentualiIngredienti.hashCode();
		result = prime * result + nomeBirra.hashCode();
		result = prime * result + nota.hashCode();
		result = prime * result + procedimento.hashCode();
		result = prime * result + Arrays.hashCode(strumenti);
		result = prime * result + Double.hashCode(tempo);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj instanceof Ricetta) {
			Ricetta ric = (Ricetta) obj;
			return nomeBirra.equals(ric.nomeBirra) && tempo == ric.tempo && procedimento.equals(ric.procedimento)
					&& Arrays.equals(strumenti, ric.strumenti)
					&& percentualiIngredienti.equals(ric.percentualiIngredienti) && nota.equals(ric.nota);
		}

		return false;
	}

}
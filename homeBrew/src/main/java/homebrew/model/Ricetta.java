package homebrew.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Ricetta {

	private String nomeBirra; // Nome della birra che permette di produrre la ricetta, identifica univocamente
								// una ricetta
	private double tempo; // Tempo necessario per eseguire la ricetta
	private String procedimento; // Procedimento della ricetta
	private HashSet<String> nomiStrumenti; // Indica l'attrezzatura necessaria per mettere in atto la ricetta
	private HashMap<String, Double> ingredienti; // é l'insieme di ingredienti necessari per la ricetta
															// con le relative percentuali
	private Nota nota; // Nota a cui c'è scritto appunti sulla ricetta di questa birra

	public Ricetta(String nomeBirra, double tempo, String procedimento, Set<String> strumenti,
			Map<String, Double> ingredienti, Nota nota) {
		this.nomeBirra = nomeBirra;
		this.tempo = tempo;
		this.procedimento = procedimento;
		this.nomiStrumenti = new HashSet<>(strumenti);
		this.ingredienti = new HashMap<>(ingredienti);
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

	public HashSet<String> getStrumenti() {
		return new HashSet<>(nomiStrumenti);
	}

	public HashMap<String, Double> getIngredienti() {
		return new HashMap<>(ingredienti);
	}

	public Nota getNota() {
		return nota;
	}

	@Override
	public String toString() {
		return "[nomeBirra=" + nomeBirra + ", tempo=" + tempo + ", procedimento=" + procedimento + ", strumenti="
				+ nomiStrumenti + ", ingredienti=" + ingredienti + ", nota=" + nota + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ingredienti.hashCode();
		result = prime * result + nomeBirra.hashCode();
		result = prime * result + nota.hashCode();
		result = prime * result + procedimento.hashCode();
		result = prime * result + nomiStrumenti.hashCode();
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
					&& nomiStrumenti.equals(ric.nomiStrumenti)
					&& ingredienti.equals(ric.ingredienti) && nota.equals(ric.nota);
		}

		return false;
	}

}
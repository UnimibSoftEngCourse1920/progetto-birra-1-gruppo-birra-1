package homebrew.model;

import java.util.HashMap;
import java.util.Map.Entry;

public class QuantitaRicetta {
	
	private Ricetta ricetta;
	private HashMap<String, Double> quantitaIngredienti; //Per ogni ingrediente presente nella ricetta, tengo traccia della quantità
	
	public QuantitaRicetta(Ricetta ricetta, double quantitaBirra) {
		this.ricetta = ricetta;
		
		HashMap<String, Double> ingr = ricetta.getIngredienti();
		quantitaIngredienti = new HashMap<>(ingr.size());
		
		for (Entry<String, Double> coppia : ingr.entrySet())
			quantitaIngredienti.put(coppia.getKey(), coppia.getValue() * quantitaBirra);
	}
	
	public Ricetta getRicetta() {
		return ricetta;
	}
	
	public HashMap<String, Double> getQuantitaIngredienti() {
		return new HashMap<>(quantitaIngredienti);
	}
	
	@Override
	public String toString() {
		return "[ricetta=" + ricetta + ", quantitaIngredienti=" + quantitaIngredienti + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + quantitaIngredienti.hashCode();
		result = prime * result + ricetta.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj instanceof QuantitaRicetta) {
			QuantitaRicetta qr = (QuantitaRicetta) obj;
			return ricetta.equals(qr.ricetta) && quantitaIngredienti.equals(qr.quantitaIngredienti);
		}
		
		return false;
	}
	
}
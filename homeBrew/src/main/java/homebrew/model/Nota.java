package homebrew.model;

public class Nota {
	
	private String titolo;		//Titolo della nota
	private String descrizione; //Testo della nota
	
	//viene creato il costruttore di Nota
	public Nota(String titolo, String descrizione) {
		this.titolo = titolo;
		this.descrizione = descrizione;
	}
	//Vengono aggiunti i metodi per ottenere i vari valori delle variabili provate
	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
	@Override
	public String toString() {
		return "[titolo=" + titolo + ", descrizione=" + descrizione + "]";
	}
	
	//Si definiscono i metodi per confrontare gli oggetti di tipo Nota
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + descrizione.hashCode();
		result = prime * result + titolo.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj instanceof Nota) {
			Nota nota = (Nota) obj;
			return titolo.equals(nota.titolo) && descrizione.equals(nota.descrizione);
		}
		
		return false;
	}
	
}
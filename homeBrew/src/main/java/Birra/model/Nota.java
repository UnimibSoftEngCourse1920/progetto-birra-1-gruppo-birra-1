package Birra.model;

public class Nota {
	
	private String titolo, descrizione;
	
	public Nota(String titolo, String descrizione) {
		this.titolo = titolo;
		this.descrizione = descrizione;
	}

	public String getTitolo() {
		return titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}
	
}
package homeBrew.domain;

public class Ricetta 
{
	private String nomeBirra; //Nome della birra che permette di produrre la ricetta, identifica univocamente una ricetta
	private double tempo; //Tempo necessario per eseguire la ricetta
	private String procedimento; //Procedimento della ricetta
	private Attrezzatura[] strumenti; //Indica l'attrezzatura necessaria per mettere in atto la ricetta
	private Ingrediente[] ingredienti; //é l'insieme di ingredienti necessari per la ricetta
	public Ricetta(String nomeBirra, double tempo, String procedimento, Ingrediente[] ingredienti, Attrezzatura[] strumenti )
	{
		this.nomeBirra=nomeBirra;
		this.tempo=tempo;
		this.procedimento=procedimento;
		this.strumenti=strumenti;
		this.ingredienti=ingredienti;
	}
	public String getNomeBirra()
	{
		return this.nomeBirra;
	}
	public double getTempo() {
		return tempo;
	}
	public String procedimento ()
	{
		return procedimento;
	}
	public Attrezzatura[] getStrumenti()
	{
		return this.strumenti;
	}
	public Ingrediente[] getIngredienti()
	{
		return this.ingredienti;
	}
}

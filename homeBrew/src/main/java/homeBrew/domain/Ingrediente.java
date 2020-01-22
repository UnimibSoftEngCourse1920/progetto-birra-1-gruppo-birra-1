package homeBrew.domain;

public class Ingrediente 
{
	private String nome; //nome dell'ingrediente		
	private double quantita; //Quantita (assoluta) dell'ingrediente
	private boolean bloccato; //se true significa che l'ingrediente non può essere usato dal birraio
	private String tipo; //tipo di ingrediente (malto/luppoli/zucchero/acqua/lievito)
	public Ingrediente(String nome, double quantita, boolean bloccato, String tipo)
	{
		this.nome=nome;
		this.quantita=quantita;
		this.bloccato=bloccato;
		this.tipo=tipo;
	}
	public String getNome()
	{
		return this.nome;
	}
	public double getQuantita()
	{
		return this.quantita;
	}
	public boolean isBloccato()
	{
		return this.bloccato;
	}
	public String getTipo()
	{
		return this.tipo;
	}
}

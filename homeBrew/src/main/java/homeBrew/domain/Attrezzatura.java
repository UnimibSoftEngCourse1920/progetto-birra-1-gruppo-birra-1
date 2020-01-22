package homeBrew.domain;

public class Attrezzatura 
{
	private String nome; //Nome dello strumento 
	private double portata; //Capacità dello strumento
	private String tipo; //Tipo dello strumento (può essere di tre tipi tubo, fementatore, cisterna.
	public Attrezzatura(String nome, double portata, String tipo)
	{
		this.nome=nome;
		this.portata=portata;
		this.tipo=tipo;
	}
	public String getNome()
	{
		return this.nome;
	}
	public double getPortata()
	{
		return this.portata;
	}
	public String tipo()
	{
		return this.tipo;
	}
}

package Birra.model;

public class Attrezzatura {
	
	private String nome; // Nome dello strumento
	private double portata; // Capacità dello strumento
	private TipoAttrezzatura tipo; // Tipo dello strumento (può essere di tre tipi: tubo, fementatore, cisterna)

	public Attrezzatura(String nome, double portata, TipoAttrezzatura tipo) {
		this.nome = nome;
		this.portata = portata;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public double getPortata() {
		return portata;
	}

	public TipoAttrezzatura tipo() {
		return tipo;
	}
}

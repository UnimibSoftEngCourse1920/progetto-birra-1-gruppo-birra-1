package Birra.model;

public class Attrezzatura {

	private String nome; // Nome dello strumento
	private double portata; // Capacità dello strumento
	private TipoAttrezzatura tipo; // Tipo dello strumento (può essere di tre tipi: tubo, fementatore, cisterna)

	//definisco il costrutture di Attrezzatura
	public Attrezzatura(String nome, double portata, TipoAttrezzatura tipo) { 
		this.nome = nome;
		this.portata = portata;
		this.tipo = tipo;
	}
	
	//definisco i metodi per ottenere le informazioni presenti nelle variabili private
	public String getNome() {
		return nome;
	}

	public double getPortata() {
		return portata;
	}

	public TipoAttrezzatura tipo() {
		return tipo;
	}

	@Override
	public String toString() {
		return "[nome=" + nome + ", portata=" + portata + ", tipo=" + tipo + "]";
	}
	
	//definisco i metodi per la comparazione con altri oggetti simili
	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj || (obj instanceof Attrezzatura && nome.equals(((Attrezzatura) obj).nome));
	}

}

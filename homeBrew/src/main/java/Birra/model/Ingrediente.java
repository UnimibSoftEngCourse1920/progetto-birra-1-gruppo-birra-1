package Birra.model;

public class Ingrediente {
	
	private String nome; // nome dell'ingrediente
	private double quantita; // Quantita (assoluta) dell'ingrediente
	private boolean bloccato; // se true significa che l'ingrediente non può essere usato dal birraio
	private TipoIngrediente tipo; // tipo di ingrediente (malto/luppoli/zucchero/acqua/lievito)
	
	public Ingrediente(String nome, double quantita, boolean bloccato, TipoIngrediente tipo) {
		this.nome = nome;
		this.quantita = quantita;
		this.bloccato = bloccato;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public double getQuantita() {
		return quantita;
	}

	public boolean isBloccato() {
		return bloccato;
	}

	public TipoIngrediente getTipo() {
		return tipo;
	}
}
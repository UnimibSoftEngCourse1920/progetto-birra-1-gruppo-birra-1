package homebrew.model;

public class Ingrediente {

	private String nome; // nome dell'ingrediente
	private double quantita; // Quantita disponibile dell'ingrediente
	private boolean bloccato; // se true significa che l'ingrediente non può essere usato dal birraio
	private TipoIngrediente tipo; // tipo di ingrediente (malto/luppoli/zucchero/acqua/lievito)

	//definisco il costrutture di Ingredienti
	public Ingrediente(String nome, double quantita, boolean bloccato, TipoIngrediente tipo) {
		this.nome = nome;
		this.quantita = quantita;
		this.bloccato = bloccato;
		this.tipo = tipo;
	}
	//definisco i metodi per ottenere le informazioni delle variabili private 
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

	@Override
	public String toString() {
		return "[nome=" + nome + ", quantita=" + quantita + ", bloccato=" + bloccato + ", tipo=" + tipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Boolean.hashCode(bloccato);
		result = prime * result + nome.hashCode();
		result = prime * result + Double.hashCode(quantita);
		result = prime * result + tipo.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj instanceof Ingrediente) {
			Ingrediente ingr = (Ingrediente) obj;
			return nome.equals(ingr.nome) && quantita == ingr.quantita && bloccato == ingr.bloccato
					&& tipo == ingr.tipo;
		}

		return false;
	}

}
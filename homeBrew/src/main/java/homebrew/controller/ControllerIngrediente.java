package homebrew.controller;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import homebrew.model.Ingrediente;
import homebrew.model.TipoIngrediente;

/*
 * La classe controller ingrediente si occupa della gestione degli ingredienti memorizzati nel 
 * database
 */
public class ControllerIngrediente {

	/*
	 * Viene eliminata la ricetta identificata dal parametro nomeBirra dalla tabella
	 * ricettaIngrediente.
	 */
	public void disassociaRicetta(String nomeBirra) throws SQLException {
		String sql = "delete from ricettaIngrediente where nomeBirra = '" + nomeBirra + "'";
		DBUtils.update(sql);
	}

	/*
	 * Per ogni ingrediente contenuto nella HashMap ingredienti viene associato alla
	 * ricetta identificata dal parametro nomeBirra
	 */
	public void associaRicetta(String nomeBirra, HashMap<String, Double> ingredienti) throws SQLException {
		for (Entry<String, Double> coppia : ingredienti.entrySet())
			associaRicetta(nomeBirra, coppia.getKey(), coppia.getValue());
	}

	/*
	 * Vengono inseriti la ricetta (identificata dal parametro nomeBirra) e
	 * l'ingrediente identificato dal parametro nomeIngrediente nella tabella
	 * ricettaIngrediente, in modo tale da associare alla ricetta l'ingrediente
	 * necessario.
	 */
	public void associaRicetta(String nomeBirra, String nomeIngrediente, double percentuale) throws SQLException {
		String sql = "insert into ricettaIngrediente (nomeBirra, nomeIngrediente, percentuale) values ('" + nomeBirra
				+ "', '" + nomeIngrediente + "', " + percentuale + ")";
		DBUtils.update(sql);
	}

	/*
	 * Viene eliminato l'ingrediente (identificato dal parametro nomeingrediente)
	 * dalla tabella ingrediente
	 */
	public void eliminaIngrediente(String nomeIngrediente) throws SQLException {
		String sql = "delete from ingrediente where nomeIngrediente = '" + nomeIngrediente + "'";
		DBUtils.update(sql);
	}

	/*
	 * Viene modificato l'ingrediente inserito nel database
	 */
	public void modificaIngrediente(Ingrediente ingr) throws SQLException {
		String sql = "update ingrediente set quantita = " + ingr.getQuantita() + ", tipo = '" + ingr.getTipo()
				+ "', bloccato = " + ingr.isBloccato() + " where nomeIngrediente = '" + ingr.getNome() + "'";
		DBUtils.update(sql);
	}

	/*
	 * Dato un insieme di ingredienti, vengono aggiunti al database
	 */
	public void aggiungiIngredienti(Set<Ingrediente> ingredienti) throws SQLException {
		for (Ingrediente ingr : ingredienti)
			aggiungiIngrediente(ingr);
	}

	/*
	 * Viene aggiunto l'ingrediente nel database
	 */
	public void aggiungiIngrediente(Ingrediente ingr) throws SQLException {
		String sql = "insert ignore into ingrediente (nomeIngrediente, quantita, tipo, bloccato) values ('"
				+ ingr.getNome() + "', " + ingr.getQuantita() + ", '" + ingr.getTipo() + "', " + ingr.isBloccato()
				+ ")";
		DBUtils.update(sql);
	}

	/*
	 * Vengono prelevati tuttti gli ingredienti associati alla ricetta identificata
	 * dal parametro nomeBirra
	 */
	public HashMap<String, Double> getQuantitaIngredienti(String nomeBirra) throws SQLException {
		String queryIngredienti = "select nomeIngrediente, percentuale from ricettaIngrediente where nomeBirra = '"
				+ nomeBirra + "'";
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(queryIngredienti);
		HashMap<String, Double> ingredienti = new HashMap<>(rows.size());

		for (HashMap<String, Object> row : rows)
			ingredienti.put((String) row.get("nomeIngrediente"), (double) row.get("percentuale"));

		return ingredienti;
	}

	/*
	 * Viene prelevato dal database l'ingrediente identificato dal suo nome
	 */
	public Ingrediente getIngrediente(String nomeIngrediente) throws SQLException {
		String query = "select * from ingrediente where nomeIngrediente = '" + nomeIngrediente + "'";
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(query);
		return rows.isEmpty() ? null : parseIngrediente(rows.get(0));
	}

	/*
	 * Dato il risultato della query per prelevare un ingrediente dal database viene
	 * restituito un ogetto di tipo Ingrediente
	 */
	private Ingrediente parseIngrediente(HashMap<String, Object> row) {
		return new Ingrediente((String) row.get("nomeIngrediente"), (double) row.get("quantita"),
				(boolean) row.get("bloccato"), TipoIngrediente.valueOf((String) row.get("tipo")));
	}
}

package Birra.controller;

import java.util.*;

import Birra.model.Ingrediente;
import Birra.model.TipoIngrediente;

public class ControllerIngrediente {
	
	public void disassociaRicetta(String nomeBirra) {
		String sql = "delete from ricettaIngrediente where nomeBirra = '" + nomeBirra + "'";
		DBUtils.update(sql);
	}
	
	public void associaRicetta(String nomeBirra, Ingrediente[] ingredienti) {
		for (Ingrediente ingr : ingredienti)
			associaRicetta(nomeBirra, ingr.getNome());
	}
	
	public void associaRicetta(String nomeBirra, String nomeIngrediente) {
		String sql = "insert ignore into ricettaIngrediente (nomeBirra, nomeIngrediente) values ('" + nomeBirra + "',"
				+ nomeIngrediente + "')";
		DBUtils.update(sql);
	}
	
	public void eliminaIngrediente(String nomeIngrediente) {
		String sql = "delete from ingrediente where nomeIngrediente = '" + nomeIngrediente + "'";
		DBUtils.update(sql);
	}
	
	public void modificaIngrediente(Ingrediente ingr) {
		eliminaIngrediente(ingr.getNome());
		aggiungiIngrediente(ingr);
	}
	
	public void aggiungiIngredienti(Ingrediente[] ingredienti) {
		for (Ingrediente ingr : ingredienti)
			aggiungiIngrediente(ingr);
	}
	
	public void aggiungiIngrediente(Ingrediente ingr) {
		String sql = "insert ignore into ingrediente (nomeIngrediente, quantita, tipo, bloccato) values ('"
				+ ingr.getNome() + "'," + ingr.getQuantita() + "'," + ingr.getTipo() + "'," + ingr.isBloccato() + "')";
		DBUtils.update(sql);
	}

	public Ingrediente[] getIngredienti(String nomeBirra) {
		String queryIngredienti = "select ingrediente.* from ingrediente join ricettaIngrediente where nomeBirra = "
				+ "'" + nomeBirra + "'";
		ArrayList<HashMap<String, String>> rows = DBUtils.getRows(queryIngredienti);
		ArrayList<Ingrediente> ingredienti = new ArrayList<>();
	
		for (HashMap<String, String> row : rows)
			ingredienti.add(parseIngrediente(row));
	
		return ingredienti.toArray(new Ingrediente[ingredienti.size()]);
	}

	public Ingrediente getIngrediente(String nomeIngrediente) {
		String query = "select * from ingrediente where nomeIngrediente = " + "'" + nomeIngrediente + "'";
		return parseIngrediente(DBUtils.getRows(query).get(0));
	}

	public Ingrediente parseIngrediente(HashMap<String, String> row) {
		return new Ingrediente(row.get("nomeIngrediente"), Double.parseDouble(row.get("quantita")),
				Boolean.parseBoolean(row.get("bloccato")), TipoIngrediente.valueOf(row.get("tipo")));
	}
}
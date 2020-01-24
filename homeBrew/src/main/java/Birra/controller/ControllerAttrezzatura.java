package Birra.controller;

import java.util.ArrayList;
import java.util.HashMap;

import Birra.model.Attrezzatura;
import Birra.model.TipoAttrezzatura;

public class ControllerAttrezzatura {

	public void disassociaRicetta(String nomeBirra) {
		String sql = "delete from ricettaAttrezzatura where nomeBirra = '" + nomeBirra + "'";
		DBUtils.update(sql);
	}

	public void associaRicetta(String nomeBirra, Attrezzatura[] strumenti) {
		for (Attrezzatura str : strumenti)
			associaRicetta(nomeBirra, str.getNome());
	}

	public void associaRicetta(String nomeBirra, String nomeAttrezzatura) {
		String sql = "insert ignore into ricettaAttrezzatura (nomeBirra, nomeAttrezzatura) values ('" + nomeBirra
				+ "', '" + nomeAttrezzatura + "')";
		DBUtils.update(sql);
	}

	public Attrezzatura[] getStrumenti(String nomeBirra) {
		String queryStrumenti = "select attrezzatura.* from attrezzatura join ricettaAttrezzatura where nomeBirra = '"
				+ nomeBirra + "'";
		ArrayList<HashMap<String, String>> rows = DBUtils.getRows(queryStrumenti);
		Attrezzatura[] strumenti = new Attrezzatura[rows.size()];
		
		for (int i = 0; i < strumenti.length; i++)
			strumenti[i] = parseAttrezzatura(rows.get(i));
		
		return strumenti;
	}

	public Attrezzatura parseAttrezzatura(HashMap<String, String> row) {
		return new Attrezzatura(row.get("nomeAttrezzatura"), Double.parseDouble(row.get("portata")),
				TipoAttrezzatura.valueOf(row.get("tipo")));
	}
}

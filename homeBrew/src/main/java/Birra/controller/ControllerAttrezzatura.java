package Birra.controller;

import java.util.ArrayList;
import java.util.HashMap;

import Birra.model.Attrezzatura;
import Birra.model.TipoAttrezzatura;
/**
 * La classe ControllerAttrezzatura si occupa della gestione delle attrezzature memorizzate nel database
 * 
 *
 */
public class ControllerAttrezzatura {

	/*
	 * Il metodo disassociRicetta elimina dalla tabella ricettaAttrezzatura la ricetta identificata dal parametro nomeBirra
	 */
	public void disassociaRicetta(String nomeBirra) {
		String sql = "delete from ricettaAttrezzatura where nomeBirra = '" + nomeBirra + "'";
		DBUtils.update(sql);
	}
	
	/*
	 * Viene associato ogni strumento contenuto nel vettore strumenti alla ricetta identificata dal parametro nomeBirra
	 */
	public void associaRicetta(String nomeBirra, Attrezzatura[] strumenti) {
		for (Attrezzatura str : strumenti)
			associaRicetta(nomeBirra, str.getNome());
	}
	
	/*
	 * Viene associata la ricetta (identificata dal parametro nomeBirra) allo strumento (identificato dal 
	 * parametro nomeAttrezzatura), inserendo (se non già presente) nella tabella ricettaAttrezzatura 
	 * l'identificativo della ricetta e quello dello strumento
	 */
	public void associaRicetta(String nomeBirra, String nomeAttrezzatura) {
		String sql = "insert into ricettaAttrezzatura (nomeBirra, nomeAttrezzatura) values ('" + nomeBirra
				+ "', '" + nomeAttrezzatura + "')";
		DBUtils.update(sql);
	}
	
	/*
	 * Vengono prelevati dal database tutti gli strumenti associati alla ricetta identificata dal parametro nomeBirra
	 */
	public Attrezzatura[] getStrumenti(String nomeBirra) {
		String queryStrumenti = "select attrezzatura.* from attrezzatura natural join ricettaAttrezzatura where nomeBirra = '"
				+ nomeBirra + "'";
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(queryStrumenti);
		Attrezzatura[] strumenti = new Attrezzatura[rows.size()];
		
		for (int i = 0; i < strumenti.length; i++)
			strumenti[i] = parseAttrezzatura(rows.get(i));
		
		return strumenti;
	}
	
	/*
	 * Viene creato un oggetto di tipo attrezzatura a partire dal risultato della query 
	 * che preleva gli strumenti memorizzati nel database.
	 */
	private Attrezzatura parseAttrezzatura(HashMap<String, Object> row) {
		return new Attrezzatura((String) row.get("nomeAttrezzatura"), (double) row.get("portata"),
				TipoAttrezzatura.valueOf(row.get("tipo").toString()));
	}
}

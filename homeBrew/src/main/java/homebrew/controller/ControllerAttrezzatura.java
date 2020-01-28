package Birra.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Birra.model.Attrezzatura;
import Birra.model.TipoAttrezzatura;

/**
 * La classe ControllerAttrezzatura si occupa della gestione delle attrezzature
 * memorizzate nel database
 * 
 *
 */
public class ControllerAttrezzatura {

	/*
	 * Il metodo disassociRicetta elimina dalla tabella ricettaAttrezzatura la
	 * ricetta identificata dal parametro nomeBirra
	 */
	public void disassociaRicetta(String nomeBirra) throws SQLException {
		String sql = "delete from ricettaAttrezzatura where nomeBirra = '" + nomeBirra + "'";
		DBUtils.update(sql);
	}

	/*
	 * Viene associato ogni strumento contenuto nel vettore strumenti alla ricetta
	 * identificata dal parametro nomeBirra
	 */
	public void associaRicetta(String nomeBirra, Set<String> nomiStrumenti) throws SQLException {
		for (String nome : nomiStrumenti)
			associaRicetta(nomeBirra, nome);
	}

	/*
	 * Viene associata la ricetta (identificata dal parametro nomeBirra) allo
	 * strumento (identificato dal parametro nomeAttrezzatura), inserendo (se non
	 * già presente) nella tabella ricettaAttrezzatura l'identificativo della
	 * ricetta e quello dello strumento
	 */
	public void associaRicetta(String nomeBirra, String nomeAttrezzatura) throws SQLException {
		String sql = "insert into ricettaAttrezzatura (nomeBirra, nomeAttrezzatura) values ('" + nomeBirra + "', '"
				+ nomeAttrezzatura + "')";
		DBUtils.update(sql);
	}

	/*
	 * Vengono prelevati dal database tutti gli strumenti associati alla ricetta
	 * identificata dal parametro nomeBirra
	 */
	public HashSet<String> getNomiStrumenti(String nomeBirra) throws SQLException {
		String sql = "select nomeAttrezzatura from ricettaAttrezzatura where nomeBirra = '" + nomeBirra + "'";
		return parseNomiStrumenti(DBUtils.getRows(sql));
	}

	public HashSet<String> getNomiStrumenti() throws SQLException {
		String sql = "select nomeAttrezzatura from attrezzatura";
		return parseNomiStrumenti(DBUtils.getRows(sql));
	}

	private HashSet<String> parseNomiStrumenti(ArrayList<HashMap<String, Object>> rows) {
		HashSet<String> nomi = new HashSet<>(rows.size());

		for (int i = 0; i < rows.size(); i++)
			nomi.add((String) rows.get(i).get("nomeAttrezzatura"));

		return nomi;
	}

	public HashSet<Attrezzatura> getStrumenti(Set<String> nomi) throws SQLException {
		HashSet<Attrezzatura> strum = new HashSet<>();

		for (String nome : nomi)
			strum.add(getStrumento(nome));

		return strum;
	}

	public Attrezzatura getStrumento(String nome) throws SQLException {
		String sql = "select * from attrezzatura where nomeAttrezzatura = '" + nome +"';";
		ArrayList<HashMap<String, Object>> rows = DBUtils.getRows(sql);
		return rows.isEmpty() ? null : parseAttrezzatura(rows.get(0));
	}

	/*
	 * Viene creato un oggetto di tipo attrezzatura a partire dal risultato della
	 * query che preleva gli strumenti memorizzati nel database.
	 */
	private Attrezzatura parseAttrezzatura(HashMap<String, Object> row) {
		return new Attrezzatura((String) row.get("nomeAttrezzatura"), (double) row.get("portata"),
				TipoAttrezzatura.valueOf((String) row.get("tipo")));
	}
}

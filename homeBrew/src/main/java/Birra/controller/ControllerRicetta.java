package Birra.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Birra.model.*;

public class ControllerRicetta 
{
	public boolean aggiungiRicetta(Ricetta ricetta)
	{
		Statement st=null;
		String queryInserisciRicetta= getQueryInserisciRicetta(ricetta); //Creo la query per l'inserimento della ricetta
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/homeBrew", "root",getPsw()); //Connessione al database
             Statement preparedStatement =  conn.prepareStatement(queryInserisciRicetta)) {
        	st = conn.createStatement();
        	st.executeUpdate(queryInserisciRicetta); //Viene eseguita la query
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
        }finally {
        	if(st!=null)
				try {
					st.close();
				} catch (SQLException e) {
					System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
				}
        	System.out.println("Closing database connection");
        }
        return true;
	}
	/*
	 * Metodo che restituisce la passowrd del database, è più sicuro rispetto a scrivere la 
	 * password direttamente nel metodo getConnection di DriverManager
	 */
	private String getPsw()
	{
		return "root";
	}
	/*
	 * Metodo che crea la query 
	 */
	private String getQueryInserisciRicetta(Ricetta ricetta)
	{
		String query="";
		Nota nota=ricetta.getNota();
		if(nota!=null)
		{
			query = "insert into ricetta (nomeBirra, tempo, procedimento, titoloNota, descrizioneNota) values ('" +ricetta.getNomeBirra() +"',"+ricetta.getTempo()
			+",'"+ricetta.getProcedimento()+"','"+nota.getTitolo()+"','"+nota.getDescrizione()+"');";
		}
		else
		{
			query="insert into ricetta (nomeBirra, tempo, procedimento) values ('"+ricetta.getNomeBirra() +"',"+ricetta.getTempo()
			+",'"+ricetta.getProcedimento()+"');";
		}
		return query;
	}
}

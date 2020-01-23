package Birra.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Birra.model.*;

public class ControllerRicetta 
{
	/*
	 * Aggiungo una ricetta nel db
	 */
	public boolean aggiungiRicetta(Ricetta ricetta)
	{
		Statement st=null;
		String queryInserisciRicetta= getQueryInserisciRicetta(ricetta); //Creo la query per l'inserimento della ricetta
		
		System.out.println(queryInserisciRicetta);
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
	 * Questo metodo dato il nome della birra che una ricetta permette di produrre, restituisce una 
	 * stringa contenente le informazioni della ricetta come nomeBirra, tempo, procedimento, il titolo di eventauli note
	 * e il testo di eventauli note 
	 * restituisce null se la ricetta non è salvat nel database
	 */
	public String getRicetta(String ricetta) 
	{
		ResultSet rs=null;
		String result = null;
		String queryGetRicetta =  getQueryGetRicetta(ricetta);
		 try (Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://127.0.0.1:3306/homeBrew", "root",getPsw()); //Connessione al database
	            Statement preparedStatement =  conn.prepareStatement(queryGetRicetta)) {
			 	rs = preparedStatement.executeQuery(queryGetRicetta); //Eseguo la query
	        	while (rs.next())
	            {
	        		result = "NomeBirra: " + rs.getString("nomeBirra")+" Tempo: "+rs.getString("tempo")+
	        				" Procedimento: "+rs.getString("procedimento")+" titoloNota: "+rs.getString("titoloNota")+" descrizioneNota: "+
	        				rs.getString("descrizioneNota");
	            }
	        } catch (SQLException e) {
	            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            System.out.println(e);
	        }finally {
	        	if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
					}
				}
	        	System.out.println("Closing database connection");
		 return result;
	}
	/*
	 * Questo metodo restituisce le info relative ad una nota, data in input la ricetta ad essa associata
	 * restituisce null se la ricetta non è stata salvata nel db
	 */
	public String getNota(String ricetta)
	{
		ResultSet rs=null;
		String result = null;
		String queryGetNota =  getQueryGetNota(ricetta);
		 try (Connection conn = DriverManager.getConnection(
	                "jdbc:mysql://127.0.0.1:3306/homeBrew", "root",getPsw()); //Connessione al database
	             Statement preparedStatement =  conn.prepareStatement(queryGetNota)) {
			 	 rs = preparedStatement.executeQuery(queryGetNota); //Eseguo la query
	        	while (rs.next())
	            {
	        		result = "titoloNota: " + rs.getString("titoloNota")+" descrizioneNota: "+
	        				rs.getString("descrizioneNota");
	            }
	        } catch (SQLException e) {
	            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	        } catch (Exception e) {
	            System.out.println(e);
	        }finally {
	        	if(rs!=null)
					try {
						rs.close();
					} catch (SQLException e) {
						System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
					}
	        	System.out.println("Closing database connection");
	        }
		 return result;
	}
	/*
	 * Aggiungo la nota ricevuta in input alla ricetta ricevuta anch'essa in input
	 */
	public boolean aggiungiNota(Nota nota, Ricetta ricetta)
	{
		Statement st=null;
		String queryAggiungiNota= getQueryAggiungiNota(nota, ricetta); //Creo la query per l'inserimento della nota
		try (Connection conn = DriverManager.getConnection(
				"jdbc:mysql://127.0.0.1:3306/homeBrew", "root",getPsw()); //Connessione al database
			Statement preparedStatement =  conn.prepareStatement(queryAggiungiNota)) {
				st = conn.createStatement();
				st.executeUpdate(queryAggiungiNota); //Viene eseguita la query
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
	 * Metodo che restituisce la query necessaria per aggiungere una nota
	 */
	private String getQueryAggiungiNota(Nota nota, Ricetta ricetta)
	{
		String query = "";
		query = "update ricetta set titoloNota = '" + nota.getTitolo()+ "', descrizioneNota = '"+
		nota.getDescrizione()+ "' where nomeBirra = '"+ricetta.getNomeBirra()+"';";
		return query;
	}
	private String getQueryGetNota(String ricetta)
	{
		String query = "";
		query = "select ricetta.titoloNota, ricetta.descrizioneNota from ricetta where nomeBirra = " + "'" + ricetta + "';";
		return query;
	}
	/*
	 * Meotodo che restituisce la query necessaria per prelevare la 
	 * ricetta dal db, dato l'attributo nomeBirra.
	 */
	private String getQueryGetRicetta(String ricetta)
	{
		String query = "";
		query = "select * from ricetta where nomeBirra = " + "'" + ricetta + "';";
		return query;
	}
	/*
	 * Metodo che crea le query necessarie per inserire la ricetta e l'attrezzatura nel db 
	 */
	private String getQueryInserisciRicetta(Ricetta ricetta)
	{
		String query="";
		Nota nota=ricetta.getNota();
		//Attrezzatura[] strumenti=ricetta.getStrumenti();
		if(nota!=null)
		{
			query = "insert ignore into ricetta (nomeBirra, tempo, procedimento, titoloNota, descrizioneNota) values ('" +ricetta.getNomeBirra() +"',"+ricetta.getTempo()
			+",'"+ricetta.getProcedimento()+"','"+nota.getTitolo()+"','"+nota.getDescrizione()+"');";
		}
		if (nota==null)
		{
			query="insert ignore into ricetta (nomeBirra, tempo, procedimento) values ('"+ricetta.getNomeBirra() +"',"+ricetta.getTempo()
			+",'"+ricetta.getProcedimento()+"');";
		}
		return query;
	}
}

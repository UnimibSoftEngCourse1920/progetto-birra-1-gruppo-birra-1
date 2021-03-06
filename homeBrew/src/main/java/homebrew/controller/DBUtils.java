package homebrew.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/*
 * La classe DBUtils permette di eseguire le query necessarie per l'interazione con 
 * il database
 */
public class DBUtils {

	/*
	 * Costruttore provato, utile per nascondere quello implicito
	 */
	private DBUtils ()
	{
	}
	
	/*
	 * Viene eseguita una query di aggiornamento dati del db
	 */
	public static void update(String sql) throws SQLException {
		try (Connection conn = dbConnection(); Statement st = conn.createStatement()) {
			st.executeUpdate(sql); // Viene eseguito l'update
		} catch (SQLException e) {
			throw e;
		}

		printClosingConnection();
	}

	/*
	 * Viene eseguita una query di tipo select
	 */
	public static ArrayList<HashMap<String, Object>> getRows(String query) throws SQLException {
		ArrayList<HashMap<String, Object>> rows = new ArrayList<>();

		// Eseguo la query
		try (Connection conn = dbConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query)) {
			
			ResultSetMetaData md = rs.getMetaData();
			int nColumns = md.getColumnCount();

			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>(nColumns);

				for (int i = 1; i <= nColumns; i++)
					row.put(md.getColumnName(i), rs.getObject(i));

				rows.add(row);
			}
		} catch (SQLException e) {
			throw e;
		}

		printClosingConnection();
		return rows;
	}

	/*
	 * Viene eseguita la connessione con il db
	 */
	private static Connection dbConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/homeBrew", "root", getPsw()); // Connessione al
																										// database
	}

	/*
	 * Viene stampato un messaggio di chiusura della connessione con il db
	 */
	private static void printClosingConnection() {
		System.out.println("Closing database connection");
	}

	/*
	 * Viene restitiuta la password per l'accesso al database
	 */
	private static String getPsw() {
		return "root";
	}
}
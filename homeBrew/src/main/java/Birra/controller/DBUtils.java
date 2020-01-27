package Birra.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * La classe DBUtils permette di eseguire le query di aggiornamento e select
 */
public class DBUtils {

	/*
	 * Viene eseguita una query di aggiornamento dati del db
	 */
	public static void update(String sql) {
		System.out.println(sql);

		try (Connection conn = DBConnection(); Statement st = conn.createStatement();) {
			st.executeUpdate(sql); // Viene eseguito l'update

		} catch (SQLException e) {
			printSQLException(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Viene eseguita una query di tipo select
	 */
	public static ArrayList<HashMap<String, Object>> getRows(String query) {
		ArrayList<HashMap<String, Object>> rows = null;

		try (Connection conn = DBConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);) {
			// Eseguo la query
			ResultSetMetaData md = rs.getMetaData();
			int nColumns = md.getColumnCount();
			rows = new ArrayList<>();

			while (rs.next()) {
				HashMap<String, Object> row = new HashMap<>(nColumns);

				for (int i = 1; i <= nColumns; i++)
					row.put(md.getColumnName(i), rs.getObject(i));

				rows.add(row);
			}

		} catch (SQLException e) {
			printSQLException(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		printClosingConnection();
		return rows;
	}

	/*
	 * Viene eseguita la connessione con il db
	 */
	private static Connection DBConnection() throws SQLException {
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
	 * Viene stampata una eccezione
	 */
	private static void printSQLException(SQLException e) {
		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	}

	/*
	 * Viene restitiuta la password per l'accesso al database
	 */
	private static String getPsw() {
		return "root";
	}
}
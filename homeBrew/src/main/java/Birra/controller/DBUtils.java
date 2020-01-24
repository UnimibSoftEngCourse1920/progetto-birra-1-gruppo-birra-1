package Birra.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBUtils {

	public static void update(String sql) {
		System.out.println(sql);

		try (Connection conn = DBConnection();
				Statement st = conn.createStatement();) {
			st.executeUpdate(sql); // Viene eseguito l'update

		} catch (SQLException e) {
			printSQLException(e);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static ArrayList<HashMap<String, String>> getRows(String query) {
		ArrayList<HashMap<String, String>> rows = null;
		
		try (Connection conn = DBConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);) {
			 // Eseguo la query
			ResultSetMetaData md = rs.getMetaData();
			int nColumns = md.getColumnCount();
			rows = new ArrayList<>();
			
			while (rs.next()) {
				HashMap<String, String> row = new HashMap<>(nColumns);

				for (int i = 1; i <= nColumns; i++)
					row.put(md.getColumnName(i), rs.getString(i));

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
	
	private static Connection DBConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/homeBrew", "root", getPsw()); // Connessione al
																									// database
	}
	
	private static void printClosingConnection() {
		System.out.println("Closing database connection");
	}

	private static void printSQLException(SQLException e) {
		System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	}
	private static String getPsw()
	{
		return "root";
	}
}
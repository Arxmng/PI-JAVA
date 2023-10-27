package projetoIntegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão ao banco de dados
 * @author SergioFurgeri
 */

public class BD {
	// faz conexão ao BD
	public Connection con = null;
	
	// executa instruções em SQL
	public PreparedStatement st = null;
	
	// recebe resultado de uma query (select)
	public ResultSet rs = null;
	
	// caminho do driver
	private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private final String DATABASE_NAME = "PI";
	private final String URL = 
			"jdbc:sqlserver://localhost:1433;databasename="+DATABASE_NAME;
	private final String LOGIN = "sa";
	private final String PASSWORD = "fatec";
	
	
	public boolean getConnection() {
		try {
			Class.forName(DRIVER); // carrega o driver
			con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			System.out.println("Carregou!");
			return true;
		}
		catch(SQLException e) {
			System.out.println("Falha na conexão!" + e);
		}
		catch(ClassNotFoundException e) {
			System.out.println("Driver não encontrado!");
		}
		return false;
	}
	
	public void close() {
		try {
			if(rs!=null) rs.close();
		}
		catch(SQLException e) {}
		try {
			if(st!=null) st.close();
		}
		catch(SQLException e) {}
		try {
			if(con!=null) con.close();
			System.out.println("Desconectou...");
		}
		catch(SQLException e) {}
	}
	
	public static void main(String[] args) {
		BD bd = new BD();
		bd.getConnection();
	}
}

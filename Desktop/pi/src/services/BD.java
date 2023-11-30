package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão ao banco de dados.
 * 
 * <p>
 * Esta classe é utilizada para estabelecer a conexão com o banco de dados,
 * executar instruções SQL e fechar recursos relacionados ao banco de dados.
 * </p>
 * 
 * <p>
 * Antes de utilizar esta classe, é necessário fornecer as informações de
 * conexão no arquivo "banco.txt". As informações incluem o driver JDBC, nome do
 * banco de dados, URL de conexão, nome de usuário e senha.
 * </p>
 * 
 * @author sergio.furgeri
 */
public class BD {
	// faz conexão ao BD
	public Connection con = null;

	// executa instruções em SQL
	public PreparedStatement st = null;

	// recebe resultado de uma query (select)
	public ResultSet rs = null;

	// caminho do driver
	private String driver = "";
	private String databaseName = "";
	private String url = "";
	private String login = "";
	private String password = "";

	 /**
     * Construtor da classe BD. Lê as informações de conexão do arquivo "banco.txt".
     */
	public BD() {
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("banco.txt"));
			driver = br.readLine();
			databaseName = br.readLine();
			url = br.readLine() + databaseName;
			login = br.readLine();
			password = br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	 /**
     * Estabelece a conexão com o banco de dados.
     * 
     * @return true se a conexão for bem-sucedida, false caso contrário.
     */
	public boolean getConnection() {
		try {
			Class.forName(driver); // carrega o driver
			con = DriverManager.getConnection(url, login, password);
			System.out.println("Conectou...");
			return true;
		} catch (SQLException e) {
			System.out.println("Falha na conexão " + e);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver não encontrado!");
		}
		return false;
	}

	/**
     * Fecha os recursos associados ao banco de dados.
     */
	public void close() {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
		}
		try {
			if (st != null)
				st.close();
		} catch (SQLException e) {
		}
		try {
			if (con != null) {
				con.close();
				System.out.println("Desconectou...");
			}
		} catch (SQLException e) {
		}
	}

	 /**
     * Método principal para testar a conexão com o banco de dados.
     * 
     * @param args Os argumentos da linha de comando (não utilizados).
     */
	public static void main(String[] args) {
		BD bd = new BD();
		bd.getConnection();
		bd.close();
	}
}

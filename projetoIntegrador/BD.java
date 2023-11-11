package projetoIntegrador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão ao banco de dados.
 * Esta classe fornece métodos para estabelecer a conexão com o banco de dados e realizar operações relacionadas a banco de dados.
 *
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
            "jdbc:sqlserver://localhost:1433;databaseName=" + DATABASE_NAME;
    private final String LOGIN = "sa";
    private final String PASSWORD = "fatec";

    /**
     * Construtor da classe BD. Inicializa uma conexão com o banco de dados.
     */
    public BD() {
        if (getConnection()) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão!");
        }
    }

    /**
     * Tenta estabelecer uma conexão com o banco de dados.
     *
     * @return True se a conexão for estabelecida com sucesso, false caso contrário.
     */
    public boolean getConnection() {
        try {
            Class.forName(DRIVER); // carrega o driver
            con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Falha na conexão: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado: " + e.getMessage());
        }
        return false;
    }

    /**
     * Fecha a conexão com o banco de dados, os objetos de instrução (statement) e o resultado da consulta (ResultSet).
     */
    public void close() {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
        }
        try {
            if (st != null) st.close();
        } catch (SQLException e) {
        }
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
        }
    }
}

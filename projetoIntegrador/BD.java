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
            "jdbc:sqlserver://localhost:1433;databaseName=" + DATABASE_NAME; // Corrigi o typo aqui
    private final String LOGIN = "sa";
    private final String PASSWORD = "fatec";

    public BD() {
        if (getConnection()) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão!");
        }
    }

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

    // Exemplo de método para realizar uma consulta
    public void consultarDados() {
        try {
            String sql = "SELECT * FROM Cliente";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                String usuario = rs.getString("Usuario");
                String nome = rs.getString("Nome");
                String telefone = rs.getString("Telefone");

                System.out.println("Usuário: " + usuario + ", Nome: " + nome + ", Telefone: " + telefone);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar dados: " + e.getMessage());
        } finally {
            // Certifique-se de fechar a conexão, o PreparedStatement e o ResultSet quando terminar
            close();
        }
    }

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

    public static void main(String[] args) {
        BD bd = new BD();
        bd.consultarDados(); // Chama o método para consultar dados
    }
}

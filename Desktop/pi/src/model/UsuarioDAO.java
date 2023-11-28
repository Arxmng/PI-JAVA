package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.BD;

/**
 * Classe que fornece funcionalidades para verificar as credenciais de usuários no banco de dados.
 */
public class UsuarioDAO {
    private Connection con;

    /**
     * Construtor da classe `UsuarioDAO`.
     * Estabelece a conexão com o banco de dados usando a classe `BD`.
     */
    public UsuarioDAO() {
        // Estabeleça a conexão com o banco de dados aqui, usando o código de conexão que você forneceu na classe BD
        con = new BD().con;
    }
    
    public boolean verificarCredenciaisFuncionario(String codFunc) {
        String sql = "SELECT COUNT(*) AS Count FROM Funcionario WHERE codigo_func = ?";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, codFunc);
            ResultSet rs = st.executeQuery();
            rs.next();
            int count = rs.getInt("Count");
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica se o cliente está ativado com base no nome de usuário.
     *
     * @param usuario O nome de usuário a ser verificado.
     * @return `true` se o cliente está ativado, `false` caso contrário.
     */
    public boolean verificarClienteAtivo(String usuario) {
        String sql = "SELECT COUNT(*) AS Count FROM Cliente WHERE Usuario = ? AND Ativado = 1";
        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, usuario);
            ResultSet rs = st.executeQuery();
            rs.next();
            int count = rs.getInt("Count");
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }	
}

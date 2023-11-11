package projetoIntegrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    /**
     * Verifica as credenciais do usuário com base no nome de usuário e no tipo (funcionário ou cliente).
     *
     * @param usuario O nome de usuário a ser verificado.
     * @param tipo    O tipo de usuário a ser verificado (funcionário ou cliente).
     * @return `true` se as credenciais são válidas, `false` caso contrário.
     */
    public boolean verificarCredenciais(String usuario, String tipo) {
        String tabela = (tipo.equals("funcionario")) ? "Funcionario" : "Cliente";
        String sql = "SELECT COUNT(*) AS Count FROM " + tabela + " WHERE Usuario = ?";
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

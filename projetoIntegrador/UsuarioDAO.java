package projetoIntegrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection con;

    public UsuarioDAO() {
        // Estabeleça a conexão com o banco de dados aqui, usando o código de conexão que você forneceu na classe BD
        con = new BD().con;
    }

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


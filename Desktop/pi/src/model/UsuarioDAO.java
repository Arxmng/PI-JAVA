package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import services.BD;

/**
 * Classe que fornece funcionalidades para verificar as credenciais de usuários no banco de dados.
 */
public class UsuarioDAO {
    private String sql;
    private BD bd;

    /**
     * Construtor da classe `UsuarioDAO`.
     * Estabelece a conexão com o banco de dados usando a classe `BD`.
     */
    public UsuarioDAO() {
        bd = new BD();
        bd.getConnection(); // Estabelece a conexão ao instanciar o objeto
    }

    /**
     * Verifica se o funcionário existe no banco de dados.
     *
     * @param codFunc O código do funcionário a ser verificado.
     * @return `true` se o funcionário está ativado, `false` caso contrário.
     */
    public boolean verificarCredenciaisFuncionario(String codFunc) {
        sql = "SELECT COUNT(*) AS Count FROM Funcionario WHERE codigo_func = ?";
        try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
            stmt.setString(1, codFunc);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                int count = rs.getInt("Count");
                return count > 0;
            }
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
        sql = "SELECT COUNT(*) AS Count FROM Cliente WHERE Usuario = ? AND Ativado = 1";
        try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                int count = rs.getInt("Count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean cadastrarCliente(Cliente cliente) {
        sql = "INSERT INTO Cliente (Usuario, Nome, Telefone, Telefone_responsavel, CPF, Data_nasc, email, Cidade, Estado, Como_conheceu, Ativado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
            stmt.setString(1, cliente.getUsuario());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getTelefoneResponsavel());
            stmt.setString(5, cliente.getCpf());
            stmt.setString(6, cliente.getDataNascimento());
            stmt.setString(7, cliente.getEmail());
            stmt.setString(8, cliente.getCidade());
            stmt.setString(9, cliente.getEstado());
            stmt.setString(10, cliente.getComoConheceu());
            stmt.setBoolean(11, false);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bd.close();
        }
        return false;
    }

    public boolean adicionarUsuario(String usuario) {
        sql = "SELECT COUNT(*) AS Count FROM Cliente WHERE Usuario = ? AND Ativado = 1";
        try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
            stmt.setString(1, usuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("Count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
            bd.close();
        }
        return false;
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     * Chame este método quando finalizar todas as operações no banco de dados.
     */
    public void fecharConexao() {
        bd.close();
    }
}

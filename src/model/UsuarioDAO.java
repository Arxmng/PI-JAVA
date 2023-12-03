package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import javax.swing.JOptionPane;

import services.BD;

/**
 * Classe que fornece funcionalidades para verificar as credenciais de usuários
 * no banco de dados.
 */
public class UsuarioDAO {
	private String sql;
	private BD bd;

	/**
	 * Construtor da classe `UsuarioDAO`. Estabelece a conexão com o banco de dados
	 * usando a classe `BD`.
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

	/**
	 * Cadastra um novo cliente no banco de dados.
	 *
	 * @param cliente O objeto Cliente a ser cadastrado.
	 * @return `true` se o cadastro for bem-sucedido, `false` caso contrário.
	 */
	//
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

	/**
	 * Adiciona um novo usuário (cliente) ao banco de dados, verificando se ele já
	 * existe.
	 *
	 * @param usuario O nome de usuário a ser verificado e adicionado.
	 * @return `true` se o usuário foi adicionado com sucesso, `false` caso
	 *         contrário.
	 */
	//
	public boolean adicionarUsuario(String usuario) {
		sql = "SELECT COUNT(*) AS Count FROM Cliente WHERE Usuario = ? AND Ativado = 1";
		try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
			stmt.setString(1, usuario);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt("Count");
					return count == 0; // Retorna true se o usuário não existir
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
	 * Ativa a conta de cliente após a verificação do código de ativação.
	 *
	 * @param codigoAtivacao O código de ativação a ser verificado e usado para
	 *                       ativar a conta.
	 */
	//
	public void ativarContaDoCliente(String codigoAtivacao) {
		try {
			String sql = "UPDATE Cliente SET Ativado = 1 WHERE Usuario = ? AND Ativado = 0";
			PreparedStatement st = bd.con.prepareStatement(sql);
			st.setString(1, codigoAtivacao);
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Conta ativada com sucesso.");
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao ativar a conta.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifica se a ativação do cliente é válida.
	 *
	 * @param codigoAtivacao  O código de ativação a ser verificado.
	 * @param mostrarMensagem Indica se deve exibir mensagens de erro.
	 * @return O objeto Cliente associado ao código de ativação, ou null se a
	 *         ativação não for válida.
	 */
	//
	public Cliente verificarAtivacaoCliente(String codigoAtivacao, boolean mostrarMensagem) {
		Cliente cliente = null;
		try {
			System.out.println("Código de ativação: " + codigoAtivacao); // Adicione este log
			String sql = "SELECT Nome, Telefone, Telefone_responsavel, CPF, Data_nasc, email, Cidade, Estado, Como_conheceu FROM Cliente WHERE Usuario = ? AND Ativado = 0";
			System.out.println("SQL: " + sql); // Adicione este log
			PreparedStatement st = bd.con.prepareStatement(sql);
			st.setString(1, codigoAtivacao);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				// Crie um objeto Cliente com os dados do banco de dados
				cliente = new Cliente(rs.getString("Nome"), rs.getString("email"), rs.getString("CPF"),
						rs.getString("Data_nasc"), rs.getString("Telefone"), rs.getString("Telefone_responsavel"),
						rs.getString("Estado"), rs.getString("Cidade"), rs.getString("Como_conheceu"));
			} else {
				if (mostrarMensagem) {
					JOptionPane.showMessageDialog(null, "Cliente já ativado ou código inválido.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cliente;
	}

	/**
     * Salva as alterações de dados do cliente no banco de dados.
     *
     * @param usuarioAtivacao O nome de usuário do cliente a ser modificado.
     * @param nome            O novo nome do cliente.
     * @param telefone        O novo número de telefone do cliente.
     * @param telefoneResponsavel O novo número de telefone do responsável do cliente.
     * @param cpf             O novo CPF do cliente.
     * @param dataNascimento  A nova data de nascimento do cliente.
     * @param email           O novo endereço de e-mail do cliente.
     * @param cidade          A nova cidade do cliente.
     * @param estado          O novo estado do cliente.
     * @param comoConheceu    A nova fonte pela qual o cliente conheceu o serviço.
     * @return `true` se as alterações foram salvas com sucesso, `false` caso contrário.
     */
	public boolean salvarAlteracoesCliente(String usuarioAtivacao, String nome, String telefone,
			String telefoneResponsavel, String cpf, String dataNascimento, String email, String cidade, String estado,
			String comoConheceu) {
		try {
			String sql = "UPDATE Cliente SET Nome = ?, Telefone = ?, Telefone_responsavel = ?, CPF = ?, Data_nasc = ?, email = ?, Cidade = ?, Estado = ?, Como_conheceu = ? WHERE Usuario = ? AND Ativado = 0";
			try (PreparedStatement st = bd.con.prepareStatement(sql)) {
				st.setString(1, nome);
				st.setString(2, telefone);
				st.setString(3, telefoneResponsavel);
				st.setString(4, cpf);
				st.setString(5, dataNascimento);
				st.setString(6, email);
				st.setString(7, cidade);
				st.setString(8, estado);
				st.setString(9, comoConheceu);
				st.setString(10, usuarioAtivacao);

				int rowsAffected = st.executeUpdate();

				return rowsAffected > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * Adiciona uma nova sessão ao banco de dados.
	 *
	 * @param usuario           O nome de usuário do cliente associado à sessão.
	 * @param codigoMaquina     O código da máquina associada à sessão.
	 * @param dataSQL           A data da sessão em formato SQL.
	 * @param horaInicio        A hora de início da sessão.
	 * @param horaFim           A hora de término da sessão.
	 * @param codigoFuncionario O código do funcionário associado à sessão.
	 */
	public void adicionarSessao(String usuario, String codigoMaquina, Date dataSQL, Time horaInicio, Time horaFim,
	        String codigoFuncionario) {
	    try {
	        String sql = "INSERT INTO Sessao (preco, dia, hora_inicio, hora_fim, Usuario, Codigo_func, Codigo_maq) "
	                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

	        // Preço pode ser 0, conforme mencionado
	        float preco = 0;

	        try (PreparedStatement st = bd.con.prepareStatement(sql)) {
	            st.setFloat(1, preco);
	            st.setDate(2, dataSQL);
	            st.setTime(3, horaInicio);
	            st.setTime(4, horaFim);
	            st.setString(5, usuario);
	            st.setString(6, codigoFuncionario);
	            st.setString(7, codigoMaquina);

	            int rowsAffected = st.executeUpdate();
	            bd.con.commit();

	            if (rowsAffected > 0) {
	                JOptionPane.showMessageDialog(null, "Sessão adicionada com sucesso.");
	            } else {
	                JOptionPane.showMessageDialog(null, "Falha ao adicionar a sessão.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public boolean verificarCodigoMaquinaExistente(String codigoMaquina) {
	    String sql = "SELECT COUNT(*) AS Count FROM Maquina WHERE Codigo_maq = ?";
	    try (PreparedStatement stmt = bd.con.prepareStatement(sql)) {
	        stmt.setString(1, codigoMaquina);
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
	 * Fecha a conexão com o banco de dados. Chame este método quando finalizar
	 * todas as operações no banco de dados.
	 */
	public void fecharConexao() {
		bd.close();
	}

}

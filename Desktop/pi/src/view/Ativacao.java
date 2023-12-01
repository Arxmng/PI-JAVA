package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import model.Cliente;
import model.UsuarioDAO;

/**
 * Gerencia a ativação de contas de clientes. Permite que os funcionários
 * insiram o código (usuário) e atualizem os dados do cliente associado a esse
 * código, ativando para uso.
 */
public class Ativacao {

	private static boolean modoEdicao = false;

	private static JTextField campoNome;
	private static JTextField campoTelefone;
	private static JTextField campoTelefoneResponsavel;
	private static JTextField campoCPF;
	private static JTextField campoDataNascimento;
	private static JTextField campoEmail;
	private static JTextField campoCidade;
	private static JTextField campoEstado;
	private static JTextField campoComoConheceu;
	private static JTextField campoAtivacao;
	private static String codigoAtivacao;
	private static JButton botaoEditarSalvar;
	private static JButton botaoAtivar;

	/**
	 * Método principal que inicia a interface gráfica para ativação de contas de
	 * clientes.
	 *
	 * @param args Os argumentos da linha de comando (não utilizados).
	 */

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				criarTelaAtivacaoCliente();
			}
		});
	}

	/**
	 * Cria e exibe a interface gráfica para a ativação de contas de clientes.
	 */
	public static void criarTelaAtivacaoCliente() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		JFrame janela = new JFrame("Ativação de Conta (Cliente)");
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janela.setSize(400, 740);

		JPanel painel = new JPanel();
		painel.setLayout(null);

		JLabel rotuloInstrucoes = new JLabel("Insira o código de ativação:");
		rotuloInstrucoes.setBounds(20, 20, 350, 20);

		campoAtivacao = new JTextField();
		campoAtivacao.setBounds(20, 40, 250, 30);

		JButton botaoProcurarDados = new JButton("Procurar");
		botaoProcurarDados.setBounds(270, 40, 100, 30);
		botaoProcurarDados.setBackground(new Color(0, 191, 255));
		botaoProcurarDados.setFont(new Font("Arial", Font.BOLD, 12));

		botaoProcurarDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				codigoAtivacao = campoAtivacao.getText();
				Cliente cliente = usuarioDAO.verificarAtivacaoCliente(codigoAtivacao, true);

				if (cliente != null) {
					preencherDadosCliente();
					ativarModoVisualizacao();
				} else {
					limparCampos();
				}
			}
		});

		botaoEditarSalvar = new JButton("Editar");
		botaoEditarSalvar.setBounds(50, 650, 100, 30);
		botaoEditarSalvar.setBackground(new Color(0, 191, 255));
		botaoEditarSalvar.setFont(new Font("Arial", Font.BOLD, 12));

		botaoEditarSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modoEdicao) {
					if (salvarAlteracoes()) {
						JOptionPane.showMessageDialog(janela, "Dados atualizados com sucesso.");
						usuarioDAO.ativarContaDoCliente(codigoAtivacao);
						limparCampos();
					} else {
						JOptionPane.showMessageDialog(janela, "Falha ao atualizar os dados.");
					}
				} else {
					ativarModoEdicao();
				}
			}
		});

		botaoAtivar = new JButton("Ativar");
		botaoAtivar.setBounds(220, 650, 100, 30);
		botaoAtivar.setBackground(new Color(0, 191, 255));
		botaoAtivar.setFont(new Font("Arial", Font.BOLD, 12));

		botaoAtivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UsuarioDAO usuarioDAO = new UsuarioDAO();
				String codigoAtivacao = campoAtivacao.getText();

				if (modoEdicao) {
					if (usuarioDAO.salvarAlteracoesCliente(codigoAtivacao, campoNome.getText(), campoTelefone.getText(),
							campoTelefoneResponsavel.getText(), campoCPF.getText(), campoDataNascimento.getText(),
							campoEmail.getText(), campoCidade.getText(), campoEstado.getText(),
							campoComoConheceu.getText())) {
						JOptionPane.showMessageDialog(janela, "Dados atualizados com sucesso.");
						usuarioDAO.ativarContaDoCliente(codigoAtivacao);
						limparCampos();
					} else {
						JOptionPane.showMessageDialog(janela, "Falha ao atualizar os dados.");
					}
				} else {
					Cliente cliente = usuarioDAO.verificarAtivacaoCliente(codigoAtivacao, true);

					if (cliente != null) {
						usuarioDAO.ativarContaDoCliente(codigoAtivacao);
						JOptionPane.showMessageDialog(janela, "Conta ativada com sucesso.");
						limparCampos();
					} else {
						JOptionPane.showMessageDialog(janela, "Falha ao ativar a conta.");
					}
				}
			}
		});

		// Rótulo e campo de Nome
		JLabel rotuloNome = new JLabel("Nome:");
		rotuloNome.setBounds(20, 100, 350, 20);
		campoNome = new JTextField();
		campoNome.setBounds(20, 120, 350, 30);
		campoNome.setEditable(false); // Inicia como não editável

		// Rótulo e campo de Telefone
		JLabel rotuloTelefone = new JLabel("Telefone:");
		rotuloTelefone.setBounds(20, 160, 350, 20);

		MaskFormatter mascaraTelefone = null;
		try {
			mascaraTelefone = new MaskFormatter("(##) ####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.printStackTrace();
		}

		campoTelefone = new JFormattedTextField(mascaraTelefone);
		campoTelefone.setBounds(20, 180, 350, 30);
		campoTelefone.setEditable(false);

		// Rótulo e campo de Telefone Responsável
		JLabel rotuloTelefoneResponsavel = new JLabel("Telefone Responsável:");
		rotuloTelefoneResponsavel.setBounds(20, 220, 350, 20);

		MaskFormatter mascaraTelefoneResponsavel = null;
		try {
			mascaraTelefoneResponsavel = new MaskFormatter("(##) ####-####");
			mascaraTelefoneResponsavel.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.printStackTrace();
		}

		campoTelefoneResponsavel = new JFormattedTextField(mascaraTelefoneResponsavel);
		campoTelefoneResponsavel.setBounds(20, 240, 350, 30);
		campoTelefoneResponsavel.setEditable(false);

		// Rótulo e campo de CPF
		JLabel rotuloCPF = new JLabel("CPF:");
		rotuloCPF.setBounds(20, 280, 350, 20);

		MaskFormatter mascaraCPF = null;
		try {
			mascaraCPF = new MaskFormatter("###.###.###-##");
			mascaraCPF.setPlaceholderCharacter('_'); // Caractere que aparecerá como espaço em branco
		} catch (Exception e) {
			e.printStackTrace();
		}

		campoCPF = new JFormattedTextField(mascaraCPF);
		campoCPF.setBounds(20, 300, 350, 30);
		campoCPF.setEditable(false); // Inicia como não editável

		// Rótulo e campo de Data de Nascimento
		JLabel rotuloDataNascimento = new JLabel("Data de Nascimento:");
		rotuloDataNascimento.setBounds(20, 340, 350, 20);

		MaskFormatter mascaraDataNascimento = null;
		try {
			mascaraDataNascimento = new MaskFormatter("##/##/####");
			mascaraDataNascimento.setPlaceholderCharacter('_');
		} catch (Exception e) {
			e.printStackTrace();
		}

		campoDataNascimento = new JFormattedTextField(mascaraDataNascimento);
		campoDataNascimento.setBounds(20, 360, 350, 30);
		campoDataNascimento.setEditable(false);

		// Rótulo e campo de Email
		JLabel rotuloEmail = new JLabel("Email:");
		rotuloEmail.setBounds(20, 400, 350, 20);
		campoEmail = new JTextField();
		campoEmail.setBounds(20, 420, 350, 30);
		campoEmail.setEditable(false); // Inicia como não editável

		// Rótulo e campo de Cidade
		JLabel rotuloCidade = new JLabel("Cidade:");
		rotuloCidade.setBounds(20, 460, 350, 20);
		campoCidade = new JTextField();
		campoCidade.setBounds(20, 480, 350, 30);
		campoCidade.setEditable(false); // Inicia como não editável

		// Rótulo e campo de Estado
		JLabel rotuloEstado = new JLabel("Estado:");
		rotuloEstado.setBounds(20, 520, 350, 20);
		campoEstado = new JTextField();
		campoEstado.setBounds(20, 540, 350, 30);
		campoEstado.setEditable(false); // Inicia como não editável

		// Rótulo e campo de Como Conheceu
		JLabel rotuloComoConheceu = new JLabel("Como Conheceu:");
		rotuloComoConheceu.setBounds(20, 580, 350, 20);
		campoComoConheceu = new JTextField();
		campoComoConheceu.setBounds(20, 600, 350, 30);
		campoComoConheceu.setEditable(false); // Inicia como não editável

		// Botão para editar/salvar

		botaoEditarSalvar = new JButton("Editar");
		botaoEditarSalvar.setBounds(50, 650, 100, 30);
		botaoEditarSalvar.setBackground(new Color(0, 191, 255));
		botaoEditarSalvar.setFont(new Font("Arial", Font.BOLD, 12));

		botaoEditarSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modoEdicao) {
					if (salvarAlteracoes()) {
						JOptionPane.showMessageDialog(janela, "Dados atualizados com sucesso.");
						usuarioDAO.ativarContaDoCliente(codigoAtivacao);
						limparCampos();
					} else {
						JOptionPane.showMessageDialog(janela, "Falha ao atualizar os dados.");
					}
				} else {
					ativarModoEdicao();
				}
			}
		});

		// Adicione os componentes ao painel
		painel.add(rotuloInstrucoes);
		painel.add(campoAtivacao);
		painel.add(botaoProcurarDados);
		painel.add(rotuloNome);
		painel.add(campoNome);
		painel.add(rotuloTelefone);
		painel.add(campoTelefone);
		painel.add(rotuloTelefoneResponsavel);
		painel.add(campoTelefoneResponsavel);
		painel.add(rotuloCPF);
		painel.add(campoCPF);
		painel.add(rotuloDataNascimento);
		painel.add(campoDataNascimento);
		painel.add(rotuloEmail);
		painel.add(campoEmail);
		painel.add(rotuloCidade);
		painel.add(campoCidade);
		painel.add(rotuloEstado);
		painel.add(campoEstado);
		painel.add(rotuloComoConheceu);
		painel.add(campoComoConheceu);
		painel.add(botaoEditarSalvar);
		painel.add(botaoAtivar);

		janela.add(painel);
		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}

	/**
	 * Preenche os campos com os dados do cliente após a verificação do código de
	 * ativação.
	 */
	public static void preencherDadosCliente() {
		String codigoAtivacao = campoAtivacao.getText();
		UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crie uma instância de UsuarioDAO
		Cliente cliente = usuarioDAO.verificarAtivacaoCliente(codigoAtivacao, true);

		if (cliente != null) {
			campoNome.setText(cliente.getNome());
			campoTelefone.setText(cliente.getTelefone());
			campoTelefoneResponsavel.setText(cliente.getTelefoneResponsavel());
			campoCPF.setText(cliente.getCpf());
			campoDataNascimento.setText(cliente.getDataNascimento());
			campoEmail.setText(cliente.getEmail());
			campoCidade.setText(cliente.getCidade());
			campoEstado.setText(cliente.getEstado());
			campoComoConheceu.setText(cliente.getComoConheceu());
		} else {
			limparCampos();
		}
	}

	/**
	 * Salva as alterações do cliente após a edição dos campos.
	 *
	 * @return true se as alterações foram salvas com sucesso, false caso contrário.
	 */
	private static boolean salvarAlteracoes() {
		String codigoAtivacao = campoAtivacao.getText();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		return usuarioDAO.salvarAlteracoesCliente(codigoAtivacao, campoNome.getText(), campoTelefone.getText(),
				campoTelefoneResponsavel.getText(), campoCPF.getText(), campoDataNascimento.getText(),
				campoEmail.getText(), campoCidade.getText(), campoEstado.getText(), campoComoConheceu.getText());
	}

	/**
	 * Limpa os campos de entrada de dados.
	 */

	public static void limparCampos() {
		campoNome.setText("");
		campoTelefone.setText("");
		campoTelefoneResponsavel.setText("");
		campoCPF.setText("");
		campoDataNascimento.setText("");
		campoEmail.setText("");
		campoCidade.setText("");
		campoEstado.setText("");
		campoComoConheceu.setText("");
	}

	/**
	 * Ativa o modo de edição, permitindo a edição dos campos de entrada de dados.
	 */
	private static void ativarModoEdicao() {
		modoEdicao = true;
		botaoEditarSalvar.setText("Salvar");
		campoNome.setEditable(true);
		campoTelefone.setEditable(true);
		campoTelefoneResponsavel.setEditable(true);
		campoCPF.setEditable(true);
		campoDataNascimento.setEditable(true);
		campoEmail.setEditable(true);
		campoCidade.setEditable(true);
		campoEstado.setEditable(true);
		campoComoConheceu.setEditable(true);
		botaoAtivar.setEnabled(false); // Desabilita o botão "Ativar" no modo de edição
	}

	/**
	 * Ativa o modo de visualização, tornando os campos de entrada de dados somente
	 * leitura.
	 */

	public static void ativarModoVisualizacao() {
		campoNome.setEditable(false);
		campoTelefone.setEditable(false);
		campoTelefoneResponsavel.setEditable(false);
		campoCPF.setEditable(false);
		campoDataNascimento.setEditable(false);
		campoEmail.setEditable(false);
		campoCidade.setEditable(false);
		campoEstado.setEditable(false);
		campoComoConheceu.setEditable(false);
	}

}
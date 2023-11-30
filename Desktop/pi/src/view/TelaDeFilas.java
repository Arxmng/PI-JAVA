package view;

import javax.swing.*;

import model.Cliente;
import model.UsuarioDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * A classe `TelaDeFilas` representa a interface gráfica para gerenciar filas de atendimento para diferentes serviços.
 * Os clientes podem ser adicionados e removidos das filas, com opções para definir o tempo de sessão.
 */
public class TelaDeFilas extends JFrame {
	private static final long serialVersionUID = 1L;
	private Map<String, DefaultListModel<Cliente>> filas;
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	private static String codigoFuncionarioLogado;
	
	 /**
     * Cria uma instância da classe `TelaDeFilas`.
     * Inicializa a interface gráfica com abas para diferentes filas e botões para adicionar e remover clientes.
     *
     * @param codigoFuncionarioLogado O código do funcionário logado que será utilizado nas operações do banco de dados.
     */
	public TelaDeFilas(String codigoFuncionarioLogado) {
		TelaDeFilas.codigoFuncionarioLogado = codigoFuncionarioLogado;
		setTitle("Tela de Filas");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JTabbedPane abas = new JTabbedPane();
		String[] titulos = { "PC's", "Consoles", "Simuladores", "VR's" };
		filas = new HashMap<>();

		for (String titulo : titulos) {
			DefaultListModel<Cliente> modeloLista = new DefaultListModel<>();
			JList<Cliente> listaPessoas = new JList<>(modeloLista);
			filas.put(titulo, modeloLista);

			JPanel aba = new JPanel();
			aba.setLayout(new BorderLayout());
			JButton botaoAdicionar = new JButton("Adicionar Pessoa");
			botaoAdicionar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String usuario = JOptionPane.showInputDialog("Insira o ID do usuário:");
					if (usuario != null && !usuario.isEmpty()) {
						boolean clienteAtivo = usuarioDAO.verificarClienteAtivo(usuario);
						if (clienteAtivo) {
							// Solicita ao usuário o tempo de sessão desejado
							String tempoSessaoStr = JOptionPane.showInputDialog("Quanto tempo de sessão (em minutos)?");
							if (tempoSessaoStr != null && !tempoSessaoStr.isEmpty()) {
								try {
									int tempoSessao = Integer.parseInt(tempoSessaoStr);
									Cliente cliente = new Cliente(usuario, modeloLista);

									// Adiciona uma nova sessão ao banco de dados
									adicionarSessao(cliente, tempoSessao);

									cliente.setHoraExpiracaoSessao(calcularHoraExpiracaoSessao(tempoSessao));
									modeloLista.addElement(cliente);
									iniciarTemporizador(cliente, modeloLista, tempoSessao);
								} catch (NumberFormatException ex) {
									JOptionPane.showMessageDialog(null,
											"Por favor, insira um valor válido para o tempo de sessão.");
								}
							} else {
								JOptionPane.showMessageDialog(null, "Tempo de sessão não especificado.");
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"Cliente não encontrado ou não está ativo no banco de dados.");
						}
					}
				}
			});

			JButton botaoRemover = new JButton("Remover Pessoa");
			botaoRemover.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Cliente clienteSelecionado = listaPessoas.getSelectedValue();
					if (clienteSelecionado != null) {
						modeloLista.removeElement(clienteSelecionado);
					} else {
						JOptionPane.showMessageDialog(null, "Selecione uma pessoa para remover.");
					}
				}
			});

			JPanel botoes = new JPanel();
			botoes.add(botaoAdicionar);
			botoes.add(botaoRemover);

			aba.add(new JScrollPane(listaPessoas), BorderLayout.CENTER);
			aba.add(botoes, BorderLayout.SOUTH);

			abas.addTab(titulo, aba);
		}

		add(abas);
		setVisible(true);
	}

	 /**
     * Calcula a hora de expiração da sessão com base no tempo de sessão especificado.
     *
     * @param tempoSessaoEmMinutos O tempo de sessão em minutos.
     * @return A data e hora de expiração da sessão.
     */
	private Date calcularHoraExpiracaoSessao(int tempoSessaoEmMinutos) {
		// Obter a data e hora atual
		Calendar calendar = Calendar.getInstance();

		// Adicionar o tempo de sessão em minutos à data atual
		calendar.add(Calendar.MINUTE, tempoSessaoEmMinutos);
		Date horaExpiracao = calendar.getTime();

		// Retornar a hora de expiração da sessão
		return horaExpiracao;
	}

	/**
     * Inicia um temporizador para remover o cliente da fila após o tempo de sessão expirar.
     *
     * @param cliente      O cliente a ser removido.
     * @param modeloLista  O modelo de lista associado à fila.
     * @param tempoSessao  O tempo de sessão do cliente.
     */
	private void iniciarTemporizador(Cliente cliente, DefaultListModel<Cliente> modeloLista, int tempoSessao) {
		if (cliente.getHoraEntradaSessao() != null) {
			long tempoMilissegundos = tempoSessao * 60 * 1000; // Convertendo minutos para milissegundos
			long horaExpiracaoMilissegundos = cliente.getHoraEntradaSessao().getTime() + tempoMilissegundos;

			java.util.Timer temporizador = new java.util.Timer();
			temporizador.schedule(new TimerTask() {
				@Override
				public void run() {
					modeloLista.removeElement(cliente);
					temporizador.cancel();
				}
			}, horaExpiracaoMilissegundos);
		} else {
			System.out.println("A hora de entrada da sessão é null.");
		}
	}

	
	 /**
     * Adiciona um cliente à fila com a opção de definir o tempo de sessão.
     *
     * @param titulo        O título da fila.
     * @param modeloLista   O modelo de lista associado à fila.
     */
	private void adicionarSessao(Cliente cliente, int tempoSessao) {
		try {
			// Obter o código da máquina (podendo ser adaptado para escolha do usuário)
			String codigoMaquina = JOptionPane.showInputDialog("Insira o código da máquina:");

			// Obter a data e hora atuais
			java.util.Date dataAtual = new java.util.Date();
			java.sql.Date dataSQL = new java.sql.Date(dataAtual.getTime());
			java.sql.Time horaInicio = new java.sql.Time(dataAtual.getTime());

			// Calcular a hora de fim adicionando o tempo de sessão em minutos
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataAtual);
			calendar.add(Calendar.MINUTE, tempoSessao);
			java.sql.Time horaFim = new java.sql.Time(calendar.getTimeInMillis());

			// Adicionar a sessão com o código do funcionário logado
			usuarioDAO.adicionarSessao(cliente.getUsuario(), codigoMaquina, dataSQL, horaInicio, horaFim,
					codigoFuncionarioLogado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
     * O método principal que inicia a aplicação criando uma instância de `TelaDeFilas`.
     *
     * @param args Os argumentos da linha de comando (não utilizados neste caso).
     */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TelaDeFilas telaDeFilas = new TelaDeFilas(codigoFuncionarioLogado);
				telaDeFilas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				telaDeFilas.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// Fechar a conexão com o banco de dados ao fechar a janela
						telaDeFilas.usuarioDAO.fecharConexao();
					}
				});

				// Adiciona um cliente com a opção de definir o tempo de sessão
				String usuario = JOptionPane.showInputDialog("Insira o ID do usuário:");
				if (usuario != null && !usuario.isEmpty()) {
					boolean clienteAtivo = telaDeFilas.usuarioDAO.verificarClienteAtivo(usuario);
					if (clienteAtivo) {
						// Pergunta ao usuário quanto tempo de sessão deseja
						String tempoSessaoStr = JOptionPane.showInputDialog("Quanto tempo de sessão (em minutos)?");
						if (tempoSessaoStr != null && !tempoSessaoStr.isEmpty()) {
							try {
								int tempoSessao = Integer.parseInt(tempoSessaoStr);
								Cliente cliente = new Cliente(usuario, null);
								cliente.setHoraExpiracaoSessao(telaDeFilas.calcularHoraExpiracaoSessao(tempoSessao));
								telaDeFilas.filas.get("PC's").addElement(cliente);
								telaDeFilas.iniciarTemporizador(cliente, telaDeFilas.filas.get("PC's"), tempoSessao);
							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(null,
										"Por favor, insira um valor válido para o tempo de sessão.");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Cliente não encontrado ou não está ativo no banco de dados.");
					}
				}
			}
		});
	}
}

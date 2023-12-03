package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * A classe `TelaFuncionario` representa a interface gráfica para as ações do
 * funcionário. Nesta tela, um funcionário pode ativar clientes ou manipular a
 * fila de atendimento.
 */
public class TelaFuncionario extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Cria uma instância da classe `TelaFuncionario`.
	 *
	 * @param telaLoginFuncionario A tela de login do funcionário associada.
	 */
	public TelaFuncionario(TelaLoginFuncionario telaLoginFuncionario) {
		setTitle("Tela do Funcionário");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JButton btnAtivarCliente = new JButton("Ativar Cliente");
		JButton btnManipularFila = new JButton("Manipular Fila");

		btnAtivarCliente.addActionListener(e -> ativarCliente());
		btnManipularFila.addActionListener(e -> manipularFila(telaLoginFuncionario.getLoginFuncionario()));

		setLayout(null);

		btnAtivarCliente.setBounds(65, 35, 150, 30);
		btnManipularFila.setBounds(65, 85, 150, 30);

		add(btnAtivarCliente);
		add(btnManipularFila);

		setVisible(true);
	}

	/**
	 * Abre a tela de ativação de cliente.
	 */
	private void ativarCliente() {
		SwingUtilities.invokeLater(() -> Ativacao.criarTelaAtivacaoCliente());
	}

	/**
	 * Abre a tela de manipulação da fila de atendimento.
	 *
	 * @param loginFuncionario O login do funcionário responsável pela manipulação
	 *                         da fila.
	 */
	private void manipularFila(String loginFuncionario) {
		SwingUtilities.invokeLater(() -> new TelaDeFilas(loginFuncionario));
	}
}
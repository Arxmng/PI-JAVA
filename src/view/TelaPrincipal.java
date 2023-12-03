package view;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe `TelaPrincipal` representa a tela principal da aplicação.
 * Nesta tela, os usuários podem escolher acessar a funcionalidade de funcionário ou cliente.
 */
public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
     * Cria uma instância da classe `TelaPrincipal`.
     * Inicializa a interface gráfica com botões para acesso como funcionário ou cliente.
     */
	public TelaPrincipal() {
		setTitle("Tela Principal");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JButton btnFuncionario = new JButton("Funcionário");
		JButton btnCliente = new JButton("Cliente");

		btnFuncionario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaFuncionario();
			}
		}); 

		btnCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTelaCliente();
			}
		});

		setLayout(null);

		btnFuncionario.setBounds(65, 35, 150, 30);
		btnCliente.setBounds(65, 85, 150, 30);

		add(btnFuncionario);
		add(btnCliente);

		setVisible(true);
	}

	/**
     * Abre a tela de login do funcionário quando o botão correspondente é clicado.
     */
	private void abrirTelaFuncionario() {
		new TelaLoginFuncionario(this);
	}

	 /**
     * Abre a tela do cliente quando o botão correspondente é clicado.
     */
	private void abrirTelaCliente() {
		new TelaCliente(this);
	}

	/**
     * Método principal que inicia a aplicação criando uma instância de `TelaPrincipal`.
     *
     * @param args Os argumentos da linha de comando (não utilizados neste caso).
     */
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}
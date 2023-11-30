package view;

import javax.swing.JButton;
import javax.swing.JFrame;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe `TelaCliente` representa a interface gráfica para a interação do
 * cliente. Nesta tela, o cliente pode efetuar login ou cadastrar uma nova
 * conta.
 */
public class TelaCliente extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private TelaPrincipal telaPrincipal;
	
	/**
     * Cria uma instância da classe `TelaCliente`.
     *
     * @param telaPrincipal A tela principal que contém esta tela de cliente.
     */
    public TelaCliente(TelaPrincipal telaPrincipal) {
    	this.telaPrincipal = telaPrincipal;
    	
        setTitle("Tela do Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnLogin = new JButton("Login");
        JButton btnCadastrar = new JButton("Cadastrar");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaLoginCliente();
            }
        });

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCadastro();
            }
        });

        setLayout(null);
        
        btnLogin.setBounds(65, 35, 150, 30);
        btnCadastrar.setBounds(65, 85, 150, 30);
        
        add(btnLogin);
        add(btnCadastrar);

        setVisible(true);
    }
    
    /**
     * Abre a tela de login do cliente e fecha a tela atual.
     */
    void abrirTelaLoginCliente() {
        new TelaLoginCliente(this);
        telaPrincipal.dispose();
    }
    
    /**
     * Abre a tela de cadastro de clientes e fecha a tela atual.
     */
    private void abrirTelaCadastro() {
    	CadastroClientes.abrirTelaCadastroClientes();
    	telaPrincipal.dispose();
    }

}

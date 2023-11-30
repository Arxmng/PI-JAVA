package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.UsuarioDAO;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe `TelaLoginFuncionario` representa a interface gráfica para o login de funcionários.
 * Os funcionários utilizam esta tela para inserir suas credenciais e acessar a aplicação.
 */
public class TelaLoginFuncionario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private TelaPrincipal telaPrincipal;
    private String loginFuncionario; 

    /**
     * Cria uma instância da classe `TelaLoginFuncionario`.
     *
     * @param telaPrincipal A tela principal associada a esta tela de login.
     */
    public TelaLoginFuncionario(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;

        setTitle("Login de Funcionário");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");

        campoUsuario = new JTextField(15);
        campoSenha = new JPasswordField(15);

        JButton btnLogin = new JButton("Login");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        setLayout(new GridLayout(3, 2));

        add(labelUsuario);
        add(campoUsuario);
        add(labelSenha);
        add(campoSenha);
        add(new JLabel());
        add(btnLogin);

        setVisible(true);
    }

    /**
     * Realiza a tentativa de login usando as credenciais fornecidas pelo funcionário.
     */
    private void realizarLogin() {
        String codFunc = campoUsuario.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Verificar as credenciais usando o UsuarioDAO
        if (usuarioDAO.verificarCredenciaisFuncionario(codFunc)) {
            loginFuncionario = codFunc; // Armazenar o login do funcionário
            new TelaFuncionario(this); // Passar a referência da tela de login
            dispose();
            telaPrincipal.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login falhou. Verifique suas credenciais.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Obtém o login do funcionário autenticado.
     *
     * @return O login do funcionário autenticado.
     */
    public String getLoginFuncionario() {
        return loginFuncionario;
    }
}
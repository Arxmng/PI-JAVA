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

public class TelaLoginFuncionario extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private TelaPrincipal telaPrincipal;
    private String loginFuncionario; 

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

    public String getLoginFuncionario() {
        return loginFuncionario;
    }
}
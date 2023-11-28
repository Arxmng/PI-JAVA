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

public class TelaLoginCliente extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private TelaCliente telaCliente;

    
    public TelaLoginCliente(TelaCliente telaCliente) {
        this.telaCliente = telaCliente;

        setTitle("Login de Cliente");
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
        String usuario = campoUsuario.getText();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Verificar se o cliente está ativado usando o UsuarioDAO
        if (usuarioDAO.verificarClienteAtivo(usuario)) {
            System.out.println("Login bem-sucedido!");
            dispose();
            telaCliente.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login falhou. Verifique suas credenciais ou se sua conta está ativada.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
    }
}

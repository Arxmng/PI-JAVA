package projetoIntegrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDeLogin extends JFrame {
	private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private UsuarioDAO usuarioDAO;

    public TelaDeLogin() {
        // Configurações da janela
        setTitle("Tela de Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        usuarioDAO = new UsuarioDAO();

        // Crie um JTabbedPane
        JTabbedPane abas = new JTabbedPane();

        // Crie a aba de login de funcionário
        JPanel abaFuncionario = criarAba("Funcionário");
        abas.addTab("Funcionário", abaFuncionario);

        // Crie a aba de login de cliente
        JPanel abaCliente = criarAba("Cliente");
        abas.addTab("Cliente", abaCliente);

        // Adicione as abas à janela
        add(abas);

        setVisible(true);
    }

    private JPanel criarAba(String tipo) {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");

        campoUsuario = new JTextField(15);
        campoSenha = new JPasswordField(15);

        JButton botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                char[] senhaChars = campoSenha.getPassword();
                String senha = new String(senhaChars);

                // Verifique o tipo (funcionário ou cliente) com base na aba selecionada
                if (tipo.equals("Funcionário") && usuario.startsWith("funcionario_") && senha.equals(usuario)) {
                    JOptionPane.showMessageDialog(null, "Login de funcionário bem-sucedido!");
                } else if (tipo.equals("Cliente") && usuario.startsWith("cliente_") && senha.equals(usuario)) {
                    JOptionPane.showMessageDialog(null, "Login de cliente bem-sucedido!");
                } else if (usuarioDAO.verificarCredenciais(usuario, tipo)) {
                    JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login falhou. Tente novamente.");
                    campoUsuario.setText("");
                    campoSenha.setText("");
                }
            }
        });

        painel.add(labelUsuario);
        painel.add(campoUsuario);
        painel.add(labelSenha);
        painel.add(campoSenha);
        painel.add(new JLabel()); // Espaço em branco
        painel.add(new JLabel()); // Espaço em branco
        painel.add(new JLabel()); // Espaço em branco
        painel.add(botaoLogin);

        return painel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaDeLogin();
            }
        });
    }
}

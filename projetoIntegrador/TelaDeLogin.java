package projetoIntegrador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaDeLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaDeLogin() {
        // Configurações da janela
        setTitle("Tela de Login");
        setSize(400, 200); // Aumentei um pouco o tamanho para acomodar as abas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crie um JTabbedPane
        JTabbedPane abas = new JTabbedPane();

        // Crie a aba de login de funcionário
        JPanel abaFuncionario = criarAbaFuncionario();
        abas.addTab("Funcionário", abaFuncionario);

        // Crie a aba de login de cliente
        JPanel abaCliente = criarAbaCliente();
        abas.addTab("Cliente", abaCliente);

        // Adicione as abas à janela
        add(abas);

        setVisible(true);
    }

    private JPanel criarAbaFuncionario() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");

        campoUsuario = new JTextField(15);
        campoSenha = new JPasswordField(15);

        JButton botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String senha = new String(campoSenha.getPassword());

                // Verifique aqui se o nome de usuário e a senha dos funcionários são válidos
                if (usuario.equals("funcionario") && senha.equals("senha_funcionario")) {
                    JOptionPane.showMessageDialog(null, "Login de funcionário bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login de funcionário falhou. Tente novamente.");
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
        painel.add(botaoLogin);

        return painel;
    }

    private JPanel criarAbaCliente() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(3, 2));

        JLabel labelUsuario = new JLabel("Usuário:");
        JLabel labelSenha = new JLabel("Senha:");

        campoUsuario = new JTextField(15);
        campoSenha = new JPasswordField(15);

        JButton botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String senha = new String(campoSenha.getPassword());

                // Verifique aqui se o nome de usuário e a senha dos clientes são válidos
                if (usuario.equals("cliente") && senha.equals("senha_cliente")) {
                    JOptionPane.showMessageDialog(null, "Login de cliente bem-sucedido!");
                } else {
                    JOptionPane.showMessageDialog(null, "Login de cliente falhou. Tente novamente.");
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

package projetoIntegrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroClientes {

    // Defina suas informações de conexão com o banco de dados
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=PI";
    private static final String USUARIO = "Vitor Oliveira";
    private static final String SENHA = "2106";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                criarTelaCadastroClientes();
            }
        });
    }

    public static void criarTelaCadastroClientes() {
        // Cria uma janela JFrame
        JFrame janela = new JFrame("Cadastro de Clientes");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(500, 500);

        // Cria um painel JPanel
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(10, 2));

        // Cria rótulos e campos de texto para os campos do cliente
        JLabel rotuloNome = new JLabel("Nome Completo:");
        JTextField campoNome = new JTextField(20);

        JLabel rotuloEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField(20);

        JLabel rotuloCPF = new JLabel("CPF:");
        JFormattedTextField campoCPF = criarCampoCPF();

        JLabel rotuloDataNascimento = new JLabel("Data de Nascimento:");
        JFormattedTextField campoDataNascimento = criarCampoDataNascimento();

        JLabel rotuloTelefone = new JLabel("Telefone:");
        JFormattedTextField campoTelefone = criarCampoTelefone();

        JLabel rotuloTelefoneResponsavel = new JLabel("Telefone do Responsável (opcional):");
        JFormattedTextField campoTelefoneResponsavel = criarCampoTelefone();

        JLabel rotuloEstado = new JLabel("Estado:");
        JTextField campoEstado = criarCampoEstado();

        JLabel rotuloCidade = new JLabel("Cidade:");
        JTextField campoCidade = new JTextField(20);

        JLabel rotuloComoNosConheceu = new JLabel("Como nos conheceu:");
        JTextField campoComoNosConheceu = new JTextField(20);

        // Cria um botão para cadastrar
        JButton botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBackground(new Color(144, 238, 144)); // Verde Claro
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 12)); // Define o estilo negrito

        // Cria um botão para limpar os campos
        JButton botaoLimpar = new JButton("Limpar Campos");
        botaoLimpar.setBackground(new Color(255, 0, 0)); // Vermelho
        botaoLimpar.setFont(new Font("Arial", Font.BOLD, 12)); // Define o estilo negrito
        botaoLimpar.setForeground(Color.WHITE); // Define a cor do texto como branco
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                campoNome.setText("");
                campoEmail.setText("");
                campoCPF.setText("");
                campoDataNascimento.setText("");
                campoTelefone.setText("");
                campoTelefoneResponsavel.setText("");
                campoEstado.setText("");
                campoCidade.setText("");
                campoComoNosConheceu.setText("");
            }
        });

        // Adiciona um ouvinte de ação ao botão cadastrar
        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aqui você pode adicionar código para processar o cadastro do cliente
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String cpf = campoCPF.getText();
                String dataNascimento = campoDataNascimento.getText();
                String telefone = campoTelefone.getText();
                String telefoneResponsavel = campoTelefoneResponsavel.getText();
                String estado = campoEstado.getText();
                String cidade = campoCidade.getText();
                String comoNosConheceu = campoComoNosConheceu.getText();

                // Gera um login com as 3 primeiras letras do nome e 5 números aleatórios
                String login = gerarLogin(nome);

                try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA)) {
                    String consultaSQL = "INSERT INTO clientes (nome, email, cpf, data_nascimento, telefone, telefone_responsavel, estado, cidade, como_conheceu, login) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conexao.prepareStatement(consultaSQL);
                    stmt.setString(1, nome);
                    stmt.setString(2, email);
                    stmt.setString(3, cpf);
                    stmt.setString(4, dataNascimento);
                    stmt.setString(5, telefone);
                    stmt.setString(6, telefoneResponsavel);
                    stmt.setString(7, estado);
                    stmt.setString(8, cidade);
                    stmt.setString(9, comoNosConheceu);
                    stmt.setString(10, login);

                    int linhasAfetadas = stmt.executeUpdate();
                    if (linhasAfetadas > 0) {
                        JOptionPane.showMessageDialog(janela, "Cliente cadastrado com sucesso.\nSeu Login é: " + login);
                    } else {
                        JOptionPane.showMessageDialog(janela, "Falha ao cadastrar o cliente.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(janela, "Erro ao conectar ao banco de dados.");
                }
            }
        });

        // Adiciona os componentes ao painel
        painel.add(rotuloNome);
        painel.add(campoNome);
        painel.add(rotuloEmail);
        painel.add(campoEmail);
        painel.add(rotuloCPF);
        painel.add(campoCPF);
        painel.add(rotuloDataNascimento);
        painel.add(campoDataNascimento);
        painel.add(rotuloTelefone);
        painel.add(campoTelefone);
        painel.add(rotuloTelefoneResponsavel);
        painel.add(campoTelefoneResponsavel);
        painel.add(rotuloEstado);
        painel.add(campoEstado);
        painel.add(rotuloCidade);
        painel.add(campoCidade);
        painel.add(rotuloComoNosConheceu);
        painel.add(campoComoNosConheceu);
        painel.add(botaoLimpar); // Botão para limpar campos
        painel.add(botaoCadastrar);

        // Adiciona o painel à janela
        janela.add(painel);

        // Centraliza a janela na tela
        janela.setLocationRelativeTo(null);

        // Exibe a janela
        janela.setVisible(true);
    }

    // Função para gerar um login com as 3 primeiras letras do nome e 5 números aleatórios
    public static String gerarLogin(String nome) {
        StringBuilder login = new StringBuilder();
        if (nome.length() >= 3) {
            login.append(nome.substring(0, 3)); // 3 primeiras letras do nome
        } else {
            login.append(nome); // Caso o nome seja menor que 3 letras
        }

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            login.append(random.nextInt(10)); // 5 números aleatórios
        }

        return login.toString();
    }

    public static JFormattedTextField criarCampoCPF() {
        try {
            MaskFormatter mascara = new MaskFormatter("###.###.###-##");
            JFormattedTextField campoCPF = new JFormattedTextField(mascara);
            return campoCPF;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    public static JFormattedTextField criarCampoTelefone() {
        try {
            MaskFormatter mascara = new MaskFormatter("(##) ####-####");
            JFormattedTextField campoTelefone = new JFormattedTextField(mascara);
            return campoTelefone;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    public static JFormattedTextField criarCampoDataNascimento() {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            JFormattedTextField campoDataNascimento = new JFormattedTextField(mascara);
            return campoDataNascimento;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    public static JTextField criarCampoEstado() {
        JTextField campoEstado = new JTextField(2);

        // Cria um filtro para permitir no máximo 2 caracteres
        ((AbstractDocument) campoEstado.getDocument()).setDocumentFilter(new DocumentFilter() {
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String novoTexto = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (novoTexto.length() <= 2) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        return campoEstado;
    }
}

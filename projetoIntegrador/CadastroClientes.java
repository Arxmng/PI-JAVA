package projetoIntegrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe que representa a tela de cadastro de clientes.
 * Permite o cadastro de informações de clientes, como nome, CPF, data de nascimento, etc.
 */
public class CadastroClientes {

    /**
     * Método principal que inicia a aplicação de cadastro de clientes.
     *
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                criarTelaCadastroClientes();
            }
        });
    }

    /**
     * Cria a interface da tela de cadastro de clientes.
     */
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

        JLabel rotulocomoConheceu = new JLabel("Como nos conheceu:");
        JTextField campocomoConheceu = new JTextField(20);

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
                campocomoConheceu.setText("");
            }
        });

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtenha os dados do cliente a partir dos campos
                String nome = campoNome.getText();
                String email = campoEmail.getText();
                String cpf = campoCPF.getText();
                String dataNascimento = campoDataNascimento.getText();
                String telefone = campoTelefone.getText();
                String telefoneResponsavel = campoTelefoneResponsavel.getText();
                String estado = campoEstado.getText();
                String cidade = campoCidade.getText();
                String comoConheceu = campocomoConheceu.getText();

                // Crie um objeto Cliente com os dados
                Cliente cliente = new Cliente(nome, email, cpf, dataNascimento, telefone, telefoneResponsavel, estado, cidade, comoConheceu);
                
                // Realize o cadastro
                boolean cadastradoComSucesso = cadastrarCliente(cliente);

                if (cadastradoComSucesso) {
                    JOptionPane.showMessageDialog(janela, "Cliente cadastrado com sucesso.\nSeu Login é: " + cliente.getUsuario());
                } else {
                    JOptionPane.showMessageDialog(janela, "Falha ao cadastrar o cliente.");
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
        painel.add(rotulocomoConheceu);
        painel.add(campocomoConheceu);
        painel.add(botaoLimpar); // Botão para limpar campos
        painel.add(botaoCadastrar);

        // Adiciona o painel à janela
        janela.add(painel);

        // Centraliza a janela na tela
        janela.setLocationRelativeTo(null);

        // Exibe a janela
        janela.setVisible(true);
    }

    /**
     * Função para realizar o cadastro do cliente no banco de dados.
     *
     * @param cliente O objeto Cliente a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public static boolean cadastrarCliente(Cliente cliente) {
        BD bd = new BD(); // Crie uma instância da classe BD para gerenciar a conexão
        try {
            // Obtém uma conexão com o banco de dados
            if (bd.getConnection()) {
                // SQL para inserir um novo cliente na tabela "Cliente"
                String sql = "INSERT INTO Cliente (Usuario, Nome, Telefone, Telefone_responsavel, CPF, Data_nasc, email, Cidade, Estado, Como_conheceu, Ativado) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                // Cria um PreparedStatement para a consulta SQL
                PreparedStatement stmt = bd.con.prepareStatement(sql);

                // Preenche os parâmetros da consulta com os dados do cliente
                stmt.setString(1, cliente.getUsuario()); // Considerando que "Usuario" é o login
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getTelefone());
                stmt.setString(4, cliente.getTelefoneResponsavel());
                stmt.setString(5, cliente.getCpf());
                stmt.setString(6, cliente.getDataNascimento());
                stmt.setString(7, cliente.getEmail());
                stmt.setString(8, cliente.getCidade());
                stmt.setString(9, cliente.getEstado());
                stmt.setString(10, cliente.getComoConheceu());
                stmt.setBoolean(11, false); 

                // Executa a consulta para inserir o cliente no banco de dados
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    // Cadastro bem-sucedido
                    return true;
                } else {
                    // Falha no cadastro
                    return false;
                }
            } else {
                // Falha na conexão com o banco de dados
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Fecha a conexão com o banco de dados, independentemente do resultado
            bd.close();
        }
    }

    /**
     * Cria e retorna um campo de CPF formatado.
     *
     * @return O campo de CPF formatado.
     */
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

    /**
     * Cria e retorna um campo de telefone formatado.
     *
     * @return O campo de telefone formatado.
     */
    public static JFormattedTextField criarCampoTelefone() {
        try {
            MaskFormatter mascara = new MaskFormatter("(##) #####-####");
            JFormattedTextField campoTelefone = new JFormattedTextField(mascara);
            return campoTelefone;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    /**
     * Cria e retorna um campo de data de nascimento formatado.
     *
     * @return O campo de data de nascimento formatado.
     */
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

    /**
     * Cria e retorna um campo de estado com limite de 2 caracteres.
     *
     * @return O campo de estado com limite de 2 caracteres.
     */
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

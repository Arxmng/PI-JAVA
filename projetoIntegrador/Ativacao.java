package projetoIntegrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Ativacao {

	private static Connection con;
    private static boolean modoEdicao = false;
    private static JButton botaoAtivar;

    private static JTextField campoNome;
    private static JTextField campoTelefone;
    private static JTextField campoTelefoneResponsavel;
    private static JTextField campoCPF;
    private static JTextField campoDataNascimento;
    private static JTextField campoEmail;
    private static JTextField campoCidade;
    private static JTextField campoEstado;
    private static JTextField campoComoConheceu;

    private static String usuarioAtivacao;
    private static JTextField campoAtivacao;

    private static String codigoAtivacao;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                criarTelaAtivacaoCliente();
            }
        });
    }
   
    public static void criarTelaAtivacaoCliente() {
    	  con = new BD().con;
          JFrame janela = new JFrame("Ativação de Conta (Cliente)");
          janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          janela.setSize(400, 740);

          JPanel painel = new JPanel();
          painel.setLayout(null);

        JLabel rotuloInstrucoes = new JLabel("Insira o código de ativação:");
        rotuloInstrucoes.setBounds(20, 20, 350, 20);

        campoAtivacao = new JTextField(); 
        campoAtivacao.setBounds(20, 40, 250, 30);

        JButton botaoProcurarDados = new JButton("Procurar");
        botaoProcurarDados.setBounds(270, 40, 100, 30);
        botaoProcurarDados.setBackground(new Color(0, 191, 255));
        botaoProcurarDados.setFont(new Font("Arial", Font.BOLD, 12));

        botaoProcurarDados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codigoAtivacao = campoAtivacao.getText(); // Define o código de ativação
                boolean ativacaoValida = verificarAtivacaoCliente(codigoAtivacao);

                if (ativacaoValida) {
                    preencherDadosCliente();
                    ativarModoVisualizacao(); // Defina o modo de visualização ao preencher os dados
                } else {
                    JOptionPane.showMessageDialog(janela, "Código inválido.");
                    limparCampos(); // Limpe os campos se o código for inválido
                }
            }
        });

        // Rótulo e campo de Nome
        JLabel rotuloNome = new JLabel("Nome:");
        rotuloNome.setBounds(20, 100, 350, 20);
        campoNome = new JTextField();
        campoNome.setBounds(20, 120, 350, 30);
        campoNome.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Telefone
        JLabel rotuloTelefone = new JLabel("Telefone:");
        rotuloTelefone.setBounds(20, 160, 350, 20);
        campoTelefone = new JTextField();
        campoTelefone.setBounds(20, 180, 350, 30);
        campoTelefone.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Telefone Responsável
        JLabel rotuloTelefoneResponsavel = new JLabel("Telefone Responsável:");
        rotuloTelefoneResponsavel.setBounds(20, 220, 350, 20);
        campoTelefoneResponsavel = new JTextField();
        campoTelefoneResponsavel.setBounds(20, 240, 350, 30);
        campoTelefoneResponsavel.setEditable(false); // Inicia como não editável

        // Rótulo e campo de CPF
        JLabel rotuloCPF = new JLabel("CPF:");
        rotuloCPF.setBounds(20, 280, 350, 20);
        campoCPF = new JTextField();
        campoCPF.setBounds(20, 300, 350, 30);
        campoCPF.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Data de Nascimento
        JLabel rotuloDataNascimento = new JLabel("Data de Nascimento:");
        rotuloDataNascimento.setBounds(20, 340, 350, 20);
        campoDataNascimento = new JTextField();
        campoDataNascimento.setBounds(20, 360, 350, 30);
        campoDataNascimento.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Email
        JLabel rotuloEmail = new JLabel("Email:");
        rotuloEmail.setBounds(20, 400, 350, 20);
        campoEmail = new JTextField();
        campoEmail.setBounds(20, 420, 350, 30);
        campoEmail.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Cidade
        JLabel rotuloCidade = new JLabel("Cidade:");
        rotuloCidade.setBounds(20, 460, 350, 20);
        campoCidade = new JTextField();
        campoCidade.setBounds(20, 480, 350, 30);
        campoCidade.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Estado
        JLabel rotuloEstado = new JLabel("Estado:");
        rotuloEstado.setBounds(20, 520, 350, 20);
        campoEstado = new JTextField();
        campoEstado.setBounds(20, 540, 350, 30);
        campoEstado.setEditable(false); // Inicia como não editável

        // Rótulo e campo de Como Conheceu
        JLabel rotuloComoConheceu = new JLabel("Como Conheceu:");
        rotuloComoConheceu.setBounds(20, 580, 350, 20);
        campoComoConheceu = new JTextField();
        campoComoConheceu.setBounds(20, 600, 350, 30);
        campoComoConheceu.setEditable(false); // Inicia como não editável

        // Botão para editar/salvar
        JButton botaoEditarSalvar = new JButton("Editar");
        botaoEditarSalvar.setBounds(50, 650, 100, 30);
        botaoEditarSalvar.setBackground(new Color(0, 191, 255));
        botaoEditarSalvar.setFont(new Font("Arial", Font.BOLD, 12));

        botaoEditarSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modoEdicao) {
                    botaoEditarSalvar.setText("Editar");
                    modoEdicao = false;
                    ativarModoVisualizacao();
                    botaoAtivar.setEnabled(true); // Habilita o botão "Ativar"
       
                    if (salvarAlteracoesCliente(codigoAtivacao)) {
                        JOptionPane.showMessageDialog(janela, "Dados atualizados com sucesso.");
                        ativarContaDoCliente(codigoAtivacao); // Ativar a conta após a atualização
                    } else {
                        JOptionPane.showMessageDialog(janela, "Falha ao atualizar os dados.");
                    }
                } else {
                    botaoEditarSalvar.setText("Salvar");
                    modoEdicao = true;
                    ativarModoEdicao();
                    botaoAtivar.setEnabled(false); // Desabilita o botão "Ativar"
                }
            }
        });

        
     // Botão para ativar a conta
        botaoAtivar = new JButton("Ativar"); // Mova a inicialização do botão para cá
        botaoAtivar.setBounds(220, 650, 100, 30);
        botaoAtivar.setBackground(new Color(0, 191, 255));
        botaoAtivar.setFont(new Font("Arial", Font.BOLD, 12));

        botaoAtivar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigoAtivacao = campoAtivacao.getText();

                if (modoEdicao) {
                    if (salvarAlteracoesCliente(codigoAtivacao)) {
                        JOptionPane.showMessageDialog(janela, "Dados atualizados com sucesso.");
                        ativarContaDoCliente(codigoAtivacao); // Ativa a conta após a atualização
                    } else {
                        JOptionPane.showMessageDialog(janela, "Falha ao atualizar os dados.");
                    }
                } else {
                    boolean ativacaoValida = verificarAtivacaoCliente(codigoAtivacao);

                    if (ativacaoValida) {
                        ativarContaDoCliente(codigoAtivacao); // Ativa a conta
                        JOptionPane.showMessageDialog(janela, "Conta ativada com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(janela, "Código inválido.");
                    }
                }
            }
        });

        painel.add(botaoAtivar);

        // Adicione os componentes ao painel
        painel.add(rotuloInstrucoes);
        painel.add(campoAtivacao);
        painel.add(botaoProcurarDados);
        painel.add(rotuloNome);
        painel.add(campoNome);
        painel.add(rotuloTelefone);
        painel.add(campoTelefone);
        painel.add(rotuloTelefoneResponsavel);
        painel.add(campoTelefoneResponsavel);
        painel.add(rotuloCPF);
        painel.add(campoCPF);
        painel.add(rotuloDataNascimento);
        painel.add(campoDataNascimento);
        painel.add(rotuloEmail);
        painel.add(campoEmail);
        painel.add(rotuloCidade);
        painel.add(campoCidade);
        painel.add(rotuloEstado);
        painel.add(campoEstado);
        painel.add(rotuloComoConheceu);
        painel.add(campoComoConheceu);
        painel.add(botaoEditarSalvar);

        janela.add(painel);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    public static void preencherDadosCliente() {
        usuarioAtivacao = campoAtivacao.getText();
        try {
            String sql = "SELECT Nome, Telefone, Telefone_responsavel, CPF, Data_nasc, email, Cidade, Estado, Como_conheceu FROM Cliente WHERE Usuario = ? AND Ativado = 0"; // Adicione a condição Ativado = 0
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, usuarioAtivacao);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                campoNome.setText(rs.getString("Nome"));
                campoTelefone.setText(rs.getString("Telefone"));
                campoTelefoneResponsavel.setText(rs.getString("Telefone_responsavel"));
                campoCPF.setText(rs.getString("CPF"));
                campoDataNascimento.setText(rs.getString("Data_nasc"));
                campoEmail.setText(rs.getString("email"));
                campoCidade.setText(rs.getString("Cidade"));
                campoEstado.setText(rs.getString("Estado"));
                campoComoConheceu.setText(rs.getString("Como_conheceu"));
            } else {
                JOptionPane.showMessageDialog(null, "Cliente já ativado ou código inválido.");
                limparCampos();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void limparCampos() {
        campoNome.setText("");
        campoTelefone.setText("");
        campoTelefoneResponsavel.setText("");
        campoCPF.setText("");
        campoDataNascimento.setText("");
        campoEmail.setText("");
        campoCidade.setText("");
        campoEstado.setText("");
        campoComoConheceu.setText("");
    }



    public static void ativarModoEdicao() {
        campoNome.setEditable(true);
        campoTelefone.setEditable(true);
        campoTelefoneResponsavel.setEditable(true);
        campoCPF.setEditable(true);
        campoDataNascimento.setEditable(true);
        campoEmail.setEditable(true);
        campoCidade.setEditable(true);
        campoEstado.setEditable(true);
        campoComoConheceu.setEditable(true);
    }

    public static void ativarModoVisualizacao() {
        campoNome.setEditable(false);
        campoTelefone.setEditable(false);
        campoTelefoneResponsavel.setEditable(false);
        campoCPF.setEditable(false);
        campoDataNascimento.setEditable(false);
        campoEmail.setEditable(false);
        campoCidade.setEditable(false);
        campoEstado.setEditable(false);
        campoComoConheceu.setEditable(false);
    }
    
    public static void ativarContaDoCliente(String codigoAtivacao) {
        try {
            String sql = "UPDATE Cliente SET Ativado = 1 WHERE Usuario = ? AND Ativado = 0";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, usuarioAtivacao); 
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Conta ativada com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao ativar a conta.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static boolean salvarAlteracoesCliente(String codigoAtivacao) {
        try {
            String sql = "UPDATE Cliente SET Nome = ?, Telefone = ?, Telefone_responsavel = ?, CPF = ?, Data_nasc = ?, email = ?, Cidade = ?, Estado = ?, Como_conheceu = ? WHERE Usuario = ? AND Ativado = 0";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, campoNome.getText());
            st.setString(2, campoTelefone.getText());
            st.setString(3, campoTelefoneResponsavel.getText());
            st.setString(4, campoCPF.getText());
            st.setString(5, campoDataNascimento.getText());
            st.setString(6, campoEmail.getText());
            st.setString(7, campoCidade.getText());
            st.setString(8, campoEstado.getText());
            st.setString(9, campoComoConheceu.getText());
            st.setString(10, usuarioAtivacao); 

            int rowsAffected = st.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean verificarAtivacaoCliente(String codigoAtivacao) {
        try {
            String sql = "SELECT COUNT(*) AS Count FROM Cliente WHERE Ativado = 0 AND Usuario = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, codigoAtivacao);
            ResultSet rs = st.executeQuery();
            rs.next();
            int count = rs.getInt("Count");
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}

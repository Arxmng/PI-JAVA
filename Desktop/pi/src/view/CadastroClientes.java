package view;

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

import model.Cliente;
import model.UsuarioDAO;

public class CadastroClientes {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				criarTelaCadastroClientes();
			}
		});
	}

	public static void criarTelaCadastroClientes() {
		JFrame janela = new JFrame("Cadastro de Clientes");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setSize(500, 500);

		JPanel painel = criarPainelCadastro();

		janela.add(painel);

		janela.setLocationRelativeTo(null);
		janela.setVisible(true);
	}

	public static JPanel criarPainelCadastro() {
		JPanel painel = new JPanel();
		painel.setLayout(new GridLayout(10, 2));

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

		JButton botaoCadastrar = new JButton("Cadastrar");
		botaoCadastrar.setBackground(new Color(144, 238, 144));
		botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 12));

		JButton botaoLimpar = new JButton("Limpar Campos");
		botaoLimpar.setBackground(new Color(255, 0, 0));
		botaoLimpar.setFont(new Font("Arial", Font.BOLD, 12));
		botaoLimpar.setForeground(Color.WHITE);
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

		JButton jButton = new JButton();
		jButton.addActionListener(new ActionListener() {
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
		        UsuarioDAO usuarioDAO = new UsuarioDAO();
		        boolean cadastradoComSucesso = usuarioDAO.cadastrarCliente(cliente);

		        if (cadastradoComSucesso) {
		            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso.\nSeu Login é: " + cliente.getUsuario());
		        } else {
		            JOptionPane.showMessageDialog(null, "Falha ao cadastrar o cliente.");
		        }
			}
		});

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
		painel.add(botaoLimpar);
		painel.add(botaoCadastrar);

		return painel;
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
			MaskFormatter mascara = new MaskFormatter("(##) #####-####");
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
	
	public static void abrirTelaCadastroClientes() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame janela = new JFrame("Cadastro de Clientes");
                janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                janela.setSize(500, 500);

                JPanel painel = criarPainelCadastro();

                janela.add(painel);

                janela.setLocationRelativeTo(null);
                janela.setVisible(true);
            }
        });
    }

	
}

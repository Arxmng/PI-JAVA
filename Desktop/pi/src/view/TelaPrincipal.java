package view;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaPrincipal() {
        setTitle("Tela Principal");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnFuncionario = new JButton("Funcionário");
        JButton btnCliente = new JButton("Cliente");

        btnFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaFuncionario();
            }
        });

        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirTelaCliente();
            }
        });

        setLayout(null);

        btnFuncionario.setBounds(65, 35, 150, 30);
        btnCliente.setBounds(65, 85, 150, 30);

        add(btnFuncionario);
        add(btnCliente);

        setVisible(true);
    }

    private void abrirTelaFuncionario() {
        new TelaLoginFuncionario(this); 
    }

    private void abrirTelaCliente() {
        new TelaCliente(this); 
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}

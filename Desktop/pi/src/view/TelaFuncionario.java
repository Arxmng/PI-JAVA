package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class TelaFuncionario extends JFrame {

    public TelaFuncionario() {
        setTitle("Tela do Funcionário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnAtivarCliente = new JButton("Ativar Cliente");
        JButton btnManipularFila = new JButton("Manipular Fila");

        btnAtivarCliente.addActionListener(e -> ativarCliente());
        btnManipularFila.addActionListener(e -> manipularFila());

        setLayout(null);

        btnAtivarCliente.setBounds(65, 35, 150, 30);
        btnManipularFila.setBounds(65, 85, 150, 30);

        add(btnAtivarCliente);
        add(btnManipularFila);

        setVisible(true);
    }

    private void ativarCliente() {
        SwingUtilities.invokeLater(() -> Ativacao.criarTelaAtivacaoCliente());
    }
    private void manipularFila() {
    	 SwingUtilities.invokeLater(() -> new TelaDeFilas());
    }
}

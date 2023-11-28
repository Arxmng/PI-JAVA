package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Classe que representa a tela de filas de usuários em diferentes abas.
 */
public class TelaDeFilas extends JFrame {
    private static final long serialVersionUID = 1L;
    private Map<String, DefaultListModel<Usuario>> filas;

    /**
     * Construtor da classe `TelaDeFilas`.
     * Configura a interface da tela de filas.
     */
    public TelaDeFilas() {
        // Configurações da janela
        setTitle("Tela de Filas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crie um JTabbedPane
        JTabbedPane abas = new JTabbedPane();

        // Crie as abas para PC's, Consoles, Simuladores e VR's
        String[] titulos = { "PC's", "Consoles", "Simuladores", "VR's" };
        filas = new HashMap<>();

        for (String titulo : titulos) {
            DefaultListModel<Usuario> modeloLista = new DefaultListModel<>();
            JList<Usuario> listaPessoas = new JList<>(modeloLista);
            filas.put(titulo, modeloLista);

            JPanel aba = new JPanel();
            aba.setLayout(new BorderLayout());

            // Botão de Adicionar Pessoa
            JButton botaoAdicionar = new JButton("Adicionar Pessoa");
            botaoAdicionar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String idUsuario = JOptionPane.showInputDialog("Insira o ID do usuário:");
                    if (idUsuario != null && !idUsuario.isEmpty()) {
                        String tempoSessaoStr = JOptionPane.showInputDialog("Insira o tempo de sessão (em minutos):");
                        if (tempoSessaoStr != null && !tempoSessaoStr.isEmpty()) {
                            try {
                                int tempoSessaoMinutos = Integer.parseInt(tempoSessaoStr);
                                Usuario usuario = new Usuario(idUsuario, tempoSessaoMinutos);
                                modeloLista.addElement(usuario);
                                // Iniciar um temporizador para remover o usuário após o tempo de sessão
                                iniciarTemporizador(usuario, modeloLista);
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "Tempo de sessão inválido. Insira um número inteiro.");
                            }
                        }
                    }
                }
            });

            // Botão de Remover Pessoa
            JButton botaoRemover = new JButton("Remover Pessoa");
            botaoRemover.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Usuario usuarioSelecionado = listaPessoas.getSelectedValue();
                    if (usuarioSelecionado != null) {
                        modeloLista.removeElement(usuarioSelecionado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selecione uma pessoa para remover.");
                    }
                }
            });

            JPanel botoes = new JPanel();
            botoes.add(botaoAdicionar);
            botoes.add(botaoRemover);

            aba.add(new JScrollPane(listaPessoas), BorderLayout.CENTER);
            aba.add(botoes, BorderLayout.SOUTH);

            abas.addTab(titulo, aba);
        }

        // Adicione as abas à janela
        add(abas);

        setVisible(true);
    }

    /**
     * Inicia um temporizador para remover um usuário da fila após o tempo de sessão.
     *
     * @param usuario     O usuário a ser removido.
     * @param modeloLista O modelo da lista de usuários.
     */
    private void iniciarTemporizador(Usuario usuario, DefaultListModel<Usuario> modeloLista) {
        java.util.Timer temporizador = new java.util.Timer();
        temporizador.schedule(new TimerTask() {
            @Override
            public void run() {
                modeloLista.removeElement(usuario);
                temporizador.cancel();
            }
        }, usuario.getTempoSessaoMinutos() * 60 * 1000); // Converter o tempo de sessão em milissegundos
    }

    /**
     * Método principal que inicia a aplicação da Tela de Filas.
     *
     * @param args Argumentos de linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaDeFilas();
            }
        });
    }
}

/**
 * Classe que representa um usuário na fila.
 */
class Usuario {
    private String id;
    private Date horarioEntrada;
    private int tempoSessaoMinutos; // em minutos

    /**
     * Construtor da classe `Usuario`.
     *
     * @param id                O ID do usuário.
     * @param tempoSessaoMinutos O tempo de sessão do usuário em minutos.
     */
    public Usuario(String id, int tempoSessaoMinutos) {
        this.id = id;
        this.horarioEntrada = new Date();
        this.tempoSessaoMinutos = tempoSessaoMinutos;
    }

    /**
     * Obtém o ID do usuário.
     *
     * @return O ID do usuário.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtém o horário de entrada do usuário.
     *
     * @return O horário de entrada do usuário.
     */
    public Date getHorarioEntrada() {
        return horarioEntrada;
    }

    /**
     * Obtém o tempo de sessão do usuário em minutos.
     *
     * @return O tempo de sessão do usuário em minutos.
     */
    public int getTempoSessaoMinutos() {
        return tempoSessaoMinutos;
    }

    /**
     * Obtém o horário de saída do usuário com base no tempo de sessão.
     *
     * @return O horário de saída do usuário.
     */
    public Date getHorarioSaida() {
        long tempoSessaoMillis = tempoSessaoMinutos * 60 * 1000; // Converter o tempo de sessão para milissegundos
        return new Date(horarioEntrada.getTime() + tempoSessaoMillis);
    }

    /**
     * Retorna uma representação em string do usuário.
     *
     * @return Uma string que descreve o usuário, incluindo o ID, horário de entrada e horário de saída.
     */
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return "ID: " + id + " | Entrada: " + dateFormat.format(horarioEntrada) + " | Saída: "
                + dateFormat.format(getHorarioSaida());
    }
}

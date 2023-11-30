package model;

import java.text.SimpleDateFormat;
import java.util.Random;

import javax.swing.DefaultListModel;

/**
 * Representa um cliente com informações pessoais e dados de registro.
 */

public class Cliente {
	private String nome;
	private String email;
	private String cpf;
	private String dataNascimento;
	private String telefone;
	private String telefoneResponsavel;
	private String estado;
	private String cidade;
	private String comoConheceu;
	private String usuario;

	private java.util.Date horaEntradaSessao;
	private java.util.Date horaExpiracaoSessao;

	/**
	 * Cria um novo objeto Cliente com informações pessoais e gera automaticamente um nome de usuário.
	 * @param nome                O nome do cliente.
	 * @param email               O endereço de e-mail do cliente.
	 * @param cpf                 O número de CPF do cliente.
	 * @param dataNascimento      A data de nascimento do cliente.
	 * @param telefone            O número de telefone do cliente.
	 * @param telefoneResponsavel O número de telefone do responsável do cliente.
	 * @param estado              O estado onde o cliente reside.
	 * @param cidade              A cidade onde o cliente reside.
	 * @param comoConheceu        A fonte pela qual o cliente conheceu o serviço.
	 */


	public Cliente(String nome, String email, String cpf, String dataNascimento, String telefone,
			String telefoneResponsavel, String estado, String cidade, String comoConheceu) {
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.telefone = telefone;
		this.telefoneResponsavel = telefoneResponsavel;
		this.estado = estado;
		this.cidade = cidade;
		this.comoConheceu = comoConheceu;
		this.usuario = gerarUsuario(nome);
	}
	

	/**
	 * Cria um objeto Cliente com base em um nome de usuário existente e uma lista de clientes.
	 * @param usuario       O nome de usuário do cliente.
	 * @param modeloLista   A lista de clientes utilizada para determinar a hora de entrada da sessão.
	 */
	public Cliente(String usuario, DefaultListModel<Cliente> modeloLista) {
	    this.usuario = usuario;
	    if (modeloLista != null && !modeloLista.isEmpty()) {
	        // Obtém o último cliente na fila
	        Cliente ultimoCliente = modeloLista.lastElement();

	        // Define a horaEntradaSessao como a horaExpiracaoSessao do último colocado
	        this.horaEntradaSessao = ultimoCliente.getHoraExpiracaoSessao();
	    } else {
	        // Se não houver ninguém na fila ou modeloLista for null, define a horaEntradaSessao como a hora atual
	        this.horaEntradaSessao = new java.util.Date();
	    }
	}

	/**
	 * Gera um nome de usuário baseado no nome do cliente. Sendo composto pelos três
	 * primeiros dígitos do nome e 5 números gerados aleatoriamente.
	 * 
	 * @param nome O nome do cliente.
	 * @return O nome de usuário gerado.
	 */
	private static String gerarUsuario(String nome) {
		StringBuilder usuario = new StringBuilder();
		if (nome.length() >= 3) {
			usuario.append(nome.substring(0, 3)); // 3 primeiras letras do nome
		} else {
			usuario.append(nome); // Caso o nome seja menor que 3 letras
		}

		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			usuario.append(random.nextInt(10)); // 5 números aleatórios
		}

		return usuario.toString();
	}
	
	/**
	 * Retorna uma representação em formato de string do objeto Cliente.
	 * @return Uma string formatada contendo o ID do Cliente, hora de entrada e hora de expiração da sessão.
	 */	@Override
	public String toString() {
	    SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm:ss");
	    String horaEntrada = horaEntradaSessao != null ? formatadorHora.format(horaEntradaSessao) : "N/A";
	    String horaExpiracao = horaExpiracaoSessao != null ? formatadorHora.format(horaExpiracaoSessao) : "N/A";

	    return "ID do Cliente: " + this.usuario + " | Hora Entrada: " + horaEntrada + " | Hora Expiração: " + horaExpiracao;
	}

	// Getters e Setters (métodos de acesso) para os atributos
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComoConheceu() {
		return comoConheceu;
	}

	public void setComoConheceu(String comoConheceu) {
		this.comoConheceu = comoConheceu;
	}

	public String getUsuario() {
		return usuario;
	}

	public java.util.Date getHoraExpiracaoSessao() {
		return horaExpiracaoSessao;
	}

	public void setHoraExpiracaoSessao(java.util.Date date) {
		this.horaExpiracaoSessao = date;
	}

	public java.util.Date getHoraEntradaSessao() {
		return horaEntradaSessao;
	}

	public void setHoraEntradaSessao(java.util.Date horaEntradaSessao) {
		this.horaEntradaSessao = horaEntradaSessao;
	}

}
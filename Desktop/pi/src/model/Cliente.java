package model;

import java.util.Random;

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

	    /**
	     * Cria um novo objeto Cliente com informações pessoais.
	     *
	     * @param nome               O nome do cliente.
	     * @param email              O endereço de e-mail do cliente.s
	     * @param cpf                O número de CPF do cliente.
	     * @param dataNascimento     A data de nascimento do cliente.
	     * @param telefone           O número de telefone do cliente.
	     * @param telefoneResponsavel O número de telefone do responsável do cliente.
	     * @param estado             O estado onde o cliente reside.
	     * @param cidade             A cidade onde o cliente reside.
	     * @param comoConheceu       A fonte pela qual o cliente conheceu o serviço.
	     */
	    public Cliente(String nome, String email, String cpf, String dataNascimento, String telefone, String telefoneResponsavel, String estado, String cidade, String comoConheceu) {
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
	     * Gera um nome de usuário baseado no nome do cliente.
	     * Sendo composto pelos três primeiros dígitos do nome
	     * e 5 números gerados aleatoriamente. 
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

}
package projetoIntegrador;

public class Cliente {
    private String nome;
    private String email;
    private String cpf;
    private String dataNascimento;
    private String telefone;
    private String telefoneResponsavel;
    private String estado;
    private String cidade;
    private String comoNosConheceu;
    private String login;

    public Cliente(String nome, String email, String cpf, String dataNascimento, String telefone, String telefoneResponsavel, String estado, String cidade, String comoNosConheceu) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.telefoneResponsavel = telefoneResponsavel;
        this.estado = estado;
        this.cidade = cidade;
        this.comoNosConheceu = comoNosConheceu;
        this.login = gerarLogin(nome);
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

    public String getComoNosConheceu() {
        return comoNosConheceu;
    }

    public void setComoNosConheceu(String comoNosConheceu) {
        this.comoNosConheceu = comoNosConheceu;
    }

    public String getLogin() {
        return login;
    }

    // Método para gerar o login com base no nome
    private String gerarLogin(String nome) {
        // Implemente a geração do login com base no nome, se necessário
        return ""; // Este é um exemplo, você pode adaptá-lo conforme suas necessidades
    }
}

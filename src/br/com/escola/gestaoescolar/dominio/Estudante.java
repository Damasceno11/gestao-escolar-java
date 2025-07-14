package br.com.escola.gestaoescolar.dominio;

public class Estudante {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;

    public Estudante(String nome, String telefone, String endereco, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }


}

package br.com.escola.gestaoescolar.model;

import java.util.Objects;

public class Estudante {

    private Integer codigo;  // Agora é Integer, gerado pelo banco
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private Curso curso;

    public Estudante(Integer codigo, String nome, String cpf, String email, String telefone, String endereco, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.curso = curso;
    }

    public Estudante(String nome, String cpf, String email, String telefone, String endereco, Curso curso) {
        this(null, nome, cpf, email, telefone, endereco, curso);
    }

    // Getters e Setters

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Estudante)) return false;
        Estudante other = (Estudante) o;
        return Objects.equals(cpf, other.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cpf);
    }
}

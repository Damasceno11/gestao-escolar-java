package br.com.escola.gestaoescolar.dominio;

public class Curso {
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private Nivel nivel;



    public Curso(String codigo, String nome, int cargaHoraria, Nivel nivel) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.nivel = nivel;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;

    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public Nivel getNivel() {
        return nivel;
    }
}

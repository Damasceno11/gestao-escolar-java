package br.com.escola.gestaoescolar.model;

import java.time.LocalDate;

public class Turma {

    private String codigo;
    private Curso curso;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Periodo periodo;

    public Turma(String codigo, Curso curso, LocalDate dataInicio, LocalDate dataFim, Periodo periodo) {
        this.codigo = codigo;
        this.curso = curso;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.periodo = periodo;
    }

    public String getCodigo() {
        return codigo;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public Periodo getPeriodo() {
        return periodo;
    }
}

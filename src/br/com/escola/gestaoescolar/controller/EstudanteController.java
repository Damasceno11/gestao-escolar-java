package br.com.escola.gestaoescolar.controller;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.dao.EstudanteDAO;
import br.com.escola.gestaoescolar.model.Estudante;

import java.util.List;

public class EstudanteController {

    private final EstudanteDAO estudanteDAO = new EstudanteDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    public void cadastrar(Estudante estudante) {
        // Não valida código, pois será gerado no banco
        if (estudante.getNome() == null || estudante.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (estudante.getCpf() == null || estudante.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório!");
        }
        if (estudante.getCurso() == null || cursoDAO.buscarPorCodigo(estudante.getCurso().getCodigo()) == null) {
            throw new IllegalArgumentException("Curso inválido!");
        }
        estudanteDAO.inserir(estudante);
    }

    public List<Estudante> listar() {
        return estudanteDAO.listar();
    }

    public boolean excluir(Integer codigo) {
        return estudanteDAO.excluir(codigo);
    }
}

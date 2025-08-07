package br.com.escola.gestaoescolar.controller;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.dao.EstudanteDAO;
import br.com.escola.gestaoescolar.model.Estudante;

import java.util.List;

public class EstudanteController {

    private final EstudanteDAO estudanteDAO = new EstudanteDAO();
    private final CursoDAO cursoDAO = new CursoDAO();

    public void cadastrar(Estudante estudante) {
        validarEstudante(estudante, false);
        estudanteDAO.inserir(estudante);
    }

    public void atualizar(Estudante estudante) {
        validarEstudante(estudante, true);

        // Verificar se o estudante existe antes de atualizar
        Estudante existente = estudanteDAO.buscarPorCodigo(estudante.getCodigo());
        if (existente == null) {
            throw new IllegalArgumentException("Estudante com código " + estudante.getCodigo() + " não encontrado.");
        }

        estudanteDAO.atualizar(estudante);
    }

    public List<Estudante> listar() {
        return estudanteDAO.listar();
    }

    public boolean excluir(Integer codigo) {
        return estudanteDAO.excluir(codigo);
    }

    private void validarEstudante(Estudante estudante, boolean isAtualizacao) {
        if (isAtualizacao) {
            if (estudante.getCodigo() == null) {
                throw new IllegalArgumentException("Código do estudante é obrigatório para atualização.");
            }
        }

        if (estudante.getNome() == null || estudante.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }

        if (estudante.getCpf() == null || estudante.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório!");
        }

        if (estudante.getCurso() == null || cursoDAO.buscarPorCodigo(estudante.getCurso().getCodigo()) == null) {
            throw new IllegalArgumentException("Curso inválido!");
        }
    }
}

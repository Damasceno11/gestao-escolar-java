package br.com.escola.gestaoescolar.controller;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.dao.TurmaDAO;
import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Turma;

import java.util.List;

public class TurmaController {

    private final TurmaDAO turmaDAO;
    private final CursoDAO cursoDAO;

    public TurmaController() {
        this.turmaDAO = new TurmaDAO();
        this.cursoDAO = new CursoDAO();
    }

    public void cadastrar(Turma turma) {
        if (turma == null) throw new IllegalArgumentException("Turma não pode ser nula.");
        if (turma.getCodigo() == null || turma.getCodigo().isBlank())
            throw new IllegalArgumentException("Código é obrigatório.");
        if (turma.getCurso() == null)
            throw new IllegalArgumentException("Curso é obrigatório.");
        if (turma.getDataInicio() == null)
            throw new IllegalArgumentException("Data de início é obrigatória.");
        if (turma.getDataFim() == null)
            throw new IllegalArgumentException("Data de fim é obrigatória.");
        if (turma.getPeriodo() == null)
            throw new IllegalArgumentException("Período é obrigatório.");

        // Valida curso no banco
        Curso cursoBanco = cursoDAO.buscarPorCodigo(turma.getCurso().getCodigo());
        if (cursoBanco == null) {
            throw new IllegalArgumentException("Curso inválido.");
        }

        turmaDAO.inserir(turma);
    }

    public List<Turma> listar() {
        return turmaDAO.listar();
    }

    public boolean excluir(String codigo) {
        return turmaDAO.excluir(codigo);
    }
}

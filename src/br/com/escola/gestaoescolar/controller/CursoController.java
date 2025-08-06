package br.com.escola.gestaoescolar.controller;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.model.Curso;

import java.util.List;

public class CursoController {

    private final CursoDAO cursoDAO;

    public CursoController() {
        this.cursoDAO = new CursoDAO();
    }

    public void cadastrar(Curso curso) {
        if (curso == null) throw new IllegalArgumentException("Curso não pode ser nulo.");
        if (curso.getCodigo() == null || curso.getCodigo().isBlank())
            throw new IllegalArgumentException("Código é obrigatório.");
        if (curso.getNome() == null || curso.getNome().isBlank())
            throw new IllegalArgumentException("Nome é obrigatório.");
        if (curso.getCargaHoraria() <= 0)
            throw new IllegalArgumentException("Carga horária deve ser maior que 0.");
        if (curso.getNivel() == null)
            throw new IllegalArgumentException("Nível é obrigatório.");

        if (cursoDAO.buscarPorCodigo(curso.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe um curso com esse código.");
        }

        cursoDAO.inserir(curso);
    }

    public List<Curso> listar() {
        return cursoDAO.listar();
    }

    public Curso buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) return null;
        return cursoDAO.buscarPorCodigo(codigo);
    }

    public boolean excluir(String codigo) {
        return cursoDAO.excluir(codigo);
    }
}

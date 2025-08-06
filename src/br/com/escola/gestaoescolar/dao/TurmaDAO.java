package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Periodo;
import br.com.escola.gestaoescolar.model.Turma;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private final CursoDAO cursoDAO = new CursoDAO();

    public void inserir(Turma turma) {
        String sql = "INSERT INTO turma (codigo, data_inicio, data_fim, periodo, curso_codigo) VALUES (?, ?, ?, ?::periodo_enum, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getCodigo());
            stmt.setDate(2, Date.valueOf(turma.getDataInicio()));
            stmt.setDate(3, Date.valueOf(turma.getDataFim()));
            stmt.setString(4, turma.getPeriodo().name());
            stmt.setString(5, turma.getCurso().getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir turma: " + e.getMessage(), e);
        }
    }

    public List<Turma> listar() {
        List<Turma> lista = new ArrayList<>();
        String sql = "SELECT codigo, data_inicio, data_fim, periodo, curso_codigo FROM turma ORDER BY codigo";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = cursoDAO.buscarPorCodigo(rs.getString("curso_codigo"));

                lista.add(new Turma(
                        rs.getString("codigo"),
                        curso,
                        rs.getDate("data_inicio").toLocalDate(),
                        rs.getDate("data_fim").toLocalDate(),
                        Periodo.valueOf(rs.getString("periodo"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar turma: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean excluir(String codigo) {
        String sql = "DELETE FROM turma WHERE codigo = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir turma: " + e.getMessage(), e);
        }
    }
}

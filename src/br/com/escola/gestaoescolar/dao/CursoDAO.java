package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Nivel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void inserir(Curso curso) {
        String sql = "INSERT INTO curso (codigo, nome, carga_horaria, nivel) VALUES (?, ?, ?, ?::nivel_enum)";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getCodigo());
            stmt.setString(2, curso.getNome());
            stmt.setInt(3, curso.getCargaHoraria());
            // Usar setObject com Types.OTHER para enum PostgreSQL
            stmt.setString(4, curso.getNivel().name()); // Usar enum sem acento para banco
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir curso no PostgreSQL: " + e.getMessage(), e);
        }
    }

    public List<Curso> listar() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT codigo, nome, carga_horaria, nivel FROM curso ORDER BY codigo";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Curso(
                        rs.getString("codigo"),
                        rs.getString("nome"),
                        rs.getInt("carga_horaria"),
                        Nivel.fromString(rs.getString("nivel"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar curso: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean excluir(String codigo) {
        String sql = "DELETE FROM curso WHERE codigo = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir curso: " + e.getMessage(), e);
        }
    }

    public Curso buscarPorCodigo(String codigo) {
        String sql = "SELECT codigo, nome, carga_horaria, nivel FROM curso WHERE codigo = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Curso(
                            rs.getString("codigo"),
                            rs.getString("nome"),
                            rs.getInt("carga_horaria"),
                            Nivel.fromString(rs.getString("nivel"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage(), e);
        }
        return null;
    }
}

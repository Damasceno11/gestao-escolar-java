package br.com.escola.gestaoescolar.dao;

import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Estudante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudanteDAO {

    public void inserir(Estudante estudante) {
        String sql = "INSERT INTO estudante (nome, cpf, email, telefone, endereco, curso_codigo) VALUES (?, ?, ?, ?, ?, ?) RETURNING codigo";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudante.getNome());
            stmt.setString(2, estudante.getCpf());
            stmt.setString(3, estudante.getEmail());
            stmt.setString(4, estudante.getTelefone());
            stmt.setString(5, estudante.getEndereco());
            stmt.setString(6, estudante.getCurso().getCodigo());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estudante.setCodigo(rs.getInt("codigo"));
                } else {
                    throw new SQLException("Falha ao obter o código do estudante inserido.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir estudante: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Estudante estudante) {
        String sql = "UPDATE estudante SET nome = ?, cpf = ?, email = ?, telefone = ?, endereco = ?, curso_codigo = ? WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estudante.getNome());
            stmt.setString(2, estudante.getCpf());
            stmt.setString(3, estudante.getEmail());
            stmt.setString(4, estudante.getTelefone());
            stmt.setString(5, estudante.getEndereco());
            stmt.setString(6, estudante.getCurso().getCodigo());
            stmt.setInt(7, estudante.getCodigo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar estudante: " + e.getMessage(), e);
        }
    }

    public Estudante buscarPorCodigo(Integer codigo) {
        String sql = """
            SELECT e.codigo, e.nome, e.cpf, e.email, e.telefone, e.endereco, e.curso_codigo, c.nome AS nome_curso 
            FROM estudante e 
            INNER JOIN curso c ON e.curso_codigo = c.codigo
            WHERE e.codigo = ?
        """;

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Curso curso = new Curso(
                            rs.getString("curso_codigo"),
                            rs.getString("nome_curso"),
                            0,
                            null
                    );

                    return new Estudante(
                            rs.getInt("codigo"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("endereco"),
                            curso
                    );
                } else {
                    return null;  // Estudante não encontrado
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar estudante por código: " + e.getMessage(), e);
        }
    }

    public List<Estudante> listar() {
        List<Estudante> estudantes = new ArrayList<>();
        String sql = """
            SELECT e.codigo, e.nome, e.cpf, e.email, e.telefone, e.endereco, e.curso_codigo, c.nome AS nome_curso 
            FROM estudante e 
            INNER JOIN curso c ON e.curso_codigo = c.codigo
            ORDER BY e.codigo
        """;

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getString("curso_codigo"),
                        rs.getString("nome_curso"),
                        0,
                        null
                );

                Estudante estudante = new Estudante(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        curso
                );

                estudantes.add(estudante);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar estudantes: " + e.getMessage(), e);
        }
        return estudantes;
    }

    public boolean excluir(Integer codigo) {
        String sql = "DELETE FROM estudante WHERE codigo = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir estudante: " + e.getMessage(), e);
        }
    }
}

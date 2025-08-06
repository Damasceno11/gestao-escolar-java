package br.com.escola.gestaoescolar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgres {

    private static final String URL = "jdbc:postgresql://localhost:5432/gestao_curso";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado!", e);
        }
    }

    public static void testarConexao() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Conexão com PostgreSQL estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Falha na conexão com PostgreSQL: " + e.getMessage());
        }
    }
}

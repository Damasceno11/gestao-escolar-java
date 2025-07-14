package br.com.escola.gestaoescolar.funcionalidades;

import br.com.escola.gestaoescolar.dominio.Curso;
import br.com.escola.gestaoescolar.dominio.Nivel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CadastroDeCurso {
    private Path arquivo;

    public CadastroDeCurso() {
        try{
            arquivo = Path.of("cursos.csv");
            if(!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        }catch (Exception e) {
            System.out.println("Erro ao criar arquivo de cursos");
        }
    }

    public void cadastrar(Curso curso) {
        if (curso.getCodigo().isBlank()) throw new IllegalArgumentException("Código é obrigatório.");
        if (curso.getNome().isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (curso.getCargaHoraria() <= 0) throw new IllegalArgumentException("Carga horária deve ser maior que 0.");
        if (curso.getNivel() == null) throw new IllegalArgumentException("Nivel é obrigatório.");

        if (codigoJaExiste(curso.getCodigo())) {
            throw new IllegalArgumentException("Já existe um curso com esse código");
        }

        try {
            String linha = curso.getCodigo() + "," +
                    curso.getNome() + "," +
                    curso.getCargaHoraria() + "," +
                    curso.getNivel().getRotulo() + "\n";

            Files.writeString(arquivo, linha, StandardOpenOption.APPEND);

            System.out.println("Curso cadastrado com sucesso!");
        }catch (IOException e) {
            System.out.println("Erro ao cadastrar curso: " + e.getMessage());
        }
    }

    public ArrayList<Curso> listar() {
        var lista = new ArrayList<Curso>();
        try {
            var linhas = Files.readAllLines(arquivo);
            for (var linha : linhas) {
                var campos = linha.split(",");

                var curso = new Curso(
                        campos[0],
                        campos[1],
                        Integer.parseInt(campos[2]),
                        Nivel.fromString(campos[3])
                );
                lista.add(curso);
            }
        }catch (IOException e) {
            System.out.println("Erro ao ler curso do arquivo: " + e.getMessage());
        }
        return lista;
    }

    public boolean codigoJaExiste(String codigo) {
        return listar().stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(codigo));
    }

    public Curso buscarPorCodigo(String codigo) {
        return listar().stream()
                .filter(c ->c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }
}

package br.com.escola.gestaoescolar.funcionalidades;

import br.com.escola.gestaoescolar.dominio.Curso;
import br.com.escola.gestaoescolar.dominio.Periodo;
import br.com.escola.gestaoescolar.dominio.Turma;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;

public class CadastroDeTurma {

    private Path arquivo;
    private CadastroDeCurso cadastroDeCurso;

    public CadastroDeTurma(CadastroDeCurso cadastroDeCurso) {
        this.cadastroDeCurso = cadastroDeCurso;
        try {
            arquivo = Path.of(System.getProperty("user.dir"),"turmas.csv");
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar arquivo de turma! " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cadastrar(Turma turma) {
        // Validações
        if (turma.getCodigo().isBlank()) {
            throw new IllegalArgumentException("Código é obrigatório!");
        }

        if (turma.getCurso() == null) {
            throw new IllegalArgumentException("Curso é obrigatório!");
        }

        if (turma.getDataInicio() == null) {
            throw new IllegalArgumentException("Data de início é obrigatório!");
        }

        if (turma.getDataFim() == null) {
            throw new IllegalArgumentException("Data de fim é obrigatório!");
        }

        if (turma.getPeriodo() == null) {
            throw new IllegalArgumentException("Periodo é obrigatório!");
        }

        try {
            String linha = turma.getCodigo() + "," +
                    turma.getCurso().getCodigo() + "," +
                    turma.getDataInicio() + "," +
                    turma.getDataFim() + "," +
                    turma.getPeriodo().getRotulo() + "\n";

            Files.writeString(arquivo, linha, StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar turma no arquivo! " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Turma> listar() {
        var lista = new ArrayList<Turma>();

        try {
            var linhas = Files.readAllLines(arquivo);

            for (var linha : linhas) {
                try {
                    linha = linha.strip(); //remove espaços e quebras extras
                    var campos = linha.split(",");

                    if (campos.length != 5) {
                        System.out.println("Linha ignorada (inválida): " + linha);
                        continue;
                    }
                    String codigo = campos[0].trim();
                    String codigoCurso = campos[1].trim();
                    LocalDate dataInicio = LocalDate.parse(campos[2].trim());
                    LocalDate dataFim = LocalDate.parse(campos[3].trim());
                    Periodo periodo = Periodo.fromString(campos[4].trim());

                    Curso curso = cadastroDeCurso.buscarPorCodigo(codigoCurso);

                    if (curso == null) {
                        System.out.println("Curso não encontrado para a turma: " + codigo );
                        continue;
                    }

                    var turma = new Turma(codigo, curso, dataInicio, dataFim,periodo);
                    lista.add(turma);

                }catch (Exception e) {
                    System.out.println("Erro ao processar linha: " +linha);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar turmas do arquivo!");
            e.printStackTrace();
        }

        return lista;
    }

    public boolean codigoJaExiste(String codigo) {
        return listar().stream().anyMatch(t -> t.getCodigo().equalsIgnoreCase(codigo));
    }
}


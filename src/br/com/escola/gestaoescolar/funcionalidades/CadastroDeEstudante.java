package br.com.escola.gestaoescolar.funcionalidades;

import br.com.escola.gestaoescolar.dominio.Estudante;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CadastroDeEstudante {

    private Path arquivo;

    public CadastroDeEstudante() {
        try {
            arquivo = Path.of("estudantes.csv");
            if (!Files.exists(arquivo)) {
                Files.createFile(arquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar arquivo de estudante!");
        }
    }

    public void cadastrar(Estudante estudante) {
        // Validações obrigatórias

        if (estudante.getNome().isBlank()) {
            throw new IllegalArgumentException("Campo nome é obrigatório!");
        }
        if (estudante.getCpf().isBlank()) {
            throw new IllegalArgumentException("Campo cpf é obrigatório!");
        }
        if (estudante.getEmail().isBlank()) {
            throw new IllegalArgumentException("Campo email é obrigatório!");
        }
        if (estudante.getTelefone().isBlank()) {
            throw new IllegalArgumentException("Campo telefone é obrigatório!");
        }
        if (estudante.getEndereco().isBlank()) {
            throw new IllegalArgumentException("Campo endereço é obrigatório!");
        }

        // Validação de CPF e Email únicos
        if (cpfJaExiste(estudante.getCpf())){
            throw new IllegalArgumentException("Já existe um estudante com esse CPF!");
        }
        if (emailJaExiste(estudante.getEmail())){
            throw new IllegalArgumentException("Já existe um estudante com esse email ");
        }

        try {
             String linha = estudante.getNome() + "," +
                            estudante.getTelefone() + "," +
                            estudante.getEndereco() + "," +
                            estudante.getCpf() + "," +
                            estudante.getEmail() + "\n";

            Files.writeString(arquivo,linha, StandardOpenOption.APPEND);
            System.out.println("Estudante cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar estudante no arquivo!" + e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<Estudante> listar() {
        var lista = new ArrayList<Estudante>();
        try {
            var linhas = Files.readAllLines(arquivo);

            for (var linha : linhas) {
                var campos = linha.split(",");
                if (campos.length == 5) {
                    var estudante = new Estudante(
                            campos[0],
                            campos[1],
                            campos[2],
                            campos[3],
                            campos[4]
                    );
                    lista.add(estudante);
                }

            }

            return lista;

        } catch (Exception e) {
            System.out.println("Erro ao carregar estudante do arquivo!");

        }
return lista;
    }
    public boolean cpfJaExiste(String cpf) {
        return listar().stream()
                .anyMatch(e -> e.getCpf().equalsIgnoreCase(cpf));
    }

    public boolean emailJaExiste(String email) {
        return listar().stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
    }
}



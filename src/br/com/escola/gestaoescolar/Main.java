package br.com.escola.gestaoescolar;

import br.com.escola.gestaoescolar.dominio.*;
import br.com.escola.gestaoescolar.funcionalidades.CadastroDeCurso;
import br.com.escola.gestaoescolar.funcionalidades.CadastroDeEstudante;
import br.com.escola.gestaoescolar.funcionalidades.CadastroDeTurma;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        //Exception -> Checked Exception
        //Runtime Exception -> Unchecked Exception
        var opcao = -1;

        while (opcao != 0) {
            exibirMenu();

            try {
                opcao = Integer.parseInt(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número de 0 a 4.");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarTurma(leitor);
                case 2 -> cadastrarEstudantes(leitor);
                case 3 -> {
                    try {
                        listarTurmas();
                    } catch (IOException e) {
                        System.out.println("Erro ao listar turmas: " + e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        listarEstudantes();
                    } catch (IOException e) {
                        System.out.println("Erro ao listar estrudantes: " + e.getMessage());
                    }
                }
                case 5 -> cadastrarCurso(leitor);
                case 6 -> listarCursos();
                case 0 -> System.out.println("Programa encerrado...");
                default -> System.out.println("Opção inválida, digite entre 0 e 4.");
            }
        }

        leitor.close();
    }

    private static void exibirMenu() {
        System.out.println("\n----- MENU GESTÃO ESCOLAR -----");
        System.out.println("1 - Cadastrar turma");
        System.out.println("2 - Cadastrar estudante");
        System.out.println("3 - Listar turmas");
        System.out.println("4 - Listar estudantes");
        System.out.println("5 - Cadastar curso");
        System.out.println("6 - Listar curso");
        System.out.println("0 - Sair");
        System.out.println("Escola uma opção: ");
    }

    private static void cadastrarTurma(Scanner leitor) {
        System.out.println("Digite o codigo da turma: ");
        var codigo = leitor.nextLine();

        System.out.println("Digite o código do curso da turma: ");
        var codigoCurso = leitor.nextLine();

        System.out.println("Digite a data de início (yyyy-MM-dd): ");
        var dataInicioStr = leitor.nextLine();

        System.out.println("Digite a data de fim (yyyy-MM-dd): ");
        var dataFimStr = leitor.nextLine();

        System.out.println("Digite o período (MATUTINO, VESPERTINO, NOTURNO): ");
        var periodoStr = leitor.nextLine();

        LocalDate dataInicio;
        LocalDate dataFim;
        Periodo periodo;

        try {
            dataInicio = LocalDate.parse(dataInicioStr);
            dataFim = LocalDate.parse(dataFimStr);
            periodo = Periodo.fromString(periodoStr);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use yyyy-MM-dd." + e.getMessage());
            return;
        }catch (IllegalArgumentException e) {
            System.out.println("Período inválido. Use MATUTINO, VESPERTINO OU NOTURNO");
            return;
        }

        CadastroDeCurso cadastroDeCurso = new CadastroDeCurso();
        Curso curso = cadastroDeCurso.buscarPorCodigo(codigoCurso);

        if (curso == null) {
            System.out.println("Curso não encontrado com código: " + codigoCurso);
            return;
        }

        Turma turma = new Turma(codigo, curso, dataInicio, dataFim, periodo);
        CadastroDeTurma cadastroturma = new CadastroDeTurma(cadastroDeCurso);

        try {
            cadastroturma.cadastrar(turma);
            System.out.println("Turma cadastrada com sucesso!");
        }catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
        }
    }

    private static void cadastrarEstudantes(Scanner leitor) {
        System.out.println("Digite o nome do estudante: ");
        var nome = leitor.nextLine();

        System.out.println("Digite o cpf do estudante: ");
        var cpf = leitor.nextLine();

        System.out.println("Digite o email do estudante: ");
        var email = leitor.nextLine();

        System.out.println("Digite o telefone do estudante: ");
        var telefone = leitor.nextLine();

        System.out.println("Digite o endereço do estudante: ");
        var endereco = leitor.nextLine();

        Estudante estudante = new Estudante(nome, cpf, email, telefone, endereco);
        CadastroDeEstudante cadastro = new CadastroDeEstudante();

        try {
            cadastro.cadastrar(estudante);
            System.out.println("Estudante cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar estudante: " + e.getMessage());
        }
    }

    private static void cadastrarCurso(Scanner leitor) {
        System.out.println("Digite o codigo do curso: ");
        var codigo = leitor.nextLine();

        System.out.println("Digite o nome do curso: ");
        var nome = leitor.nextLine();

        System.out.println("Digite a carga horaria do curso: ");
        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(leitor.nextLine());
        }catch (NumberFormatException e) {
            System.out.println("Carga horária invalida.");
            return;
        }

        System.out.println("Digite o nível do curso (BASICO, INTERMEDIARIO, AVANÇADO: )");
        var nivelStr = leitor.nextLine();

        Nivel nivel;
        try{
            nivel = Nivel.fromString(nivelStr);
        }catch (IllegalArgumentException e) {
            System.out.println("Nível invalido");
            return;
        }

        Curso curso = new Curso(codigo, nome, cargaHoraria, nivel);
        CadastroDeCurso cadastro = new CadastroDeCurso();

        try {
            cadastro.cadastrar(curso);
        }catch (IllegalArgumentException e) {
            System.out.println("Errro ao cadastrar curso: " + e.getMessage());
        }
    }

    private static void listarTurmas() throws IOException {
        CadastroDeCurso cadastroDeCurso = new CadastroDeCurso();
        CadastroDeTurma cadastro = new CadastroDeTurma(cadastroDeCurso);
        var turmasCadastradas = cadastro.listar();

        if (turmasCadastradas.isEmpty()) {
            System.out.println("Nenhuma turma cadastrada.");
        } else {
            System.out.println("\n--- TURMAS CADASTRADAS ---");
            for (var turma : turmasCadastradas) {
                System.out.println(turma.getCodigo() + "," +
                        turma.getCurso().getNome()+ "," +
                         turma.getDataInicio() + "," +
                        turma.getDataFim() + "," +
                        turma.getPeriodo().getRotulo());
            }
        }
    }

    private static void listarEstudantes() throws IOException {
        CadastroDeEstudante cadastro = new CadastroDeEstudante();
        var estudantesCadastrados = cadastro.listar();
        if (estudantesCadastrados.isEmpty()) {
            System.out.println("Nenhum estudante cadastrado.");
        } else {
            System.out.println("\n--- ESTUDANTES CADASTRADOS ---");
        }
        for (var estudante : estudantesCadastrados) {
            System.out.println(estudante.getNome() + "," +
                    estudante.getCpf() + "," +
                    estudante.getEmail() + "," +
                    estudante.getTelefone() + "," +
                    estudante.getEndereco());
        }

    }

    private static void listarCursos() {
        CadastroDeCurso cadastro = new CadastroDeCurso();
        var cursosCadastrados = cadastro.listar();
        if (cursosCadastrados.isEmpty()) {
            System.out.println("Nenhum curso cadastrado.");
        }else {
            System.out.println("\n--- CURSOS CADASTRADOS ---");
            for (var curso : cursosCadastrados) {
                System.out.println(curso.getCodigo() + "," +
                        curso.getNome() + "," +
                        curso.getCargaHoraria() + "," +
                        curso.getNivel().getRotulo());
            }
        }
    }

}
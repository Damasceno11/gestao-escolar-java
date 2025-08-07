package br.com.escola.gestaoescolar.view;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel painelCentral;

    private TelaCadastroCurso telaCadastroCurso;
    private TelaListarCursos telaListarCursos;
    private TelaCadastroEstudante telaCadastroEstudante;
    private TelaListarEstudantes telaListarEstudantes;
    private TelaCadastroTurma telaCadastroTurma;
    private TelaListarTurmas telaListarTurmas;

    public static final String TELA_CADASTRO_CURSO = "cadastroCurso";
    public static final String TELA_LISTAR_CURSOS = "listarCursos";
    public static final String TELA_CADASTRO_ESTUDANTE = "cadastroEstudante";
    public static final String TELA_LISTAR_ESTUDANTES = "listarEstudantes";
    public static final String TELA_CADASTRO_TURMA = "cadastroTurma";
    public static final String TELA_LISTAR_TURMAS = "listarTurmas";

    public MainGUI() {
        setTitle("Gestão Escolar - Interface Gráfica");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        telaCadastroCurso = new TelaCadastroCurso();
        telaListarCursos = new TelaListarCursos(telaCadastroCurso,this);
        telaCadastroEstudante = new TelaCadastroEstudante();
        telaListarEstudantes = new TelaListarEstudantes(telaCadastroEstudante, this);
        telaCadastroTurma = new TelaCadastroTurma();
        telaListarTurmas = new TelaListarTurmas();

        painelCentral.add(telaCadastroCurso, TELA_CADASTRO_CURSO);
        painelCentral.add(telaListarCursos, TELA_LISTAR_CURSOS);
        painelCentral.add(telaCadastroEstudante, TELA_CADASTRO_ESTUDANTE);
        painelCentral.add(telaListarEstudantes, TELA_LISTAR_ESTUDANTES);
        painelCentral.add(telaCadastroTurma, TELA_CADASTRO_TURMA);
        painelCentral.add(telaListarTurmas, TELA_LISTAR_TURMAS);

        JPanel menuLateral = new JPanel(new GridLayout(10, 1, 5, 5));
        JButton btnCadastroCurso = new JButton("Cadastro Curso");
        JButton btnListarCursos = new JButton("Listar Cursos");
        JButton btnCadastroEstudante = new JButton("Cadastro Estudante");
        JButton btnListarEstudantes = new JButton("Listar Estudantes");
        JButton btnCadastroTurma = new JButton("Cadastro Turma");
        JButton btnListarTurmas = new JButton("Listar Turmas");
        JButton btnSair = new JButton("Sair");

        menuLateral.add(btnCadastroCurso);
        menuLateral.add(btnListarCursos);
        menuLateral.add(btnCadastroEstudante);
        menuLateral.add(btnListarEstudantes);
        menuLateral.add(btnCadastroTurma);
        menuLateral.add(btnListarTurmas);
        menuLateral.add(btnSair);

        btnCadastroCurso.addActionListener(e -> mostrarTela(TELA_CADASTRO_CURSO));
        btnListarCursos.addActionListener(e -> {
            telaListarCursos.carregarCursos();
            mostrarTela(TELA_LISTAR_CURSOS);
        });
        btnCadastroEstudante.addActionListener(e -> mostrarTela(TELA_CADASTRO_ESTUDANTE));
        btnListarEstudantes.addActionListener(e -> {
            telaListarEstudantes.carregarEstudantes();
            mostrarTela(TELA_LISTAR_ESTUDANTES);
        });
        btnCadastroTurma.addActionListener(e -> mostrarTela(TELA_CADASTRO_TURMA));
        btnListarTurmas.addActionListener(e -> {
            telaListarTurmas.carregarTurmas();
            mostrarTela(TELA_LISTAR_TURMAS);
        });
        btnSair.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(menuLateral, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(painelCentral, nomeTela);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGUI::new);
    }
}

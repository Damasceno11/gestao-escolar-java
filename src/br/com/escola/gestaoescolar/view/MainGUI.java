package br.com.escola.gestaoescolar.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {

    // Agora o CardLayout e o painel central são atributos
    private CardLayout cardLayout;
    private JPanel painelCentral;

    public MainGUI() {
        setTitle("Gestão Escolar - Interface Gráfica");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Painel central com CardLayout
        cardLayout = new CardLayout();
        painelCentral = new JPanel(cardLayout);

        // Criar as telas
        TelaCadastroCurso telaCadastroCurso = new TelaCadastroCurso();
        TelaListarCursos telaListarCursos = new TelaListarCursos();

        TelaCadastroEstudante telaCadastroEstudante = new TelaCadastroEstudante();
        TelaListarEstudantes telaListarEstudantes = new TelaListarEstudantes();

        TelaCadastroTurma telaCadastroTurma = new TelaCadastroTurma();
        TelaListarTurmas telaListarTurmas = new TelaListarTurmas();

        // Adidionar telas ao painel central
        painelCentral.add(telaCadastroCurso, "cadastroCurso");
        painelCentral.add(telaListarCursos, "listarCursos");

        painelCentral.add(telaCadastroEstudante, "cadastroEstudante");
        painelCentral.add(telaListarEstudantes, "listarEstudantes");

        painelCentral.add(telaCadastroTurma, "cadastroTurma");
        painelCentral.add(telaListarTurmas, "listarTurmas");

        // Criar menu lateral
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new GridLayout(10, 1, 5, 5));

        JButton btnCadastroCurso = new JButton("Cadastro Curso");
        JButton btnListarCursos = new JButton("Listar Cursos");

        JButton btnCadastroEstudante = new JButton("Cadastro Estudante");
        JButton btnListarEstudantes = new JButton("Listar Estudantes");

        JButton btnCadastroTurma = new JButton("Cadastro Turma");
        JButton btnListarTurmas = new JButton("Listar Turmas");

        JButton btnSair = new JButton("Sair");

        // Adicionar botões no menu lateral

        menuLateral.add(btnCadastroCurso);
        menuLateral.add(btnListarCursos);

        menuLateral.add(btnCadastroEstudante);
        menuLateral.add(btnListarEstudantes);

        menuLateral.add(btnCadastroTurma);
        menuLateral.add(btnListarTurmas);

        menuLateral.add(btnSair);

        // Eventos do menu
        btnCadastroCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(painelCentral, "cadastroCurso");
            }
        });

        btnListarCursos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Atualiza tabela antes de mostrar
                telaListarCursos.carregarCursos();
                cardLayout.show(painelCentral, "listarCursos");
            }
        });

        btnCadastroEstudante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(painelCentral, "cadastroEstudante");
            }
        });

        btnListarEstudantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaListarEstudantes.carregarEstudantes();
                cardLayout.show(painelCentral, "listarEstudantes");
            }
        });

        btnCadastroTurma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(painelCentral, "cadastroTurma");
            }
        });

        btnListarTurmas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telaListarTurmas.carregarTurmas();
                cardLayout.show(painelCentral, "listarTurmas");
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Layout principal
        setLayout(new BorderLayout());
        add(menuLateral, BorderLayout.WEST);
        add(painelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // Executa na thread correta do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainGUI();
            }
        });
    }
}

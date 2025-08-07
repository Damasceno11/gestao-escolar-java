package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarCursos extends JPanel {

    private JTable tabelaCursos;
    private DefaultTableModel modeloTabela;
    private JButton btnExcluir;
    private JButton btnEditar;

    private final CursoDAO cursoDAO = new CursoDAO();

    // Referência para permitir comunicação com a tela de cadastro
    private TelaCadastroCurso telaCadastroCurso;
    private MainGUI mainGUI; // Para manipular movimentação das telas no CardLayout

    public TelaListarCursos(TelaCadastroCurso telaCadastroCurso, MainGUI mainGUI) {
        this.telaCadastroCurso = telaCadastroCurso;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());

        modeloTabela = new DefaultTableModel(
                new Object[]{"Código", "Nome", "Carga Horária", "Nível"}, 0
        );
        tabelaCursos = new JTable(modeloTabela);
        tabelaCursos.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabelaCursos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEditar = new JButton("Editar Curso");
        btnExcluir = new JButton("Excluir Curso");
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        btnExcluir.addActionListener(e -> excluirCurso());
        btnEditar.addActionListener(e -> editarCurso());
    }

    private void excluirCurso() {
        int linhaSelecionada = tabelaCursos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            String codigoCurso = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirma exclusão do curso selecionado?",
                    "Excluir Curso",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = cursoDAO.excluir(codigoCurso);
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Curso excluído com sucesso!");
                    carregarCursos();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir curso.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um curso para excluir.");
        }
    }

    private void editarCurso() {
        int linhaSelecionada = tabelaCursos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            String codigoCurso = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            Curso curso = cursoDAO.buscarPorCodigo(codigoCurso);
            if (curso != null) {
                telaCadastroCurso.carregarParaEditar(curso);
                mainGUI.mostrarTela(MainGUI.TELA_CADASTRO_CURSO);
            } else {
                JOptionPane.showMessageDialog(null, "Curso não encontrado para edição.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um curso para editar.");
        }
    }

    public void carregarCursos() {
        modeloTabela.setRowCount(0);
        List<Curso> cursos = cursoDAO.listar();
        for (Curso curso : cursos) {
            modeloTabela.addRow(new Object[]{
                    curso.getCodigo(),
                    curso.getNome(),
                    curso.getCargaHoraria(),
                    curso.getNivel().getRotulo()
            });
        }
    }
}

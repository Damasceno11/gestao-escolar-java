package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListarCursos extends JPanel {

    private JTable tabelaCursos;
    private DefaultTableModel modeloTabela;
    //private CursoController controller;
    private JButton btnExcluir;

    private final CursoDAO cursoDAO = new CursoDAO(); // Agora usando PostgreSQL

    public TelaListarCursos() {
        setLayout(new BorderLayout());

        //controller = new CursoController();

        modeloTabela = new DefaultTableModel(
                new Object[]{"Código", "Nome", "Carga Horária", "Nível"}, 0
        );
        tabelaCursos = new JTable(modeloTabela);
        tabelaCursos.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tabelaCursos);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir Curso");
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabelaCursos.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    String codigoCurso = (String) modeloTabela.getValueAt(linhaSelecionada, 0); // Agora String
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Confirma exclusão do curso selecionado?",
                            "Excluir Curso",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean sucesso = cursoDAO.excluir(codigoCurso); // Se quiser voltar para arquivo CSV so mudar DAO para controller novamente
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
        });
    }

    public void carregarCursos() {
        modeloTabela.setRowCount(0);
        List<Curso> cursos = cursoDAO.listar(); // Se quiser voltar para arquivo CSV so mudar cursoDAO para controller novamente
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

package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.dao.TurmaDAO;
import br.com.escola.gestaoescolar.model.Turma;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaListarTurmas extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    //private TurmaController controller;
    private TurmaDAO turmaDAO = new TurmaDAO();
    private JButton btnExcluir;

    public TelaListarTurmas() {
        setLayout(new BorderLayout());

        //controller = new TurmaController(new CursoController());

        modelo = new DefaultTableModel(
                new Object[]{"Código", "Início", "Fim", "Período", "Curso"}, 0
        );
        tabela = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir Turma");
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    String codigoTurma = (String) modelo.getValueAt(linhaSelecionada, 0); // Agora String
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Confirma exclusão da turma selecionada?",
                            "Excluir Turma",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean sucesso = turmaDAO.excluir(codigoTurma);
                        if (sucesso) {
                            JOptionPane.showMessageDialog(null, "Turma excluída com sucesso!");
                            carregarTurmas();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao excluir turma.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione uma turma para excluir.");
                }
            }
        });
    }

    public void carregarTurmas() {
        modelo.setRowCount(0);
        List<Turma> turmas = turmaDAO.listar();
        for (Turma t : turmas) {
            modelo.addRow(new Object[]{
                    t.getCodigo(),
                    t.getDataInicio(),
                    t.getDataFim(),
                    t.getPeriodo(),
                    t.getCurso().getCodigo()
            });
        }
    }
}

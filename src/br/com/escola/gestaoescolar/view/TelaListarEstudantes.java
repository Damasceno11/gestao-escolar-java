package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.dao.EstudanteDAO;
import br.com.escola.gestaoescolar.model.Estudante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListarEstudantes extends JPanel {

    private JTable tabelaEstudantes;
    private DefaultTableModel tabela;
    private EstudanteDAO estudanteDAO = new EstudanteDAO();
    private JButton btnExcluir;

    public TelaListarEstudantes() {
        setLayout(new BorderLayout());

        // Agora a primeira coluna é o CÓDIGO
        tabela = new DefaultTableModel(
                new Object[]{"Código", "Nome", "CPF", "Email", "Telefone", "Endereço"}, 0
        );
        tabelaEstudantes = new JTable(tabela);

        JScrollPane scrollPane = new JScrollPane(tabelaEstudantes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir Estudante");
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        btnExcluir.addActionListener(e -> excluirEstudante());
    }

    private void excluirEstudante() {
        int linhaSelecionada = tabelaEstudantes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Integer codigo = (Integer) tabela.getValueAt(linhaSelecionada, 0); // CÓDIGO como identificador
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirma exclusão do estudante selecionado?",
                    "Excluir Estudante",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = estudanteDAO.excluir(Integer.valueOf(codigo));
                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Estudante excluído com sucesso!");
                    carregarEstudantes();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir estudante.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um estudante para excluir.");
        }
    }

    public void carregarEstudantes() {
        tabela.setRowCount(0);
        List<Estudante> estudantes = estudanteDAO.listar();
        for (Estudante e : estudantes) {
            tabela.addRow(new Object[]{
                    e.getCodigo(),
                    e.getNome(),
                    e.getCpf(),
                    e.getEmail(),
                    e.getTelefone(),
                    e.getEndereco()
            });
        }
    }
}

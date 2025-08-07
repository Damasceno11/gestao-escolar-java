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
    private JButton btnEditar;
    private JButton btnExcluir;

    private TelaCadastroEstudante telaCadastroEstudante;
    private MainGUI mainGUI;

    public TelaListarEstudantes(TelaCadastroEstudante telaCadastroEstudante, MainGUI mainGUI) {
        this.telaCadastroEstudante = telaCadastroEstudante;
        this.mainGUI = mainGUI;

        setLayout(new BorderLayout());

        tabela = new DefaultTableModel(
                new Object[]{"Código", "Nome", "CPF", "Email", "Telefone", "Endereço"}, 0
        );
        tabelaEstudantes = new JTable(tabela);

        JScrollPane scrollPane = new JScrollPane(tabelaEstudantes);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnEditar = new JButton("Editar Estudante");
        btnExcluir = new JButton("Excluir Estudante");

        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        add(painelBotoes, BorderLayout.SOUTH);

        btnEditar.addActionListener(e -> editarEstudante());
        btnExcluir.addActionListener(e -> excluirEstudante());
    }

    private void excluirEstudante() {
        int linhaSelecionada = tabelaEstudantes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Integer codigo = (Integer) tabela.getValueAt(linhaSelecionada, 0);
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Confirma exclusão do estudante selecionado?",
                    "Excluir Estudante",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                boolean sucesso = estudanteDAO.excluir(codigo);
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

    private void editarEstudante() {
        int linhaSelecionada = tabelaEstudantes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            Integer codigo = (Integer) tabela.getValueAt(linhaSelecionada, 0);
            String nome = (String) tabela.getValueAt(linhaSelecionada, 1);
            String cpf = (String) tabela.getValueAt(linhaSelecionada, 2);
            String email = (String) tabela.getValueAt(linhaSelecionada, 3);
            String telefone = (String) tabela.getValueAt(linhaSelecionada, 4);
            String endereco = (String) tabela.getValueAt(linhaSelecionada,5);

            Estudante estudante = new Estudante(codigo, nome, cpf, email, telefone, endereco, null);

            // Chama o formulário de cadastro para editar
            telaCadastroEstudante.carregarParaEditar(estudante);

            // Trocar para tela cadastroEstudante via MainGUI
            mainGUI.mostrarTela(MainGUI.TELA_CADASTRO_ESTUDANTE);

        } else {
            JOptionPane.showMessageDialog(null, "Selecione um estudante para editar.");
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

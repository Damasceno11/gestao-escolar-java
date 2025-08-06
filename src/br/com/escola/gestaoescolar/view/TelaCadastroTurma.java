package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.controller.TurmaController;
import br.com.escola.gestaoescolar.dao.CursoDAO;
import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Periodo;
import br.com.escola.gestaoescolar.model.Turma;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaCadastroTurma extends JPanel {

    private JTextField txtCodigo, txtDataInicio, txtDataFim;
    private JComboBox<Periodo> cmbPeriodo;
    private JComboBox<Curso> cmbCurso;
    private JButton btnSalvar, btnCancelar;

    private final CursoDAO cursoDAO = new CursoDAO();
    private final TurmaController turmaController = new TurmaController();

    public TelaCadastroTurma() {
        setLayout(null);
        setPreferredSize(new Dimension(600, 400));

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 30, 150, 25);
        add(lblCodigo);

        JLabel lblDataInicio = new JLabel("Data Início (dd/MM/yyyy):");
        lblDataInicio.setBounds(30, 70, 200, 25);
        add(lblDataInicio);

        JLabel lblDataFim = new JLabel("Data Fim (dd/MM/yyyy):");
        lblDataFim.setBounds(30, 110, 200, 25);
        add(lblDataFim);

        JLabel lblPeriodo = new JLabel("Período:");
        lblPeriodo.setBounds(30, 150, 150, 25);
        add(lblPeriodo);

        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds(30, 190, 150, 25);
        add(lblCurso);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(240, 30, 200, 25);
        add(txtCodigo);

        txtDataInicio = new JTextField();
        txtDataInicio.setBounds(240, 70, 200, 25);
        add(txtDataInicio);

        txtDataFim = new JTextField();
        txtDataFim.setBounds(240, 110, 200, 25);
        add(txtDataFim);

        cmbPeriodo = new JComboBox<>(Periodo.values());
        cmbPeriodo.setBounds(240, 150, 200, 25);
        add(cmbPeriodo);

        cmbCurso = new JComboBox<>();
        cmbCurso.setBounds(240, 190, 200, 25);
        add(cmbCurso);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(100, 240, 100, 30);
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(250, 240, 100, 30);
        add(btnCancelar);

        carregarCursos();

        btnSalvar.addActionListener(e -> salvarTurma());
        btnCancelar.addActionListener(e -> limparCampos());
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoDAO.listar();
        cmbCurso.removeAllItems();
        for (Curso c : cursos) {
            cmbCurso.addItem(c);
        }
    }

    private void salvarTurma() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Curso cursoSelecionado = (Curso) cmbCurso.getSelectedItem();
            Periodo periodoSelecionado = (Periodo) cmbPeriodo.getSelectedItem();

            Turma turma = new Turma(
                    txtCodigo.getText(),
                    cursoSelecionado,
                    LocalDate.parse(txtDataInicio.getText(), formatter),
                    LocalDate.parse(txtDataFim.getText(), formatter),
                    periodoSelecionado
            );

            turmaController.cadastrar(turma);

            JOptionPane.showMessageDialog(this, "Turma cadastrada com sucesso!");
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtDataInicio.setText("");
        txtDataFim.setText("");
        cmbPeriodo.setSelectedIndex(0);
        cmbCurso.setSelectedIndex(0);
    }
}

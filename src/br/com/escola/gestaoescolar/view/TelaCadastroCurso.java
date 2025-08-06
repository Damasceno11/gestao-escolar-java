package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.controller.CursoController;
import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Nivel;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroCurso extends JPanel {

    private JTextField txtCodigo, txtNome, txtCargaHoraria;
    private JComboBox<String> cmbNivel;
    private JButton btnSalvar, btnCancelar;

    private final CursoController cursoController = new CursoController();

    public TelaCadastroCurso() {
        setLayout(null);
        setPreferredSize(new Dimension(600, 400));

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, 30, 100, 25);
        add(lblCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 70, 100, 25);
        add(lblNome);

        JLabel lblCargaHoraria = new JLabel("Carga Horária:");
        lblCargaHoraria.setBounds(30, 110, 100, 25);
        add(lblCargaHoraria);

        JLabel lblNivel = new JLabel("Nível:");
        lblNivel.setBounds(30, 150, 100, 25);
        add(lblNivel);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(140, 30, 200, 25);
        add(txtCodigo);

        txtNome = new JTextField();
        txtNome.setBounds(140, 70, 200, 25);
        add(txtNome);

        txtCargaHoraria = new JTextField();
        txtCargaHoraria.setBounds(140, 110, 200, 25);
        add(txtCargaHoraria);

        cmbNivel = new JComboBox<>();
        for (Nivel nivel : Nivel.values()) {
            cmbNivel.addItem(nivel.getRotulo());
        }
        cmbNivel.setBounds(140, 150, 200, 25);
        add(cmbNivel);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(70, 200, 100, 30);
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 200, 100, 30);
        add(btnCancelar);

        btnSalvar.addActionListener(e -> salvarCurso());
        btnCancelar.addActionListener(e -> limparCampos());
    }

    private void salvarCurso() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nome = txtNome.getText().trim();
            int cargaHoraria = Integer.parseInt(txtCargaHoraria.getText().trim());
            String nivelSelecionado = (String) cmbNivel.getSelectedItem();

            Curso curso = new Curso(codigo, nome, cargaHoraria, Nivel.fromString(nivelSelecionado));
            cursoController.cadastrar(curso);

            JOptionPane.showMessageDialog(this, "Curso cadastrado com sucesso!");
            limparCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtCargaHoraria.setText("");
        cmbNivel.setSelectedIndex(0);
    }
}

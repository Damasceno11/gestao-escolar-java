package br.com.escola.gestaoescolar.view;

import br.com.escola.gestaoescolar.controller.CursoController;
import br.com.escola.gestaoescolar.controller.EstudanteController;
import br.com.escola.gestaoescolar.model.Curso;
import br.com.escola.gestaoescolar.model.Estudante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroEstudante extends JPanel {
    private JTextField txtNome, txtTelefone, txtEndereco, txtCpf, txtEmail;
    private JButton btnSalvar, btnCancelar;
    private JComboBox<Curso> comboCurso;

    private final EstudanteController estudanteController = new EstudanteController();
    private final CursoController cursoController = new CursoController();

    public TelaCadastroEstudante() {
        setLayout(null);
        setPreferredSize(new Dimension(600, 400));

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 100, 25);
        add(lblNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(30, 70, 100, 25);
        add(lblCpf);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 110, 100, 25);
        add(lblEmail);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(30, 150, 100, 25);
        add(lblTelefone);

        JLabel lblEndereco = new JLabel("Endereço:");
        lblEndereco.setBounds(30, 190, 100, 25);
        add(lblEndereco);

        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setBounds(30, 230, 100, 25);
        add(lblCurso);

        txtNome = new JTextField();
        txtNome.setBounds(140, 30, 200, 25);
        add(txtNome);

        txtCpf = new JTextField();
        txtCpf.setBounds(140, 70, 200, 25);
        add(txtCpf);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 110, 200, 25);
        add(txtEmail);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(140, 150, 200, 25);
        add(txtTelefone);

        txtEndereco = new JTextField();
        txtEndereco.setBounds(140, 190, 200, 25);
        add(txtEndereco);

        comboCurso = new JComboBox<>();
        comboCurso.setBounds(140, 230, 200, 25);
        add(comboCurso);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(70, 280, 100, 30);
        add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 280, 100, 30);
        add(btnCancelar);

        carregarCursos();

        btnSalvar.addActionListener(e -> salvarEstudante());
        btnCancelar.addActionListener(e -> limparCampos());
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoController.listar();
        comboCurso.removeAllItems();
        for (Curso c : cursos) {
            comboCurso.addItem(c);
        }
    }

    private void salvarEstudante() {
        try {
            Curso cursoSelecionado = (Curso) comboCurso.getSelectedItem();
            if (cursoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um curso válido");
                return;
            }

            Estudante estudante = new Estudante(
                    txtNome.getText(),
                    txtCpf.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    txtEndereco.getText(),
                    cursoSelecionado
            );

            estudanteController.cadastrar(estudante);

            JOptionPane.showMessageDialog(this, "Estudante cadastrado com sucesso!");
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            ex.printStackTrace();  // Isso ajuda a ver o erro completo no console
        }
    }


    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        if (comboCurso.getItemCount() > 0) {
            comboCurso.setSelectedIndex(0);
        }
    }
}

package br.com.escola.gestaoescolar.model;

import java.text.Normalizer;

public enum Nivel {
    BASICO("Básico"),
    INTERMEDIARIO("Intermediário"),
    AVANCADO("Avançado");

    private final String rotulo;

    Nivel(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public static Nivel fromString(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Valor não pode ser vazio");
        }

        String valorNormalizado = Normalizer
                .normalize(valor, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();

        for (Nivel nivel : Nivel.values()) {
            String rotuloNormalizado = Normalizer
                    .normalize(nivel.rotulo, Normalizer.Form.NFD)
                    .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                    .toUpperCase();

            if (rotuloNormalizado.equals(valorNormalizado) || nivel.name().equalsIgnoreCase(valorNormalizado)) {
                return nivel;
            }
        }

        throw new IllegalArgumentException("Nível inválido: " + valor);
    }
}

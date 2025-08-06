package br.com.escola.gestaoescolar.model;

public enum Periodo {
    MATUTINO("Matutino"),
    VESPERTINO("Vespertino"),
    NOTURNO("Noturno"),
    SABADOS("Sábados");

    private final String rotulo;

    Periodo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public static Periodo fromString(String valor) {
        for (Periodo p : Periodo.values()) {
            if (p.rotulo.equalsIgnoreCase(valor) || p.name().equalsIgnoreCase(valor)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Período inválido: " + valor);
    }
}

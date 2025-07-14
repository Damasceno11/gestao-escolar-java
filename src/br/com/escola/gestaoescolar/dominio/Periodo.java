package br.com.escola.gestaoescolar.dominio;

public enum Periodo {
    MATUTINO ("matutino"),
    VESPERTINO ("vespertino"),
    NOTURNO ("noturno"),
    SABADOS ("sábados");

    private final String rotulo;

    private Periodo(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public static Periodo fromString(String valor) {
        for (Periodo p : Periodo.values()) {
            if (p.rotulo.equalsIgnoreCase(valor)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Período inválido: " + valor);
    }
}

package br.com.escola.gestaoescolar.dominio;

public enum Nivel {
    BASICO("básico"),
    INTERMEDIARIO("intermediário"),
    AVANCADO("avançado");

    private final String rotulo;

    Nivel(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public static Nivel fromString(String valor) {
        for (Nivel nivel : Nivel.values() ) {
            if(nivel.rotulo.equalsIgnoreCase(valor)) {
                return nivel;
            }
        }
        throw new IllegalArgumentException("Nível inválido: " + valor);
    }
}

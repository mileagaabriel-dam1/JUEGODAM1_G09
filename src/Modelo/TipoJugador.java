public enum TipoJugador {
    HUMANO,
    IA;

    public boolean esIA() {
        return this == IA;
    }
}
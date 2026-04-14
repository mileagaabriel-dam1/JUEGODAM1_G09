public enum TipoCasilla {
    PINGUINO,
    OSO,
    AGUJERO,
    TRINEO,
    INTERROGANTE;

    public boolean esPeligrosa() {
        return this == OSO || this == AGUJERO;
    }
}
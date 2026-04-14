package Modelo;

public class Casilla {

    private int posicion;
    private TipoCasilla tipo;
    private Entidad entidad;

    public Casilla(int posicion, TipoCasilla tipo) {
        this.posicion = posicion;
        this.tipo = tipo;
        this.entidad = crearEntidad(tipo);
    }

    private Entidad crearEntidad(TipoCasilla tipo) {

        switch (tipo) {

            case PINGUINO:
                return new Pinguino();

            case OSO:
                return new Oso();

            case AGUJERO:
                return new AgujeroHielo();

            case TRINEO:
                return new Trineo();

            case INTERROGANTE:
                return null;

            default:
                throw new IllegalArgumentException("Tipo de casilla desconocido: " + tipo);
        }
    }

    public String getSimbolo() {
        return (entidad != null) ? entidad.getSimbolo() : "❓";
    }

    public String getDescripcion() {
        return (entidad != null)
                ? entidad.getDescripcion()
                : "Casilla misteriosa";
    }

    public TipoCasilla getTipo() {
        return tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public Entidad getEntidad() {
        return entidad;
    }
}
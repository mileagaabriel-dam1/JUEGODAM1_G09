package Modelo;

public class Casilla {

    private int posicion;
    private TipoCasilla tipo;
    private Entidad entidad;

    public Casilla(int posicion, TipoCasilla tipo) {
        this.posicion = posicion;
        this.tipo = tipo;

        switch (tipo) {
            case PINGUINO:
                entidad = new Pinguino();
                break;
            case OSO:
                entidad = new Oso();
                break;
            case AGUJERO:
                entidad = new AgujeroHielo();
                break;
            case TRINEO:
                entidad = new Trineo();
                break;
            case INTERROGANTE:
                entidad = null;
                break;
        }
    }

    public String getSimbolo() {
        if (entidad != null) {
            return entidad.getSimbolo();
        } else {
            return "❓";
        }
    }

    public String getDescripcion() {
        if (entidad != null) {
            return entidad.getDescripcion();
        } else {
            return "Casilla misteriosa";
        }
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
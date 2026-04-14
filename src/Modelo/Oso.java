package Modelo;

public class Oso implements Entidad {

    private static final int POS_INICIO = 0;

    @Override
    public String getNombre() {
        return "Oso";
    }

    @Override
    public String getSimbolo() {
        return "OSO";
    }

    @Override
    public String getDescripcion() {
        return "Un oso salvaje que te devuelve al inicio del tablero.";
    }

    @Override
    public String interactuar(Jugador jugador) {

        jugador.setPosicion(POS_INICIO);

        return "¡El oso te ataca! Vuelves al inicio del tablero.";
    }
}
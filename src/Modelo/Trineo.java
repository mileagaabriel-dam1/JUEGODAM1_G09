package Modelo;

public class Trineo implements Entidad {

    @Override
    public String getNombre() {
        return "Trineo";
    }

    @Override
    public String getSimbolo() {
        return "TRINEO";
    }

    @Override
    public String getDescripcion() {
        return "Te impulsa hacia adelante varios pasos.";
    }

    @Override
    public String interactuar(Jugador jugador) {

        int avance = (int) (Math.random() * 4) + 2;

        int nuevaPos = jugador.getPosicion() + avance;

        jugador.setPosicion(nuevaPos);

        return "¡TRINEO! Avanzas " + avance + " casillas.";
    }
}
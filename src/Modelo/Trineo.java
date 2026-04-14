package Modelo;

public class Trineo implements Entidad {

    @Override
    public String getNombre() {
        return "Trineo";
    }

    @Override
    public String getSimbolo() {
        return "🛷";
    }

    @Override
    public String getDescripcion() {
        return "Te impulsa hacia adelante";
    }

    @Override
    public String interactuar(Jugador jugador) {
        int avance = (int) (Math.random() * 4) + 2;
        int nuevaPos = Math.min(jugador.getPosicion() + avance, 49);
        jugador.setPosicion(nuevaPos);
        return "🛷 ¡TRINEO! Avanzas " + avance + " casillas extra";
    }
}
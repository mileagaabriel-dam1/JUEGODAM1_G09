package Modelo;

public class Pinguino implements Entidad {

    @Override
    public String getNombre() {
        return "Pingüino";
    }

    @Override
    public String getSimbolo() {
        return "P"; // más limpio para tablero en consola
    }

    @Override
    public String getDescripcion() {
        return "Casilla de inicio del jugador.";
    }

    @Override
    public String interactuar(Jugador jugador) {
        return "Estás en la casilla de inicio con los pingüinos.";
    }
}
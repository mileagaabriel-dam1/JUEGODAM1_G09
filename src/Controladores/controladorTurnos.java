package Controladores;

import Modelo.Jugador;
import java.util.List;

public class ControladorTurnos {

    private List<Jugador> jugadores;
    private int actual = 0;

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        actual = 0;
    }

    public Jugador getJugadorActual() {
        return jugadores.get(actual);
    }

    public void siguienteTurno() {
        actual++;
        if (actual >= jugadores.size()) actual = 0;
    }
}
package Controladores;

import Modelo.Jugador;
import java.util.List;

public class ControladorTurnos {

    private List<Jugador> jugadores;
    private int turnoActual;

    public ControladorTurnos() {
        this.turnoActual = 0;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
    }

    public Jugador getJugadorActual() {
        if (jugadores != null && !jugadores.isEmpty()) {
            return jugadores.get(turnoActual);
        }
        return null;
    }

    public void siguienteTurno() {
        if (jugadores != null && !jugadores.isEmpty()) {
            turnoActual = (turnoActual + 1) % jugadores.size();
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
    
    public int getTurnoActual() {
        return turnoActual;
    }
}
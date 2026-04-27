package Controladores;

import Modelo.Jugador;
import java.util.List;

public class controladorTurnos {

    // Lista de la gente que está echando la partida
    private List<Jugador> jugadores;
    // Un número (0, 1, 2...) que nos dice a quién le toca según su posición en la lista
    private int turnoActual;

    public controladorTurnos() {
        // Al empezar, el turno siempre es para el primero (el de la posición 0)
        this.turnoActual = 0;
    }

    // Este método sirve para meter a los jugadores en el gestor de turnos y resetear a 0
    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
        this.turnoActual = 0;
    }

    // Para saber quién es el que tiene que mover el muñeco ahora mismo
    public Jugador getJugadorActual() {
        // Comprobamos que haya gente en la lista para que no nos de un error de "null"
        if (jugadores != null && !jugadores.isEmpty()) {
            return jugadores.get(turnoActual);
        }
        return null;
    }

    // Este es el método estrella: pasa al siguiente jugador
    public void siguienteTurno() {
        if (jugadores != null && !jugadores.isEmpty()) {
            // TRUCO DE CLASE: Usamos el '%' (módulo) para que el turno sea circular.
            // Si hay 3 jugadores, cuando llegamos al 2 y sumamos 1, el % hace que vuelva al 0.
            turnoActual = (turnoActual + 1) % jugadores.size();
        }
    }

    // Getters básicos para sacar información del turno
    public List<Jugador> getJugadores() {
        return jugadores;
    }
    
    public int getTurnoActual() {
        return turnoActual;
    }
}
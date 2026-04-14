package Controladores;

import Modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorJugador {

    private List<Jugador> jugadores;

    public ControladorJugador() {
        this.jugadores = new ArrayList<>();
    }

    public void inicializarJugadores(int numHumanos, boolean incluirIA) {

        jugadores.clear();

        for (int i = 1; i <= numHumanos; i++) {
            jugadores.add(new Jugador("Jugador " + i, "Azul", TipoJugador.HUMANO));
        }

        if (incluirIA) {
            jugadores.add(new Jugador("IA-Pingu", "Rojo", TipoJugador.IA));
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }
}
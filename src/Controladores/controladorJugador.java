package Controladores;

import Modelo.Jugador;
import Modelo.TipoJugador;
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
            jugadores.add(new Jugador("Jugador " + i, obtenerColor(i), TipoJugador.HUMANO));
        }

        if (incluirIA) {
            jugadores.add(new Jugador("IA-Pingu", "Rojo", TipoJugador.IA));
        }
    }
    
    private String obtenerColor(int num) {
        switch(num) {
            case 1: return "Azul";
            case 2: return "Verde";
            case 3: return "Amarillo";
            default: return "Negro";
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void registrarVictoria(Jugador ganador) {
        System.out.println("¡" + ganador.getNombre() + " ha ganado!");
    }
}
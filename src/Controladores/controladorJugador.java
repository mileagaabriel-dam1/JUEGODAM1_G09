package Controladores;

import Modelo.Jugador;
import Modelo.TipoJugador;
import java.util.ArrayList;
import java.util.List;

public class ControladorJugador {

    // Lista privada para guardar a los jugadores. Usamos List para que sea flexible.
    private List<Jugador> jugadores;

    // El constructor: simplemente prepara la lista vacía (el ArrayList)
    public ControladorJugador() {
        this.jugadores = new ArrayList<>();
    }

    // Este método es el que crea a los personajes según lo que hayamos elegido en el menú
    public void inicializarJugadores(int numHumanos, boolean incluirIA) {
        jugadores.clear(); // Limpiamos la lista por si acaso había alguien de una partida anterior

        // Un bucle for para crear tantos humanos como hayamos dicho
        for (int i = 1; i <= numHumanos; i++) {
            // Añadimos un nuevo jugador dándole nombre, color y tipo HUMANO
            jugadores.add(new Jugador("Jugador " + i, obtenerColor(i), TipoJugador.HUMANO));
        }

        // Si marcamos la casilla de la IA, la añadimos al final del grupo
        if (incluirIA) {
            jugadores.add(new Jugador("IA-Pingu", "Rojo", TipoJugador.IA));
        }
    }
    
    // Método privado (solo para uso interno) para repartir colores según el número de jugador
    private String obtenerColor(int num) {
        switch(num) {
            case 1: return "Azul";
            case 2: return "Verde";
            case 3: return "Amarillo";
            default: return "Negro"; // Por si hay más de 3, que salgan en negro
        }
    }

    // Getter para que el resto del juego pueda saber quiénes están jugando
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    // Un simple aviso por consola cuando alguien llega a la meta
    public void registrarVictoria(Jugador ganador) {
        System.out.println("¡" + ganador.getNombre() + " ha ganado!");
    }
}
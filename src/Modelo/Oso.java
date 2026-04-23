package Modelo;

// El Oso también es una 'Entidad', así que tiene que tener los 4 métodos obligatorios
public class Oso implements Entidad {

    @Override
    public String getNombre() {
        return "Oso";
    }

    @Override
    public String getSimbolo() {
        // El emoji que asustará a los jugadores en el tablero
        return "🐻";
    }

    @Override
    public String getDescripcion() {
        return "El oso te atacará, vuelves al inicio";
    }

    // Lo que pasa cuando el jugador cae en la casilla del Oso
    @Override
    public String interactuar(Jugador jugador) {
        // Mandamos al jugador a la casilla 0 directamente
        jugador.setPosicion(0);
        
        // Devolvemos el mensaje que se mostrará en el historial o pantalla
        return "🐻 ¡EL OSO TE HA ATRAPADO! Vuelves al inicio";
    }
}
package Modelo;

// El 'implements Entidad' significa que esta clase tiene que cumplir las normas 
// que pusimos en la interfaz Entidad (tener nombre, símbolo, etc.)
public class AgujeroHielo implements Entidad {

    @Override
    public String getNombre() { 
        return "Agujero de Hielo"; 
    }
    
    @Override
    public String getSimbolo() { 
        // El dibujito que saldrá en el tablero
        return "🕳️"; 
    }

    @Override
    public String getDescripcion() {
        return "Has caído en un agujero, retrocedes 3 casillas";
    }

    // Aquí es donde se decide qué le pasa al jugador cuando pisa el agujero
    @Override
    public String interactuar(Jugador jugador) {
        // Calculamos la nueva posición: la actual menos 3.
        // Usamos Math.max(0, ...) para que si está al principio no retroceda a posiciones negativas (que petaría el juego)
        int nuevaPos = Math.max(0, jugador.getPosicion() - 3);
        
        // Movemos al jugador físicamente a su nueva casilla
        jugador.setPosicion(nuevaPos);
        
        return "  Te has caído en un agujero. Retrocedes 3 casillas";
    }
}
package Modelo;

// El Trineo es una 'Entidad' que beneficia al jugador
public class Trineo implements Entidad {

    @Override
    public String getNombre() {
        return "Trineo";
    }

    @Override
    public String getSimbolo() {
        return "🛷"; // El icono de velocidad
    }

    @Override
    public String getDescripcion() {
        return "Te impulsa hacia adelante";
    }

    // Lógica para darle un empujón al jugador
    @Override
    public String interactuar(Jugador jugador) {
        // Calculamos un avance aleatorio de entre 2 y 5 casillas
        // (Math.random() * 4 da 0-3, + 2 da un rango de 2 a 5)
        int avance = (int) (Math.random() * 4) + 2;
        
        // Calculamos la nueva posición usando Math.min para no pasarnos de la meta (casilla 49)
        int nuevaPos = Math.min(jugador.getPosicion() + avance, 49);
        
        // Movemos al jugador
        jugador.setPosicion(nuevaPos);
        
        return "🛷 ¡TRINEO! Avanzas " + avance + " casillas extra";
    }
}
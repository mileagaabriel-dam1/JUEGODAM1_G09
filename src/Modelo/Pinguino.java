package Modelo;

// El Pingüino es una 'Entidad' más, aunque sea el bueno de la película
public class Pinguino implements Entidad {

    @Override
    public String getNombre() {
        return "Pingüino";
    }

    @Override
    public String getSimbolo() {
        return "🐧"; // El icono del protagonista
    }

    @Override
    public String getDescripcion() {
        return "Punto de salida";
    }

    // Al ser una casilla segura, la interacción no cambia nada del jugador
    @Override
    public String interactuar(Jugador jugador) {
        // Solo devuelve un mensaje de texto, no toca posición ni inventario
        return "🐧 Estás en la casilla de los pingüinos.";
    }
}
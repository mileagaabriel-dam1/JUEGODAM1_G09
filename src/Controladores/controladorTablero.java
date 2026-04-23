package Controladores;

import Modelo.Tablero;
import Modelo.Casilla;

public class ControladorTablero {

    // La variable donde guardamos el mapa del juego
    private Tablero tablero;

    // Al crear el controlador, creamos el objeto Tablero (el que tiene las casillas)
    public ControladorTablero() {
        this.tablero = new Tablero();
    }

    // Sirve para preguntar: "¿Oye, qué hay en la casilla número X?"
    public Casilla getCasilla(int posicion) {
        return tablero.getCasilla(posicion);
    }

    // Este llama al método del modelo para pintar el tablero por consola
    public void mostrarTablero() {
        tablero.mostrarTablero();
    }

    // Para saber cuánto mide el tablero (cuántas casillas hay en total)
    public int getTamanoTablero() {
        return tablero.getTamano();
    }

    // Getter para pasarle el objeto tablero entero a quien lo necesite
    public Tablero getTablero() {
        return tablero;
    }
}
package Controladores;

import Modelo.Tablero;
import Modelo.Casilla;

public class ControladorTablero {

    private Tablero tablero;

    public ControladorTablero() {
        this.tablero = new Tablero();
    }

    public Casilla getCasilla(int posicion) {
        return tablero.getCasilla(posicion);
    }

    public int getTamanoTablero() {
        return tablero.getTamano();
    }

    public Tablero getTablero() {
        return tablero;
    }
}
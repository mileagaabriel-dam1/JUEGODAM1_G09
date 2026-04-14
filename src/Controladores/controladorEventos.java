package Controladores;

import Modelo.*;

public class ControladorEventos {

    public String procesarCasilla(Jugador j, Casilla c) {

        switch (c.getTipo()) {

            case OSO:
                return new Oso().interactuar(j);

            case AGUJERO:
                return new AgujeroHielo().interactuar(j);

            case TRINEO:
                return new Trineo().interactuar(j);

            case PINGUINO:
                return new Pinguino().interactuar(j);

            case INTERROGANTE:
                return eventoAleatorio(j);
        }

        return "";
    }

    private String eventoAleatorio(Jugador j) {

        int r = (int)(Math.random() * 5);

        switch (r) {
            case 0: j.getInventario().agregarPez(); return "Pez";
            case 1: j.getInventario().agregarBolaNieve(); return "Bola nieve";
            case 2: j.getInventario().agregarDado(); return "Dado";
            case 3: j.getInventario().quitarDado(); return "Pierdes dado";
            case 4: j.setPosicion(Math.max(0, j.getPosicion() - 2)); return "Retroceso";
        }

        return "";
    }
}
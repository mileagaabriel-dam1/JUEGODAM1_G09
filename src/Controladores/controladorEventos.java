package Controladores;

import Modelo.*;
import Vista.GestorSonido;

public class ControladorEventos {

    public String procesarCasilla(Jugador jugador, Casilla casilla) {

        Entidad entidad = null;
        String mensaje = "";

        switch(casilla.getTipo()) {
            case PINGUINO:
                entidad = new Pinguino();
                break;

            case OSO:
                entidad = new Oso();
                GestorSonido.getInstancia().reproducirEfecto("oso");
                break;

            case AGUJERO:
                entidad = new AgujeroHielo();
                GestorSonido.getInstancia().reproducirEfecto("agujero");
                break;

            case TRINEO:
                entidad = new Trineo();
                GestorSonido.getInstancia().reproducirEfecto("trineo");
                break;

            case INTERROGANTE:
                return eventoAleatorio(jugador);
        }

        if (entidad != null) {
            mensaje = entidad.interactuar(jugador);
        }

        return mensaje;
    }

    private String eventoAleatorio(Jugador jugador) {
        int evento = (int)(Math.random() * 5);
        String mensaje = "  ❓ ";

        switch(evento) {
            case 0:
                mensaje += "¡Evento: Encuentras un pez!";
                jugador.getInventario().agregarPez();
                GestorSonido.getInstancia().reproducirEfecto("item");
                break;

            case 1:
                mensaje += "¡Evento: Encuentras bolas de nieve!";
                jugador.getInventario().agregarBolaNieve();
                GestorSonido.getInstancia().reproducirEfecto("item");
                break;

            case 2:
                mensaje += "¡Evento: Ganas un dado extra!";
                jugador.getInventario().agregarDado();
                GestorSonido.getInstancia().reproducirEfecto("item");
                break;

            case 3:
                if (jugador.getInventario().getDados() > 1) {
                    mensaje += "¡Evento: Pierdes un dado!";
                    jugador.getInventario().quitarDado();
                } else {
                    mensaje += "¡Evento: Casi pierdes un dado!";
                }
                break;

            case 4:
                Foca foca = new Foca();
                mensaje = foca.interactuar(jugador);
                GestorSonido.getInstancia().reproducirEfecto("foca");
                break;
        }

        return mensaje;
    }
}
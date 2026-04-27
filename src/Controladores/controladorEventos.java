package Controladores;

import Modelo.*;

public class controladorEventos {

    // Este es el método que se activa cuando el jugador cae en una casilla
    public String procesarCasilla(Jugador jugador, Casilla casilla) {

        Entidad entidad = null;
        String mensaje = "";

        // Miramos qué hay en la casilla con un switch de toda la vida
        switch(casilla.getTipo()) {
            case PINGUINO:
                entidad = new Pinguino(); // Aparece un pingüino
                break;

            case OSO:
                entidad = new Oso(); // ¡Peligro! Un oso
                break;

            case AGUJERO:
                entidad = new AgujeroHielo();
                break;

            case TRINEO:
                entidad = new Trineo();
                break;

            case INTERROGANTE:
                // Si cae en el ?, llamamos al sorteo de abajo y salimos de aquí
                return eventoAleatorio(jugador);
        }

        // Si la casilla tenía algo (oso, pingüino, etc.), hacemos que pase algo
        if (entidad != null) {
            mensaje = entidad.interactuar(jugador);
        }

        return mensaje;
    }

    // Aquí es donde se reparte la suerte (buena o mala)
    private String eventoAleatorio(Jugador jugador) {
    	
        // Sacamos un número al azar entre 0 y 4 (típica fórmula de clase)
        int evento = (int)(Math.random() * 5);
        String mensaje = "  ❓ ";

        switch(evento) {
            case 0:
                mensaje += "¡Evento: Encuentras un pez!";
                jugador.getInventario().agregarPez(); // Directo a la mochila
                break;

            case 1:
                mensaje += "¡Evento: Encuentras bolas de nieve!";
                jugador.getInventario().agregarBolaNieve();
                break;

            case 2:
                mensaje += "¡Evento: Ganas un dado extra!";
                jugador.getInventario().agregarDado(); // Dopamos al jugador
                break;

            case 3:
                // Ojo: solo le quitamos un dado si tiene más de uno (para no dejarlo sin jugar)
                if (jugador.getInventario().getDados() > 1) {
                    mensaje += "¡Evento: Pierdes un dado!";
                    jugador.getInventario().quitarDado();
                } else {
                    mensaje += "¡Evento: Casi pierdes un dado!";
                }
                break;

            case 4:
                // En este evento te sale una foca de repente
                Foca foca = new Foca();
                mensaje = foca.interactuar(jugador);
                break;
        }

        return mensaje;
    }
}
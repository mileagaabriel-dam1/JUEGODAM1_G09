package Controladores; 

import Modelo.*; 
import java.util.List;

public class controladorEventos { 
    private controladorJuego gestorPrincipal;

    public void setControladorJuego(controladorJuego gestor) {
        this.gestorPrincipal = gestor;
    }

    public String procesarCasilla(Jugador jugador, Casilla casilla) {
        Entidad entidad = null; 
        String mensaje = ""; 
        int posicionInicial = jugador.getPosicion();

        switch(casilla.getTipo()) {
            case PINGUINO:
                entidad = new Pinguino(); 
                break;
            case OSO:
                entidad = new Oso(); 
                break;
            case AGUJERO:
                entidad = new AgujeroHielo(); 
                break;
            case TRINEO:
                entidad = new Trineo(); 
                break;
            case INTERROGANTE:
                return eventoAleatorio(jugador);
        }

        if (entidad != null) {
            mensaje = entidad.interactuar(jugador);
            if (jugador.getPosicion() != posicionInicial) {
                System.out.println("LOG: El jugador se ha movido a la casilla " + jugador.getPosicion());
            }
        }
        return mensaje; 
    }

    private String eventoAleatorio(Jugador jugador) {
        int evento = (int)(Math.random() * 5);
        String mensaje = "   ❓ "; 

        switch(evento) {
            case 0:
                mensaje += "¡Evento: Encuentras un pez!";
                jugador.getInventario().agregarPez(); 
                break;
            case 1:
                mensaje += "¡Evento: Encuentras bolas de nieve!";
                jugador.getInventario().agregarBolaNieve(); 
                break;
            case 2:
                mensaje += "¡Evento: Ganas un dado extra!";
                jugador.getInventario().agregarDado(); 
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
                break;
        }
        return mensaje; 
    }

    // PELEA DE NIEVE / ENCUENTROS EN LA MISMA CASILLA (REQUISITOS DEL PDF)
    public String gestionarPelea(Jugador actual, List<Jugador> lista) {
        for (Jugador oponente : lista) {
            
            if (!oponente.getNombre().equals(actual.getNombre()) && oponente.getPosicion() == actual.getPosicion()) {
             
                // ==========================================================
                // 🛑 COLETASO DE LA FOCA (Si uno es la IA)
                // ==========================================================
                if (actual.getTipo() == TipoJugador.IA || oponente.getTipo() == TipoJugador.IA) {
                    Jugador humano = (actual.getTipo() != TipoJugador.IA) ? actual : oponente;
                    System.out.println("🦭💥 [FOCA] Encuentro directo. Coletazo inmediato.");
                    
                    int posicionAnteriorForat = 0; // Por defecto la salida (casilla 1)
                    
                    // Buscamos dinámicamente el agujero de hielo anterior en el tablero real
                    if (gestorPrincipal != null && gestorPrincipal.getControladorTablero() != null) {
                        Tablero tableroReal = gestorPrincipal.getControladorTablero().getTablero(); 
                        if (tableroReal != null) {
                            for (int i = humano.getPosicion() - 1; i > 0; i--) {
                                // Buscamos si la casilla del mapa es de tipo AGUJERO
                                if (tableroReal.getCasilla(i).getTipo() == TipoCasilla.AGUJERO) {
                                    posicionAnteriorForat = i;
                                    break; // Al encontrar el primero yendo hacia atrás, paramos
                                }
                            }
                        }
                    }
                    
                    humano.setPosicion(posicionAnteriorForat);
                    return "🦭💥 [FOCA] ¡Coletazo directo a " + humano.getNombre() + "! Lo manda al agujero de la casilla " + (posicionAnteriorForat + 1);
                } 
                
                // ==========================================================
                // ⚔️ GUERRA DE JUGADORES CLÁSICA (Sabiendo las reglas del PDF)
                // ==========================================================
                else {
                    int bolasActual = actual.getInventario().getBolasNieve();
                    int bolasOponente = oponente.getInventario().getBolasNieve();
                    
                    // REQUISITO EXCEL·LENT: Ambos gastan TODAS sus bolas de nieve siempre
                    int diferencia = Math.abs(bolasActual - bolasOponente);
                    
                    // Vaciamos los dos inventarios de bolas
                    while(actual.getInventario().getBolasNieve() > 0) actual.getInventario().quitarBolaNieve();
                    while(oponente.getInventario().getBolasNieve() > 0) oponente.getInventario().quitarBolaNieve();
                    
                    if (bolasActual > bolasOponente) {
                        // Gana el jugador actual. El oponente retrocede la DIFERENCIA exacta de casillas
                        oponente.setPosicion(Math.max(0, oponente.getPosicion() - diferencia));
                        return "⚔️ ¡PELEA! " + actual.getNombre() + " gana (" + bolasActual + " vs " + bolasOponente + "). " + oponente.getNombre() + " retrocede " + diferencia + " casillas y ambos se quedan sin munición!";
                    } 
                    else if (bolasOponente > bolasActual) {
                        // Gana el oponente. El jugador actual retrocede la DIFERENCIA exacta
                        actual.setPosicion(Math.max(0, actual.getPosicion() - diferencia));
                        return "⚔️ ¡PELEA! " + oponente.getNombre() + " gana la batalla de nieve. " + actual.getNombre() + " retrocede " + diferencia + " casillas. ¡Bolas gastadas!";
                    } 
                    else {
                        // EMPATE TÉCNICO: Se quedan en la misma casilla pero gastan todas las bolas por el tiroteo
                        return "⚔️ ¡EMPATE! Ambos tenían " + bolasActual + " bolas. ¡Se han acribillado a la vez y se quedan a 0!";
                    }
                }
            }
        }
        return null; 
    }
}
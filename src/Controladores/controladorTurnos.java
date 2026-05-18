package Controladores; 

import Modelo.Jugador;
import java.util.List; 

public class controladorTurnos { 
    private List<Jugador> jugadores;
    private int turnoActual;
    private controladorJuego gestorPrincipal;

    public controladorTurnos() {
        this.turnoActual = 0;
    }

    public void setControladorJuego(controladorJuego gestor) {
        this.gestorPrincipal = gestor;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores; 
        this.turnoActual = 0; 
    }

    public Jugador getJugadorActual() {
        if (jugadores != null && !jugadores.isEmpty()) {
            return jugadores.get(turnoActual);
        }
        return null; 
    }

    public void siguienteTurno() {
        if (jugadores == null || jugadores.isEmpty()) return;

        // 1️⃣ Comprobar si el que acaba de mover ha ganado
        Jugador jugadorQueAcabaDeMover = getJugadorActual();
        if (gestorPrincipal != null && jugadorQueAcabaDeMover != null) {
            if (jugadorQueAcabaDeMover.getPosicion() >= 49) {
                gestorPrincipal.comprobarVictoria(jugadorQueAcabaDeMover);
                return; 
            }
        }

        // 2️⃣ Avanzamos el turno al siguiente de la lista
        turnoActual = (turnoActual + 1) % jugadores.size();
        Jugador nuevoJugadorActivo = getJugadorActual();

        // 3️⃣ COMPROBACIÓN RESTRUCTURADA: ¿El nuevo jugador es una IA o un Humano?
        if (nuevoJugadorActivo != null && nuevoJugadorActivo.getTipo() == Modelo.TipoJugador.IA) {
            
            // ==========================================================
            // 🤖 SI ES LA IA -> SÍ SE EJECUTA AUTOMÁTICAMENTE
            // ==========================================================
            java.util.Random dadoFoca = new java.util.Random();
            int tiradaIA = dadoFoca.nextInt(6) + 1;
            
            int posicionOrigen = nuevoJugadorActivo.getPosicion();
            int nuevaPosicion = Math.min(49, posicionOrigen + tiradaIA);
            
            String msgTurno = "🤖 [Turno de la IA] La Foca saca un " + tiradaIA + " y avanza...";
            System.out.println("\n" + msgTurno);
            
            if (gestorPrincipal != null && gestorPrincipal.getVista() != null) {
                gestorPrincipal.getVista().getVistaEventos().agregarEvento(msgTurno);
            }
            
            // Requisito del robo de inventario
            for (Jugador j : jugadores) {
                if (j.getTipo() != Modelo.TipoJugador.IA) { 
                    if (j.getPosicion() > posicionOrigen && j.getPosicion() <= nuevaPosicion) {
                        String txtRobo = "🦭🎒 [FOCA] Pasa por encima de " + j.getNombre() + " y le quita la mitad de sus bolas!";
                        System.out.println(txtRobo);
                        if (gestorPrincipal != null && gestorPrincipal.getVista() != null) {
                            gestorPrincipal.getVista().getVistaEventos().agregarEvento(txtRobo);
                        }

                        int bolesActuales = j.getInventario().getBolasNieve();
                        int bolesAPerder = bolesActuales / 2;
                        for (int k = 0; k < bolesAPerder; k++) {
                            j.getInventario().quitarBolaNieve();
                        }
                    }
                }
            }
            
            nuevoJugadorActivo.setPosicion(nuevaPosicion);
            String msgFinFoca = "🦭 La Foca termina en la casilla: " + (nuevoJugadorActivo.getPosicion() + 1);
            System.out.println(msgFinFoca);
            
            if (gestorPrincipal != null && gestorPrincipal.getVista() != null) {
                gestorPrincipal.getVista().getVistaEventos().agregarEvento(msgFinFoca);
            }
            
            // Gestionar peleas/coletazos
            if (gestorPrincipal != null && gestorPrincipal.getControladorEventos() != null) {
                String choqueMsg = gestorPrincipal.getControladorEventos().gestionarPelea(nuevoJugadorActivo, jugadores);
                if (choqueMsg != null && gestorPrincipal.getVista() != null) {
                    gestorPrincipal.getVista().getVistaEventos().agregarEvento(choqueMsg);
                }
            }
            
            // Refrescar pantallas
            if (gestorPrincipal != null && gestorPrincipal.getVista() != null) {
                javafx.application.Platform.runLater(() -> {
                    gestorPrincipal.getVista().getVistaTablero().actualizarPosiciones(
                        gestorPrincipal.getControladorJugador(), gestorPrincipal.getControladorTurnos()
                    );
                    gestorPrincipal.getVista().getVistaJugador().actualizar(gestorPrincipal.getControladorTurnos());
                });
            }
            
            // Al terminar la IA, volvemos a llamar a siguienteTurno para que el puntero pase al humano
            siguienteTurno();

        } else {
            // ==========================================================
            // 👤 SI ES HUMANO -> NO HACEMOS NADA. EL CÓDIGO SE DETIENE.
            // ==========================================================
            System.out.println("LOG: Esperando a que el jugador humano lance el dado manualmente...");
            
            // Solo actualizamos las tarjetas visuales para que veas que es tu turno
            if (gestorPrincipal != null && gestorPrincipal.getVista() != null) {
                javafx.application.Platform.runLater(() -> {
                    gestorPrincipal.getVista().getVistaJugador().actualizar(this);
                });
            }
        }
    }

    public List<Jugador> getJugadores() { return jugadores; }
    public int getTurnoActual() { return turnoActual; }
}
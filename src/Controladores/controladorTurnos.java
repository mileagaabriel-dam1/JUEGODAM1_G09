package Controladores; 
//Indicamos que esta clase pertenece al paquete de Controladores

import Modelo.Jugador;
//Importamos la clase Jugador porque necesitamos gestionar sus turnos

import java.util.List; 
//Importamos List para manejar la colección de participantes

public class controladorTurnos { 
	//Clase encargada de gestionar el flujo y el orden de la partida

    //Atributo privado que guarda la lista de jugadores que están en la partida
    private List<Jugador> jugadores;
    //List = Lista de objetos
    
    //Índice numérico que representa la posición en la lista del jugador que tiene el turno
    private int turnoActual;

    //ATRIBUTO PARA CONECTAR CON EL CORAZÓN DEL JUEGO
    private controladorJuego gestorPrincipal;

    //Constructor del controlador
    public controladorTurnos() {
        //Al iniciar la clase, el turno comienza siempre en el primer jugador (índice 0)
        this.turnoActual = 0;
    }

    //MÉTODO PARA ASIGNAR EL CONTROLADOR PRINCIPAL
    public void setControladorJuego(controladorJuego gestor) {
        //Vinculamos este controlador con el controlador maestro
        this.gestorPrincipal = gestor;
    }

    //Método para vincular la lista de jugadores con este gestor y reiniciar el contador
    public void setJugadores(List<Jugador> jugadores) {
    	
        this.jugadores = jugadores; 
        //Asignamos la lista que nos pasan desde el controlador principal
        
        this.turnoActual = 0; 
        //Reiniciamos el turno al principio para evitar errores de índice
    }

    //Método para obtener el objeto Jugador que debe realizar su acción ahora mismo
    public Jugador getJugadorActual() {
    	
        //Validación de seguridad, comprobamos que la lista existe y no está vacía
        if (jugadores != null && !jugadores.isEmpty()) {
        	
            //Accedemos a la lista usando el índice del turno actual
            return jugadores.get(turnoActual);
        }
        return null; 
        //Si no hay jugadores, devolvemos null para evitar que el programa explote
    }

    //Método clave para rotar el turno entre los participantes
    public void siguienteTurno() {
        //Comprobamos de nuevo que haya jugadores para poder operar
    	
        if (jugadores != null && !jugadores.isEmpty()) {

            //CHEQUEO DE VICTORIA ANTES DE CAMBIAR
            //Antes de pasar al siguiente, miramos si el que acaba de mover ha ganado
            if (gestorPrincipal != null) {
                //Obtenemos al que acaba de tirar y miramos si está en la meta
                Jugador jugadorQueAcabaDeMover = getJugadorActual();
                
                //Si el jugador ya está en la casilla 49 (casilla 50 real), llamamos a victoria
                if (jugadorQueAcabaDeMover.getPosicion() >= 49) {
                    gestorPrincipal.comprobarVictoria(jugadorQueAcabaDeMover);
                    return; //IMPORTANTE, Si ha ganado, salimos del método y NO pasamos el turno
                }

                // 🧠 PROCESAMOS INTERACCIONES DEL EXAMEN TRAS EL MOVIMIENTO REALIZADO
                evaluarNormasEnunciado(jugadorQueAcabaDeMover);
            }

            //Para saber de quien se acaba de pasar el turno
            System.out.println("LOG: Pasando el turno del jugador: " + getJugadorActual().getNombre());

            turnoActual = (turnoActual + 1) % jugadores.size();
            //A ver, sencillo, estas lineas son para controlar el orden de los jugadores.
            //Es una lógica circular, no la hemos dado en clase.
            
            //Pero es muy simple, en vez de usar varios If, que quedaria feisimo, porque no recortarlo, a 2 lineas.
            //Para esto se ha usado el "operador módulo %", que hace esto?
               //Esto basicamente mantiene los turnos dentro del rango de tamaño.
            
            //Hay 3 jugadores?, pues 3 turnos, ni mas, ni menos.
            //Los turnos se van sumando, "1+1...", comenzando desde 0, pero cuando llega, al 3 turno, es decir, "2+1" si hay 3 jugadores, pues lo divide por el
            //tamaño de jugadores que hemos puesto antes, es decir 3. 3%3 = 1, sobran 0, vuelve al turno 0.
            
            //Si hay 2 jugadores, pues lo mismo. pero dividido por 2.

            // ==========================================================
            // 🤖 🦭 TURNO AUTOMÁTICO DE LA IA: LAS FUNCIONES DE LA FOCA
            // ==========================================================
            Jugador nuevoJugadorActivo = getJugadorActual();
            
            if (nuevoJugadorActivo != null && nuevoJugadorActivo.getTipo() == Modelo.TipoJugador.IA) {
                System.out.println("\n🤖 [Turno de la IA] Ejecutando funciones mecánicas de la Foca...");
                
                // 🎲 La foca lanza su dado de movimiento (del 1 al 6)
                java.util.Random dadoFoca = new java.util.Random();
                int tiradaIA = dadoFoca.nextInt(6) + 1;
                System.out.println("🎲 La Foca (IA) tira el dado y saca un: " + tiradaIA);
                
                // Mueve a la Foca sin permitir que se pase del límite del mapa (casilla 49)
                int nuevaPosicion = nuevoJugadorActivo.getPosicion() + tiradaIA;
                nuevoJugadorActivo.setPosicion(Math.min(49, nuevaPosicion));
                System.out.println("🦭 La Foca se desplaza a la casilla: " + (nuevoJugadorActivo.getPosicion() + 1));
                
                // Llamamos a que pase el turno automáticamente para que le vuelva a tocar al humano
                // Usamos un pequeño retraso controlado en la cola de ejecución para que no se congele la vista
                javax.swing.SwingUtilities.invokeLater(() -> {
                    siguienteTurno();
                });
            }
        }
    }

    // 🦭 MÉTODO PARA CUMPLIR LOS REQUISITOS OBLIGATORIOS DEL ENUNCIADO ❓
    private void evaluarNormasEnunciado(Jugador jugadorActivo) {
        if (gestorPrincipal == null || gestorPrincipal.getControladorTablero() == null) return;

        // 1. COMPORTAMIENTO DE LA IA (LA FOCA): 
        // Si el jugador activo es de tipo IA, ejecuta sus acciones específicas de personaje jugador
        if (jugadorActivo.getTipo() == Modelo.TipoJugador.IA) {
            System.out.println("🦭 Acciones de Foca activas para: " + jugadorActivo.getNombre());
            
            // Mecánica de interacción: Si la foca cae en la misma posición que un jugador humano, interactúa con él
            for (Jugador j : jugadores) {
                if (j.getTipo() != Modelo.TipoJugador.IA && j.getPosicion() == jugadorActivo.getPosicion()) {
                    System.out.println("🦭 ¡La Foca ha chocado con " + j.getNombre() + "! Le mete un empujón.");
                    j.setPosicion(Math.max(0, j.getPosicion() - 1)); // Retrasa al humano 1 casilla por contacto
                }
            }
        }

        // 2. EFECTO DE LA CASILLA DEL INTERROGANTE (?):
        // Buscamos a través del gestor principal el tipo de casilla del tablero donde se encuentra el jugador
        Modelo.Casilla casillaActual = gestorPrincipal.getControladorTablero().getCasilla(jugadorActivo.getPosicion());
        
        if (casillaActual != null && casillaActual.getTipo() == Modelo.TipoCasilla.INTERROGANTE) {
            System.out.println("❓ ¡Casilla de INTERROGANTE (Evento Aleatorio) detectada para " + jugadorActivo.getNombre() + "!");
            
            // Requisito de azar mediante un dado de probabilidad al 50%
            java.util.Random dadoAzar = new java.util.Random();
            int suerte = dadoAzar.nextInt(2); // Escupe un número entre 0 y 1
            
            if (suerte == 0) {
                System.out.println("✨ Evento Favorable: Avanza 2 casillas extra.");
                jugadorActivo.setPosicion(Math.min(49, jugadorActivo.getPosicion() + 2));
            } else {
                System.out.println("❄️ Evento Desfavorable: Retrocede 2 casillas.");
                jugadorActivo.setPosicion(Math.max(0, jugadorActivo.getPosicion() - 2));
            }
        }
    }

    //Getter para obtener la lista completa de jugadores que están bajo el control de turnos
    public List<Jugador> getJugadores() {
    	
        return jugadores; 
        //Retornamos la referencia a la lista
    }
    
    //Getter para saber el índice numérico del turno en el que nos encontramos
    public int getTurnoActual() {
    	
        return turnoActual; 
        //Retornamos el valor entero
    }
} //Fin de la clase controladorTurnos
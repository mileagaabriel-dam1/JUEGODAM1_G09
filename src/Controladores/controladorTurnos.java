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
    
    //Índice numérico que representa la posición en la lista del jugador que tiene el turno
    private int turnoActual;

    //Constructor del controlador
    public controladorTurnos() {
        //Al iniciar la clase, el turno comienza siempre en el primer jugador (índice 0)
        this.turnoActual = 0;
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
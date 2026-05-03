package Controladores; 
//Declaramos el paquete para organizar el proyecto y evitar conflictos de nombres

import Modelo.Jugador; 
//Importamos la clase Jugador del paquete Modelo para poder usar sus objetos

import Modelo.TipoJugador; 
//Importamos el Enum que clasifica si el usuario es humano o máquina

import java.util.ArrayList; 
//Importamos la implementación de lista basada en arrays redimensionables(Puede variar de tamaño)

import java.util.List; 
//Importamos la interfaz List para trabajar con colecciones de forma genérica

public class controladorJugador { 
	//Definición de la clase que gestionará a todos los participantes

    //Declaramos una lista de objetos 'Jugador' como privada para proteger el acceso directo (encapsulamiento)
    private List<Jugador> jugadores;

    //Constructor de la clase, se ejecuta al hacer 'new controladorJugador()'
    public controladorJugador() {
    	
        //Inicializamos la lista como un ArrayList vacío para que esté lista para recibir datos
        this.jugadores = new ArrayList<>();
    }

    //Método público para configurar la partida recibiendo parámetros desde la interfaz (Vista)
    public void inicializarJugadores(int numHumanos, boolean incluirIA) {
        jugadores.clear(); 
        //Borramos cualquier jugador previo para empezar la partida desde cero

        //Iniciamos un bucle que se repetirá tantas veces como humanos hayamos indicado
        for (int i = 1; i <= numHumanos; i++) {
        	
            //Creamos un nuevo objeto Jugador y lo metemos en la lista
            jugadores.add(new Jugador("Jugador " + i, obtenerColor(i), TipoJugador.HUMANO));
        } 
        //Fin del bucle for


        if (incluirIA) {
            
        	//Añadimos un último jugador a la lista con nombre fijo y comportamiento de IA
            jugadores.add(new Jugador("IA-Pingu", "Rojo", TipoJugador.IA));
        }
    } 
    //Fin del método inicializarJugadores
    
    // Método auxiliar privado para asignar colores; solo lo ve esta clase
    private String obtenerColor(int num) {
        // Evaluamos el número de orden del jugador para darle una identidad visual
        switch(num) {
            case 1: return "Azul";
            case 2: return "Verde";
            case 3: return "Amarillo";
            default: return "Negro"; 
            //Color de seguridad por si el switch no encuentra el número
        }
    } 
    //Fin del método obtenerColor

    //Método Getter para que otros controladores puedan leer la lista de jugadores
    public List<Jugador> getJugadores() {
        return jugadores; // Retornamos la referencia a nuestra lista privada
    }

    //Método para imprimir en la consola del sistema el resultado final del juego
    public void registrarVictoria(Jugador ganador) {
    	
        
        System.out.println("¡" + ganador.getNombre() + " ha ganado!");
    }
} 
//Fin de la clase controladorJugador
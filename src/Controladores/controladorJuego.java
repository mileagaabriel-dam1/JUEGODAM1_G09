package Controladores; 
//Definimos el paquete donde se encuentra este controlador principal

public class controladorJuego { 
	//Esta es la clase "corazón" que centraliza todos los subsistemas del juego(Objeto maestro)

	//Esto añade un mayor orden entre los códigos.
	
    //Declaramos los atributos privados. Usamos 'private' para aplicar el encapsulamiento,
    //así protegemos estas instancias y solo se pueden gestionar desde esta clase.
	
    private controladorTablero controladorTablero; 
    //Referencia al gestor del mapa y las casillas
    
    private controladorJugador controladorJugador; 
    //Referencia al gestor de los datos del usuario
    
    private controladorTurnos controladorTurnos;   
    //Referencia al gestor del flujo de la partida
    
    private controladorEventos controladorEventos; 
    //Referencia al gestor de la lógica de encuentros

    //Este es el constructor de la clase. Se encarga de inicializar los objetos al empezar la partida.
    public controladorJuego() {
    	
        // Usamos 'this' para diferenciar los atributos de la clase y asignarles una nueva instancia (new)
        this.controladorTablero = new controladorTablero(); 
        //Instanciamos el controlador del tablero
        
        this.controladorJugador = new controladorJugador(); 
        //Instanciamos el controlador del jugador
        
        this.controladorTurnos = new controladorTurnos();   
        //Instanciamos el controlador de turnos
        
        this.controladorEventos = new controladorEventos(); 
        //Instanciamos el controlador de eventos
    }

    //Ahora definimos los 'Getters'.
    
    //Como los atributos son privados, creamos estos métodos públicos para que 
    //otras partes del programa (como la Vista) puedan acceder a ellos de forma controlada.

    public controladorTablero getControladorTablero() {
        return controladorTablero; 
        //Devuelve la instancia que gestiona el tablero
    }

    public controladorJugador getControladorJugador() {
        return controladorJugador; 
        //Devuelve la instancia que gestiona al jugador
    }

    public controladorTurnos getControladorTurnos() {
        return controladorTurnos; 
        //Devuelve la instancia que gestiona los turnos
    }

    public controladorEventos getControladorEventos() {
        return controladorEventos; 
        //Devuelve la instancia que gestiona los eventos
    }
}
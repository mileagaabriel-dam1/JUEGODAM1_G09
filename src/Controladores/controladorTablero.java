package Controladores; 
//Indicamos que esta clase gestiona la lógica dentro del paquete Controladores

import Modelo.Tablero; 
//Importamos la clase Tablero del Modelo para poder crear el mapa

import Modelo.Casilla; 
//Importamos la clase Casilla para saber qué estamos pisando

public class controladorTablero { 
	//Definimos la clase que controlará todo lo referente al escenario del juego

    //Declaramos el atributo privado 'tablero'. Solo este controlador puede manejarlo directamente.
    private Tablero tablero;

    //Constructor de la clase, se ejecuta al iniciar el controlador del tablero
    public controladorTablero() {
    	
        //Al instanciar esta clase, creamos automáticamente el objeto Tablero (el mapa de casillas)
        this.tablero = new Tablero();
    }

    //Método público para obtener una casilla específica pasando su índice por parámetro
    public Casilla getCasilla(int posicion) {
    	
        //Llama al método del modelo para devolver la información de esa posición concreta
        return tablero.getCasilla(posicion);
    }

    public void mostrarTablero() {
    	
        //Delega la responsabilidad de "dibujarse" al propio objeto tablero
        tablero.mostrarTablero();
    }

    //Método que devuelve la longitud total del camino del juego
    public int getTamanoTablero() {
    	
        //Consulta al modelo cuántas casillas tiene el tablero en total
        return tablero.getTamano();
    }

    //Getter estándar para obtener la instancia completa del objeto tablero
    public Tablero getTablero() {
    	
        //Permite que otras clases (como la Vista) accedan a los datos del mapa
        return tablero;
    }
} //Fin de la clase controladorTablero
package Modelo; 
//Definimos que este Enum forma parte de la lógica de datos

//Un 'enum' (enumeración) es un tipo especial de clase que define un conjunto de constantes.
//Sirve para que en todo el programa solo existan estos 5 tipos de casillas, 
//actuando como un "menú cerrado" para evitar errores de escritura.

public enum TipoCasilla {
	
    PINGUINO,     
    //Representa la casilla de salida o zonas seguras
    
    OSO,          
    //Representa los peligros que mandan al jugador al inicio
    
    AGUJERO,      
    //Representa los obstáculos que hacen retroceder posiciones
    
    TRINEO,       
    //Representa las ventajas que ayudan al jugador a avanzar
    
    INTERROGANTE  
    //Representa los eventos sorpresa o aleatorios
} 
//Fin del catálogo de tipos
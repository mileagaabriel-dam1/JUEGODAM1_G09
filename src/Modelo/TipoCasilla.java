package Modelo;

// Un 'enum' es una lista de palabras fijas (constantes).
// Sirve para que en todo el programa solo existan estos 5 tipos de casillas.

public enum TipoCasilla {
	
    PINGUINO,     // Casilla de salida/segura
    
    OSO,          // Casilla de peligro
    
    AGUJERO,      // Casilla de obstáculo
    
    TRINEO,       // Casilla de ventaja
    
    INTERROGANTE  // Casilla de evento aleatorio
}
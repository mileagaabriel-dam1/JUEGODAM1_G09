package Modelo; 
//Seguimos en la capa de datos

//Este enumerado es el "interruptor" de control del jugador.
//Define quién tiene el mando, una persona física o la lógica del programa.

public enum TipoJugador {
	
    HUMANO, 
    //El jugador real, cuyas acciones dependen de los clics en la interfaz gráfica.
    
    IA      
    //La "Inteligencia Artificial" (el ordenador), que mueve automáticamente siguiendo su lógica.
}
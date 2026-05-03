package main; 
//Declaramos el paquete 'main' donde reside el punto de entrada de la aplicación

import Vista.VistaJavaFX; 
//Importamos la clase de la interfaz para conectar la lógica con la ventana

public class main { 
	//Definimos la clase principal del proyecto
    
    //El método 'main' es el estándar de Java: es el primer método que el sistema busca para ejecutar
    public static void main(String[] args) {
        
        //Imprimimos por consola el título del proyecto y el grupo (muy útil para que el tribunal os identifique)
        System.out.println("JOC DEL PINGÜ - DAM1 - G09");
        
        //Mostramos los nombres de los integrantes del equipo que han desarrollado el juego
        System.out.println("IAN RUBIO, OSCAR MUÑOZ, GABRIEL MILEA");

        //Esta es la llamada crítica, ejecutamos el método main de la clase VistaJavaFX.
        //Al pasarle 'args', permitimos que la librería de JavaFX tome el control, 
        //inicialice la ventana, cargue los gráficos y arranque el bucle de la aplicación.
        VistaJavaFX.main(args);
    }
} 
//Fin de la clase main
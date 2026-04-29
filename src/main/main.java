package main; //Indica que este archivo está en la carpeta principal

import Vista.VistaJavaFX; //Importamos la interfaz gráfica para poder lanzarla

public class main {
    //El método 'main' es el que busca Java siempre para empezar a leer el código
    public static void main(String[] args) {
        
        //Estos Print son solo para que salgan vuestros nombres en la consola al arrancar
        System.out.println("JOC DEL PINGÜ - DAM1 - G09");
        System.out.println("IAN RUBIO, OSCAR MUÑOZ, GABRIEL MILEA");

        //Esta línea es la que hace la magia,	 llama a la clase de la interfaz (JavaFX)
        //y le dice que se ponga en marcha. A partir de aquí, se abre la ventana del juego.
        VistaJavaFX.main(args);
    }
}
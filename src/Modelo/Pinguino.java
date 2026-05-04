package Modelo; 

//El Pingüino también firma el contrato de "Randy" (implements Entidad)
public class Pinguino implements Entidad {

    @Override //Cumpliendo con Randy, dar su nombre
    public String getNombre() {
        return "Pingüino";
    }

    @Override //Cumpliendo con Randy, enseñar el emoji en el tablero
    public String getSimbolo() {
        return "🐧"; //Representa la seguridad en el tablero
    }

    @Override 
    //Explicar esta casilla
    public String getDescripcion() {
        return "Punto de salida";
    }

    //Interacción neutral,
    //A diferencia del Oso o la Foca, el Pingüino es pacífico.
    //Randy nos obliga a tener este método, pero aquí no modificamos
    //ninguna estadística del jugador.
    
    @Override
    public String interactuar(Jugador jugador) {
        //Simplemente saludamos al jugador. 
        //No hay cambios en posición ni en inventario.
        return "🐧 Estás en la casilla de los pingüinos.";
    }
} //Fin de la clase Pinguino
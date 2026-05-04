package Modelo; 

//El Oso es un "empleado" de Randy, así que firma el contrato de Entidad (implements Entidad)
public class Oso implements Entidad {

    @Override //Cumple la norma de Randy, dar su nombre
    public String getNombre() {
        return "Oso";
    }

    @Override //Es lo mismo que la foca, enseñar su simbolo en el tablero.
    public String getSimbolo() {
        return "🐻"; 
        //El icono que aparecerá en el tablero
    }

    @Override //Esta obligado a presentarse por "Randy"
    public String getDescripcion() {
        return "El oso te atacará, vuelves al inicio";
    }

    //El zarpazo del Oso,
    //Cuando un jugador cae aquí, el Oso usa el permiso de Randy para
    //resetear totalmente la posición del jugador.
    
    @Override
    public String interactuar(Jugador jugador) {
        //Ejecutamos el castigo máximo, mandamos al objeto jugador a la casilla 0
        jugador.setPosicion(0);
        
        //,Retornamos el grito de guerra del Oso para que el usuario sepa qué ha pasado
        return "¡EL OSO TE HA ATRAPADO! Vuelves al inicio";
    }
} //Fin de la clase Oso
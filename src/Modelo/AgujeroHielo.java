package Modelo; 
//Definimos que esta clase pertenece a la capa del Modelo (datos y lógica de objetos)

//La palabra 'implements Entidad' indica que esta clase hereda el contrato de la interfaz Entidad.
//Esto nos obliga a escribir los métodos getNombre, getSimbolo, etc.
public class AgujeroHielo implements Entidad {

    @Override 
    public String getNombre() { 
        return "Agujero de Hielo"; 
        //Bueno esto no requiere comentario explicando lo que hace, y que es el override, no?
    }
    
    @Override 
    public String getSimbolo() { 
        return "🕳️"; 
    }

    @Override 
    public String getDescripcion() {
        return "Has caído en un agujero, retrocedes 3 casillas";
    }

    //Este es el método más importante, ejecuta la lógica de "castigo" sobre el jugador
    @Override
    public String interactuar(Jugador jugador) {
        int nuevaPos = Math.max(0, jugador.getPosicion() - 3);
        //Calculamos la posición de destino restando 3 a la actual.
        //Usamos Math.max(0, ...) como escudo, si el jugador está en la casilla 2 y resta 3,
        //daría -1 (error). Math.max elige el número más alto entre 0 y el resultado, evitando posiciones negativas.

        
        jugador.setPosicion(nuevaPos);
        //Actualizamos la posición del objeto jugador con el nuevo valor calculado

        return "Te has caído en un agujero. Retrocedes 3 casillas";
        //Devolvemos el texto informativo que el controlador mostrará por pantalla

    }
} 
//Fin de la clase AgujeroHielo
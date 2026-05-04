package Modelo; 

//El Trineo cumple con las normas de Randy (implements Entidad)
public class Trineo implements Entidad {

    @Override 
    //Diciendo su nombre
    public String getNombre() {
        return "Trineo";
    }

    @Override //Cumpliendo el contrato: Icono visual
    public String getSimbolo() {
        return "🛷"; //El emoji que simboliza velocidad y ventaja
    }

    @Override //Cumpliendo el contrato, Descripción del beneficio
    public String getDescripcion() {
        return "Te impulsa hacia adelante";
    }

    //Lógica de Impulso,
    //El trineo no siempre corre igual, por eso le metemos un poco de azar.
    
    @Override
    public String interactuar(Jugador jugador) {
        //Generamos un avance aleatorio entre 2 y 5 casillas.
        //Math.random() * 4 genera de 0.0 a 3.99... Al sumar 2, tenemos de 2.0 a 5.99...
        //Al convertir a (int), nos da exactamente 2, 3, 4 o 5.
        int avance = (int) (Math.random() * 4) + 2;
        
        //Usamos Math.min para no "pasarnos de frenada". 
        //Si el jugador está en la 48 y avanza 5, llegaría a la 53 (error).
        //Math.min elige el número más pequeño entre el resultado y el tope (49).
        int nuevaPos = Math.min(jugador.getPosicion() + avance, 49);
        
        //Aplicamos el cambio de posición al objeto jugador
        jugador.setPosicion(nuevaPos);
        
        //Devolvemos el mensaje de éxito para que el jugador se alegre al verlo
        return "🛷 ¡TRINEO! Avanzas " + avance + " casillas extra";
    }
} //Fin de la clase Trineo
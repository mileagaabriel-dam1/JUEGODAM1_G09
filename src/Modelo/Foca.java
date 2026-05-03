package Modelo; 

//La Foca es una empleada de "Randy", así que tiene que cumplir sus normas (implements Entidad)
public class Foca implements Entidad {

    @Override //Cumpliendo el molde de Randy, decir su nombre
    public String getNombre() { 
        return "Foca"; 
    }

    @Override //Otro molde, mostrar su simbolo en el tablero.
    public String getSimbolo() { 
    	
        return "🦭"; 
        //El emoji que el usuario verá en la interfaz
    }
    
    @Override //Obligado a presentarse
    public String getDescripcion() { 
        return "Te ha encontrado una Foca, cuidado con ella"; 
    }
    
    //Aquí es donde la Foca usa el permiso de Randy para "toquetear" al jugador
    @Override
    public String interactuar(Jugador jugador) {
        String mensaje = "🦭 Te ha atrapado una foca. ";

        //Logica al azar, generamos un número aleatorio entre 0 y 3.
        // (int) convierte el número decimal de Math.random en un entero para el switch.
        int accion = (int)(Math.random() * 4);
        
        //Dependiendo del número que haya salido, la Foca hace una cosa u otra
        switch(accion) {
            case 0:
                mensaje += "Te ha quitado un pez.";
                //Accedemos al inventario del jugador para restarle un objeto
                jugador.getInventario().quitarPez(); 
                break;
            case 1:
                mensaje += "Te ha quitado una bola de nieve.";
                //Quitamos "munición" llamando al método específico del inventario
                jugador.getInventario().quitarBolaNieve(); 
                break;
            case 2:
                mensaje += "Te hace retroceder 2 casillas.";
                //Usamos Math.max(0, ...) para asegurarnos de que si retrocede, no baje de la casilla 0 (evita errores)
                jugador.setPosicion(Math.max(0, jugador.getPosicion() - 2));
                break;
            case 3:
                //El caso de "piedad", no le hace nada al jugador
                mensaje += "¡Te has salvado esta vez!";
                break;
        }
        
        return mensaje; 
        //Devolvemos el parte de guerra para que salga por pantalla
    }
} 
//Fin de la clase Foca
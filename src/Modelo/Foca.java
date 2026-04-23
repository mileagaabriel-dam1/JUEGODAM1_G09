package Modelo;

// La Foca cumple el contrato de 'Entidad'
public class Foca implements Entidad {

    @Override
    public String getNombre() { 
        return "Foca"; 
    }

    @Override
    public String getSimbolo() { 
        return "🦭"; // El emoji que saldrá en la casilla
    }
    
    @Override
    public String getDescripcion() { 
        return "Te ha encontrado una Foca, cuidado con ella"; 
    }
    
    // Aquí es donde la Foca decide qué trastada hacerte
    @Override
    public String interactuar(Jugador jugador) {
        String mensaje = "🦭 Te ha atrapado una foca. ";

        // TRUCO DE AZAR: Generamos un número aleatorio entre 0 y 3
        int accion = (int)(Math.random() * 4);
        
        switch(accion) {
            case 0:
                mensaje += "Te ha quitado un pez.";
                jugador.getInventario().quitarPez(); // Accede al inventario y resta uno
                break;
            case 1:
                mensaje += "Te ha quitado una bola de nieve.";
                jugador.getInventario().quitarBolaNieve(); // Lo mismo con las bolas
                break;
            case 2:
                mensaje += "Te hace retroceder 2 casillas.";
                // Igual que en el agujero, usamos Math.max para no salirnos del tablero por abajo
                jugador.setPosicion(Math.max(0, jugador.getPosicion() - 2));
                break;
            case 3:
                // El caso de suerte: la foca pasa de ti
                mensaje += "¡Te has salvado esta vez!";
                break;
        }
        
        return mensaje;
    }
}
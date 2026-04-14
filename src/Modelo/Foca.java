package Modelo;

public class Foca implements Entidad {

    @Override
    public String getNombre() {
        return "Foca";
    }

    @Override
    public String getSimbolo() {
        return "🦭"; // o "F" si quieres modo consola puro
    }

    @Override
    public String getDescripcion() {
        return "Una foca traviesa que puede robarte recursos o hacerte retroceder.";
    }

    @Override
    public String interactuar(Jugador jugador) {

        int accion = (int)(Math.random() * 4);

        switch (accion) {

            case 0:
                jugador.getInventario().quitarPez();
                return "La foca te roba un pez.";

            case 1:
                jugador.getInventario().quitarBolaNieve();
                return "La foca te roba una bola de nieve.";

            case 2:
                jugador.setPosicion(Math.max(0, jugador.getPosicion() - 2));
                return "La foca te empuja hacia atrás 2 casillas.";

            case 3:
            default:
                return "La foca te mira... pero decides huir a tiempo.";
        }
    }
}
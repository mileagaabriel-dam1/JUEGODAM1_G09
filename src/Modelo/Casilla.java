package Modelo;

public class Casilla {

    // Atributos privados: la posición, el tipo (de un Enum) y lo que hay dentro
    private int posicion;
    private TipoCasilla tipo;
    private Entidad entidad;

    // El constructor: cuando creamos una casilla, le decimos su número y qué va a ser
    public Casilla(int posicion, TipoCasilla tipo) {
        this.posicion = posicion;
        this.tipo = tipo;

        // Según el tipo que nos pasen, metemos un objeto u otro dentro de la casilla
        switch (tipo) {
            case PINGUINO:
                entidad = new Pinguino();
                break;
            case OSO:
                entidad = new Oso();
                break;
            case AGUJERO:
                entidad = new AgujeroHielo();
                break;
            case TRINEO:
                entidad = new Trineo();
                break;
            case INTERROGANTE:
                // Las de "?" no tienen entidad fija porque el premio se elige al azar luego
                entidad = null;
                break;
        }
    }

    // Método para sacar el icono (el emoji) que se verá en el tablero
    public String getSimbolo() {
        if (entidad != null) {
            // Si hay algo dentro (oso, pingüino...), que nos dé su símbolo
            return entidad.getSimbolo();
        } else {
            // Si está vacía o es interrogante, ponemos el símbolo por defecto
            return "❓";
        }
    }

    // Para que el juego nos diga qué hace esa casilla si caemos en ella
    public String getDescripcion() {
        if (entidad != null) {
            return entidad.getDescripcion();
        } else {
            return "Casilla misteriosa";
        }
    }

    // Getters estándar para que los controladores puedan cotillear qué hay en la casilla
    public TipoCasilla getTipo() {
        return tipo;
    }

    public int getPosicion() {
        return posicion;
    }

    public Entidad getEntidad() {
        return entidad;
    }
}
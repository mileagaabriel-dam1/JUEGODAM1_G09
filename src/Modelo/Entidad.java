package Modelo;

/**
 * Interfaz base para todas las entidades del juego.
 */
public interface Entidad {

    String getNombre();

    String getSimbolo();

    String getDescripcion();

    String interactuar(Jugador jugador);
}
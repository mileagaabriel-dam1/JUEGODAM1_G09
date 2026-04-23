package Modelo;

// 'interface' significa que esto es una lista de reglas.
// Cualquier clase que quiera ser una "Entidad" (Oso, Agujero, Pinguino...)
// TIENE que escribir estos 4 métodos sí o sí.
public interface Entidad {
    
    // Todas las entidades deben decirnos cómo se llaman
    String getNombre();
    
    // Todas deben tener un icono o emoji para el tablero
    String getSimbolo();
    
    // Todas deben explicar qué hacen
    String getDescripcion();
    
    // Este es el más importante: define qué le pasa al jugador
    // cuando choca con esta entidad (perder turno, retroceder, etc.)
    String interactuar(Jugador jugador);
}
package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private List<Casilla> casillas; // La lista que guarda todas las cajitas (casillas)
    private static final int TAMANO = 50; // El juego siempre tendrá 50 casillas

    public Tablero() {
        casillas = new ArrayList<>();
        generarTablero(); // En cuanto se crea el tablero, se rellena solo
    }

    // El método que "monta" el escenario
    private void generarTablero() {
        // La casilla 0 siempre es el Pingüino (la salida segura)
        casillas.add(new Casilla(0, TipoCasilla.PINGUINO));

        // Sacamos todos los tipos posibles (OSO, FOCA, TRINEO, etc.) del Enum
        TipoCasilla[] tipos = TipoCasilla.values();

        // Rellenamos desde la casilla 1 hasta la 49 de forma aleatoria
        for (int i = 1; i < TAMANO; i++) {
            int aleatorio = (int) (Math.random() * tipos.length);
            // Creamos una casilla nueva con un tipo al azar y la metemos en la lista
            casillas.add(new Casilla(i, tipos[aleatorio]));
        }
    }

    // Para pedir una casilla concreta por su número
    public Casilla getCasilla(int index) {
        return casillas.get(index);
    }

    public int getTamano() {
        return TAMANO;
    }

    // Este método es para debugear: pinta el tablero en la consola con emojis
    public void mostrarTablero() {
        System.out.println("\n--- TABLERO (50 casillas) ---");
        for (int i = 0; i < TAMANO; i++) {
            // Cada 10 casillas, hace un salto de línea para que se vea ordenado
            if (i % 10 == 0 && i > 0) {
                System.out.println();
            }
            System.out.print((i+1) + ":" + casillas.get(i).getSimbolo() + " ");
        }
        System.out.println("\n");
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }
}
package Modelo; 
//Capa de Modelo, el tablero es la estructura de datos principal

import java.util.ArrayList;
import java.util.List;

public class Tablero {

    //La lista que guarda todas las instancias de Casilla (nuestro mapa)
    private List<Casilla> casillas; 
    
    //Constante estática, El tamaño del mundo está definido por Randy y no cambia.
    private static final int TAMANO = 50; 

    //Constructor, Al crear el objeto Tablero, se autogenera el mapa
    public Tablero() {
        casillas = new ArrayList<>();
        generarTablero(); 
        //Llamada al método privado que monta la lógica del escenario
    }

    //El "constructor de mundos", Rellena la lista de casillas.
    
    private void generarTablero() {
        //Regla de Randy, La casilla 0 es sagrada, siempre es el inicio (Pingüino)
        casillas.add(new Casilla(0, TipoCasilla.PINGUINO));

        //Obtenemos un array con todos los valores posibles del Enum (OSO, FOCA, etc.)
        TipoCasilla[] tipos = TipoCasilla.values();

        //Bucle para rellenar el resto del tablero (de la 1 a la 49)
        for (int i = 1; i < TAMANO; i++) {
            //Generamos un índice aleatorio basado en la cantidad de tipos que existen
            int aleatorio = (int) (Math.random() * tipos.length);
            
            //Creamos la casilla con ese tipo al azar y la añadimos a nuestra lista
            casillas.add(new Casilla(i, tipos[aleatorio]));
        }
    }

    //Método para obtener una casilla específica (muy usado por el Controlador)
    public Casilla getCasilla(int index) {
        return casillas.get(index);
    }

    //Getter para consultar la longitud total del tablero
    public int getTamano() {
        return TAMANO;
    }

    //Método de visualización por consola:
    //Sirve para que los programadores veamos si el mapa se ha creado bien.
    
    public void mostrarTablero() {
        System.out.println("\n--- TABLERO (50 casillas) ---");
        for (int i = 0; i < TAMANO; i++) {
            //Operador módulo (%): Si el resto de i/10 es 0, saltamos de línea (filas de 10)
            if (i % 10 == 0 && i > 0) {
                System.out.println();
            }
            //Pintamos el número de casilla y el símbolo de la entidad que vive allí
            System.out.print((i+1) + ":" + casillas.get(i).getSimbolo() + " ");
        }
        System.out.println("\n");
    }

    //Getter para obtener la lista completa (necesario para la interfaz JavaFX)
    public List<Casilla> getCasillas() {
        return casillas;
    }
} //Fin de la clase Tablero
package Modelo; 
//Perteneciente a la capa del Modelo (Datos del jugador)

import java.util.HashMap; 
//Importamos HashMap para gestionar el almacén de objetos
import java.util.Map; 
//Importamos la interfaz Map para seguir las buenas prácticas

public class Inventario {

    //El HashMap actúa como una mochila, la CLAVE es el tipo de ítem y el VALOR es la cantidad
    private Map<Item.TipoItem, Integer> items;

    //Constantes 'static final', son reglas fijas. El máximo de objetos no cambia nunca.
    private static final int MAX_PECES = 2;
    private static final int MAX_BOLAS = 6;
    private static final int MAX_DADOS = 3;
    //Explicación de que es "static final", bueno ya esta arriba, pero basicamente es un valor fijo, ni mas, ni menos.
    //Si esto dice que el MAX_PECES son 2, MAX_BOLAS son 6, y MAX_DADOS son 3, sera asi.

    //Constructor, prepara la mochila al empezar el juego
    public Inventario() {
        items = new HashMap<>();
        //Un hashmap es como un arraylist, pero tu le pones una etiqueta a cada cosa.
        //Inicializamos los valores por defecto, Randy nos regala 2 peces y 1 dado para empezar
        items.put(Item.TipoItem.PEZ, 2);
        items.put(Item.TipoItem.BOLA_NIEVE, 0);
        items.put(Item.TipoItem.DADO, 1);
    }

    
    public void agregarPez() {
        int actual = items.get(Item.TipoItem.PEZ); 
        //Miramos cuántos tenemos ahora
        
        if (actual < MAX_PECES) { 
        	//Solo sumamos si no hemos llegado al límite de pezes
            items.put(Item.TipoItem.PEZ, actual + 1);
        }
    }

    //MÉTODOS PARA QUITAR ÍTEMS

    public boolean quitarPez() {
        int actual = items.get(Item.TipoItem.PEZ);
        if (actual > 0) { 
        	//Comprobamos que haya algo para quitar (no podemos tener -1 peces)
            items.put(Item.TipoItem.PEZ, actual - 1);
            return true; 
            //Devolvemos true porque la operación ha tenido éxito
        }
        return false; 
        //Devolvemos false si la mochila estaba vacía
    }

    //(Los métodos de Bolas de Nieve y Dados siguen la misma lógica de control de stock)

    public void agregarBolaNieve() {
        int actual = items.get(Item.TipoItem.BOLA_NIEVE);
        if (actual < MAX_BOLAS) {
            items.put(Item.TipoItem.BOLA_NIEVE, actual + 1);
        }
    }

    public boolean quitarBolaNieve() {
        int actual = items.get(Item.TipoItem.BOLA_NIEVE);
        if (actual > 0) {
            items.put(Item.TipoItem.BOLA_NIEVE, actual - 1);
            return true;
        }
        return false;
    }

    public void agregarDado() {
        int actual = items.get(Item.TipoItem.DADO);
        if (actual < MAX_DADOS) {
            items.put(Item.TipoItem.DADO, actual + 1);
        }
    }

    public boolean quitarDado() {
        int actual = items.get(Item.TipoItem.DADO);
        if (actual > 0) {
            items.put(Item.TipoItem.DADO, actual - 1);
            return true;
        }
        return false;
    }

    //GETTERS
    //Sirven para que la interfaz o los controladores consulten las cantidades

    public int getPeces() {
        return items.get(Item.TipoItem.PEZ);
    }

    public int getBolasNieve() {
        return items.get(Item.TipoItem.BOLA_NIEVE);
    }

    public int getDados() {
        return items.get(Item.TipoItem.DADO);
    }

    //Método estético, devuelve un String formateado con los iconos para la interfaz gráfica
    public String obtenerResumen() {
        return String.format(
            "🐟: %d/2 | ⛄: %d/6 | 🎲: %d/3",
            getPeces(), getBolasNieve(), getDados()
            
            //%d es un marcador de posición(la d viene de decimal), por asi decirl
            //El primer %d se cambia, por lo que devuelve "getPeces"
            //El segundo &d lo mismo, pero por getBolasNieve
            //El tercer %d, por getDados
            
            //Para entenderlo mejor, si el jugador tiene 1 pez, 3 bolas y 2 dados, saldria esto:
            
            //1/2, 3/6, 2/3
        );
    }
} // Fin de la mochila
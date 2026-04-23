package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    // El HashMap es como un almacén: guarda el Tipo de Item y la cantidad (Integer)
    private Map<Item.TipoItem, Integer> items;

    // Ponemos límites máximos para que el jugador no sea infinito
    private static final int MAX_PECES = 2;
    private static final int MAX_BOLAS = 6;
    private static final int MAX_DADOS = 3;

    public Inventario() {
        items = new HashMap<>();
        // Al empezar la partida, damos 2 peces y 1 dado por defecto
        items.put(Item.TipoItem.PEZ, 2);
        items.put(Item.TipoItem.BOLA_NIEVE, 0);
        items.put(Item.TipoItem.DADO, 1);
    }

    // Métodos para AGREGAR cosas (siempre comprobando que no pasemos del máximo)
    public void agregarPez() {
        int actual = items.get(Item.TipoItem.PEZ);
        if (actual < MAX_PECES) {
            items.put(Item.TipoItem.PEZ, actual + 1);
        }
    }

    // Métodos para QUITAR cosas (devuelven 'true' si han podido quitarlo, o 'false' si no tenías nada)
    public boolean quitarPez() {
        int actual = items.get(Item.TipoItem.PEZ);
        if (actual > 0) {
            items.put(Item.TipoItem.PEZ, actual - 1);
            return true;
        }
        return false;
    }

    // --- Los métodos de Bolas de Nieve y Dados funcionan igual que los de arriba ---

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

    // Getters para que el juego sepa cuántas cosas nos quedan
    public int getPeces() {
        return items.get(Item.TipoItem.PEZ);
    }

    public int getBolasNieve() {
        return items.get(Item.TipoItem.BOLA_NIEVE);
    }

    public int getDados() {
        return items.get(Item.TipoItem.DADO);
    }

    // Este método crea una frase bonita con emojis para mostrar la mochila por pantalla
    public String obtenerResumen() {
        return String.format(
            "🐟: %d/2 | ⛄: %d/6 | 🎲: %d/3",
            getPeces(), getBolasNieve(), getDados()
        );
    }
}
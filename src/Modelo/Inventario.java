package Modelo;

import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private Map<Item.TipoItem, Integer> items;

    private static final int MAX_PECES = 2;
    private static final int MAX_BOLAS = 6;
    private static final int MAX_DADOS = 3;

    public Inventario() {

        items = new HashMap<>();

        items.put(Item.TipoItem.PEZ, 2);
        items.put(Item.TipoItem.BOLA_NIEVE, 0);
        items.put(Item.TipoItem.DADO, 1);
    }

    // 🔥 MÉTODOS SEGUROS
    public void agregarPez() {
        modificar(Item.TipoItem.PEZ, MAX_PECES, +1);
    }

    public boolean quitarPez() {
        return modificar(Item.TipoItem.PEZ, MAX_PECES, -1);
    }

    public void agregarBolaNieve() {
        modificar(Item.TipoItem.BOLA_NIEVE, MAX_BOLAS, +1);
    }

    public boolean quitarBolaNieve() {
        return modificar(Item.TipoItem.BOLA_NIEVE, MAX_BOLAS, -1);
    }

    public void agregarDado() {
        modificar(Item.TipoItem.DADO, MAX_DADOS, +1);
    }

    public boolean quitarDado() {
        return modificar(Item.TipoItem.DADO, MAX_DADOS, -1);
    }

    // 🔥 MÉTODO CENTRALIZADO
    private boolean modificar(Item.TipoItem tipo, int max, int cambio) {

        int actual = items.getOrDefault(tipo, 0);
        int nuevo = actual + cambio;

        if (nuevo >= 0 && nuevo <= max) {
            items.put(tipo, nuevo);
            return true;
        }

        return false;
    }

    // GETTERS SEGUROS
    public int getPeces() {
        return items.getOrDefault(Item.TipoItem.PEZ, 0);
    }

    public int getBolasNieve() {
        return items.getOrDefault(Item.TipoItem.BOLA_NIEVE, 0);
    }

    public int getDados() {
        return items.getOrDefault(Item.TipoItem.DADO, 0);
    }

    public String obtenerResumen() {
        return String.format(
            "Peces: %d/%d | Bolas de nieve: %d/%d | Dados: %d/%d",
            getPeces(), MAX_PECES,
            getBolasNieve(), MAX_BOLAS,
            getDados(), MAX_DADOS
        );
    }
}
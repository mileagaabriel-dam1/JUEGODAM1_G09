package Modelo;

public class Item {

    public enum TipoItem {
        PEZ, BOLA_NIEVE, DADO
    }

    private TipoItem tipo;
    private String nombre;

    public Item(TipoItem tipo) {
        this.tipo = tipo;
        this.nombre = definirNombre(tipo);
    }

    private String definirNombre(TipoItem tipo) {

        switch (tipo) {

            case PEZ:
                return "Pez";

            case BOLA_NIEVE:
                return "Bola de nieve";

            case DADO:
                return "Dado extra";

            default:
                return "Desconocido";
        }
    }

    public String getSimbolo() {

        switch (tipo) {

            case PEZ:
                return "PEZ";

            case BOLA_NIEVE:
                return "NIEVE";

            case DADO:
                return "DADO";

            default:
                return "OBJETO";
        }
    }

    public TipoItem getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }
}
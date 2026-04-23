package Modelo;

public class Item {

    // El 'enum' es una lista de valores fijos. 
    // Sirve para no usar Strings (como "Pez") y evitar errores de escritura.
    public enum TipoItem {
        PEZ, BOLA_NIEVE, DADO
    }

    private TipoItem tipo;
    private String nombre;

    // Constructor: según el tipo que le pasemos, le asigna un nombre de texto
    public Item(TipoItem tipo) {
        this.tipo = tipo;

        switch (tipo) {
            case PEZ:
                this.nombre = "Pez";
                break;
            case BOLA_NIEVE:
                this.nombre = "Bola de nieve";
                break;
            case DADO:
                this.nombre = "Dado extra";
                break;
        }
    }

    // Método para sacar el emoji correspondiente a cada objeto
    public String getSimbolo() {
        switch (tipo) {
            case PEZ: return "🐟";
            case BOLA_NIEVE: return "⛄";
            case DADO: return "🎲";
            default: return "📦"; // Un paquete por si acaso hubiera un error
        }
    }

    // Getters para que el resto de clases sepan qué item es este
    public TipoItem getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }
}
package Modelo; 
//Forma parte del Modelo porque define los datos de los objetos del juego

public class Item {

    //El 'enum' es una lista de valores constantes.
    //Randy (el jefe) prefiere esto antes que usar textos (Strings), porque
    //así evitamos errores de escritura como poner "Pez" en un sitio y "pes" en otro.
    
    public enum TipoItem {
        PEZ, BOLA_NIEVE, DADO
    }

    private TipoItem tipo;  
    //El tipo técnico (del Enum)
    
    private String nombre;  
    //El nombre legible para jugadores

    //Constructor, al crear un Item, le asignamos su identidad
    public Item(TipoItem tipo) {
        this.tipo = tipo;

        //Usamos un switch para decidir qué nombre ponerle según su tipo
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

    //Método para obtener la representación visual del objeto.
    //Ayuda a la interfaz gráfica a saber qué emoji pintar.
    
    public String getSimbolo() {
        switch (tipo) {
            case PEZ: return "🐟";
            case BOLA_NIEVE: return "⛄";
            case DADO: return "🎲";
            default: return "📦"; 
            //Un "paquete" de seguridad por si Randy se confunde
        }
    }

    //Getters estándar
    
    public TipoItem getTipo() {
        return tipo; 
        //Devuelve el valor del Enum (ej, TipoItem.PEZ)
    }

    public String getNombre() {
        return nombre; 
        //Devuelve el texto (ej, "Pez")
    }
} //Fin de la clase Item
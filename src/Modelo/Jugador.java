package Modelo; 
//Forma parte del Modelo porque contiene los datos del usuario

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    //Atributos privados, Encapsulamiento puro. Solo el jugador sabe sus secretos.
    private String nombre;
    private String color;
    private TipoJugador tipo;     
    //HUMANO o IA (usando otro Enum para evitar errores)
    
    private int posicion;         
    //Su ubicación actual en el tablero
    
    private Inventario inventario; 
    //La instancia de su mochila personalizada
    
    private List<String> historial; 
    //Registro de eventos para saber qué ha hecho el jugador

    //Constructor, Define la identidad del jugador al unirse a la partida
    public Jugador(String nombre, String color, TipoJugador tipo) {
        this.nombre = nombre;
        this.color = color;
        this.tipo = tipo;
        this.posicion = 0; 
        //Randy dice que todos empiezan en la casilla de salida (0)
        
        this.inventario = new Inventario(); 
        //Le entregamos su equipo inicial
        
        this.historial = new ArrayList<>(); 
        //Inicializamos la lista de acciones
    }

    //El motor de movimiento, Genera un número aleatorio entre 1 y 6.
    //Usamos (int)(Math.random() * 6) que da de 0 a 5, y sumamos 1.
    //Para que no pueda dar 0, que sino, complicado llegar al final
    
    public int lanzarDado() {
        int resultado = (int) (Math.random() * 6) + 1;
        registrarAccion("Ha sacado un " + resultado); 
        //Guardamos el evento en su historial
        return resultado;
    }

    //Método para añadir una línea de texto a su diario de a bordo
    public void registrarAccion(String accion) {
        historial.add(accion);
    }

    //Getters para que los controladores puedan consultar sus datos
    public String getNombre() { return nombre; }
    public String getColor() { return color; }
    public TipoJugador getTipo() { return tipo; }
    public int getPosicion() { return posicion; }

    //SetPosicion con validación (Escudo de seguridad).
    //Evita que el jugador "se salga del mundo" si una foca o un oso lo empujan demasiado.
    //Es decir, que no se salga del tablero vamos
    
    public void setPosicion(int posicion) {
        if (posicion < 0) {
            this.posicion = 0; 
            //Si retrocede de más, se queda en la casilla 0
            
        } else if (posicion > 49) {
            this.posicion = 49; 
            //Si se pasa de la meta (casilla 50), se queda en la última
        } else {
            this.posicion = posicion; 
            //Posición válida dentro del rango
        }
    }

    //Acceso a su mochila
    public Inventario getInventario() {
        return inventario;
    }

    //Acceso a su registro de movimientos
    public List<String> getHistorial() {
        return historial;
    }

    //Método boolean rápido para que el controlador sepa si debe esperar un clic o moverlo solo
    public boolean esIA() {
        return tipo == TipoJugador.IA;
    }
} 
//Fin de la clase Jugador
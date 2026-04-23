package Modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    // Datos básicos del jugador
    private String nombre;
    private String color;
    private TipoJugador tipo; // HUMANO o IA
    private int posicion;
    private Inventario inventario; // Su mochila
    private List<String> historial; // Para guardar lo que ha ido haciendo

    // Constructor: crea un jugador desde cero al empezar la partida
    public Jugador(String nombre, String color, TipoJugador tipo) {
        this.nombre = nombre;
        this.color = color;
        this.tipo = tipo;
        this.posicion = 0; // Todos empiezan en la casilla 0
        this.inventario = new Inventario(); // Se le da una mochila nueva
        this.historial = new ArrayList<>(); // Se prepara la lista de sus movimientos
    }

    // El método para tirar el dado (genera un número del 1 al 6)
    public int lanzarDado() {
        int resultado = (int) (Math.random() * 6) + 1;
        registrarAccion("Ha sacado un " + resultado);
        return resultado;
    }

    // Guarda una frase en el historial del jugador para saber qué le ha pasado
    public void registrarAccion(String accion) {
        historial.add(accion);
    }

    // --- Getters estándar ---
    public String getNombre() { return nombre; }
    public String getColor() { return color; }
    public TipoJugador getTipo() { return tipo; }
    public int getPosicion() { return posicion; }

    // Método importante: cambia la posición pero con "topes" para no romper el juego
    public void setPosicion(int posicion) {
        if (posicion < 0) {
            this.posicion = 0; // No puede haber posiciones negativas
        } else if (posicion > 49) {
            this.posicion = 49; // Si el tablero tiene 50 casillas, el tope es la 49
        } else {
            this.posicion = posicion;
        }
    }

    public Inventario getInventario() {
        return inventario;
    }

    public List<String> getHistorial() {
        return historial;
    }

    // Método rápido para saber si este jugador es la máquina o una persona
    public boolean esIA() {
        return tipo == TipoJugador.IA;
    }
}
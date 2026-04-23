package Controladores;

public class ControladorJuego {

    // Ponemos 'private' para que nadie de fuera nos toque estas variables por error.
    // Solo se pueden usar dentro de esta clase.
    private ControladorTablero controladorTablero;
    private ControladorJugador controladorJugador;
    private ControladorTurnos controladorTurnos;
    private ControladorEventos controladorEventos;

    // Este es el constructor. Es lo que se ejecuta primero para "encender" todo.
    public ControladorJuego() {
        // Usamos 'this' para referirnos a las variables de arriba y darles valor con 'new'
        this.controladorTablero = new ControladorTablero();
        this.controladorJugador = new ControladorJugador();
        this.controladorTurnos = new ControladorTurnos();
        this.controladorEventos = new ControladorEventos();
    }

    // Estos son los 'Getters'. 
    // Como las variables de arriba son privadas, usamos estos métodos 
    // 'public' para poder pedirlas desde otras clases de forma segura.

    public ControladorTablero getControladorTablero() {
        return controladorTablero;
    }

    public ControladorJugador getControladorJugador() {
        return controladorJugador;
    }

    public ControladorTurnos getControladorTurnos() {
        return controladorTurnos;
    }

    public ControladorEventos getControladorEventos() {
        return controladorEventos;
    }
}
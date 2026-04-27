package Controladores;

public class controladorJuego {

    // Ponemos 'private' para que nadie de fuera nos toque estas variables por error.
    // Solo se pueden usar dentro de esta clase.
    private controladorTablero controladorTablero;
    private controladorJugador controladorJugador;
    private controladorTurnos controladorTurnos;
    private controladorEventos controladorEventos;

    // Este es el constructor. Es lo que se ejecuta primero para "encender" todo.
    public controladorJuego() {
        // Usamos 'this' para referirnos a las variables de arriba y darles valor con 'new'
        this.controladorTablero = new controladorTablero();
        this.controladorJugador = new controladorJugador();
        this.controladorTurnos = new controladorTurnos();
        this.controladorEventos = new controladorEventos();
    }

    // Estos son los 'Getters'. 
    // Como las variables de arriba son privadas, usamos estos métodos 
    // 'public' para poder pedirlas desde otras clases de forma segura.

    public controladorTablero getControladorTablero() {
        return controladorTablero;
    }

    public controladorJugador getControladorJugador() {
        return controladorJugador;
    }

    public controladorTurnos getControladorTurnos() {
        return controladorTurnos;
    }

    public controladorEventos getControladorEventos() {
        return controladorEventos;
    }
}
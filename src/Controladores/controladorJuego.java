package Controladores;

public class ControladorJuego {

    private ControladorTablero controladorTablero;
    private ControladorJugador controladorJugador;
    private ControladorTurnos controladorTurnos;
    private ControladorEventos controladorEventos;

    public ControladorJuego() {
        this.controladorTablero = new ControladorTablero();
        this.controladorJugador = new ControladorJugador();
        this.controladorTurnos = new ControladorTurnos();
        this.controladorEventos = new ControladorEventos();
    }

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
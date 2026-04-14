package Controladores;

public class ControladorJuego {

    private ControladorTablero controladorTablero;
    private ControladorJugador controladorJugador;
    private ControladorTurnos controladorTurnos;
    private ControladorEventos controladorEventos;

    public ControladorJuego() {
        controladorTablero = new ControladorTablero();
        controladorJugador = new ControladorJugador();
        controladorTurnos = new ControladorTurnos();
        controladorEventos = new ControladorEventos();
    }

    public ControladorTablero getControladorTablero() { return controladorTablero; }
    public ControladorJugador getControladorJugador() { return controladorJugador; }
    public ControladorTurnos getControladorTurnos() { return controladorTurnos; }
    public ControladorEventos getControladorEventos() { return controladorEventos; }
}
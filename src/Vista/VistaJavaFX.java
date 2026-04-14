package Vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controladores.*;

public class VistaJavaFX extends Application {

    private ControladorJuego controladorJuego;
    private ControladorTablero controladorTablero;
    private ControladorJugador controladorJugador;
    private ControladorTurnos controladorTurnos;

    private BorderPane root;

    private VistaMenu vistaMenu;
    private VistaJuego vistaJuego;
    private VistaTableroConImagenes vistaTablero;
    private VistaJugador vistaJugador;
    private VistaEventos vistaEventos;

    @Override
    public void start(Stage primaryStage) {

        controladorJuego = new ControladorJuego();
        controladorTablero = controladorJuego.getControladorTablero();
        controladorJugador = controladorJuego.getControladorJugador();
        controladorTurnos = controladorJuego.getControladorTurnos();

        vistaMenu = new VistaMenu(this);
        vistaJuego = new VistaJuego(this);
        vistaTablero = new VistaTableroConImagenes(this);
        vistaJugador = new VistaJugador(this);
        vistaEventos = new VistaEventos(this);

        primaryStage.setTitle("JOC DEL PINGÜ - DAM1 - G09");
        primaryStage.setWidth(1100);
        primaryStage.setHeight(750);

        root = new BorderPane();

        root.setTop(vistaMenu.getVista());
        root.setCenter(vistaTablero.getVista());

        VBox derecha = new VBox(10);
        derecha.setPadding(new Insets(10));
        derecha.getChildren().addAll(
            vistaJugador.getVista(),
            vistaJuego.getVista(),
            vistaEventos.getVista()
        );
        derecha.setPrefWidth(300);

        root.setRight(derecha);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        mostrarMenuPrincipal();
    }

    public void mostrarMenuPrincipal() {
        vistaJuego.ocultar();
        vistaJugador.limpiar();
        vistaTablero.limpiar();
        if (vistaEventos != null) vistaEventos.limpiar();
    }

    public void iniciarPartida(int numHumanos, boolean incluirIA) {
        controladorJugador.inicializarJugadores(numHumanos, incluirIA);
        controladorTurnos.setJugadores(controladorJugador.getJugadores());

        vistaTablero.inicializarTablero(controladorTablero);
        vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);

        vistaJugador.actualizar(controladorTurnos);
        vistaEventos.limpiar();
        vistaEventos.agregarEvento("🎮 ¡Partida iniciada!");
        vistaEventos.agregarEvento("👥 Jugadores: " + controladorJugador.getJugadores().size());
        vistaJuego.mostrar();

        if (controladorTurnos.getJugadorActual() != null &&
            controladorTurnos.getJugadorActual().esIA()) {
            vistaJuego.lanzarDadoIA();
        }
    }

    public void lanzarDado() {
        vistaJuego.lanzarDado();
    }

    public ControladorJuego getControladorJuego() { return controladorJuego; }
    public ControladorTablero getControladorTablero() { return controladorTablero; }
    public ControladorJugador getControladorJugador() { return controladorJugador; }
    public ControladorTurnos getControladorTurnos() { return controladorTurnos; }
    public VistaTableroConImagenes getVistaTablero() { return vistaTablero; }
    public VistaJugador getVistaJugador() { return vistaJugador; }
    public VistaEventos getVistaEventos() { return vistaEventos; }

    // MAIN CORREGIDO para Java 8
    public static void main(String[] args) {
        launch(args);  // ← Así funciona en Java 8
    }
}
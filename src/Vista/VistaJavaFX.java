package Vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controladores.*;

//'extends Application' es la clave: le dice a Java que esta clase es una App de JavaFX
public class VistaJavaFX extends Application {

    //CONTROLADORES, Las conexiones con la lógica del juego
    private controladorJuego controladorJuego;
    private controladorTablero controladorTablero;
    private controladorJugador controladorJugador;
    private controladorTurnos controladorTurnos;

    //LAYOUT, El contenedor principal (el lienzo)
    private BorderPane root;

    //SUB-VISTAS, Cada parte de la interfaz modularizada
    private VistaMenu vistaMenu;
    private VistaJuego vistaJuego;
    private VistaTableroConImagenes vistaTablero;
    private VistaJugador vistaJugador;
    private VistaEventos vistaEventos;

    @Override
    public void start(Stage primaryStage) {
        //Inicializamos los controladores (la lógica)
        controladorJuego = new controladorJuego();
        controladorTablero = controladorJuego.getControladorTablero();
        controladorJugador = controladorJuego.getControladorJugador();
        controladorTurnos = controladorJuego.getControladorTurnos();

        //Inicializamos las vistas pasando 'this' para que puedan comunicarse con esta clase
        vistaMenu = new VistaMenu(this);
        vistaJuego = new VistaJuego(this);
        vistaTablero = new VistaTableroConImagenes(this);
        vistaJugador = new VistaJugador(this);
        vistaEventos = new VistaEventos(this);

        //Configuración de la ventana (título y tamaño)
        primaryStage.setTitle("JOC DEL PINGÜ - DAM1 - G09");
        primaryStage.setWidth(1100);
        primaryStage.setHeight(750);

        //ORGANIZACIÓN DEL ESPACIO (BorderPane)
        root = new BorderPane();

        //Arriba ponemos el menú
        root.setTop(vistaMenu.getVista());
        //En el centro el tablero (que ocupará el máximo espacio posible)
        root.setCenter(vistaTablero.getVista());

        //A la derecha creamos una columna (VBox) para agrupar 3 paneles
        VBox derecha = new VBox(10); 
        //10px de separación entre elementos
        derecha.setPadding(new Insets(10));
        derecha.getChildren().addAll(
            vistaJugador.getVista(), 
            //Info del jugador
            vistaJuego.getVista(),   
            //Botón de tirar dado
            vistaEventos.getVista()  
            //Diario de eventos
        );
        derecha.setPrefWidth(300);

        root.setRight(derecha);

        //Creamos la "escena" con nuestro layout y la mostramos
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Al arrancar, mostramos el estado de "Menu" (limpio)
        mostrarMenuPrincipal();
    }

    //Método para dejar la interfaz lista para una nueva partida
    public void mostrarMenuPrincipal() {
        vistaJuego.ocultar();
        vistaJugador.limpiar();
        vistaTablero.limpiar();
        if (vistaEventos != null) vistaEventos.limpiar();
    }

    //Este método lo llama el botón "Empezar" de la VistaMenu
    public void iniciarPartida(int numHumanos, boolean incluirIA) {
        //Configuramos los jugadores en el controlador
        controladorJugador.inicializarJugadores(numHumanos, incluirIA);
        controladorTurnos.setJugadores(controladorJugador.getJugadores());

        //Dibujamos el tablero y colocamos los pingüinos en la salida
        vistaTablero.inicializarTablero(controladorTablero);
        vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);

        //Actualizamos los textos y logs
        vistaJugador.actualizar(controladorTurnos);
        vistaEventos.limpiar();
        vistaEventos.agregarEvento("🎮 ¡Partida iniciada!");
        vistaEventos.agregarEvento("👥 Jugadores: " + controladorJugador.getJugadores().size());
        
        vistaJuego.mostrar(); 
        //Habilitamos los botones de juego

        //Si el primer jugador es la IA, ¡que empiece a jugar ya!
        if (controladorTurnos.getJugadorActual() != null &&
            controladorTurnos.getJugadorActual().esIA()) {
            vistaJuego.lanzarDadoIA();
        }
    }

    public void lanzarDado() {
        vistaJuego.lanzarDado();
    }

    //Getters para que las sub-vistas puedan acceder a la lógica
    public controladorJuego getControladorJuego() { return controladorJuego; }
    public controladorTablero getControladorTablero() { return controladorTablero; }
    public controladorJugador getControladorJugador() { return controladorJugador; }
    public controladorTurnos getControladorTurnos() { return controladorTurnos; }
    public VistaTableroConImagenes getVistaTablero() { return vistaTablero; }
    public VistaJugador getVistaJugador() { return vistaJugador; }
    public VistaEventos getVistaEventos() { return vistaEventos; }

    //El punto de entrada del programa (obligatorio launch en Java 8)
    public static void main(String[] args) {
        launch(args); 
    }
}
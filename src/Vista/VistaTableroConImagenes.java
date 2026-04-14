package Vista;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import Controladores.*;
import Modelo.*;

public class VistaTableroConImagenes {

    private VistaJavaFX principal;
    private GridPane tableroGrid;
    private VBox vista;
    private StackPane[] casillas = new StackPane[50];
    private String[] iconosFichas = {"①", "②", "③", "④", "⑤"};

    public VistaTableroConImagenes(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {
        vista = new VBox(10);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new javafx.geometry.Insets(10));

        Label titulo = new Label("🐧 TABLERO DE JUEGO 🐧");
        titulo.setFont(new Font(18));

        tableroGrid = new GridPane();
        tableroGrid.setHgap(5);
        tableroGrid.setVgap(5);
        tableroGrid.setAlignment(Pos.CENTER);

        vista.getChildren().addAll(titulo, tableroGrid);
    }

    public void inicializarTablero(ControladorTablero controladorTablero) {
        tableroGrid.getChildren().clear();

        for (int i = 0; i < 50; i++) {
            int fila = i / 10;
            int columna = i % 10;

            Casilla casilla = controladorTablero.getCasilla(i);

            StackPane casillaPane = new StackPane();
            casillaPane.setPrefSize(55, 55);
            casillaPane.setStyle("-fx-border-color: #333; -fx-border-width: 1; -fx-background-color: #e0f7fa; -fx-background-radius: 5;");

            String emoji = obtenerEmojiCasilla(casilla.getTipo());

            Label label = new Label(emoji);
            label.setFont(new Font(22));

            Label numero = new Label(String.valueOf(i + 1));
            numero.setFont(new Font(8));
            numero.setStyle("-fx-text-fill: gray;");
            StackPane.setAlignment(numero, Pos.TOP_LEFT);

            casillaPane.getChildren().addAll(label, numero);
            tableroGrid.add(casillaPane, columna, fila);
            casillas[i] = casillaPane;
        }
    }

    private String obtenerEmojiCasilla(TipoCasilla tipo) {
        switch (tipo) {
            case PINGUINO: return "🐧";
            case OSO: return "🐻";
            case AGUJERO: return "🕳️";
            case TRINEO: return "🛷";
            case INTERROGANTE: return "❓";
            default: return "⬜";
        }
    }

    public void actualizarPosiciones(ControladorJugador controladorJugador, ControladorTurnos controladorTurnos) {
        for (int i = 0; i < casillas.length; i++) {
            StackPane casilla = casillas[i];
            while (casilla.getChildren().size() > 2) {
                casilla.getChildren().remove(2);
            }
        }

        for (int i = 0; i < controladorJugador.getJugadores().size(); i++) {
            Jugador jugador = controladorJugador.getJugadores().get(i);
            int posicion = jugador.getPosicion();

            if (posicion >= 0 && posicion < casillas.length) {
                StackPane casilla = casillas[posicion];
                
                Label ficha = new Label(iconosFichas[i]);
                ficha.setFont(new Font(14));
                ficha.setStyle("-fx-text-fill: " + obtenerColorJugador(i) + "; -fx-font-weight: bold; -fx-background-color: white; -fx-padding: 0 2 0 2;");
                StackPane.setAlignment(ficha, Pos.BOTTOM_RIGHT);
                
                casilla.getChildren().add(ficha);
            }
        }
    }
    
    private String obtenerColorJugador(int index) {
        switch(index) {
            case 0: return "red";
            case 1: return "blue";
            case 2: return "green";
            case 3: return "orange";
            default: return "black";
        }
    }

    public void animarMovimiento(Jugador jugador, int origen, int destino, Runnable callback) {
        actualizarPosiciones(principal.getControladorJugador(), principal.getControladorTurnos());
        if (callback != null) {
            callback.run();
        }
    }

    public void limpiar() {
        tableroGrid.getChildren().clear();
    }

    public VBox getVista() {
        return vista;
    }
}
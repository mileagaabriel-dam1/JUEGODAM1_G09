package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import Controladores.*;
import Modelo.*;

public class VistaTableroConImagenes {

    private VistaJavaFX principal;
    private GridPane tableroGrid;
    private VBox vista;

    private static final int TAMANO = 50;

    private StackPane[] casillas = new StackPane[TAMANO];

    public VistaTableroConImagenes(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {

        vista = new VBox(10);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(10));

        Label titulo = new Label("TABLERO");
        titulo.setFont(new Font(20));

        tableroGrid = new GridPane();
        tableroGrid.setHgap(5);
        tableroGrid.setVgap(5);

        vista.getChildren().addAll(titulo, tableroGrid);
    }

    public void inicializarTablero(ControladorTablero controladorTablero) {

        tableroGrid.getChildren().clear();

        for (int i = 0; i < TAMANO; i++) {

            int fila = i / 10;
            int columna = i % 10;

            Casilla casilla = controladorTablero.getCasilla(i);

            StackPane casillaPane = new StackPane();
            casillaPane.setPrefSize(60, 60);
            casillaPane.setStyle("-fx-border-color: black;");

            String emoji = getEmojiCasilla(casilla.getTipo());

            Label label = new Label(emoji);
            label.setFont(new Font(20));

            casillaPane.getChildren().add(label);

            tableroGrid.add(casillaPane, columna, fila);

            casillas[i] = casillaPane;
        }
    }

    private String getEmojiCasilla(TipoCasilla tipo) {

        switch (tipo) {
            case PINGUINO: return "🐧";
            case OSO: return "🐻";
            case AGUJERO: return "🕳️";
            case TRINEO: return "🛷";
            case INTERROGANTE: return "❓";
            default: return "⬜";
        }
    }

    public void actualizarPosiciones(ControladorJugador controladorJugador) {

        // limpiar fichas
        for (StackPane casilla : casillas) {

            if (casilla != null && casilla.getChildren().size() > 1) {
                casilla.getChildren().remove(1, casilla.getChildren().size());
            }
        }

        String[] iconos = {"①", "②", "③", "④", "⑤"};

        for (int i = 0; i < controladorJugador.getJugadores().size(); i++) {

            Jugador jugador = controladorJugador.getJugadores().get(i);

            int posicion = jugador.getPosicion();

            if (posicion < 0 || posicion >= TAMANO) continue;

            StackPane casilla = casillas[posicion];

            if (casilla == null) continue;

            Label ficha = new Label(iconos[i]);
            StackPane.setAlignment(ficha, Pos.BOTTOM_RIGHT);

            casilla.getChildren().add(ficha);
        }
    }

    public void limpiar() {
        tableroGrid.getChildren().clear();
    }

    public VBox getVista() {
        return vista;
    }
}
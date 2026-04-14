package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import Controladores.*;
import Modelo.*;

public class VistaJugador {

    private VistaJavaFX principal;
    private VBox vista;

    private Label jugadorLabel;
    private Label posicionLabel;
    private Label inventarioLabel;
    private Label turnoLabel;

    public VistaJugador(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {

        vista = new VBox(10);
        vista.setAlignment(Pos.TOP_LEFT);
        vista.setPadding(new Insets(15));

        vista.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #444;" +
            "-fx-border-radius: 8;" +
            "-fx-padding: 10;"
        );

        Label titulo = new Label("INFORMACIÓN");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        turnoLabel = new Label("Turno: -");
        jugadorLabel = new Label("Jugador: -");
        posicionLabel = new Label("Posición: -");
        inventarioLabel = new Label("Inventario: -");

        vista.getChildren().addAll(
                titulo,
                turnoLabel,
                jugadorLabel,
                posicionLabel,
                inventarioLabel
        );
    }

    public void actualizar(ControladorTurnos controladorTurnos) {

        if (controladorTurnos == null) {
            limpiar();
            return;
        }

        Jugador actual = controladorTurnos.getJugadorActual();

        if (actual == null) {
            limpiar();
            return;
        }

        String tipo = actual.esIA() ? "(IA)" : "";

        turnoLabel.setText("Turno: " + actual.getNombre());
        jugadorLabel.setText("Jugador: " + actual.getNombre() + " " + tipo);

        // Vista 1–50 (pero lógica 0–49)
        posicionLabel.setText("Posición: " + (actual.getPosicion() + 1) + "/50");

        Inventario inv = actual.getInventario();

        inventarioLabel.setText(
            "Inventario: " +
            (inv != null ? inv.obtenerResumen() : "vacío")
        );
    }

    public void limpiar() {
        turnoLabel.setText("Turno: -");
        jugadorLabel.setText("Jugador: -");
        posicionLabel.setText("Posición: -");
        inventarioLabel.setText("Inventario: -");
    }

    public VBox getVista() {
        return vista;
    }
}
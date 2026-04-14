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
        vista.setStyle("-fx-background-color: white; -fx-border-color: #90a4ae; -fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;");
        vista.setPrefWidth(260);

        Label titulo = new Label("📊 INFORMACIÓN");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #37474f;");

        turnoLabel = new Label("Turno: -");
        jugadorLabel = new Label("Jugador: -");
        posicionLabel = new Label("Posición: -");
        inventarioLabel = new Label("Inventario: -");

        turnoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        jugadorLabel.setFont(Font.font("Arial", 12));
        posicionLabel.setFont(Font.font("Arial", 12));
        inventarioLabel.setFont(Font.font("Arial", 12));

        vista.getChildren().addAll(titulo, turnoLabel, jugadorLabel, posicionLabel, inventarioLabel);
    }

    public void actualizar(ControladorTurnos controladorTurnos) {
        if (controladorTurnos == null || controladorTurnos.getJugadorActual() == null) {
            limpiar();
            return;
        }

        Jugador actual = controladorTurnos.getJugadorActual();
        String tipo = actual.esIA() ? " (IA)" : "";

        turnoLabel.setText("🎲 Turno: " + actual.getNombre());
        jugadorLabel.setText("👤 Jugador: " + actual.getNombre() + tipo);
        posicionLabel.setText("📍 Posición: " + (actual.getPosicion() + 1) + "/50");

        if (actual.getInventario() != null) {
            inventarioLabel.setText("🎒 " + actual.getInventario().obtenerResumen());
        } else {
            inventarioLabel.setText("🎒 Inventario: vacío");
        }
    }

    public void limpiar() {
        turnoLabel.setText("🎲 Turno: -");
        jugadorLabel.setText("👤 Jugador: -");
        posicionLabel.setText("📍 Posición: -");
        inventarioLabel.setText("🎒 Inventario: -");
    }

    public VBox getVista() { return vista; }
}
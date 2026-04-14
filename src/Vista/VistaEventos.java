package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaEventos {

    private VistaJavaFX principal;
    private VBox vista;
    private TextArea logArea;

    public VistaEventos(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {

        vista = new VBox(8);
        vista.setPadding(new Insets(15));
        vista.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: #90a4ae;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 12;"
        );

        vista.setPrefHeight(200);

        HBox tituloBox = new HBox(10);
        tituloBox.setAlignment(Pos.CENTER_LEFT);

        Label titulo = new Label("EVENTOS DEL JUEGO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #37474f;");

        tituloBox.getChildren().add(titulo);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        logArea.setWrapText(true);

        logArea.setStyle(
            "-fx-font-family: 'Monospaced';" +
            "-fx-font-size: 12px;" +
            "-fx-control-inner-background: #f5f5f5;"
        );

        vista.getChildren().addAll(tituloBox, logArea);
    }

    public void agregarEvento(String evento) {
        logArea.appendText("► " + evento + "\n");
        logArea.setScrollTop(Double.MAX_VALUE);
    }

    public void limpiar() {
        logArea.clear();
    }

    public VBox getVista() {
        return vista;
    }
}
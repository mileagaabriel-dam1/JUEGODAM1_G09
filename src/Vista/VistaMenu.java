package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;

public class VistaMenu {

    private VistaJavaFX principal;
    private HBox vista;

    private Button btnNuevaPartida;
    private Button btnSalir;

    public VistaMenu(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {

        vista = new HBox(20);
        vista.setAlignment(Pos.CENTER_LEFT);
        vista.setPadding(new Insets(10));
        vista.setStyle("-fx-background-color: #0288d1;");
        vista.setPrefHeight(70);

        Label titulo = new Label("JOC DEL PINGÜ");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        btnNuevaPartida = new Button("Nueva Partida");
        btnSalir = new Button("Salir");

        btnNuevaPartida.setOnAction(e -> mostrarDialogoNuevaPartida());
        btnSalir.setOnAction(e -> System.exit(0));

        HBox botones = new HBox(10, btnNuevaPartida, btnSalir);
        botones.setAlignment(Pos.CENTER_RIGHT);

        vista.getChildren().addAll(titulo, spacer, botones);
    }

    private void mostrarDialogoNuevaPartida() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nueva Partida");

        dialog.getDialogPane().getButtonTypes().addAll(
                ButtonType.OK,
                ButtonType.CANCEL
        );

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Spinner<Integer> numHumanos = new Spinner<>(1, 3, 1);
        numHumanos.setEditable(true);

        CheckBox incluirIA = new CheckBox("Incluir IA");

        grid.add(new Label("Jugadores humanos:"), 0, 0);
        grid.add(numHumanos, 1, 0);
        grid.add(incluirIA, 0, 1, 2, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {

            if (response == ButtonType.OK) {

                int humanos = numHumanos.getValue();
                boolean ia = incluirIA.isSelected();

                if (principal != null) {
                    principal.iniciarPartida(humanos, ia);
                }
            }
        });
    }

    public HBox getVista() {
        return vista;
    }

    public void mostrar() {
        vista.setVisible(true);
    }

    public void ocultar() {
        vista.setVisible(false);
    }
}
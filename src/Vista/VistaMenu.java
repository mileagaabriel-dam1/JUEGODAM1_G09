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

        Label titulo = new Label("🐧 JOC DEL PINGÜ 🐧");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnNuevaPartida = new Button("🎮 Nueva Partida");
        btnNuevaPartida.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnNuevaPartida.setOnAction(e -> mostrarDialogoNuevaPartida());

        Button btnSalir = new Button("🚪 Salir");
        btnSalir.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnSalir.setOnAction(e -> System.exit(0));

        HBox botones = new HBox(10, btnNuevaPartida, btnSalir);
        botones.setAlignment(Pos.CENTER_RIGHT);

        vista.getChildren().addAll(titulo, spacer, botones);
    }

    private void mostrarDialogoNuevaPartida() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nueva Partida");
        dialog.setHeaderText("⚙️ Configuración de la partida");

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField numHumanosField = new TextField("1");
        numHumanosField.setPromptText("1-3");

        CheckBox incluirIACheck = new CheckBox("Incluir IA");
        incluirIACheck.setSelected(true);

        grid.add(new Label("👥 Jugadores humanos:"), 0, 0);
        grid.add(numHumanosField, 1, 0);
        grid.add(incluirIACheck, 0, 1, 2, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int numHumanos = Integer.parseInt(numHumanosField.getText());
                    boolean incluirIA = incluirIACheck.isSelected();

                    if (numHumanos < 1 || numHumanos > 3) {
                        mostrarError("El número debe ser entre 1 y 3");
                        return;
                    }

                    if (principal != null) {
                        principal.iniciarPartida(numHumanos, incluirIA);
                    }

                } catch (NumberFormatException e) {
                    mostrarError("Introduce un número válido");
                }
            }
        });
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public HBox getVista() { return vista; }
}
package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
//NUEVAS IMPORTACIONES, Para que las imágenes funcionen en el menú
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VistaMenu {

    private VistaJavaFX principal;
    private HBox vista;

    public VistaMenu(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {
        // 1. BARRA SUPERIOR (HBox)
        vista = new HBox(20);
        vista.setAlignment(Pos.CENTER_LEFT); // Título a la izquierda
        vista.setPadding(new Insets(10));
        // Azul vibrante (#0288d1) para que destaque
        vista.setStyle("-fx-background-color: #0288d1;");
        vista.setPrefHeight(70);

        // 2. TÍTULO CON EMOJIS
        Label titulo = new Label(" JOC DEL PINGÜ ");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        // LOGO DEL TÍTULO: Cargamos la imagen del pingüino para decorar la barra
        try {
            Image imgPingu = new Image(getClass().getResourceAsStream("/resources/pinguino.png"));
            ImageView logoPingu = new ImageView(imgPingu);
            logoPingu.setFitHeight(45); // Tamaño ajustado al alto de la barra
            logoPingu.setPreserveRatio(true);
            titulo.setGraphic(logoPingu); // Ponemos la foto a la izquierda del texto
        } catch (Exception e) {
            // Si hay error cargando, el título se queda con el texto normal
        }

        // EL "SPACER": Truco de diseño para empujar los botones a la derecha
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Hace que este espacio crezca todo lo posible

        // 3. BOTONES DE ACCIÓN
        Button btnNuevaPartida = new Button("🎮 Nueva Partida");
        btnNuevaPartida.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; " +
                               "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        // Al pulsar, abrimos el formulario de configuración
        btnNuevaPartida.setOnAction(e -> mostrarDialogoNuevaPartida());

        Button btnSalir = new Button("🚪 Salir");
        btnSalir.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnSalir.setOnAction(e -> System.exit(0));

        HBox botones = new HBox(10, btnNuevaPartida, btnSalir);
        botones.setAlignment(Pos.CENTER_RIGHT);

        // Añadimos todo a la barra principal
        vista.getChildren().addAll(titulo, spacer, botones);
    }

    /**
     * DIÁLOGO DE CONFIGURACIÓN: Ventana flotante para elegir jugadores.
     */
    private void mostrarDialogoNuevaPartida() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nueva Partida");
        dialog.setHeaderText("⚙️ Configuración de la partida");

        // IMAGEN DEL DIÁLOGO: Añadimos un pequeño pingüino también en la configuración
        try {
            Image imgConf = new Image(getClass().getResourceAsStream("/resources/pinguino.png"));
            ImageView viewConf = new ImageView(imgConf);
            viewConf.setFitHeight(40);
            viewConf.setPreserveRatio(true);
            dialog.setGraphic(viewConf); // Colocamos la imagen en la cabecera del diálogo
        } catch (Exception e) {
            // No hacemos nada si falla, se mostraría el diálogo sin icono
        }

        // Añadimos botones estándar de aceptar y cancelar
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Grid para organizar el formulario dentro del diálogo
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField numHumanosField = new TextField("1");
        CheckBox incluirIACheck = new CheckBox("Incluir IA");
        incluirIACheck.setSelected(true);

        grid.add(new Label("👥 Jugadores humanos:"), 0, 0);
        grid.add(numHumanosField, 1, 0);
        grid.add(incluirIACheck, 0, 1, 2, 1);

        dialog.getDialogPane().setContent(grid);

        // Manejo de la respuesta del usuario
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int numHumanos = Integer.parseInt(numHumanosField.getText());
                    boolean incluirIA = incluirIACheck.isSelected();

                    // Validación: que no pongan 0 o 5 jugadores
                    if (numHumanos < 1 || numHumanos > 3) {
                        mostrarError("El número debe ser entre 1 y 3");
                        return;
                    }

                    // Iniciamos la partida en la clase principal
                    if (principal != null) {
                        principal.iniciarPartida(numHumanos, incluirIA);
                    }

                } catch (NumberFormatException e) {
                    mostrarError("Introduce un número válido");
                }
            }
        });
    }

    // Ventana de alerta en caso de error
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public HBox getVista() { return vista; }
}
package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
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
        // BARRA SUPERIOR (HBox)
        vista = new HBox(20);
        vista.setAlignment(Pos.CENTER_LEFT); 
        vista.setPadding(new Insets(10));
        vista.setStyle("-fx-background-color: #0288d1;");
        vista.setPrefHeight(70);

        // TÍTULO CON EMOJIS
        Label titulo = new Label(" JOC DEL PINGÜ ");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.WHITE);

        try {
            Image imgPingu = new Image(getClass().getResourceAsStream("/resources/pinguino.png"));
            ImageView logoPingu = new ImageView(imgPingu);
            logoPingu.setFitHeight(45); 
            logoPingu.setPreserveRatio(true);
            titulo.setGraphic(logoPingu); 
        } catch (Exception e) {
            // Ignorar error de carga de foto
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); 

        // Botón Nueva Partida
        Button btnNuevaPartida = new Button("🎮 Nueva Partida");
        btnNuevaPartida.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; " +
                               "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnNuevaPartida.setOnAction(e -> mostrarDialogoNuevaPartida());

        // =========================================================================
        // 💾 BOTÓN GUARDAR (Nombres corregidos con controladorJugador)
        // =========================================================================
        Button btnGuardar = new Button("💾 Guardar");
        btnGuardar.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnGuardar.setOnAction(e -> {
            if (principal != null && principal.getControladorTurnos().getJugadorActual() != null) {
                
                // Sacamos el ID logueado usando el nuevo método Getter que añadimos arriba
                int idUser = principal.getControladorJugador().getIdUsuarioLogueado();
                int posUser = principal.getControladorTurnos().getJugadorActual().getPosicion();

                boolean ok = principal.getControladorJuego().guardarPartidaActual(idUser, posUser);
                if (ok) {
                    principal.getVistaEventos().agregarEvento("💾 [MENÚ] ¡Progreso guardado en la base de datos!");
                } else {
                    principal.getVistaEventos().agregarEvento("❌ [MENÚ] No se pudo guardar la partida.");
                }
            }
        });

        // =========================================================================
        // 📂 BOTÓN CARGAR (Nombres corregidos con controladorJugador)
        // =========================================================================
        Button btnCargar = new Button("📂 Cargar");
        btnCargar.setStyle("-fx-background-color: #00bcd4; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnCargar.setOnAction(e -> {
            if (principal != null && principal.getControladorTurnos().getJugadorActual() != null) {
                
                int idUser = principal.getControladorJugador().getIdUsuarioLogueado();

                int casillaCargada = principal.getControladorJuego().cargarPartidaGuardada(idUser);
                if (casillaCargada != -1) {
                    // Sincronizamos la posición del jugador actual en la memoria del juego
                    principal.getControladorTurnos().getJugadorActual().setPosicion(casillaCargada);
                    
                    // Repintamos el tablero y actualizamos la ficha de datos lateral
                    principal.getVistaTablero().actualizarPosiciones(principal.getControladorJugador(), principal.getControladorTurnos());
                    principal.getVistaJugador().actualizar(principal.getControladorTurnos());
                    
                    principal.getVistaEventos().agregarEvento("📂 [MENÚ] ¡Partida cargada! Volviendo a la casilla " + (casillaCargada + 1));
                } else {
                    principal.getVistaEventos().agregarEvento("⚠️ [MENÚ] No se encontraron partidas guardadas a medias.");
                }
            }
        });

        Button btnSalir = new Button("🚪 Salir");
        btnSalir.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                        "-fx-font-weight: bold; -fx-padding: 8 15; -fx-background-radius: 20;");
        btnSalir.setOnAction(e -> System.exit(0));

        HBox botones = new HBox(10, btnNuevaPartida, btnGuardar, btnCargar, btnSalir);
        botones.setAlignment(Pos.CENTER_RIGHT);

        vista.getChildren().addAll(titulo, spacer, botones);
    }

    private void mostrarDialogoNuevaPartida() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Nueva Partida");
        dialog.setHeaderText("⚙️ Configuración de la partida");

        try {
            Image imgConf = new Image(getClass().getResourceAsStream("/resources/pinguino.png"));
            ImageView viewConf = new ImageView(imgConf);
            viewConf.setFitHeight(40);
            viewConf.setPreserveRatio(true);
            dialog.setGraphic(viewConf); 
        } catch (Exception e) {
            // Ignorar
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

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
package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.application.Platform;
import Controladores.*;
import Modelo.*;

public class VistaJuego {

    private VistaJavaFX principal;
    private VBox vista;
    private Button btnLanzarDado;
    private Label dadoLabel;
    private boolean juegoActivo = false;

    public VistaJuego(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {
        vista = new VBox(15);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(15));
        vista.setStyle("-fx-background-color: #ffb74d; -fx-background-radius: 15;");
        vista.setPrefWidth(260);

        Label titulo = new Label("🎲 LANZAR DADO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: white;");

        dadoLabel = new Label("🎲");
        dadoLabel.setFont(Font.font("Segoe UI Emoji", 60));
        dadoLabel.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 10;");

        btnLanzarDado = new Button("LANZAR DADO");
        btnLanzarDado.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25;");
        btnLanzarDado.setDisable(true);
        btnLanzarDado.setOnAction(e -> lanzarDado());

        vista.getChildren().addAll(titulo, dadoLabel, btnLanzarDado);
    }

    public void lanzarDado() {
        if (!juegoActivo) return;

        ControladorTurnos controladorTurnos = principal.getControladorTurnos();
        ControladorEventos controladorEventos = principal.getControladorJuego().getControladorEventos();
        Jugador jugador = controladorTurnos.getJugadorActual();

        if (jugador == null) return;

        btnLanzarDado.setDisable(true);
        principal.getVistaEventos().agregarEvento("🎲 Turno de: " + jugador.getNombre());

        animarDado(() -> {
            int dado = (int)(Math.random() * 6) + 1;
            dadoLabel.setText(getDadoEmoji(dado));

            int origen = jugador.getPosicion();
            int destino = Math.min(origen + dado, 49);

            principal.getVistaEventos().agregarEvento("🎲 " + jugador.getNombre() + " sacó un " + dado);

            VistaTableroConImagenes tablero = principal.getVistaTablero();

            tablero.animarMovimiento(jugador, origen, destino, () -> {
                jugador.setPosicion(destino);
                principal.getVistaEventos().agregarEvento("📍 " + jugador.getNombre() + " está en casilla " + (destino + 1));

                Casilla casilla = principal.getControladorTablero().getCasilla(destino);
                String mensaje = controladorEventos.procesarCasilla(jugador, casilla);
                if (mensaje != null && !mensaje.isEmpty()) {
                    principal.getVistaEventos().agregarEvento(mensaje);
                }

                tablero.actualizarPosiciones(principal.getControladorJugador(), controladorTurnos);
                principal.getVistaJugador().actualizar(controladorTurnos);

                if (destino == 49) {
                    principal.getVistaEventos().agregarEvento("🎉 ¡" + jugador.getNombre() + " HA GANADO! 🎉");
                    juegoActivo = false;
                    btnLanzarDado.setDisable(true);
                    return;
                }

                controladorTurnos.siguienteTurno();
                principal.getVistaJugador().actualizar(controladorTurnos);
                principal.getVistaEventos().agregarEvento("➡️ Siguiente: " + controladorTurnos.getJugadorActual().getNombre());

                btnLanzarDado.setDisable(false);

                if (controladorTurnos.getJugadorActual().esIA()) {
                    lanzarDadoIA();
                }
            });
        });
    }

    public void lanzarDadoIA() {
        new Thread(() -> {
            try { Thread.sleep(1500); } catch (Exception e) {}
            Platform.runLater(() -> {
                if (juegoActivo) lanzarDado();
            });
        }).start();
    }

    private void animarDado(Runnable callback) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int num = (int)(Math.random() * 6) + 1;
                Platform.runLater(() -> dadoLabel.setText(getDadoEmoji(num)));
                try { Thread.sleep(70); } catch (Exception e) {}
            }
            Platform.runLater(callback);
        }).start();
    }

    private String getDadoEmoji(int num) {
        switch(num) {
            case 1: return "⚀";
            case 2: return "⚁";
            case 3: return "⚂";
            case 4: return "⚃";
            case 5: return "⚄";
            case 6: return "⚅";
            default: return "🎲";
        }
    }

    public VBox getVista() { return vista; }

    public void mostrar() {
        juegoActivo = true;
        btnLanzarDado.setDisable(false);
        dadoLabel.setText("🎲");
    }

    public void ocultar() {
        juegoActivo = false;
        btnLanzarDado.setDisable(true);
    }
}
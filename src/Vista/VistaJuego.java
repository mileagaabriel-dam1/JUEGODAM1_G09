package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

        vista = new VBox(10);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(10));

        Label titulo = new Label("LANZAR DADO");
        titulo.setFont(new Font(16));

        dadoLabel = new Label("🎲");
        dadoLabel.setFont(new Font(40));

        btnLanzarDado = new Button("Lanzar dado");
        btnLanzarDado.setDisable(true);

        btnLanzarDado.setOnAction(e -> lanzarDado());

        vista.getChildren().addAll(titulo, dadoLabel, btnLanzarDado);
    }

    // 🔥 DADO REAL CON TU MODELO
    public void lanzarDado() {

        if (!juegoActivo) return;

        ControladorTurnos turnos = principal.getControladorTurnos();
        Jugador jugador = turnos.getJugadorActual();

        if (jugador == null) return;

        btnLanzarDado.setDisable(true);

        animarDado(() -> {

            // ✔ USAMOS TU MÉTODO REAL
            int dado = jugador.lanzarDado();

            dadoLabel.setText(getDadoEmoji(dado));

            int origen = jugador.getPosicion();
            int destino = origen + dado;

            if (destino > 49) destino = 49;

            VistaTableroConImagenes tablero = principal.getVistaTablero();

            tablero.animarMovimiento(jugador, origen, destino, () -> {

                jugador.setPosicion(destino);

                // 🔗 CONTROLADOR EVENTOS REAL
                ControladorEventos eventos =
                        principal.getControladorJuego().getControladorEventos();

                Casilla casilla =
                        principal.getControladorTablero().getCasilla(destino);

                String mensaje = eventos.procesarCasilla(jugador, casilla);

                // 🔗 VISTA EVENTOS
                principal.getVistaEventos().agregarEvento(mensaje);

                // 🔗 ACTUALIZAR TABLERO
                tablero.actualizarPosiciones(principal.getControladorJugador());

                // 🔗 ACTUALIZAR INFO JUGADOR
                principal.getVistaJugador().actualizar(turnos);

                // 🔁 SIGUIENTE TURNO
                turnos.siguienteTurno();
                principal.getVistaJugador().actualizar(turnos);

                btnLanzarDado.setDisable(false);
            });
        });
    }

    // 🎲 animación visual del dado
    private void animarDado(Runnable callback) {

        new Thread(() -> {

            for (int i = 0; i < 6; i++) {

                int num = (int)(Math.random() * 6) + 1;

                Platform.runLater(() ->
                        dadoLabel.setText(getDadoEmoji(num))
                );

                try {
                    Thread.sleep(100);
                } catch (Exception ignored) {}
            }

            Platform.runLater(callback);

        }).start();
    }

    // 🎲 emojis del dado
    private String getDadoEmoji(int num) {

        switch (num) {
            case 1: return "⚀";
            case 2: return "⚁";
            case 3: return "⚂";
            case 4: return "⚃";
            case 5: return "⚄";
            case 6: return "⚅";
        }

        return "🎲";
    }

    public VBox getVista() {
        return vista;
    }

    public void mostrar() {
        juegoActivo = true;
        btnLanzarDado.setDisable(false);
    }

    public void ocultar() {
        juegoActivo = false;
        btnLanzarDado.setDisable(true);
    }
}
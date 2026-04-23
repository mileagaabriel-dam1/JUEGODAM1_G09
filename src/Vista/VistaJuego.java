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
        // Contenedor principal con fondo naranja (#ffb74d) y bordes redondeados
        vista = new VBox(15);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new Insets(15));
        vista.setStyle("-fx-background-color: #ffb74d; -fx-background-radius: 15;");
        vista.setPrefWidth(260);

        Label titulo = new Label("🎲 LANZAR DADO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: white;");

        // Label donde aparecerá el dado girando
        dadoLabel = new Label("🎲");
        dadoLabel.setFont(Font.font("Segoe UI Emoji", 60));
        dadoLabel.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 10;");

        // Botón con estilo redondeado y llamativo
        btnLanzarDado = new Button("LANZAR DADO");
        btnLanzarDado.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; " +
                               "-fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25;");
        btnLanzarDado.setDisable(true); // Empieza desactivado hasta que inicie la partida
        btnLanzarDado.setOnAction(e -> lanzarDado());

        vista.getChildren().addAll(titulo, dadoLabel, btnLanzarDado);
    }

    // LÓGICA PRINCIPAL DEL TURNO
    public void lanzarDado() {
        if (!juegoActivo) return;

        ControladorTurnos controladorTurnos = principal.getControladorTurnos();
        ControladorEventos controladorEventos = principal.getControladorJuego().getControladorEventos();
        Jugador jugador = controladorTurnos.getJugadorActual();

        if (jugador == null) return;

        btnLanzarDado.setDisable(true); // Bloqueamos el botón para evitar "doble clic"
        principal.getVistaEventos().agregarEvento("🎲 Turno de: " + jugador.getNombre());

        // 1. Animamos el dado (efecto visual)
        animarDado(() -> {
            // 2. Calculamos el resultado real
            int dado = (int)(Math.random() * 6) + 1;
            dadoLabel.setText(getDadoEmoji(dado));

            int origen = jugador.getPosicion();
            int destino = Math.min(origen + dado, 49);

            principal.getVistaEventos().agregarEvento("🎲 " + jugador.getNombre() + " sacó un " + dado);

            VistaTableroConImagenes tablero = principal.getVistaTablero();

            // 3. Animamos al pingüino moviéndose por el tablero
            tablero.animarMovimiento(jugador, origen, destino, () -> {
                jugador.setPosicion(destino);
                principal.getVistaEventos().agregarEvento("📍 " + jugador.getNombre() + " está en casilla " + (destino + 1));

                // 4. Procesamos qué hay en la casilla (¿hay oso? ¿trineo?)
                Casilla casilla = principal.getControladorTablero().getCasilla(destino);
                String mensaje = controladorEventos.procesarCasilla(jugador, casilla);
                if (mensaje != null && !mensaje.isEmpty()) {
                    principal.getVistaEventos().agregarEvento(mensaje);
                }

                // 5. Actualizamos toda la interfaz tras el movimiento y efectos
                tablero.actualizarPosiciones(principal.getControladorJugador(), controladorTurnos);
                principal.getVistaJugador().actualizar(controladorTurnos);

                // 6. ¿Ha ganado alguien?
                if (destino == 49) {
                    principal.getVistaEventos().agregarEvento("🎉 ¡" + jugador.getNombre() + " HA GANADO! 🎉");
                    juegoActivo = false;
                    btnLanzarDado.setDisable(true);
                    return;
                }

                // 7. Pasamos el turno
                controladorTurnos.siguienteTurno();
                principal.getVistaJugador().actualizar(controladorTurnos);
                principal.getVistaEventos().agregarEvento("➡️ Siguiente: " + controladorTurnos.getJugadorActual().getNombre());

                btnLanzarDado.setDisable(false);

                // 8. Si le toca a la IA, llamamos a su método automático
                if (controladorTurnos.getJugadorActual().esIA()) {
                    lanzarDadoIA();
                }
            });
        });
    }

    // Método para que la IA "espere" un poco antes de tirar y no sea instantáneo
    public void lanzarDadoIA() {
        new Thread(() -> {
            try { Thread.sleep(1500); } catch (Exception e) {} // Espera 1.5 segundos
            Platform.runLater(() -> { // Platform.runLater es necesario para tocar la UI desde un hilo
                if (juegoActivo) lanzarDado();
            });
        }).start();
    }

    // Hilo para que el dado cambie de cara rápidamente antes de dar el resultado
    private void animarDado(Runnable callback) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int num = (int)(Math.random() * 6) + 1;
                Platform.runLater(() -> dadoLabel.setText(getDadoEmoji(num)));
                try { Thread.sleep(70); } catch (Exception e) {}
            }
            Platform.runLater(callback); // Al terminar, ejecuta la lógica del movimiento
        }).start();
    }

    // Traduce números a los caracteres especiales de dados
    private String getDadoEmoji(int num) {
        switch(num) {
            case 1: return "⚀"; case 2: return "⚁"; case 3: return "⚂";
            case 4: return "⚃"; case 5: return "⚄"; case 6: return "⚅";
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
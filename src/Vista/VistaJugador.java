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
    private VBox vista; // Contenedor vertical (apila las etiquetas una encima de otra)
    private Label jugadorLabel;
    private Label posicionLabel;
    private Label inventarioLabel;
    private Label turnoLabel;

    public VistaJugador(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {
        // 1. EL CONTENEDOR: Usamos una caja vertical con espacio de 10px entre elementos
        vista = new VBox(10);
        vista.setAlignment(Pos.TOP_LEFT);
        vista.setPadding(new Insets(15));
        
        // CSS INLINE: Fondo blanco, borde gris azulado y esquinas redondeadas
        vista.setStyle("-fx-background-color: white; -fx-border-color: #90a4ae; " +
                       "-fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;");
        vista.setPrefWidth(260);

        // 2. EL TÍTULO: Panel de estadísticas
        Label titulo = new Label("📊 INFORMACIÓN");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #37474f;");

        // 3. LAS ETIQUETAS: Aquí es donde volcaremos los datos del Jugador
        turnoLabel = new Label("Turno: -");
        jugadorLabel = new Label("Jugador: -");
        posicionLabel = new Label("Posición: -");
        inventarioLabel = new Label("Inventario: -");

        // Estilos de fuente: El turno lo ponemos en negrita porque es lo más importante
        turnoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        jugadorLabel.setFont(Font.font("Arial", 12));
        posicionLabel.setFont(Font.font("Arial", 12));
        inventarioLabel.setFont(Font.font("Arial", 12));

        // Añadimos todo al VBox principal
        vista.getChildren().addAll(titulo, turnoLabel, jugadorLabel, posicionLabel, inventarioLabel);
    }

    /**
     * MÉTODO CLAVE: Este método "mapea" los datos del Modelo a la Vista.
     * Se llama cada vez que alguien mueve o cambia el turno.
     */
    public void actualizar(controladorTurnos controladorTurnos) {
        // Si no hay partida activa, limpiamos los textos
        if (controladorTurnos == null || controladorTurnos.getJugadorActual() == null) {
            limpiar();
            return;
        }

        // Sacamos el Jugador que tiene el turno actual desde el controlador
        Jugador actual = controladorTurnos.getJugadorActual();
        
        // Comprobamos si es IA para añadirle el "mote" al nombre
        String tipo = actual.esIA() ? " (IA)" : "";

        // ACTUALIZAMOS LOS TEXTOS EN PANTALLA
        turnoLabel.setText("🎲 Turno: " + actual.getNombre());
        jugadorLabel.setText("👤 Jugador: " + actual.getNombre() + tipo);
        
        // Mostramos la posición (le sumamos 1 porque el array es 0-49, pero el humano entiende 1-50)
        posicionLabel.setText("📍 Posición: " + (actual.getPosicion() + 1) + "/50");

        // Si el jugador tiene mochila, mostramos qué lleva (peces, trineos, etc.)
        if (actual.getInventario() != null) {
            inventarioLabel.setText("🎒 " + actual.getInventario().obtenerResumen());
        } else {
            inventarioLabel.setText("🎒 Inventario: vacío");
        }
    }

    // Deja los labels como al principio (se usa al terminar o reiniciar la partida)
    public void limpiar() {
        turnoLabel.setText("🎲 Turno: -");
        jugadorLabel.setText("👤 Jugador: -");
        posicionLabel.setText("📍 Posición: -");
        inventarioLabel.setText("🎒 Inventario: -");
    }

    public VBox getVista() { return vista; }
}
package Vista; // Define la carpeta donde reside el archivo

// --- IMPORTACIONES: Cargamos las piezas de la biblioteca JavaFX ---
import javafx.geometry.Insets;      // Para gestionar el acolchado (padding) interno
import javafx.geometry.Pos;         // Para definir la alineación de los textos
import javafx.scene.control.Label;  // La clase para crear etiquetas de texto no editable
import javafx.scene.layout.VBox;    // El contenedor que apila elementos de arriba a abajo
import javafx.scene.text.Font;      // Para definir el tipo de letra
import javafx.scene.text.FontWeight; // Para aplicar estilos como la negrita (bold)
import Controladores.*;             // Traemos la lógica de control (Turnos, etc.)
import Modelo.*;                    // Traemos los datos (Jugador, Inventario, etc.)

/**
 * Clase VistaJugador: Se encarga exclusivamente de mostrar la ficha técnica
 * del jugador que tiene el turno actual en el panel lateral.
 */
public class VistaJugador {

    // --- ATRIBUTOS (Variables de la clase) ---
    private VistaJavaFX principal;  // Guardamos la referencia a la ventana maestra para comunicarnos
    private VBox vista;             // El contenedor raíz de esta sección (una caja vertical)
    
    // Etiquetas (Labels) que cambiaremos dinámicamente durante el juego
    private Label jugadorLabel;     // Muestra el nombre
    private Label posicionLabel;    // Muestra el número de casilla
    private Label inventarioLabel;  // Muestra los objetos que posee
    private Label turnoLabel;       // Indica de quién es el turno actual

    /**
     * CONSTRUCTOR
     * @param principal Recibe la instancia de la Vista principal (Dependency Injection)
     */
    public VistaJugador(VistaJavaFX principal) {
        this.principal = principal; // Asignamos la referencia
        crearVista();               // Ejecutamos el montaje visual
    }

    /**
     * MÉTODO crearVista: Define el "look and feel" del panel de información.
     */
    private void crearVista() {
        // Inicializamos el VBox con una separación de 10 píxeles entre sus hijos
        vista = new VBox(10);
        
        // Alineamos todo el contenido arriba a la izquierda del panel
        vista.setAlignment(Pos.TOP_LEFT); 
        
        // Añadimos un margen interno de 15 píxeles para que el texto no toque los bordes
        vista.setPadding(new Insets(15)); 
        
        // Estilo CSS: Fondo blanco, borde gris azulado, y esquinas redondeadas (radius: 12)
        vista.setStyle("-fx-background-color: white; -fx-border-color: #90a4ae; " +
                       "-fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;");
        
        // Fijamos el ancho a 260px para que el panel sea consistente con el resto de la UI
        vista.setPrefWidth(260); 

        // Creamos el encabezado de la sección
        Label titulo = new Label(" ℹ️ INFORMACIÓN ");
        // Fuente Arial, Negrita, tamaño 16
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        // Color de texto gris oscuro profesional
        titulo.setStyle("-fx-text-fill: #37474f;"); 

        // Inicializamos las etiquetas con valores por defecto (Placeholder)
        turnoLabel = new Label("Turno: -");
        jugadorLabel = new Label("Jugador: -");
        posicionLabel = new Label("Posición: -");
        inventarioLabel = new Label("Inventario: -");

        // Aplicamos estilos de fuente: El turno en negrita para que resalte más
        turnoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        jugadorLabel.setFont(Font.font("Arial", 12));
        posicionLabel.setFont(Font.font("Arial", 12));
        inventarioLabel.setFont(Font.font("Arial", 12));

        // Metemos todos los elementos en la "bolsa" del VBox
        // El orden de los argumentos aquí dicta el orden visual de arriba a abajo
        vista.getChildren().addAll(titulo, turnoLabel, jugadorLabel, posicionLabel, inventarioLabel);
    }

    /**
     * MÉTODO actualizar: Sincroniza la Vista con el Modelo.
     * Se llama después de cada movimiento o cambio de turno.
     * @param controladorTurnos El motor que sabe quién está jugando.
     */
    public void actualizar(controladorTurnos controladorTurnos) {
        
        // Seguridad (Fail-safe): Si no hay datos, limpiamos la interfaz para evitar errores
        if (controladorTurnos == null || controladorTurnos.getJugadorActual() == null) {
            limpiar();
            return;
        }

        // Extraemos el objeto Jugador que está activo en este momento
        Jugador actual = controladorTurnos.getJugadorActual();
        
        // Usamos un operador ternario para añadir "(IA)" al nombre si el jugador no es humano
        String tipo = actual.esIA() ? " (IA)" : "";

        // Refrescamos los textos de las etiquetas con la información real del objeto Jugador
        turnoLabel.setText(" 🚩 Turno: " + actual.getNombre());
        jugadorLabel.setText(" 👤 Jugador: " + actual.getNombre() + tipo);
        
        // Sumamos +1 a la posición porque las listas empiezan en 0, pero las casillas en 1 para el usuario
        posicionLabel.setText(" 📍 Posición: " + (actual.getPosicion() + 1) + "/50");

        // Verificamos si el jugador tiene mochila (inventario)
        if (actual.getInventario() != null) {
            // Mostramos los objetos que tiene llamando al método resumen de su inventario
            inventarioLabel.setText(" 🎒 " + actual.getInventario().obtenerResumen());
        } else {
            // Si por algún motivo el inventario es null, indicamos que está vacío
            inventarioLabel.setText(" 🎒 Inventario: vacío");
        }
    }

    /**
     * MÉTODO limpiar: Restablece el panel a su estado neutro.
     */
    public void limpiar() {
        turnoLabel.setText(" Turno: -");
        jugadorLabel.setText(" Jugador: -");
        posicionLabel.setText(" Posición: -");
        inventarioLabel.setText(" Inventario: -");
    }

    /**
     * GETTER: Permite a la clase VistaJavaFX obtener el nodo raíz de este panel
     * para colocarlo en el lado derecho de la ventana principal.
     */
    public VBox getVista() { 
        return vista; 
    }
}
package Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import Controladores.*;
import Modelo.*;

public class VistaTableroConImagenes {

    private VistaJavaFX principal;
    private GridPane tableroGrid; // El layout de rejilla (filas y columnas)
    private VBox vista;           // Contenedor principal del tablero
    private StackPane[] casillas = new StackPane[50]; // Array para guardar las 50 celdas
    private String[] iconosFichas = {"①", "②", "③", "④", "⑤"}; // Representación de los jugadores

    public VistaTableroConImagenes(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    private void crearVista() {
        vista = new VBox(10);
        vista.setAlignment(Pos.CENTER);
        vista.setPadding(new javafx.geometry.Insets(10));

        Label titulo = new Label("🐧 TABLERO DE JUEGO 🐧");
        titulo.setFont(new Font("Arial", 18));

        // El GridPane es perfecto para tableros: organiza todo en coordenadas (x, y)
        tableroGrid = new GridPane();
        tableroGrid.setHgap(5); // Espacio horizontal entre casillas
        tableroGrid.setVgap(5); // Espacio vertical entre casillas
        tableroGrid.setAlignment(Pos.CENTER);

        vista.getChildren().addAll(titulo, tableroGrid);
    }

    /**
     * Dibuja el tablero por primera vez basándose en el Modelo
     */
    public void inicializarTablero(ControladorTablero controladorTablero) {
        tableroGrid.getChildren().clear(); // Limpiamos por si había una partida previa

        for (int i = 0; i < 50; i++) {
            // Calculamos fila y columna para un tablero de 10 de ancho
            int fila = i / 10;
            int columna = i % 10;

            Casilla casilla = controladorTablero.getCasilla(i);

            // StackPane nos permite apilar cosas (el emoji del fondo + el número + la ficha)
            StackPane casillaPane = new StackPane();
            casillaPane.setPrefSize(55, 55);
            casillaPane.setStyle("-fx-border-color: #333; -fx-border-width: 1; " +
                               "-fx-background-color: #e0f7fa; -fx-background-radius: 5;");

            // Obtenemos el dibujo de la casilla (Oso, Trineo, etc.)
            String emoji = obtenerEmojiCasilla(casilla.getTipo());
            Label label = new Label(emoji);
            label.setFont(new Font(22));

            // Ponemos el número de casilla en la esquina superior izquierda
            Label numero = new Label(String.valueOf(i + 1));
            numero.setFont(new Font(8));
            numero.setStyle("-fx-text-fill: gray;");
            StackPane.setAlignment(numero, Pos.TOP_LEFT);

            casillaPane.getChildren().addAll(label, numero);
            tableroGrid.add(casillaPane, columna, fila); // Añadimos a la rejilla
            
            casillas[i] = casillaPane; // Guardamos la referencia para mover pingüinos luego
        }
    }

    private String obtenerEmojiCasilla(TipoCasilla tipo) {
        switch (tipo) {
            case PINGUINO: return "🐧";
            case OSO: return "🐻";
            case AGUJERO: return "🕳️";
            case TRINEO: return "🛷";
            case INTERROGANTE: return "❓";
            default: return "⬜";
        }
    }

    /**
     * Borra las fichas de las casillas y las vuelve a pintar en sus nuevas posiciones
     */
    public void actualizarPosiciones(ControladorJugador controladorJugador, ControladorTurnos controladorTurnos) {
        // 1. Limpiamos las fichas antiguas (borramos el 3er elemento de cada StackPane si existe)
        for (StackPane casilla : casillas) {
            while (casilla.getChildren().size() > 2) {
                casilla.getChildren().remove(2);
            }
        }

        // 2. Pintamos las fichas de los jugadores actuales
        for (int i = 0; i < controladorJugador.getJugadores().size(); i++) {
            Jugador jugador = controladorJugador.getJugadores().get(i);
            int posicion = jugador.getPosicion();

            if (posicion >= 0 && posicion < casillas.length) {
                StackPane casilla = casillas[posicion];
                
                Label ficha = new Label(iconosFichas[i]); // ①, ②, etc.
                ficha.setFont(new Font(14));
                // Le damos a cada jugador un color distinto
                ficha.setStyle("-fx-text-fill: " + obtenerColorJugador(i) + "; " +
                             "-fx-font-weight: bold; -fx-background-color: white; -fx-padding: 0 2 0 2;");
                StackPane.setAlignment(ficha, Pos.BOTTOM_RIGHT);
                
                casilla.getChildren().add(ficha); // Apilamos la ficha sobre la casilla
            }
        }
    }
    
    private String obtenerColorJugador(int index) {
        String[] colores = {"red", "blue", "green", "orange"};
        return (index < colores.length) ? colores[index] : "black";
    }

    // Este método podría ampliarse con Timelines para ver el pingüino desplazarse casilla a casilla
    public void animarMovimiento(Jugador jugador, int origen, int destino, Runnable callback) {
        actualizarPosiciones(principal.getControladorJugador(), principal.getControladorTurnos());
        if (callback != null) {
            callback.run(); // Ejecutamos la lógica que sigue al movimiento
        }
    }

    public void limpiar() {
        tableroGrid.getChildren().clear();
    }

    public VBox getVista() { return vista; }
}
package Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import Controladores.*;
import Modelo.*;

public class VistaTableroConImagenes {

    //ATRIBUTOS (Nuestros objetos y datos)
    private VistaJavaFX principal; 
    private GridPane tableroGrid; 
    //La rejilla. Imaginalo como una tabla de Excel (filas y columnas)
    private VBox vista;           
    //El contenedor principal que va de arriba a abajo
    private StackPane[] casillas = new StackPane[50]; 
    //El array de 50 huecos para guardar las casillas
    private String[] iconosFichas = {"①", "②", "③", "④", "⑤"}; 
    //Los "muñequitos" de los jugadores

    //CONSTRUCTOR, Cuando creamos la vista, guardamos la referencia principal y montamos el dibujo
    public VistaTableroConImagenes(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    //MONTAJE DE LA ESTRUCTURA VISUAL
    private void crearVista() {
        vista = new VBox(10); 
        //Caja vertical con 10px de separación
        vista.setAlignment(Pos.CENTER); 
        //Todo al centro
        vista.setPadding(new javafx.geometry.Insets(10)); 
        //Margen de seguridad por los lados

        Label titulo = new Label(" TABLERO DE JUEGO ");
        titulo.setFont(new Font("Arial", 18));

        //CONFIGURAMOS EL GRID (La cuadrícula)
        tableroGrid = new GridPane();
        tableroGrid.setHgap(5); 
        //Espacio horizontal de 5px entre celdas
        tableroGrid.setVgap(5); 
        //Espacio vertical de 5px entre celdas
        tableroGrid.setAlignment(Pos.CENTER);

        //Metemos el título y la rejilla en el contenedor principal
        vista.getChildren().addAll(titulo, tableroGrid);
    }

     //INICIALIZAR, Dibuja las 50 casillas por primera vez.
     //Es como montar el tablero del parchís antes de empezar a jugar.
     
    public void inicializarTablero(controladorTablero controladorTablero) {
        tableroGrid.getChildren().clear(); // Borramos todo por si acaso rejugamos

        for (int i = 0; i < 50; i++) {
            //MATEMÁTICAS DE DAM: 
            //Para un tablero de 10 de ancho: i/10 da la fila e i%10 da la columna.
            int fila = i / 10;
            int columna = i % 10;

            //Pedimos al modelo la información de esta casilla (qué tipo es)
            Casilla casilla = controladorTablero.getCasilla(i);

            //CREAMOS LA CASILLA (StackPane = Capas una encima de otra)
            StackPane casillaPane = new StackPane();
            casillaPane.setPrefSize(55, 55); // Tamaño fijo de 55x55 píxeles
            //Le ponemos borde, color de fondo azulito y bordes redondeados con CSS
            casillaPane.setStyle("-fx-border-color: #333; -fx-border-width: 1; " +
                               "-fx-background-color: #e0f7fa; -fx-background-radius: 5;");

            //CAPA 1, El dibujo (Emoji) según el tipo de casilla
            String emoji = obtenerEmojiCasilla(casilla.getTipo());
            Label label = new Label(emoji);
            label.setFont(new Font(22));

            // CAPA 2, El número de la casilla (para que el jugador no se pierda)
            Label numero = new Label(String.valueOf(i + 1));
            numero.setFont(new Font(8));
            numero.setStyle("-fx-text-fill: gray;");
            // Usamos el método estático para mandarlo a la esquina superior izquierda
            StackPane.setAlignment(numero, Pos.TOP_LEFT);

            //Juntamos las capas en el panel de la casilla
            casillaPane.getChildren().addAll(label, numero);
            
            //Colocamos la casilla en la rejilla en su coordenada (X, Y)
            tableroGrid.add(casillaPane, columna, fila); 
            
            //Guardamos la casilla en nuestro array de 50 para poder encontrarla rápido luego
            casillas[i] = casillaPane; 
        }
    }

    //Un simple "Traductor" de tipos de casilla a Emojis visuales
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

    
     //ACTUALIZAR: Mueve las fichas de los jugadores por el tablero.
    
    public void actualizarPosiciones(controladorJugador controladorJugador, controladorTurnos controladorTurnos) {
        //LIMPIEZA, Quitamos las fichas antiguas.
        //En cada StackPane, el [0] es el emoji y el [1] es el número.
        //Si hay un [2], es la ficha de un jugador. La borramos.
        for (StackPane casilla : casillas) {
            while (casilla.getChildren().size() > 2) {
                casilla.getChildren().remove(2);
            }
        }

        //DIBUJAR JUGADORES, Miramos dónde está cada uno y ponemos su ficha
        for (int i = 0; i < controladorJugador.getJugadores().size(); i++) {
            Jugador jugador = controladorJugador.getJugadores().get(i);
            int posicion = jugador.getPosicion();

            //Si el jugador está dentro del tablero (0-49)
            if (posicion >= 0 && posicion < casillas.length) {
                StackPane casilla = casillas[posicion];
                
                //Creamos la ficha
                Label ficha = new Label(iconosFichas[i]);
                ficha.setFont(new Font(14));
                //Estilo de la ficha: color único, negrita y fondo blanco para que se vea bien
                ficha.setStyle("-fx-text-fill: " + obtenerColorJugador(i) + "; " +
                             "-fx-font-weight: bold; -fx-background-color: white; -fx-padding: 0 2 0 2;");
                
                //Mandamos la ficha a la esquina inferior derecha de la casilla
                StackPane.setAlignment(ficha, Pos.BOTTOM_RIGHT);
                
                //La añadimos como CAPA 3 al StackPane de esa casilla
                casilla.getChildren().add(ficha); 
            }
        }
    }
    
    //Asignamos colores fijos por el índice del jugador
    private String obtenerColorJugador(int index) {
        String[] colores = {"red", "blue", "green", "orange"};
        return (index < colores.length) ? colores[index] : "black";
    }

    //Método para mover el pingüino (por ahora solo refresca, pero aquí irían las animaciones)
    public void animarMovimiento(Jugador jugador, int origen, int destino, Runnable callback) {
        actualizarPosiciones(principal.getControladorJugador(), principal.getControladorTurnos());
        if (callback != null) {
            callback.run(); 
            //Ejecuta lo siguiente que tenga que pasar (ej. activar casilla)
        }
    }

    public void limpiar() {
        tableroGrid.getChildren().clear();
    }

    public VBox getVista() { return vista; }
}
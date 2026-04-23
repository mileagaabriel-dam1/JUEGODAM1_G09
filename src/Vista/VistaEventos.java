package Vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VistaEventos {
    
    private VistaJavaFX principal; // Referencia a la ventana principal
    private VBox vista;            // El contenedor vertical donde meteremos todo
    private TextArea logArea;      // El cuadro de texto donde se escriben los mensajes
    
    public VistaEventos(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }
    
    private void crearVista() {
        //CONTENEDOR (VBox)
        // Creamos una caja vertical con un espacio de 8 píxeles entre elementos
        vista = new VBox(8);
        
        vista.setPadding(new Insets(15)); // Margen interno de 15 píxeles
        

        vista.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                       "-fx-border-color: #90a4ae; -fx-border-width: 2; -fx-border-radius: 12;");
        // - background-color: fondo blanco
        // - background-radius y border-radius: esquinas redondeadas (para que sea moderno)
        // - border-color y width: un borde gris azulado de 2 píxeles

        vista.setPrefHeight(200); // Altura fija de 200 píxeles
        
        // 2. EL TÍTULO (HBox + Label)
        HBox tituloBox = new HBox(10); // Caja horizontal para el título
        tituloBox.setAlignment(Pos.CENTER_LEFT); // Alineado a la izquierda
        
        Label titulo = new Label("📝 EVENTOS DEL JUEGO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Letra Arial, Negrita, tamaño 16
        titulo.setStyle("-fx-text-fill: #37474f;"); // Color del texto (gris oscuro azulado)
        
        tituloBox.getChildren().add(titulo);
        
        // 3. EL ÁREA DE TEXTO (TextArea)
        logArea = new TextArea();
        logArea.setEditable(false); // IMPORTANTE: El usuario no puede escribir aquí
        logArea.setPrefHeight(150);
        
        // ESTILO CSS:
        // - font-family 'Monospaced': letra de tipo "máquina de escribir" (típica de consola)
        // - control-inner-background: color de fondo del cuadro de texto (gris muy clarito)
        logArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px; " +
                         "-fx-control-inner-background: #f5f5f5;");
        logArea.setWrapText(true); // Si el texto es muy largo, salta de línea automáticamente
        
        // Añadimos el título y el cuadro de texto al contenedor principal
        vista.getChildren().addAll(tituloBox, logArea);
    }
    
    // Método para escribir cosas nuevas en el diario
    public void agregarEvento(String evento) {
        logArea.appendText("► " + evento + "\n"); // Añade el texto al final
        logArea.setScrollTop(Double.MAX_VALUE);   // Hace "auto-scroll" para ver siempre lo último
    }
    
    public void limpiar() {
        logArea.clear(); // Borra todo el historial
    }
    
    public VBox getVista() { return vista; }
}
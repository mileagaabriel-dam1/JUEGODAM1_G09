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

//Esta clase es el "Noticiero" del juego.
//Su función es mostrar por pantalla todo lo que Randy y las entidades le hacen al jugador.

public class VistaEventos {
    
    private VistaJavaFX principal; 
    //Conexión con la ventana madre
    private VBox vista;            
    //Contenedor principal (Vertical Box)
    //Contenedor que apila elementos UNO ENCIMA DE OTRO, importante.
    private TextArea logArea;      
    //El pergamino digital donde se escribe la historia
    //A ver, el log, todos sabemos lo que son los "logs" no, noticias de eventos, ..., ....
    
    public VistaEventos(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }
    
    private void crearVista() {
        //EL CONTENEDOR (VBox)
        //Usamos un VBox para que el título y el cuadro de texto se apilen de arriba a abajo.
    	
        vista = new VBox(8); 
        //8 píxeles de separación entre el título y el texto
        
        vista.setPadding(new Insets(15)); 
        //Dejamos margen alrededor para que no esté pegado al borde
        
        //ESTILO CSS, (Inline Styling):
        //Usamos -fx-background-radius para redondear las esquinas, dando un aspecto de una especie de tarjeta al tablero
        vista.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15; " +
                       "-fx-border-color: #90a4ae; -fx-border-width: 2; -fx-border-radius: 12;");
        //El setStyle es el lenguaje de diseño, lo que lleva eso delante, es o para decir, "pintame esto", "redondeame esto"...

        vista.setPrefHeight(200); 
        //Limitamos la altura para que no ocupe toda la pantalla
        
        //EL TÍTULO (HBox + Label)
        //Hbox es h, de horizontal, contenedor horizontal, simple
        HBox tituloBox = new HBox(10); 
        tituloBox.setAlignment(Pos.CENTER_LEFT); 
        //Alineamos el icono y el texto a la izquierda
        //Esto es simplemente CSS, no tiene mucho secreto
        
        Label titulo = new Label(" EVENTOS DEL JUEGO");
        //El label es una etiqueta de texto que no se puede editar, simple
        
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16)); 
        //Fuente profesional y clara
        titulo.setStyle("-fx-text-fill: #37474f;"); 
        //Color gris azulado para no cansar la vista
        
        //No hace falta explicar esto del titulo.setStyle en comentarios.
        
        tituloBox.getChildren().add(titulo);
        //El getChildren es un método que devuelve todo lo que tiene apuntado el hbox de "tituloBox"
        //.add, le estas diciendo al hbox, que añada el titulo de antes, en ese HBOX.
        
        //EL ÁREA DE TEXTO (TextArea)
        logArea = new TextArea();
        //Creas el objeto
        //Hasta que no pongas new, como aqui, el log estara vacio, y no podra hacer nada, pues aqui lo creas
        
        logArea.setEditable(false); 
        //REGLA DE ORO, El jugador solo lee, no puede borrar sus desgracias...
        
        logArea.setPrefHeight(150);
        //Bueno el textArea es un texto grande.
        //Aqui le dices, "eh, que quiero que el "LogArea" mida 150px de alto
        
        //Tipografía Monospaced, Cada letra ocupa lo mismo, como en una consola de comandos
        logArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 12px; " +
                         "-fx-control-inner-background: #f5f5f5;");
        //Bueno vamos que aqui le dices que todas las letras tienen que ocupar lo mismo
        //Que una letra ocupe 1px, y otra 5, no queda muy bien
        
        logArea.setWrapText(true); 
        //Si la foca escribe mucho, el texto salta de línea solo.
        //setWrapText es para ajustar automaticamente el texto dentro de una celda,
        //Os imaginais Excel?, pues lo que ajusta automaticamente, es lo mismo que esto.
        
        //Ensamblamos las piezas en el contenedor vertical
        vista.getChildren().addAll(tituloBox, logArea);
        //addall?, pues añadir varias cosas a la vez
        //CHE, eh, que el orden tiene una explicación eh
        //Primero quieres que salga el titulo verdad, no el logAREA y luego el titulo.
        //Pues ahi esta la explicación del orden
    }
    
    //Este método es el que llama el Controlador cada vez que pasa algo.
    //"evento", por ejemplo, que cae en un oso: La frase que queremos mostrar (ej, "El oso te ha mordido").
    
    public void agregarEvento(String evento) {
        logArea.appendText("► " + evento + "\n"); 
        //Añadimos una flechita para que parezca una lista
        //A ver, que panda el cunico en el tribunal, que es eso de "appendText"?
        //Pues simple, el setText, lo que hace es borrar el anterior y poner el nuevo.
        //Que hace esto? pues no borrar el anterior y poner el nuevo debajo, interesante eh
        
        //La \n, es para saltar de linea, que sino sale todo pegado detras del otro
        
        logArea.setScrollTop(Double.MAX_VALUE);   
        //Auto-scroll, baja la barra siempre al final para ver lo último
        //Ehh, calma aqui, que esto es nuevo en nuestra clase
        
        //Double.MAXVALUE, a ver, que es el double, y porque esta ahi
        //Es un numero que puede tener decimales, el setScrollTop, te pide numeros decimales para saber en que pixel de la barra quieres estar.
        //Claro, porque tu puedes estar entre el pixel 20,y el 30, con esa barra, es decir, puedes estar yo q se, en el 27,50.
        //Que tu eso no lo ves a ojo, pero el pc si
        
        //Y porque esta el MAX.VALUE?, con esto le dices a la barra, que baje hasta el valor máximo, es decir, infinito.
        //Hasta que la partida ya no escriba mas logs
    }
    
    //Método para resetear el diario al empezar una partida nueva
    public void limpiar() {
        logArea.clear(); 
        //No hace falta explicarlo no?
    }
    
    //Getter para que la Vista principal pueda incrustar este componente
    public VBox getVista() { return vista; }
    
    //Metodo publico, todo el mundo lo puede llamar, que devuelve el dato VBox
    //getVista es el nombre del metodo, y return vista, pues la acción de devolver el VBox que hemos hecho hasta ahora
}
package Vista;

//IMPORTACIONES, Traemos las herramientas de JavaFX para montar la interfaz
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import Controladores.*; 
//Importamos la lógica de los turnos
import Modelo.*;        
//Importamos las clases como Jugador, Inventario...

public class VistaJugador {

    //ATRIBUTOS (Nuestras variables de clase)
    private VistaJavaFX principal; 
    //Referencia a la ventana principal para estar conectados
    private VBox vista;            
    //El "táper" vertical donde meteremos las etiquetas
    private Label jugadorLabel;    
    //Etiqueta para el nombre del jugador
    private Label posicionLabel;   
    //Etiqueta para ver en qué casilla está
    private Label inventarioLabel; 
    //Etiqueta para ver sus objetos
    private Label turnoLabel;      
    //Etiqueta para saber quién tira ahora

    //CONSTRUCTOR, Se ejecuta cuando hacemos "new VistaJugador()"
    public VistaJugador(VistaJavaFX principal) {
        this.principal = principal; 
        //Guardamos la referencia que nos pasan
        crearVista();               
        //Llamamos al método que monta el dibujo
    }

    // MÉTODO PARA MONTAR LA INTERFAZ (El "dibujo" inicial)
    private void crearVista() {
        //Configuramos el contenedor VBox (Vertical Box)
        
    	//El "10" significa que habrá 10 píxeles de separación entre cada cosa que metamos
        vista = new VBox(10);
        vista.setAlignment(Pos.TOP_LEFT); 
        //Todo alineado arriba a la izquierda
        vista.setPadding(new Insets(15)); 
        //Un margen interno de 15px para que no pegue al borde
        
        //Aplicamos CSS INLINE (Estilo visual)
        //Esto es como el CSS de web pero con el prefijo "-fx-"
        vista.setStyle("-fx-background-color: white; -fx-border-color: #90a4ae; " +
                       "-fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;");
        vista.setPrefWidth(260); 
        //Le damos un ancho fijo para que no baile

        //Creamos el Título
        Label titulo = new Label(" INFORMACIÓN ");
        //Le ponemos una fuente Arial, en Negrita (Bold) y tamaño 16
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: #37474f;"); // Color gris azulado oscuro

        //Instanciamos las etiquetas (Aquí nacen los objetos, antes eran null)
        turnoLabel = new Label("Turno: -");
        jugadorLabel = new Label("Jugador: -");
        posicionLabel = new Label("Posición: -");
        inventarioLabel = new Label("Inventario: -");

        //Ajustamos las fuentes de las etiquetas
        //El turno lo ponemos en negrita (BOLD) para que destaque
        turnoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        jugadorLabel.setFont(Font.font("Arial", 12));
        posicionLabel.setFont(Font.font("Arial", 12));
        inventarioLabel.setFont(Font.font("Arial", 12));

        //Añadimos todos los Labels al VBox "vista"
        //El orden aquí es el orden en el que aparecerán de arriba a abajo
        vista.getChildren().addAll(titulo, turnoLabel, jugadorLabel, posicionLabel, inventarioLabel);
    }

    //MÉTODO ACTUALIZAR, Este es el que hace que la pantalla cambie.
    //Recibe el controlador de turnos para "cotillear" los datos del modelo y pintarlos.
    
    public void actualizar(controladorTurnos controladorTurnos) {
        
        //CONTROL DE ERRORES, Si no hay partida o no hay jugador, limpiamos y fuera
        if (controladorTurnos == null || controladorTurnos.getJugadorActual() == null) {
            limpiar();
            return;
        }

        //Obtenemos el objeto Jugador que tiene el turno ahora mismo
        Jugador actual = controladorTurnos.getJugadorActual();
        
        //Lógica visual, Si es una máquina (IA), le añadimos el texto "(IA)" al nombre
        String tipo = actual.esIA() ? " (IA)" : "";

        //REFRESCO DE DATOS, Cambiamos el texto de los Labels con los datos reales
        turnoLabel.setText(" Turno: " + actual.getNombre());
        jugadorLabel.setText(" Jugador: " + actual.getNombre() + tipo);
        
        //Ajuste de Posición:
        //Como los programadores contamos desde 0, al usuario le sumamos 1 para que no se raye
        posicionLabel.setText(" Posición: " + (actual.getPosicion() + 1) + "/50");

        //Gestión del Inventario (Mochila):
        //Si el objeto inventario existe, llamamos a su método para sacar el texto
        if (actual.getInventario() != null) {
            inventarioLabel.setText("🎒 " + actual.getInventario().obtenerResumen());
        } else {
            // Si el objeto es null, ponemos que está vacío para que no pete el programa
            inventarioLabel.setText(" Inventario: vacío");
        }
    }

    
     //MÉTODO LIMPIAR: Resetea los textos a su estado original.
     //Muy útil cuando reinicias la partida o alguien pierde.
     
    public void limpiar() {
        turnoLabel.setText(" Turno: -");
        jugadorLabel.setText(" Jugador: -");
        posicionLabel.setText(" Posición: -");
        inventarioLabel.setText(" Inventario: -");
    }

    //GETTER, Para que la clase principal pueda coger este VBox y meterlo en la ventana
    public VBox getVista() { 
        return vista; 
    }
}
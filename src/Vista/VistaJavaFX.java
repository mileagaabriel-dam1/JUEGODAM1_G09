package Vista; 
//Indica que esta clase pertenece al paquete (carpeta) Vista

//IMPORTS, Traemos las herramientas de JavaFX

import javafx.application.Application; 
//La clase base para cualquier app con ventana

import javafx.geometry.Insets;         
//Para gestionar márgenes y separaciones

import javafx.scene.Scene;             
//El "lienzo" donde se pintan los elementos

import javafx.scene.layout.BorderPane; 
//Organizador por zonas (Norte, Sur, Este, Oeste, Centro)

import javafx.scene.layout.VBox;       
//Organizador vertical (apila elementos)

import javafx.stage.Stage;             
//El "escenario" o marco de la ventana

import Controladores.*;                
//Importamos toda la lógica (nuestros controladores)

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;


//Esta es la clase principal de la interfaz. 
//'extends Application' le da los superpoderes de JavaFX para abrir ventanas.

public class VistaJavaFX extends Application {

    //ATRIBUTOS, Las piezas que forman el juego
    
    //Referencias a los controladores (La lógica del juego)
    private controladorJuego controladorJuego;
    private controladorTablero controladorTablero;
    private controladorJugador controladorJugador;
    private controladorTurnos controladorTurnos;

    // El contenedor principal donde "pegaremos" todas las partes
    private BorderPane root;
    //El borderPane, no es algo nuevo, ya lo hemos tocado en CSS.
    //Basicamente un contenedor que divide todo en 5 zonas
    //Top, Bottom, Left, Right, Center. Ahora nos suena mas eh
    
    //Las sub-clases de la vista (Modularización cada cosa en su sitio)
    private VistaMenu vistaMenu;                  
    //El menú superior
    
    private VistaJuego vistaJuego;                
    //Botones de acción (dados)
    
    private VistaTableroConImagenes vistaTablero; 
    //El tablero visual con casillas
    
    private VistaJugador vistaJugador;            
    //Panel con info del jugador actual
    
    private VistaEventos vistaEventos;            
    //El diario de mensajes (log)

    //El método start es el punto de inicio de JavaFX.
    //primaryStage es la ventana que Java crea automáticamente.
    
    @Override
    public void start(Stage primaryStage) {
        
        // --- 1. VENTANA DE INICIO DE SESIÓN / REGISTRO AUTOMÁTICO ---
        // Implementamos un diálogo personalizado para pedir Nombre y Contraseña
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Acceso al Sistema");
        dialog.setHeaderText("Bienvenido al Joc del Pingü - G09");

        // Configuramos los botones de la ventana
        ButtonType loginButtonType = new ButtonType("Entrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Creamos el contenedor para los campos de texto
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Nombre");
        PasswordField password = new PasswordField();
        password.setPromptText("Contraseña");

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(password, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Optional<ButtonType> result = dialog.showAndWait();
        
        String nombreUsuario = "";
        String passUsuario = "";

        if (result.isPresent() && result.get() == loginButtonType){
            // Normalizamos el nombre a mayúsculas para evitar duplicados en la BD
            nombreUsuario = username.getText().trim().toUpperCase();
            passUsuario = password.getText().trim();
            System.out.println("Intentando sesión para: " + nombreUsuario);
        } else {
            System.exit(0); // Si cancela, cerramos la aplicación
        }
        // ------------------------------------------------------------

        //INICIAMOS LA LÓGICA
        //Creamos el "cerebro" y obtenemos sus partes
        controladorJuego = new controladorJuego();

        // --- CONEXIÓN CON LA BASE DE DATOS ---
        // Registramos u obtenemos el ID real consultando la columna "ID" y pasando la contraseña
        int idLogueado = controladorJuego.registrarOLogearUsuario(nombreUsuario, passUsuario);
        
        // Mostramos el récord usando el ID real para evitar errores ORA-20001
        controladorJuego.mostrarMiRecord(idLogueado); 
        // ------------------------------------

        controladorTablero = controladorJuego.getControladorTablero();
        controladorJugador = controladorJuego.getControladorJugador();
        controladorTurnos = controladorJuego.getControladorTurnos();
        
        // --- VÍNCULO CON EL JUGADOR ---
        // Guardamos el ID en el controlador de jugadores para usarlo al finalizar la partida
        controladorJugador.setIdUsuarioLogueado(idLogueado);
        controladorJugador.setNombreUsuarioLogueado(nombreUsuario);

        // 2. INICIALIZAMOS LAS SUB-VISTAS
        // Pasamos 'this' a los constructores para que las vistas puedan
        // llamar a métodos de esta clase principal (comunicación bidireccional).
        vistaMenu = new VistaMenu(this);
        vistaJuego = new VistaJuego(this);
        vistaTablero = new VistaTableroConImagenes(this);
        vistaJugador = new VistaJugador(this);
        vistaEventos = new VistaEventos(this);

        //CONFIGURAMOS EL MARCO DE LA VENTANA (Stage)
        primaryStage.setTitle("JOC DEL PINGÜ - DAM1 - G09"); 
        //Título arriba
        
        primaryStage.setWidth(1100);                         
        //Ancho en píxeles
        
        primaryStage.setHeight(750);                        
        //Alto en píxeles

        //DISEÑO DE LA INTERFAZ (Layout)
        root = new BorderPane(); 
        //Creamos el organizador por zonas

        //Colocamos el menú en la parte superior (Top)
        root.setTop(vistaMenu.getVista());
        
        //Colocamos el tablero en el centro (Center). 
        //Se lleva el mayor espacio porque es la parte principal.
        root.setCenter(vistaTablero.getVista());

        //Creamos una columna derecha para agrupar varios paneles
        VBox derecha = new VBox(10);           
        //Espacio de 10px entre paneles
        derecha.setPadding(new Insets(10));    
        //Margen de 10px respecto al borde
        //Le estas diciendo que en los 4 lados, arriba, abajo, derecha, izquierda, deje 10 pixeles de espacio
        
        //Añadimos las 3 vistas a la columna en orden descendente
        derecha.getChildren().addAll(
                //getChildren, "dame tu lista vacia, y añade todo esto:"
            vistaJugador.getVista(), 
            //Arriba, Datos del jugador
            vistaJuego.getVista(),   
            //Centro, Controles del juego
            vistaEventos.getVista()  
            //Abajo, Historial de eventos
            
            //De donde viene el orden? pues es un VBox, el que llega primero, se queda arriba, el segundo, en el centro, y el ultimo, abajo
        );
        derecha.setPrefWidth(300);
        //Fijamos el ancho de la columna derecha a 300px

        //Metemos esa columna completa en la zona derecha (Right) del BorderPane
        root.setRight(derecha);
        //Le dices que el VBox derecha, coja todo y lo ponga a la derecha.
        //es decir, le dices al root, que hemos dicho que era el BorderPane, coja ese espacio en la derecha que tiene reservado,
        //y ahi ponga el VBox "derecha"


        //CARGAMOS LA ESCENA Y LANZAMOS
        Scene scene = new Scene(root); 
        //Metemos el diseño final en la "escena"
        //Calma aqui, es es el scene?, es lo que pasa centro del escenario, es decir, el stage, que se explicara mas abajo.
        //Por ejemplo, cambias de escena, del Menu, al juego, facil no?
        
     //FINAL DEL MÉTODO START

        //Aquí le decimos al marco físico (ventana) que use el lienzo (escena) que hemos creado.
        primaryStage.setScene(scene);  
        
        //Por defecto, las ventanas en JavaFX nacen "ocultas" en la memoria. 
        //Con .show() le damos la orden al sistema operativo de que la pinte en el monitor.
        primaryStage.show();           

        //Nada más abrirse la ventana, llamamos a este método propio para que la interfaz 
        //no aparezca con datos aleatorios, sino reseteada y lista para empezar.
        
        mostrarMenuPrincipal(); 
    } //Aquí se cierra el método start(Stage primaryStage)

    //MÉTODO: mostrarMenuPrincipal
    //Su objetivo es hacer un "reset" visual de todos los componentes.
    
    public void mostrarMenuPrincipal() {
        //Llama al método ocultar() de la clase VistaJuego para esconder el botón de tirar dado.
        vistaJuego.ocultar();   
        
        //Llama al método limpiar() de VistaJugador para borrar nombres o etiquetas de la partida anterior.
        vistaJugador.limpiar(); 
        
        //Llama al método limpiar() de VistaTablero para quitar pingüinos o casillas marcadas.
        vistaTablero.limpiar(); 
        
        //Seguridad, Si el objeto vistaEventos existe (no es null), borra todo el texto del historial.
        if (vistaEventos != null) vistaEventos.limpiar(); 
    }

    //MÉTODO, iniciarPartida
    //Es el "cerebro" que conecta la configuración del usuario con el motor del juego.
    //numHumanos Cuántos jugadores reales hay.
    //incluirIA Si jugará la máquina o no.
    
    public void iniciarPartida(int numHumanos, boolean incluirIA) {
        
        //LÓGICA, Le pide al controlador de jugadores que cree los objetos en memoria (nombre, color, etc).
        controladorJugador.inicializarJugadores(numHumanos, incluirIA);
        
        //LÓGICA, Le pasa esa lista de jugadores al controlador de turnos para saber quién va primero.
        controladorTurnos.setJugadores(controladorJugador.getJugadores());

        //VISUAL, Usa los datos del controlador de tablero para dibujar el mapa de juego.
        vistaTablero.inicializarTablero(controladorTablero);
        
        //VISUAL, Dibuja a los pingüinos en su posición inicial (casilla de salida).
        vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);

        //INTERFAZ, Refresca el panel derecho con el nombre del jugador que debe empezar.
        vistaJugador.actualizar(controladorTurnos);
        
        //INTERFAZ, Vacía el log de eventos por si había mensajes viejos.
        vistaEventos.limpiar();
        
        //INTERFAZ, Añade las primeras líneas de texto al historial para dar feedback al usuario.
        vistaEventos.agregarEvento(" ¡Partida iniciada!");
        vistaEventos.agregarEvento(" Jugadores: " + controladorJugador.getJugadores().size());
        
        //INTERFAZ, Hace aparecer el botón de lanzar dado para que el humano pueda jugar.
        vistaJuego.mostrar(); 

        //IA CHECK, Comprobamos si el jugador que tiene el primer turno es una IA.
        //Si lo es, lanzamos el dado automáticamente para que el juego no se detenga.
        if (controladorTurnos.getJugadorActual() != null &&
            controladorTurnos.getJugadorActual().esIA()) {
            vistaJuego.lanzarDadoIA();
        }
    }

    //MÉTODO, lanzarDado
    //Actúa como un "repetidor". Permite que otras clases den la orden de tirar el dado 
    //pasando a través de esta clase principal.
    
    public void lanzarDado() {
        vistaJuego.lanzarDado();
    }

    //SECCIÓN DE GETTERS
    //¿Por qué están aquí? Como los objetos son 'private', estas funciones son 
    //"ventanillas de información" para que otras clases puedan usarlos sin romper nada.

    public controladorJuego getControladorJuego() { return controladorJuego; }
    //Este método devuelve el cerebro principal del juego (lógica completa)

    public controladorTablero getControladorTablero() { return controladorTablero; }
    //Este devuelve el que sabe dónde están las casillas y los premios

    public controladorJugador getControladorJugador() { return controladorJugador; }
    //Este devuelve la lista de quién está jugando y sus colores

    public controladorTurnos getControladorTurnos() { return controladorTurnos; }
    //Este devuelve a quién le toca tirar el dado ahora mismo

    public VistaTableroConImagenes getVistaTablero() { return vistaTablero; }
    public VistaJugador getVistaJugador() { return vistaJugador; }
    public VistaEventos getVistaEventos() { return vistaEventos; }
    //Estos tres devuelven las piezas visuales (el tablero, la info y el log)

    
    //MÉTODO, main
    //Es lo primero que lee Java al ejecutar el programa.
     
    public static void main(String[] args) {
        //launch(args) es una función de la librería Application. 
        //Se encarga de levantar el sistema gráfico y, cuando está listo, llama al método start().
        launch(args); 
    }
} 
//Fin de la clase VistaJavaFX
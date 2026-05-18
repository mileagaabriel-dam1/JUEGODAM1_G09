package Vista; 

// IMPORTS, Traemos las herramientas de JavaFX
import javafx.application.Application; 
import javafx.geometry.Insets;         
import javafx.scene.Scene;              
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.VBox;       
import javafx.stage.Stage;              
import Controladores.*;                
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VistaJavaFX extends Application {

    // ATRIBUTOS, Las piezas que forman el juego
    private controladorJuego controladorJuego;
    private controladorTablero controladorTablero;
    private controladorJugador controladorJugador;
    private controladorTurnos controladorTurnos;

    private BorderPane root;
    
    private VistaMenu vistaMenu;                    
    private VistaJuego vistaJuego;                  
    private VistaTableroConImagenes vistaTablero; 
    private VistaJugador vistaJugador;              
    private VistaEventos vistaEventos;              

    @Override
    public void start(Stage primaryStage) {
        
        // 1️⃣ INICIAMOS LA LÓGICA INTERNA PRIMERO
        controladorJuego = new controladorJuego();
        controladorJuego.setVista(this);

        controladorTablero = controladorJuego.getControladorTablero();
        controladorJugador = controladorJuego.getControladorJugador();
        controladorTurnos = controladorJuego.getControladorTurnos();

        // 2️⃣ INSTANCIAMOS LAS SUB-VISTAS
        vistaMenu = new VistaMenu(this);
        vistaJuego = new VistaJuego(this);
        vistaTablero = new VistaTableroConImagenes(this);
        vistaJugador = new VistaJugador(this);
        vistaEventos = new VistaEventos(this);

        // 3️⃣ CONFIGURAMOS EL DISEÑO Y LA VENTANA PRINCIPAL
        primaryStage.setTitle("JOC DEL PINGÜ - DAM1 - G09"); 
        primaryStage.setWidth(1100);                                         
        primaryStage.setHeight(750);                                        

        root = new BorderPane(); 
        
        // Colocamos el menú en la parte superior y el tablero en el centro
        root.setTop(vistaMenu.getVista());
        root.setCenter(vistaTablero.getVista());

        // Agrupamos la zona derecha
        VBox derecha = new VBox(10);           
        derecha.setPadding(new Insets(10));    
        derecha.getChildren().addAll(
            vistaJugador.getVista(), 
            vistaJuego.getVista(),   
            vistaEventos.getVista()  
        );
        derecha.setPrefWidth(300);
        root.setRight(derecha);

        // Creamos la escena y mostramos la ventana en pantalla
        Scene scene = new Scene(root); 
        primaryStage.setScene(scene);  
        primaryStage.show();           

        // Dejamos la interfaz en su estado neutro inicial (tablero vacío y paneles limpios)
        mostrarMenuPrincipal(); 

        // =========================================================================
        // 4️⃣ VENTANA DE INICIO DE SESIÓN
        // =========================================================================
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Acceso al Sistema");
        dialog.setHeaderText("Bienvenido al Joc del Pingü - G09");

        try {
            Image imgLogin = new Image(getClass().getResourceAsStream("/resources/pinguino.png"));
            ImageView vistaLogin = new ImageView(imgLogin);
            vistaLogin.setFitHeight(50);
            vistaLogin.setPreserveRatio(true);
            dialog.setGraphic(vistaLogin); 
        } catch (Exception e) {
            // Continuar sin imagen si falla
        }

        ButtonType loginButtonType = new ButtonType("Entrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

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
            nombreUsuario = username.getText().trim().toUpperCase();
            passUsuario = password.getText().trim();
            System.out.println("Intentando sesión para: " + nombreUsuario);
        } else {
            System.exit(0); 
        }

        // 5️⃣ PROCESAMOS LA AUTENTICACIÓN CON LA BASE DE DATOS
        int idLogueado = controladorJuego.registrarOLogearUsuario(nombreUsuario, passUsuario);
        controladorJuego.mostrarMiRecord(idLogueado); 
        
        // Sincronizamos las variables del usuario en el controlador de sesión
        controladorJugador.setIdUsuarioLogueado(idLogueado);
        controladorJugador.setNombreUsuarioLogueado(nombreUsuario);

        // 🚨 MODIFICADO: Ya NO llamamos a iniciarPartida() de forma automática.
        // En su lugar, inicializamos visualmente el tablero limpio a la espera de acción.
        vistaTablero.inicializarTablero(controladorTablero);
        vistaEventos.agregarEvento("🔑 Sesión iniciada como: " + nombreUsuario);
        vistaEventos.agregarEvento("🎮 Haz clic en 'Nueva Partida' arriba para configurar y empezar a jugar.");

        // =========================================================================
        // 💾 6️⃣ COMPROBACIÓN OPCIONAL DE REGISTROS GUARDADOS ('M')
        // =========================================================================
        int posicionRescatada = controladorJuego.cargarPartidaGuardada(idLogueado);

        if (posicionRescatada != -1) {
            int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
                "¡Hola " + nombreUsuario + "! Hemos detectado una partida guardada en curso.\n¿Deseas reanudarla desde donde te quedaste?",
                "Continuar Partida Encontrada",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE);

            if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
                // Si decide reanudar, forzamos la creación del entorno para esa partida rescatada
                controladorJugador.inicializarJugadores(1, true); // Crea el jugador base y la IA
                controladorTurnos.setJugadores(controladorJugador.getJugadores());

                if (controladorTurnos.getJugadorActual() != null) {
                    controladorTurnos.getJugadorActual().setPosicion(posicionRescatada);
                }
                
                // Refrescamos pantallas
                vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);
                vistaJugador.actualizar(controladorTurnos);
                vistaJuego.mostrar(); 
                
                vistaEventos.agregarEvento("📂 [SISTEMA] Partida reanudada. Casilla: " + (posicionRescatada + 1));
            }
        }
    } 

    public void mostrarMenuPrincipal() {
        vistaJuego.ocultar();   
        vistaJugador.limpiar(); 
        vistaTablero.limpiar(); 
        if (vistaEventos != null) vistaEventos.limpiar(); 
    }

    public void iniciarPartida(int numHumanos, boolean incluirIA) {
        controladorJugador.inicializarJugadores(numHumanos, incluirIA);
        controladorTurnos.setJugadores(controladorJugador.getJugadores());

        vistaTablero.inicializarTablero(controladorTablero);
        vistaTablero.actualizarPosiciones(controladorJugador, controladorTurnos);
        vistaJugador.actualizar(controladorTurnos);
        
        vistaEventos.limpiar();
        vistaEventos.agregarEvento("🐧 ¡Partida creada manualmente por el jugador!");
        vistaJuego.mostrar(); 

        if (controladorTurnos.getJugadorActual() != null && controladorTurnos.getJugadorActual().esIA()) {
            vistaJuego.lanzarDadoIA();
        }
    }

    public void lanzarDado() {
        vistaJuego.lanzarDado();
    }

    // GETTERS
    public controladorJuego getControladorJuego() { return controladorJuego; }
    public controladorTablero getControladorTablero() { return controladorTablero; }
    public controladorJugador getControladorJugador() { return controladorJugador; }
    public controladorTurnos getControladorTurnos() { return controladorTurnos; }
    public VistaTableroConImagenes getVistaTablero() { return vistaTablero; }
    public VistaJugador getVistaJugador() { return vistaJugador; }
    public VistaEventos getVistaEventos() { return vistaEventos; }

    public static void main(String[] args) {
        launch(args); 
    }
}
package Vista; 
//Carpeta donde vive el archivo

//IMPORTS, Nuestras herramientas de construcción 

import javafx.geometry.Insets;         
//Para dar "aire" (márgenes) dentro del panel

import javafx.geometry.Pos;            
//Para centrar el botón y el dado

import javafx.scene.control.Button;    
//El componente botón

import javafx.scene.control.Label;     
//Para textos y mostrar el emoji del dado

import javafx.scene.layout.VBox;       
//Organizador vertical (uno encima de otro)

import javafx.scene.text.Font;         
//Para cambiar el tamaño de la letra

import javafx.scene.text.FontWeight;   
//Para poner letras en negrita

import javafx.application.Platform;    
//Muy importante Permite que los hilos externos toquen la interfaz

import Controladores.*;                
//Acceso a la lógica (turnos, eventos)

import Modelo.*;                     
//Acceso a los datos (Jugador, Casilla)

public class VistaJuego {

    //ATRIBUTOS, Lo que este panel necesita recordar
	
    private VistaJavaFX principal;     
    //Referencia a la ventana madre
    //PRINCIPAL se va a usar mas en este codigo, ya que se refiere a VistaJavaFX
    
    
    private VBox vista;                
    //El contenedor naranja que agrupa todo
    
    private Button btnLanzarDado;      
    //El botón de acción
    
    private Label dadoLabel;           
    //El recuadro donde sale el dibujo del dado
    
    private boolean juegoActivo = false; 
    //Interruptor para saber si se puede tirar o no

    //Constructor, Recibe la ventana principal y monta el diseño
    public VistaJuego(VistaJavaFX principal) {
        this.principal = principal;
        crearVista();
    }

    //Define cómo se ve el panel del dado (Colores, tamaños, fuentes)
    
    private void crearVista() {
        //Creamos la caja vertical con 15px de separación entre elementos
        vista = new VBox(15);
        vista.setAlignment(Pos.CENTER); 
        //Todo al centro
        
        vista.setPadding(new Insets(15)); 
        //Margen interno de 15px
        
        //Estilo CSS, Fondo naranja, bordes redondeados (15px)
        vista.setStyle("-fx-background-color: #ffb74d; -fx-background-radius: 15;");
        vista.setPrefWidth(260); 
        //Ancho fijo del panel

        //Etiqueta de título
        Label titulo = new Label(" LANZAR DADO");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titulo.setStyle("-fx-text-fill: white;"); 
        //Letras blancas

        //El recuadro blanco donde aparece el dado gigante
        dadoLabel = new Label("🎲");
        dadoLabel.setFont(Font.font("Segoe UI Emoji", 60)); 
        //Fuente especial para emojis
        
        dadoLabel.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 10;");

        //El botón para tirar
        btnLanzarDado = new Button("LANZAR DADO");
        btnLanzarDado.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white; " +
                               "-fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 25;");
        
        btnLanzarDado.setDisable(true); 
        //Bloqueado al inicio (hasta que se pulse "Empezar")
        
        //Al pulsar el botón, se ejecuta el método lanzarDado()
        btnLanzarDado.setOnAction(e -> lanzarDado());

        //Añadimos las tres piezas al contenedor vertical
        vista.getChildren().addAll(titulo, dadoLabel, btnLanzarDado);
    }

    //LÓGICA PRINCIPAL: Qué pasa cuando se tira el dado
    
    public void lanzarDado() {
        //Si el juego no ha empezado, no hacemos nada
        if (!juegoActivo) return;

        //Pedimos a la clase principal los controladores y el jugador que tiene el turno
        controladorTurnos controladorTurnos = principal.getControladorTurnos();
        controladorEventos controladorEventos = principal.getControladorJuego().getControladorEventos();
        Jugador jugador = controladorTurnos.getJugadorActual();

        if (jugador == null) return;

        //Bloqueamos el botón inmediatamente para que no pinchen mil veces mientras el dado gira
        btnLanzarDado.setDisable(true); 
        principal.getVistaEventos().agregarEvento("🎲 Turno de: " + jugador.getNombre());

        //ANIMACIÓN, Empezamos a hacer girar el dado visualmente
        animarDado(() -> {
            //Este bloque se ejecuta cuando la animación termina (gracias al callback)
            
            //RESULTADO REAL, Calculamos un número del 1 al 6
            int dado = (int)(Math.random() * 6) + 1;
            dadoLabel.setText(getDadoEmoji(dado)); 
            //Ponemos el dibujo que toca (⚀, ⚁...)

            int origen = jugador.getPosicion(); 
            //Casilla donde está ahora
            
            int destino = Math.min(origen + dado, 49); 
            //Casilla donde irá (máximo la 49)

            principal.getVistaEventos().agregarEvento("🎲 " + jugador.getNombre() + " sacó un " + dado);

            VistaTableroConImagenes tablero = principal.getVistaTablero();

            //MOVIMIENTO, Llamamos al tablero para que mueva al pingüino poco a poco
            tablero.animarMovimiento(jugador, origen, destino, () -> {
                
                //Cuando el pingüino termina de moverse:
                jugador.setPosicion(destino); // Guardamos su nueva posición en la lógica
                principal.getVistaEventos().agregarEvento("📍 " + jugador.getNombre() + " está en casilla " + (destino + 1));

                //EFECTOS, Miramos si la casilla tiene trineo, oso, etc.
                Casilla casilla = principal.getControladorTablero().getCasilla(destino);
                String mensaje = controladorEventos.procesarCasilla(jugador, casilla);
                
                if (mensaje != null && !mensaje.isEmpty()) {
                    principal.getVistaEventos().agregarEvento(mensaje); 
                    //Avisamos si pasó algo extra
                }

                //REFRESCAR, Actualizamos los dibujos y los textos del panel lateral
                tablero.actualizarPosiciones(principal.getControladorJugador(), controladorTurnos);
                principal.getVistaJugador().actualizar(controladorTurnos);

                //FINAL, ¿Alguien ha llegado a la meta (casilla 49)?
                if (destino == 49) {
                    principal.getVistaEventos().agregarEvento("🎉 ¡" + jugador.getNombre() + " HA GANADO! 🎉");
                    juegoActivo = false; 
                    //Paramos el juego
                    
                    btnLanzarDado.setDisable(true); 
                    //Bloqueamos el botón para siempre
                    return;
                }

                //SIGUIENTE, Pasamos el turno al siguiente jugador
                controladorTurnos.siguienteTurno();
                principal.getVistaJugador().actualizar(controladorTurnos);
                principal.getVistaEventos().agregarEvento("➡️ Siguiente: " + controladorTurnos.getJugadorActual().getNombre());

                //Desbloqueamos el botón para el siguiente jugador (si es humano)
                btnLanzarDado.setDisable(false);

                //IA, Si el nuevo jugador es una máquina, ella tira sola
                if (controladorTurnos.getJugadorActual().esIA()) {
                    lanzarDadoIA();
                }
            });
        });
    }

    //Gestiona el turno de la Inteligencia Artificial
    
    public void lanzarDadoIA() {
        //Creamos un hilo nuevo para no congelar la pantalla durante la espera
        new Thread(() -> {
            try { 
                Thread.sleep(1500); 
                //La IA "se lo piensa" 1,5 segundos (más realista)
            } catch (Exception e) {} 

            //Platform.runLater manda la orden de vuelta a la pantalla principal
            Platform.runLater(() -> { 
                if (juegoActivo) lanzarDado(); 
                //La IA tira el dado
            });
        }).start(); 
        //Arrancamos el hilo
    }

    //Efecto visual de que el dado está girando
    
    private void animarDado(Runnable callback) {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                int num = (int)(Math.random() * 6) + 1;
                //Actualizamos el dibujo del dado cada 70 milisegundos
                Platform.runLater(() -> dadoLabel.setText(getDadoEmoji(num)));
                try { Thread.sleep(70); } catch (Exception e) {}
            }
            //Al terminar las 10 vueltas, ejecutamos la lógica real
            Platform.runLater(callback);
        }).start();
    }

    //Convierte el número del dado en el símbolo Unicode correspondiente
    
    private String getDadoEmoji(int num) {
        switch(num) {
            case 1: return "⚀"; case 2: return "⚁"; case 3: return "⚂";
            case 4: return "⚃"; case 5: return "⚄"; case 6: return "⚅";
            default: return "🎲";
        }
    }

    //Devuelve el panel completo para que VistaJavaFX lo ponga a la derecha
    public VBox getVista() { return vista; }

    //Activa el panel al empezar la partida
    public void mostrar() {
        juegoActivo = true;
        btnLanzarDado.setDisable(false);
        dadoLabel.setText("🎲");
    }

    //Desactiva el panel
    public void ocultar() {
        juegoActivo = false;
        btnLanzarDado.setDisable(true);
    }
}
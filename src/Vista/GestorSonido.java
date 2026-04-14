package Vista;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;



public class GestorSonido {
    
    private static GestorSonido instancia;
    private Map<String, MediaPlayer> reproductores;
    private MediaPlayer musicaFondo;
    private boolean sonidoActivado = true;
    private boolean musicaActivada = true;
    
    private static final String[] RUTAS_POSIBLES = {
        "./Resources/audio/",
        "../Resources/audio/",
        "Resources/audio/"
    };
    
    private String rutaEncontrada = null;
    
    private GestorSonido() {
        reproductores = new HashMap<>();
        buscarRutaAudio();
        cargarTodosLosSonidos();
    }
    
    public static GestorSonido getInstancia() {
        if (instancia == null) {
            instancia = new GestorSonido();
        }
        return instancia;
    }
    
    private void buscarRutaAudio() {
        for (String ruta : RUTAS_POSIBLES) {
            File carpeta = new File(ruta);
            if (carpeta.exists() && carpeta.isDirectory()) {
                rutaEncontrada = ruta;
                System.out.println("✅ Ruta de audio encontrada: " + ruta);
                return;
            }
        }
        System.out.println("⚠️ No se encontró la carpeta de audio, sonidos desactivados");
    }
    
    private void cargarTodosLosSonidos() {
        if (rutaEncontrada == null) return;
        
        cargarSonido("dado_lanzar", "dado_lanzar.mp3");
        cargarSonido("dado_caer", "dado_caer.mp3");
        cargarSonido("victoria", "victoria.mp3");
        cargarSonido("oso", "casilla_oso.mp3");
        cargarSonido("trineo", "casilla_trineo.mp3");
        cargarSonido("agujero", "casilla_agujero.mp3");
        cargarSonido("item", "item_recogido.mp3");
        cargarSonido("foca", "foca_atrapa.mp3");
        
        try {
            File file = new File(rutaEncontrada + "musica_fondo.mp3");
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                musicaFondo = new MediaPlayer(media);
                musicaFondo.setCycleCount(MediaPlayer.INDEFINITE);
                musicaFondo.setVolume(0.3);
                System.out.println("✅ Música de fondo cargada");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error cargando música: " + e.getMessage());
        }
    }
    
    private void cargarSonido(String nombre, String archivo) {
        try {
            File file = new File(rutaEncontrada + archivo);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                reproductores.put(nombre, player);
            }
        } catch (Exception e) {
            // Silencioso
        }
    }
    
    public void reproducirEfecto(String nombre) {
        if (!sonidoActivado) return;
        
        MediaPlayer player = reproductores.get(nombre);
        if (player != null) {
            player.stop();
            player.play();
        }
    }
    
    public void reproducirEfectoDado(int resultado) {
        reproducirEfecto("dado_lanzar");
        new Thread(() -> {
            try { Thread.sleep(400); } catch (Exception e) {}
            javafx.application.Platform.runLater(() -> {
                reproducirEfecto("dado_caer");
            });
        }).start();
    }
    
    public void iniciarMusicaFondo() {
        if (!musicaActivada || musicaFondo == null) return;
        musicaFondo.play();
    }
    
    public void detenerMusicaFondo() {
        if (musicaFondo != null) musicaFondo.stop();
    }
    
    public void pausarMusicaFondo() {
        if (musicaFondo != null) musicaFondo.pause();
    }
    
    public void setSonidoActivado(boolean activado) {
        this.sonidoActivado = activado;
    }
    
    public void setMusicaActivada(boolean activada) {
        this.musicaActivada = activada;
        if (activada) iniciarMusicaFondo();
        else pausarMusicaFondo();
    }
}
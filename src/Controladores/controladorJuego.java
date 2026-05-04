package Controladores; 
//Definimos el paquete donde se encuentra este controlador principal

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class controladorJuego { 
    //Esta es la clase "corazón" que centraliza todos los subsistemas del juego(Objeto maestro)

    //Esto añade un mayor orden entre los códigos.
    
    //Declaramos los atributos privados. Usamos 'private' para aplicar el encapsulamiento,
    //así protegemos estas instancias y solo se pueden gestionar desde esta clase.
    
    private controladorTablero controladorTablero; 
    //Referencia al gestor del mapa y las casillas
    
    private controladorJugador controladorJugador; 
    //Referencia al gestor de los datos del usuario
    
    private controladorTurnos controladorTurnos;   
    //Referencia al gestor del flujo de la partida
    
    private controladorEventos controladorEventos; 
    //Referencia al gestor de la lógica de encuentros

    //Este es el constructor de la clase. Se encarga de inicializar los objetos al empezar la partida.
    public controladorJuego() {
        
        // Usamos 'this' para diferenciar los atributos de la clase y asignarles una nueva instancia (new)
        this.controladorTablero = new controladorTablero(); 
        //Instanciamos el controlador del tablero
        
        this.controladorJugador = new controladorJugador(); 
        //Instanciamos el controlador del jugador
        
        this.controladorTurnos = new controladorTurnos();   
        //Instanciamos el controlador de turnos
        
        this.controladorEventos = new controladorEventos(); 
        //Instanciamos el controlador de eventos
    }

    // --- MÉTODOS DE BASE DE DATOS PARA EL PROYECTO ---
    // Estos métodos conectan el corazón del juego con Oracle SQL

    /**
     * Busca un usuario por nombre y contraseña. Si no existe, lo registra (INSERT).
     * Devuelve el ID para poder usarlo en las partidas.
     */
    public int registrarOLogearUsuario(String nombre, String password) {
        int idEncontrado = -1;
        // Ajustado: Buscamos por nombre y contraseña para validar el acceso
        String sqlBuscar = "SELECT ID FROM JUGADORS WHERE NOM = ? AND PASSWORD = ?";
        String sqlInsertar = "INSERT INTO JUGADORS (NOM, PASSWORD) VALUES (?, ?)";

        try (Connection con = Modelo.ConexionBD.conectar()) {
            // Desactivamos el autoCommit para gestionar el guardado manualmente
            con.setAutoCommit(false);

            // 1. Intentamos buscar si el usuario ya existe con esa contraseña
            PreparedStatement psBusca = con.prepareStatement(sqlBuscar);
            psBusca.setString(1, nombre);
            psBusca.setString(2, password);
            ResultSet rs = psBusca.executeQuery();

            if (rs.next()) {
                // Si existe, recuperamos su ID
                idEncontrado = rs.getInt("ID");
                System.out.println("LOG: Login correcto. ID: " + idEncontrado);
            } else {
                // Comprobamos si el nombre existe pero la contraseña está mal
                String sqlExisteNom = "SELECT ID FROM JUGADORS WHERE NOM = ?";
                PreparedStatement psCheck = con.prepareStatement(sqlExisteNom);
                psCheck.setString(1, nombre);
                if (psCheck.executeQuery().next()) {
                    System.out.println("LOG: Contraseña incorrecta para el usuario: " + nombre);
                    return -1; // Error de credenciales
                }

                // 2. Si no existe el nombre, lo registramos como nuevo usuario
                PreparedStatement psInserta = con.prepareStatement(sqlInsertar);
                psInserta.setString(1, nombre);
                psInserta.setString(2, password);
                psInserta.executeUpdate();
                
                // Confirmamos la transacción en Oracle
                con.commit(); 
                System.out.println("LOG: Nuevo usuario '" + nombre + "' registrado con éxito.");
                
                // Obtenemos el ID generado por el Trigger/Secuencia
                ResultSet rs2 = psBusca.executeQuery();
                if (rs2.next()) idEncontrado = rs2.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("ERROR en Registro/Login: " + e.getMessage());
        }
        return idEncontrado;
    }

    /**
     * Muestra el récord histórico del jugador llamando a la Función (F) de PL/SQL.
     * Recibe el ID real del jugador que ha iniciado sesión.
     */
    public void mostrarMiRecord(int idUsuario) {
        try {
            Connection con = Modelo.ConexionBD.conectar();
            // Llamamos a la Función (F) que creamos en SQL
            java.sql.CallableStatement cs = con.prepareCall("{? = call FN_RECORD_JUGADOR(?)}");
            
            cs.registerOutParameter(1, java.sql.Types.INTEGER); // El récord que devuelve
            cs.setInt(2, idUsuario); // Pasamos el ID dinámico
            
            cs.execute();
            int record = cs.getInt(1);
            
            System.out.println("Tu récord actual guardado en la nube es: " + record);
            
        } catch (Exception e) {
            // Si el jugador es nuevo y no tiene partidas, saltará este mensaje informativo
            System.out.println("INFO: Aún no tienes récord en la tabla PARTIDES: " + e.getMessage());
        }
    }

    /**
     * Registra el resultado de la partida en la tabla PARTIDES de Oracle.
     * Se llama cuando el pingüino llega a la meta.
     */
    public void registrarNuevaPartida(int idJugador, int puntuacion) {
        // SQL para insertar la partida. 
        // Usamos SYSDATE para que Oracle ponga la fecha y hora actual automáticamente.
        String sql = "INSERT INTO PARTIDES (ID_JUGADOR, PUNTUACIO, DATA_PARTIDA) VALUES (?, ?, SYSDATE)";

        try (Connection con = Modelo.ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, puntuacion);
            
            ps.executeUpdate();
            System.out.println("LOG: Partida guardada con éxito para el ID: " + idJugador);
            
        } catch (SQLException e) {
            System.out.println("ERROR al guardar la partida en la base de datos: " + e.getMessage());
        }
    }


    //Ahora definimos los 'Getters'.
    
    //Como los atributos son privados, creamos estos métodos públicos para que 
    //otras partes del programa (como la Vista) puedan acceder a ellos de forma controlada.

    public controladorTablero getControladorTablero() {
        return controladorTablero; 
        //Devuelve la instancia que gestiona el tablero
    }

    public controladorJugador getControladorJugador() {
        return controladorJugador; 
        //Devuelve la instancia que gestiona al jugador
    }

    public controladorTurnos getControladorTurnos() {
        return controladorTurnos; 
        //Devuelve la instancia que gestiona los turnos
    }

    public controladorEventos getControladorEventos() {
        return controladorEventos; 
        //Devuelve la instancia que gestiona los eventos
    }
}
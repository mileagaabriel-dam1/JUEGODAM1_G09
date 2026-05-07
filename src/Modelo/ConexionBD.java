package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Necesario para enviar datos a las tablas
import java.sql.SQLException;

public class ConexionBD {
    // Datos de la conexión (IP de clase y usuario del Grupo 09)
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521/XEPDB2";
    private static final String USER = "DM1_2526_GRUP09"; 
    private static final String PASS = "AGRUP09";

    //Método para abrir la puerta a la base de datos
    public static Connection conectar() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("¡CONECTADO AL WORKSPACE DEL GRUP09!");
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }

    //MÉTODO PARA GUARDAR LA PARTIDA
    
    public static void guardarPartida(int idJugador, int puntuacion, String haGanado) {
        //Preparamos la frase de SQL. 
        //IMPORTANTE, No ponemos ID_PARTIDA porque el Trigger lo pone solo.
        String sql = "INSERT INTO PARTIDES (ID_JUGADOR, PUNTUACIO, GUANYADA) VALUES (?, ?, ?)";
        
        //Abrimos la conexión y preparamos el envío
        try (Connection con = conectar(); 
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            //Rellenamos las "interrogaciones" con los datos del juego
            pstmt.setInt(1, idJugador);    
            //El ID del pingüino que está jugando
            pstmt.setInt(2, puntuacion);   
            //Los puntos que ha sacado
            pstmt.setString(3, haGanado);  
            //'S' si ha ganado, 'N' si no
            
            //Le damos al botón de "enviar" a Oracle
            pstmt.executeUpdate();

            con.commit(); 
            // Confirmamos la transacción para que los cambios sean permanentes
            
            System.out.println("Partida guardada correctamente en Oracle.");
            
        } catch (SQLException e) {
            System.out.println("Error al guardar partida: " + e.getMessage());
        }
    }
}
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    // Usamos la IP y el servicio XEPDB2 de tu imagen de conexión
    private static final String URL = "jdbc:oracle:thin:@192.168.3.26:1521/XEPDB2";
    private static final String USER = "DM1_2526_GRUP09"; 
    private static final String PASS = "AGRUP09";

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
}
package Controladores; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 

public class controladorJuego { 
    private controladorTablero controladorTablero; 
    private controladorJugador controladorJugador; 
    private controladorTurnos controladorTurnos;   
    private controladorEventos controladorEventos; 
    private Vista.VistaJavaFX vista; 

    // 🚨 NUEVO ATRIBUTO: Guarda en memoria el ID de la fila de la tabla PARTIDES que se está jugando
    private int idPartidaActiva = -1; 

    public controladorJuego() {
        this.controladorTablero = new controladorTablero(); 
        this.controladorJugador = new controladorJugador(); 
        this.controladorTurnos = new controladorTurnos();   
        this.controladorEventos = new controladorEventos(); 

        // Conexiones maestras
        this.controladorTurnos.setControladorJuego(this);
        this.controladorEventos.setControladorJuego(this); // 🔗 ¡Nueva conexión añadida!
    }

    public int registrarOLogearUsuario(String nombre, String password) {
        int idEncontrado = -1;
        String sqlBuscar = "SELECT ID FROM JUGADORS WHERE NOM = ? AND PASSWORD = ?";
        String sqlInsertar = "INSERT INTO JUGADORS (NOM, PASSWORD) VALUES (?, ?)";

        try (Connection con = Modelo.ConexionBD.conectar()) {
            con.setAutoCommit(false);
            PreparedStatement psBusca = con.prepareStatement(sqlBuscar);
            psBusca.setString(1, nombre);
            psBusca.setString(2, password);
            ResultSet rs = psBusca.executeQuery();
            
            if (rs.next()) {
                idEncontrado = rs.getInt("ID");
                System.out.println("LOG: Login correcto. ID: " + idEncontrado);
            } else {
                String sqlExisteNom = "SELECT ID FROM JUGADORS WHERE NOM = ?";
                PreparedStatement psCheck = con.prepareStatement(sqlExisteNom);
                psCheck.setString(1, nombre);
                if (psCheck.executeQuery().next()) {
                    System.out.println("LOG: Contraseña incorrecta para el usuario: " + nombre);
                    return -1; 
                }

                PreparedStatement psInserta = con.prepareStatement(sqlInsertar);
                psInserta.setString(1, nombre);
                psInserta.setString(2, password);
                psInserta.executeUpdate();
                con.commit(); 
                System.out.println("LOG: Nuevo usuario '" + nombre + "' registrado con éxito.");
                
                PreparedStatement psBusca2 = con.prepareStatement(sqlBuscar);
                psBusca2.setString(1, nombre);
                psBusca2.setString(2, password);
                ResultSet rs2 = psBusca2.executeQuery();
                if (rs2.next()) idEncontrado = rs2.getInt("ID");
            }
        } catch (SQLException e) {
            System.out.println("ERROR en Registro/Login: " + e.getMessage());
        }
        return idEncontrado;
    }

    public void mostrarMiRecord(int idUsuario) {
        try (Connection con = Modelo.ConexionBD.conectar()) {
            java.sql.CallableStatement cs = con.prepareCall("{? = call FN_RECORD_JUGADOR(?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER); 
            cs.setInt(2, idUsuario); 
            cs.execute();
            int record = cs.getInt(1);
            System.out.println("Tu récord actual guardado en la nube es: " + record);
        } catch (Exception e) {
            System.out.println("INFO: Aún no tienes récord en la tabla PARTIDES: " + e.getMessage());
        }
    }

    public void registrarNuevaPartida(int idJugador, int puntuacion, String haGanado) {
        if (idJugador <= 0) {
            System.out.println("ERROR: No se puede guardar. ID de jugador no válido.");
            return;
        }	
        String sql = "INSERT INTO PARTIDES (ID_JUGADOR, PUNTUACIO, GUANYADA) VALUES (?, ?, ?)";
        try (Connection con = Modelo.ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idJugador);
            ps.setInt(2, puntuacion);
            ps.setString(3, haGanado); 
            ps.executeUpdate();
            System.out.println("LOG: Partida guardada con éxito en Oracle para el ID: " + idJugador);
        } catch (SQLException e) {
            System.out.println("ERROR al guardar la partida en la base de datos: " + e.getMessage());
        }
    }

    public void mostrarVictoriasTotales(int idUsuario) {
        try (Connection con = Modelo.ConexionBD.conectar()) {
            java.sql.CallableStatement cs = con.prepareCall("{? = call FN_PARTIDES_GUANYADES(?)}");
            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            cs.setInt(2, idUsuario);
            cs.execute();
            System.out.println("Total de partidas ganadas: " + cs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error al obtener victorias: " + e.getMessage());
        }
    }

    public void imprimirRankingViciados() {
        try (Connection con = Modelo.ConexionBD.conectar()) {
            java.sql.CallableStatement cs = con.prepareCall("{call SP_RANKING_PARTIDAS()}");
            cs.execute();
        } catch (SQLException e) {
            System.out.println("Error al ejecutar ranking: " + e.getMessage());
        }
    }

    public void comprobarVictoria(Modelo.Jugador jugador) {
        if (jugador.getPosicion() >= 49) {
            int puntosFinales = 100; 
            
            // Si gana la partida de forma legal, registramos la victoria como 'S'
            registrarNuevaPartida(jugador.getId(), puntosFinales, "S");
            
            // Opcional: Si existía un registro temporal 'N' de esta partida, podríamos dejarlo o cambiarlo, 
            // pero al registrar una nueva como 'S' ya computa para sus estadísticas/récord.
            resetearPartidaActiva();

            JOptionPane.showMessageDialog(null, 
                "¡ENHORABUENA " + jugador.getNombre().toUpperCase() + "!\nHas llegado a la meta y tu victoria se ha guardado en la nube.", 
                "Fin de la Partida - Penguin Race", 
                JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    // =========================================================================
    // 💾 SISTEMA DE GUARDADO INTELIGENTE (Sobreescribe o Inserta según corresponda)
    // =========================================================================
    public boolean guardarPartidaActual(int idUsuarioLogueado, int posicionActual) {
        if (idUsuarioLogueado <= 0) {
            System.out.println("ERROR: ID de usuario no válido para guardar.");
            return false;
        }

        // Si idPartidaActiva > 0 significa que ya recuperamos o creamos esta partida previamente en esta sesión
        if (this.idPartidaActiva > 0) {
            String sqlUpdate = "UPDATE PARTIDES SET PUNTUACIO = ? WHERE ID_PARTIDA = ?";
            try (Connection con = Modelo.ConexionBD.conectar()) {
                PreparedStatement ps = con.prepareStatement(sqlUpdate);
                ps.setInt(1, posicionActual);
                ps.setInt(2, this.idPartidaActiva);
                
                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas > 0) {
                    System.out.println("LOG: Partida existente (ID_PARTIDA: " + this.idPartidaActiva + ") ACTUALIZADA con éxito a la casilla " + posicionActual);
                    return true;
                }
            } catch (SQLException e) {
                System.out.println("ERROR al actualizar la partida en la BD: " + e.getMessage());
            }
        } 

        // Si no existía una partida activa previa, realizamos un INSERT nuevo
        String sqlInsert = "INSERT INTO PARTIDES (ID_JUGADOR, PUNTUACIO, GUANYADA) VALUES (?, ?, 'N')";
        String[] columnasId = {"ID_PARTIDA"}; // Le pedimos a Oracle que nos devuelva el ID autogenerado

        try (Connection con = Modelo.ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(sqlInsert, columnasId);
            ps.setInt(1, idUsuarioLogueado);
            ps.setInt(2, posicionActual);
            
            ps.executeUpdate();
            
            // Recogemos el ID asignado por Oracle para recordarlo en los siguientes clicks de "Guardar"
            ResultSet rsKeys = ps.getGeneratedKeys();
            if (rsKeys.next()) {
                this.idPartidaActiva = rsKeys.getInt(1);
                System.out.println("LOG: Nueva partida registrada en la BD. ID_PARTIDA asignado: " + this.idPartidaActiva + " (Casilla: " + posicionActual + ")");
            }
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR al guardar nuevo estado de la partida: " + e.getMessage());
        }
        return false;
    }

    // =========================================================================
    // 📂 SISTEMA DE CARGA DE PARTIDA (Recupera la posición y el ID_PARTIDA)
    // =========================================================================
    public int cargarPartidaGuardada(int idUsuarioLogueado) {
        // Seleccionamos tanto la columna ID_PARTIDA como la PUNTUACIO del último guardado 'N'
        String sql = "SELECT ID_PARTIDA, PUNTUACIO FROM PARTIDES WHERE ID_JUGADOR = ? AND GUANYADA = 'N' ORDER BY ID_PARTIDA DESC";
        int posicionRestaurada = -1;

        try (Connection con = Modelo.ConexionBD.conectar()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuarioLogueado);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                posicionRestaurada = rs.getInt("PUNTUACIO"); // Casilla devuelta
                
                // 🚨 ¡CLAVE!: Guardamos el identificador exacto de la fila para poder actualizarla luego
                this.idPartidaActiva = rs.getInt("ID_PARTIDA"); 
                
                System.out.println("LOG: Guardado encontrado ('N'). ID_PARTIDA en curso asignado en memoria: " + this.idPartidaActiva + ". Posición: " + posicionRestaurada);
            } else {
                System.out.println("LOG: No hay ninguna partida guardada a medias para este usuario.");
                this.idPartidaActiva = -1;
            }
        } catch (SQLException e) {
            System.out.println("ERROR al cargar partida guardada de la BD: " + e.getMessage());
        }
        return posicionRestaurada; 
    }

    // 🚨 NUEVO MÉTODO: Permite desvincular la sesión de la partida guardada si se pulsa "Nueva Partida"
    public void resetearPartidaActiva() {
        this.idPartidaActiva = -1;
        System.out.println("LOG: Memoria de partida activa reseteada (-1). Próximo guardado generará una nueva fila.");
    }

    public void setVista(Vista.VistaJavaFX vista) { this.vista = vista; }
    public Vista.VistaJavaFX getVista() { return this.vista; }
    public controladorTablero getControladorTablero() { return controladorTablero; }
    public controladorJugador getControladorJugador() { return controladorJugador; }
    public controladorTurnos getControladorTurnos() { return controladorTurnos; }
    public controladorEventos getControladorEventos() { return controladorEventos; }
}
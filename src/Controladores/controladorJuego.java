package Controladores; 
//Definimos el paquete donde se encuentra este controlador principal

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane; 
//Importamos la librería para mostrar ventanas emergentes (pantalla de victoria)

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
        
        //Usamos 'this' para diferenciar los atributos de la clase y asignarles una nueva instancia (new)
        this.controladorTablero = new controladorTablero(); 
        //Instanciamos el controlador del tablero
        
        this.controladorJugador = new controladorJugador(); 
        //Instanciamos el controlador del jugador
        
        this.controladorTurnos = new controladorTurnos();   
        //Instanciamos el controlador de turnos
        
        this.controladorEventos = new controladorEventos(); 
        //Instanciamos el controlador de eventos

        //CONEXIÓN DE CONTROLADORES
        //Vinculamos el gestor de turnos con este controlador principal
        this.controladorTurnos.setControladorJuego(this);
        //Controlador Maestro.
        //Esto es necesario porque, si el controladorTurnos detecta algo, el solo sabe de turnos, no sabe ni guardar, ni nada, por eso se conecta.
    }

    //MÉTODOS DE BASE DE DATOS PARA EL PROYECTO
    //Estos métodos conectan el corazón del juego con Oracle SQL

    //Busca un usuario por nombre y contraseña. Si no existe, lo registra (INSERT).
    //Devuelve el ID para poder usarlo en las partidas.
    
    public int registrarOLogearUsuario(String nombre, String password) {
    	//Public
        int idEncontrado = -1;
        //Si devuelve -1, algo ha fallado
        //Devuelve numero entero
        //Ajustado: Buscamos por nombre y contraseña para validar el acceso
        String sqlBuscar = "SELECT ID FROM JUGADORS WHERE NOM = ? AND PASSWORD = ?";
        //Esto busca si eL USUARIO y CONTRASEÑA, ya existe en la BD.
        String sqlInsertar = "INSERT INTO JUGADORS (NOM, PASSWORD) VALUES (?, ?)";
        //Si no existe, se prepara para insertar en la BD
        
        //"PlaceHolders", basicamente, si alguien intenta meter algo "malo", dentro de "usuario", no pasaria nada
        //Ya que los '?' lo detectarian como un simple texto sin mas, es decir, que no habria ninguna amenaza

        try (Connection con = Modelo.ConexionBD.conectar()) {
        	//El try con parentesis, es algo nuevo, pero calma
        	//Es muy simple
        	//Imagina que entramos, se conecta, etc... Pues al cerrar la ventada del juego, "el bloque", lo que hace este try con parentesis
        	//Es cerrar AUTOMÁTICAMENTE la conexión, incluso si hay un error
        	
            con.setAutoCommit(false);
            //Java por defecto guarda AUTOMATICAMENTE los commits en SQL, no queremos eso.
            //Desactivamos el autoCommit para gestionar el guardado manualmente

            //Intentamos buscar si el usuario ya existe con esa contraseña
            PreparedStatement psBusca = con.prepareStatement(sqlBuscar);
            //Se usa PreparedStatement para evitar la "inyección" en SQL, para que no se meta codigo malicioso, en pocas palabras,
            psBusca.setString(1, nombre);
            //Aqui se rellenan los '?' de antes,"placeholders"
            //el numero 1 corresponde al primer '?', y el segundo, pues al segundo '?'
            psBusca.setString(2, password);
            //Importante, se pone 1 y 2, porque el JDBC, que es la tecnologia para conectar JAVA y ORACLE, esta diseñada asi por defecto.
            //Si ponemos un 0, en alguna de esos dos '?', dara error, porque ORACLE no comienza por 0, sino por 1.
            ResultSet rs = psBusca.executeQuery();
            //ResultSet = Conjunto de resultados
            //Es una "tabla temporal" que crea JAVA para guardar lo que ORACLE ha devuelto
            
            //La posible pregunta(100% lo preguntan, miraros esto)
            
            //ExecuteQuery se usa SOLO PARA CONSULTAS(SELECT), que devuelve un objeto ResultSet con lo que se ha encontrado.
            
            //La otra es executeUpdate, pero esto es para modificar datos, ya se tendria que saber esto, pero por si acaso.
            
            if (rs.next()) {
                //Si existe, recuperamos su ID
            	//Osea, si ORACLE encuentra una fila con ese USUARIO e ID, recuperamos esos datos
                idEncontrado = rs.getInt("ID");
                //Sacamos el ID que se ha encontrado, y lo metemos dentro de nuestra variable de JAVA
                
                System.out.println("LOG: Login correcto. ID: " + idEncontrado);
                //Se imprime por pantalla que saltara si ese ID ya existe, mostrando el ID por pantalla.
                
            } else {
                //Comprobamos si el nombre existe pero la contraseña está mal
                String sqlExisteNom = "SELECT ID FROM JUGADORS WHERE NOM = ?";
                PreparedStatement psCheck = con.prepareStatement(sqlExisteNom);
                //psCheck, el PreparedStatement de antes, hay que "chequearlo",
                psCheck.setString(1, nombre);
                if (psCheck.executeQuery().next()) {
                    System.out.println("LOG: Contraseña incorrecta para el usuario: " + nombre);
                    return -1; 
                    //Error de credenciales
                    //Este codigo solo se ejecuta si no encontramos al usuario con la contraseña correcta.
                    //Si este codigo se ejecuta, tanto JAVA y PL/SQL saben que si falla, es porque la contraseña del usuario esta mal.
                    
                    //Ya que se lo estamos diciendo
                }

                //Si no existe el nombre, lo registramos como nuevo usuario
                PreparedStatement psInserta = con.prepareStatement(sqlInsertar);
                psInserta.setString(1, nombre);
                psInserta.setString(2, password);
                psInserta.executeUpdate();
                //ExecuteUpdate para actualizar los datos en ORACLE
                
                //Confirmamos la transacción en Oracle
                con.commit(); 
                //Es decir, insertamos los datos en ORACLE.
                System.out.println("LOG: Nuevo usuario '" + nombre + "' registrado con éxito.");
                
                //Obtenemos el ID generado por el Trigger/Secuencia
                psBusca.setString(1, nombre);
                psBusca.setString(2, password);
                //El ID que se ha creado, al insertarlo
                //Se obtiene mas que nada porque ORACLE ya lo crea y todo, pero JAVA no sabe que ID le ha tocado a ese usuario, asi que hay que decirselo
                ResultSet rs2 = psBusca.executeQuery();
                //Creamos otra lista de resultados, y una consulta (executeQuery)
                
                if (rs2.next()) idEncontrado = rs2.getInt("ID");
                //Cando ya tengamos el ID que ORACLE ha creado, lo ponemos en la variable de JAVA
            }
        } catch (SQLException e) {
            System.out.println("ERROR en Registro/Login: " + e.getMessage());
            //Si algo falla, por ejemplo, si ORACLE esta apagado, no funciona, no hay red...
            //Se imprime por consola el mensaje que se ve en verde, lo que viene detras, es para obtener el error que ha pasado.
            //Y mostrarlo por pantalla, para saber que ha pasado, sin eso, no sale que error es, solo sale el texto en verde
        }
        return idEncontrado;
        //Si todo ha ido bien, devolvera el ID positivo que se ha creado.
        //Si algo falla, devolvera -1, que es "error de credenciales"
    }

    //Muestra el récord histórico del jugador llamando a la Función (F) de PL/SQL.
    //Recibe el ID real del jugador que ha iniciado sesión.

    public void mostrarMiRecord(int idUsuario) {
        try (Connection con = Modelo.ConexionBD.conectar()) {
            //Llamamos a la Función (F) que creamos en SQL
            java.sql.CallableStatement cs = con.prepareCall("{? = call FN_RECORD_JUGADOR(?)}");
            
            //CallableStatement, que no panda el cúnico, es simple.
            //Esto lo que hace es llamar a "Procedimientos" y "Funciones" que ya estan guardados en ORACLE
            
            //el primer '?', es el que nos va a devolver el record de ese jugador 
            //el segundo '?', es el parámetro de entrada, el ID del jugador correspondiente, para saber de quien es el record que tiene que buscar
            
            cs.registerOutParameter(1, java.sql.Types.INTEGER); 
            //El récord que devuelve
            //este es para el primer '?', esto esta para decirle a JAVA que el primer interrogante no es para enviar datos, sino para recibirlos.
            //java.sql.Types.INTEGER); Esto es una clase de JAVA que contiene etiquetas(Constantes)
            //E INTEGER es una de ellas
            //Lo que le decimos con esto a JAVA es, que lo que le va a enviar ORACLE tiene formato de numero entero de base de datos
            
            cs.setInt(2, idUsuario); 
            //Pasamos el ID dinámico
            //En este caso, el segundo '?', estamos enviando datos, y los rellenamos con 'idUsuario'
            //Esto esta para que la busqueda sea para ese ID, y no para todos los usuarios
            
            
            cs.execute();
            int record = cs.getInt(1);
            //Se ejecuta el callableStatement de antes, llamando a la función, se lleva el valor y vuelve con el
            
            System.out.println("Tu récord actual guardado en la nube es: " + record);
            //SYSOUT para imprimir en pantalla el record de tu partida
            
        } catch (Exception e) {
            //Si el jugador es nuevo y no tiene partidas, saltará este mensaje informativo
            System.out.println("INFO: Aún no tienes récord en la tabla PARTIDES: " + e.getMessage());
        }
    }

    //Registra el resultado de la partida en la tabla PARTIDES de Oracle.
    //Se llama cuando el pingüino llega a la meta.
    
    public void registrarNuevaPartida(int idJugador, int puntuacion, String haGanado) {
        //Validación de seguridad para DAM: No intentamos insertar si el ID no es válido
        if (idJugador <= 0) {
        	//Antes de cualquier cosa con ORACLE, mira si el ID es correcto, si no se ha logueado bien, etc
            System.out.println("ERROR: No se puede guardar. ID de jugador no válido.");
            return;
        }	

        //SQL para insertar la partida. 
        //No incluimos ID_PARTIDA porque el Trigger lo pone solo usando la secuencia.
        String sql = "INSERT INTO PARTIDES (ID_JUGADOR, PUNTUACIO, GUANYADA) VALUES (?, ?, ?)";
        //Aqui insertamos las cosas en 'PARTIDES' dentro de la BD, pero aun no se guardan los valores
        //Ponemos 3 placeholders, uno para cada valor
        //Aqui falta el valor de "GUANYADA", pero ese ya lo creo directamente ORACLE. (S o N)

        try (Connection con = Modelo.ConexionBD.conectar()) {
        	//Se conecta con la BD
            PreparedStatement ps = con.prepareStatement(sql);
            //Le decimos que prepare con una sentencia
            ps.setInt(1, idJugador);
            //Con lo de arriba, le decimos que con la linea de SQL, de arriba, el primer '?' tiene que ser el idJugador
            ps.setInt(2, puntuacion);
            //Lo mismo, pero el segundo '?', tiene que ser la puntuación del jugador en esa partida.
            ps.setString(3, haGanado); 
            //Recibe 'S' o 'N'
            //S si la partida esta ganada, N si no esta ganada, esto para el tercer '?'
            
            ps.executeUpdate();
            System.out.println("LOG: Partida guardada con éxito en Oracle para el ID: " + idJugador);
            //executeUpdate para actualizar los datos en la tabla
            
        } catch (SQLException e) {
            System.out.println("ERROR al guardar la partida en la base de datos: " + e.getMessage());
        }//En caso de cualquier error, (lo que sea), que imprima por pantalla el mensaje, y el error
    }

    //Obtiene el número de victorias llamando a la función obligatoria del mínimo.

    public void mostrarVictoriasTotales(int idUsuario) {
        try (Connection con = Modelo.ConexionBD.conectar()) {
        	//Se conecta con la BD
            java.sql.CallableStatement cs = con.prepareCall("{? = call FN_PARTIDES_GUANYADES(?)}");
            //Lo mismo, llamamos a la funcion que se ha creado en SQL con esta sentencia
            //El primer placeholder, llamamos a la función por su nombre, y el segundo placeholder
            

            cs.registerOutParameter(1, java.sql.Types.INTEGER);
            
            //Esto es lo mismo que arriba, preparamos a SQL, para que cuando ORACLE le envie el valor del primer '?'
            //Haya integridad de datos, y no haya ningun error de JAVA diciendo "No me has dicho si era un numero, texto..."
            
            cs.setInt(2, idUsuario);
            //El segundo '?' para el idUsuario, que ya mandamos nosotros hacia ORACLE, para saber de quien tiene que buscar
            
            cs.execute();
            //Ejecutamos el statement
            
            System.out.println("Total de partidas ganadas: " + cs.getInt(1));
        } catch (SQLException e) {
            System.out.println("Error al obtener victorias: " + e.getMessage());
        }
    }//En caso de error... lo de siempre vamos

    //Ejecuta el procedimiento de ranking de viciados (P) del informe.
    
    public void imprimirRankingViciados() {
        try (Connection con = Modelo.ConexionBD.conectar()) {
            java.sql.CallableStatement cs = con.prepareCall("{call SP_RANKING_PARTIDAS()}");
            cs.execute();
            //Esto ejecutará la lógica en el servidor Oracle.
        } catch (SQLException e) {
            System.out.println("Error al ejecutar ranking: " + e.getMessage());
        }
    }

 //Verifica si el jugador ha llegado a la casilla 50 (índice 49).
    public void comprobarVictoria(Modelo.Jugador jugador) {
        
        //Comparamos si la posición del objeto jugador es 49 o más (la meta)
        if (jugador.getPosicion() >= 49) {
            
            //Declaramos una variable entera para la puntuación de victoria
            int puntosFinales = 100; 
            
            //Llamamos al método que conecta con Oracle para guardar los datos
            //Pasamos el ID del jugador, los puntos y "S" de que SÍ ha ganado
            registrarNuevaPartida(jugador.getId(), puntosFinales, "S");
            
            //Creamos una ventana emergente (Pop-up) para avisar al usuario
            JOptionPane.showMessageDialog(null, 
                //Personalizamos el mensaje pasando el nombre del jugador a mayúsculas
                "¡ENHORABUENA " + jugador.getNombre().toUpperCase() + "!\nHas llegado a la meta y tu victoria se ha guardado en la nube.", 
                //Título de la ventanita
                "Fin de la Partida - Penguin Race", 
                //Icono de información azul predefinido en Java Swing
                JOptionPane.INFORMATION_MESSAGE);
            
            //Cerramos el programa por completo con el código 0 (éxito)
            System.exit(0);
        }
    }

    //SECCIÓN DE GETTERS
    
    //Método público para obtener el controlador del tablero
    public controladorTablero getControladorTablero() {
        //Retorna la instancia privada que contiene la lógica de las casillas
        return controladorTablero; 
    }

    //Método público para obtener el controlador del jugador
    public controladorJugador getControladorJugador() {
        //Retorna la instancia privada que gestiona los movimientos del pingüino
        return controladorJugador; 
    }

    //Método público para obtener el controlador de turnos
    public controladorTurnos getControladorTurnos() {
        //Retorna la instancia que decide a qué jugador le toca tirar el dado
        return controladorTurnos; 
    }

    //Método público para obtener el controlador de eventos
    public controladorEventos getControladorEventos() {
        //Retorna la instancia que gestiona las trampas y premios del tablero
        return controladorEventos; 
    }
}
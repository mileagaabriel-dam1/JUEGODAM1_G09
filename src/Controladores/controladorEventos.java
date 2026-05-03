package Controladores; // Indicamos que esta clase vive en el paquete de Controladores

import Modelo.*; 
//Importamos todas las clases del modelo (Jugador, Casilla, etc.) para poder usarlas

public class controladorEventos { 
	//Definición de la clase que gestionará la lógica de las casillas

    public String procesarCasilla(Jugador jugador, Casilla casilla) {
    //Este método gestiona qué ocurre cuando un jugador cae en una casilla concreta


        Entidad entidad = null; 
        //Declaramos una variable de tipo Entidad (clase padre) para aplicar polimorfismo
        
        String mensaje = ""; 
        //Variable auxiliar para guardar el texto que devolveremos a la interfaz

        switch(casilla.getTipo()) {
        //Usamos un switch para ejecutar una lógica distinta según el tipo de casilla (Enum)

            case PINGUINO:
                entidad = new Pinguino(); 
                //Si es pingüino, instanciamos un objeto de su clase
                break;

            case OSO:
                entidad = new Oso(); 
                //Si es oso, creamos la instancia del enemigo
                break;

            case AGUJERO:
                entidad = new AgujeroHielo(); 
                //Obstáculo de tipo agujero
                break;

            case TRINEO:
                entidad = new Trineo(); 
                //Objeto de transporte o ventaja
                break;

            case INTERROGANTE:
                //Si la casilla es de evento aleatorio, llamamos al método interno y devolvemos su resultado
                return eventoAleatorio(jugador);
        }

        if (entidad != null) {
            //Si la casilla no estaba vacía (entidad no es null), ejecutamos la interacción

            mensaje = entidad.interactuar(jugador);
            //Gracias al polimorfismo, se ejecuta el método interactuar de la clase hija específica

        }

        return mensaje; 
        //Devolvemos el mensaje final para mostrarlo en el log del juego
    }

    private String eventoAleatorio(Jugador jugador) {
        //Método privado para gestionar la lógica de azar cuando se cae en una casilla '?'

    	
        int evento = (int)(Math.random() * 5);
        //Generamos un número entero aleatorio entre 0 y 4 usando casting a (int)

        String mensaje = "  ❓ "; 
        //Icono visual para el mensaje de evento

        switch(evento) {
        //Evaluamos el número obtenido para decidir qué premio o castigo recibe el jugador

            case 0:
                mensaje += "¡Evento: Encuentras un pez!";
                jugador.getInventario().agregarPez(); 
                //Accedemos al inventario del jugador para sumar un objeto
                break;

            case 1:
                mensaje += "¡Evento: Encuentras bolas de nieve!";
                jugador.getInventario().agregarBolaNieve(); 
                //Aumentamos la "munición" del jugador
                break;

            case 2:
                mensaje += "¡Evento: Ganas un dado extra!";
                jugador.getInventario().agregarDado(); 
                //Modificamos la cantidad de dados disponibles
                break;

            case 3:
                //Verificamos que el jugador tenga más de un dado antes de quitarle uno (regla de negocio)
                if (jugador.getInventario().getDados() > 1) {
                    mensaje += "¡Evento: Pierdes un dado!";
                    jugador.getInventario().quitarDado();
                } else {
                    //Si solo tiene uno, no se le quita para que pueda seguir jugando
                    mensaje += "¡Evento: Casi pierdes un dado!";
                }
                break;

            case 4:
                //Evento especial donde aparece una foca dinámicamente
                Foca foca = new Foca();
                mensaje = foca.interactuar(jugador); 
                //La foca interactúa directamente con el jugador
                break;
        }

        return mensaje; 
        //Retornamos el resultado del evento aleatorio
    }
}
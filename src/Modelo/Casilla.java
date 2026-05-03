package Modelo; 
//Definimos que esta clase forma parte de la capa de datos (Modelo)

public class Casilla { 
	//Clase que representa cada una de las posiciones del tablero

    //Atributos privados para cumplir con el encapsulamiento
    private int posicion;      
    //El número de orden de la casilla (0, 1, 2...)
    
    private TipoCasilla tipo;  
    //El tipo de casilla (usando el Enum TipoCasilla)
    
    private Entidad entidad;   
    //El objeto (Oso, Pingüino, etc.) que esta en esta casilla

    //Constructor, se usa al generar el tablero para configurar cada casilla
    public Casilla(int posicion, TipoCasilla tipo) {
        this.posicion = posicion; 
        //Asignamos la ubicación física en el mapa
        
        this.tipo = tipo;         
        //Que tipo de casilla es??, lo que salga se le asignara a esa casilla.

        //Usamos un switch para instanciar la entidad correspondiente según el tipo recibido
        switch (tipo) {
            case PINGUINO:
                entidad = new Pinguino(); 
                //Si el tipo es PINGUINO, creamos el objeto Pinguino 
                break;
                
            case OSO:
                entidad = new Oso();      
                //Si es OSO, metemos un "enemigo" en la casilla
                break;
                
            case AGUJERO:
                entidad = new AgujeroHielo(); 
                //Añadimos un obstáculo de caída
                break;
                
            case TRINEO:
                entidad = new Trineo();   
                //Añadimos un objeto de ayuda
                break;
                
            case INTERROGANTE:
                //Las casillas de evento aleatorio no tienen una entidad fija al inicio,
                //por eso dejamos la variable en null.
                entidad = null;
                break;
        }
    }

    //Método para obtener la el simbolo visual de la casilla
    public String getSimbolo() {
        if (entidad != null) {
            //Si la casilla tiene una entidad, le pedimos a ella su propio icono (polimorfismo)
            return entidad.getSimbolo();
        } else {
            // Si está vacía o es un evento (null), devolvemos el símbolo de la duda
            return "❓";
        }
    }

    // Método para describir el efecto de la casilla al jugador
    public String getDescripcion() {
        if (entidad != null) {
            //Si hay entidad, ella nos dice qué hace (ej: "Retrocedes 3 casillas")
            return entidad.getDescripcion();
        } else {
            //Texto por defecto para las casillas de tipo interrogante
            return "Casilla misteriosa";
        }
    }

    //Getters para permitir que los controladores consulten el estado de la casilla
    
    public TipoCasilla getTipo() {
        return tipo; 
        //Devuelve si es OSO, AGUJERO, etc.
    }

    public int getPosicion() {
        return posicion; 
        //Devuelve el número de la casilla
    }

    public Entidad getEntidad() {
        return entidad; 
        //Devuelve el objeto contenido (para interactuar con él)
    }
} 
//Fin de la clase Casilla
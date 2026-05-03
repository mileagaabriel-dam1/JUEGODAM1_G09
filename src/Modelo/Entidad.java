package Modelo; 
//Definimos que esta interfaz forma parte de la capa del Modelo

//Esta es la clase jefe, de los modelos, "Randy"
//Y el tiene sus propias condiciones.

//Aqui se obliga a otras clases a programar una lógica, ya establecida, con las condiciones de Randy.

//(como Oso, Agujero o Trineo) a que implementen estos métodos obligatoriamente.

public interface Entidad {
    //Entidad, (o Randy, es una clase la cual tiene su própio "molde", por asi decirlo)
	
	
    //Método que obliga a cada entidad a devolver su nombre identificativo
    String getNombre();
    
    //Método que obliga a cada entidad a tener una representación visual (emoji o texto)
    String getSimbolo();
    
    //Método que obliga a cada entidad a proporcionar una breve explicación de su efecto
    String getDescripcion();

    String interactuar(Jugador jugador);
    //Bueno y esto define que efecto tiene la entidad sobre el jugador, al pasarle el objeto jugador, esa entidad tiene permiso a cambiar los datos
    //de jugador, si es un oso, pues mandarlo de vuelta al inicio, etc.
} 
//Fin de la interfaz Entidad
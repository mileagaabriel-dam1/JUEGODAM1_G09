public void iniciarPartida(int h, boolean ia) {

    // 1. Inicializar jugadores (OBJETO)
    controladorJugador.inicializarJugadores(h, ia);

    // 2. Pasar jugadores a turnos
    controladorTurnos.setJugadores(controladorJugador.getJugadores());

    // 3. Crear tablero visual
    vistaTablero.inicializarTablero(controladorTablero);

    // 4. Pintar jugadores en tablero
    vistaTablero.actualizarPosiciones(controladorJugador);

    // 5. Actualizar panel jugador
    vistaJugador.actualizar(controladorTurnos);

    // 6. Activar juego
    vistaJuego.mostrar();
}
package controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Alfil;
import model.Caballo;
import model.Peon;
import model.Pz;
import model.Reina;
import model.Rey;
import model.Torre;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainController 
{

    // grid del fxml (tablero) //
    @FXML
    private GridPane gridTablero;

    // label que muestra de quien es el turno //
    @FXML
    private Label lblTurno;

    // label que muestra el estado actual de la partida //
    @FXML
    private Label lblEstado;

    // matriz que representa el tablero logico (8x8) // cada casilla puede contener una pieza o null si esta vacia
    private Pz[][] tablero = new Pz[8][8];

    // variable para controlar el turno actual // true = blancas, false = negras
    private boolean turnoBlancas = true;

    // guarda la pieza seleccionada //
    private int filaSeleccionada = -1;
    private int colSeleccionada = -1;

    // lista de movimientos posibles de la pieza seleccionada //
    private List<int[]> movimientosPosibles = new ArrayList<>();

    // reproductor de musica del juego //
    private MediaPlayer reproductorJuego;

    // se ejecuta cuando carga el fxml //
    @FXML
    public void initialize() 
    {
        inicializarTablero();
        actualizarTurno();
        actualizarEstado("En juego");
        dibujarTablero();
        reproducirMusicaJuego();
    }

    // coloca las piezas en su posicion inicial //
    private void inicializarTablero() 
    {

        tablero = new Pz[][]{
            {
                new Torre(false), new Caballo(false), new Alfil(false), new Reina(false),
                new Rey(false), new Alfil(false), new Caballo(false), new Torre(false)
            },

            {
                new Peon(false), new Peon(false), new Peon(false), new Peon(false),
                new Peon(false), new Peon(false), new Peon(false), new Peon(false)
            },

            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},

            {
                new Peon(true), new Peon(true), new Peon(true), new Peon(true),
                new Peon(true), new Peon(true), new Peon(true), new Peon(true)
            },

            {
                new Torre(true), new Caballo(true), new Alfil(true), new Reina(true),
                new Rey(true), new Alfil(true), new Caballo(true), new Torre(true)
            }
        };
    }

    // dibuja todo el tablero en pantalla //
    private void dibujarTablero() 
    {

        gridTablero.getChildren().clear();

        for (int fila = 0; fila < 8; fila++) 
        {
            for (int col = 0; col < 8; col++) 
            {

                Pz pieza = tablero[fila][col];

                // cada casilla //
                StackPane celda = new StackPane();

                // tamano visual de cada casilla //
                celda.setPrefSize(63, 59);

                // resalta la casilla seleccionada //
                if (fila == filaSeleccionada && col == colSeleccionada)
                {
                    celda.setStyle("-fx-background-color: rgba(255, 215, 0, 0.35); -fx-border-color: gold; -fx-border-width: 3;");
                }

                // resalta los movimientos posibles //
                else if (esMovimientoPosible(fila, col))
                {
                    celda.setStyle("-fx-background-color: rgba(0, 255, 0, 0.30); -fx-border-color: limegreen; -fx-border-width: 2;");
                }

                if (pieza != null) 
                {

                    // crea la imagen de la pieza // (obtenerRutaImagen devuelve la ruta segun la pieza)
                    ImageView img = new ImageView(
                        new Image(getClass().getResourceAsStream(pieza.obtenerRutaImagen()))
                    );

                    // tamano de la imagen de las piezas //
                    img.setFitWidth(50);
                    img.setFitHeight(50);
                    img.setPreserveRatio(true);

                    celda.getChildren().add(img);
                }

                // guardar posicion para el click // (necesario porque fila y col cambian en cada iteracion)
                final int f = fila;
                final int c = col;

                celda.setOnMouseClicked(e -> {
                    manejarClick(f, c);
                });

                // agregar al grid // (columna, fila)
                gridTablero.add(celda, col, fila);
            }
        }
    }

    // maneja el click sobre una casilla del tablero //
    private void manejarClick(int fila, int col)
    {
        Pz piezaActual = tablero[fila][col];

        // si no hay seleccion previa //
        if (filaSeleccionada == -1 && colSeleccionada == -1)
        {
            // validacion para solo permitir seleccionar piezas del turno actual //
            if (piezaActual != null && piezaActual.esBlanca() == turnoBlancas)
            {
                seleccionarPieza(fila, col);
            }

            return;
        }

        // validacion para saber si se hizo click en un movimiento valido //
        if (esMovimientoPosible(fila, col))
        {
            moverPieza(fila, col);
            return;
        }

        // validacion para saber si se hizo click en otra pieza del mismo color, cambiara la seleccion actual //
        if (piezaActual != null && piezaActual.esBlanca() == turnoBlancas)
        {
            seleccionarPieza(fila, col);
            return;
        }

        // si no fue una opcion valida, se limpia la seleccion //
        limpiarSeleccion();
        dibujarTablero();
    }

    // validacion para que  al seleccionar una pieza se calculen sus movimientos validos //
    private void seleccionarPieza(int fila, int col)
    {
        filaSeleccionada = fila;
        colSeleccionada = col;

        Pz pieza = tablero[fila][col];

        if (pieza != null)
        {
            movimientosPosibles = pieza.obtenerMovimientosValidos(tablero, fila, col);
        }
        else
        {
            movimientosPosibles = new ArrayList<>();
        }

        dibujarTablero();
    }

    // movimiento de una pieza a una nueva casilla //
    private void moverPieza(int filaDestino, int colDestino)
    {
        tablero[filaDestino][colDestino] = tablero[filaSeleccionada][colSeleccionada];
        tablero[filaSeleccionada][colSeleccionada] = null;

        limpiarSeleccion();
        cambiarTurno();

        // si el jugador actual queda en jaque, se muestra en pantalla //
        if (hayJaque(turnoBlancas))
        {
            actualizarEstado("Jaque");
        }
        else
        {
            actualizarEstado("En juego");
        }

        dibujarTablero();
    }

    // limpia la seleccion actual y los movimientos marcados //
    private void limpiarSeleccion()
    {
        filaSeleccionada = -1;
        colSeleccionada = -1;
        movimientosPosibles.clear();
    }

    // verifica si una casilla esta dentro de los movimientos posibles //
    private boolean esMovimientoPosible(int fila, int col)
    {
        for (int[] mov : movimientosPosibles)
        {
            if (mov[0] == fila && mov[1] == col)
            {
                return true;
            }
        }

        return false;
    }

    // actualiza el texto del turno segun el jugador actual //
    private void actualizarTurno()
    {
        if (turnoBlancas) 
        {
            lblTurno.setText("Turno: Blancas");
        } 
        else 
        {
            lblTurno.setText("Turno: Negras");
        }
    }

    // actualiza el estado visible de la partida //
    private void actualizarEstado(String estado)
    {
        lblEstado.setText("Estado: " + estado);
    }

    // cambia el turno actual //
    private void cambiarTurno()
    {
        turnoBlancas = !turnoBlancas;
        actualizarTurno();
    }

    // reinicia la partida completa //
    @FXML
    private void reiniciarPartida(ActionEvent evento)
    {
        // vuelve a colocar todas las piezas en su posicion inicial //
        inicializarTablero();

        // reinicia el turno a blancas //
        turnoBlancas = true;

        // limpia cualquier seleccion previa //
        limpiarSeleccion();

        // actualiza los textos de la interfaz //
        actualizarTurno();
        actualizarEstado("En juego");

        dibujarTablero();
    }

    // metodo para reproducir musica del juego //
    private void reproducirMusicaJuego()
    {
        try
        {
            String ruta = getClass().getResource("/resources/Sound/juego.mp3").toExternalForm();

            Media musica = new Media(ruta);
            reproductorJuego = new MediaPlayer(musica);

            // volumen //
            reproductorJuego.setVolume(0.1);

            // Modo Gojo(Infinito) //
            reproductorJuego.setCycleCount(MediaPlayer.INDEFINITE);

            reproductorJuego.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Logica del jaque (se verifica si el rey del color actual esta siendo atacado por EE.UU) //

    // verifica si el rey del color indicado esta en jaque //
    private boolean hayJaque(boolean esBlanca)
    {
        int filaRey = -1;
        int colRey = -1;

        // buscar el rey //
        for (int fila = 0; fila < 8; fila++)
        {
            for (int col = 0; col < 8; col++)
            {
                Pz pieza = tablero[fila][col];

                if (pieza instanceof Rey && pieza.esBlanca() == esBlanca)
                {
                    filaRey = fila;
                    colRey = col;
                }
            }
        }

        // revisision de si alguna pieza enemiga puede atacar al rey //
        for (int fila = 0; fila < 8; fila++)
        {
            for (int col = 0; col < 8; col++)
            {
                Pz pieza = tablero[fila][col];

                if (pieza != null && pieza.esBlanca() != esBlanca)
                {
                    List<int[]> movimientos = pieza.obtenerMovimientosValidos(tablero, fila, col);

                    for (int[] mov : movimientos)
                    {
                        if (mov[0] == filaRey && mov[1] == colRey)
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
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
          // cargar fuente personalizada //
        javafx.scene.text.Font.loadFont(
        getClass().getResourceAsStream("/resources/Letra/Cinzel-VariableFont_wght.ttf"),14);

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
                celda.setPrefSize(62, 58);

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

        // limpiar lista antes de calcular //
        movimientosPosibles = new ArrayList<>();

        if (pieza != null)
        {
            // obtener movimientos normales //
            List<int[]> movimientosOriginales = pieza.obtenerMovimientosValidos(tablero, fila, col);

            for (int i = 0; i < movimientosOriginales.size(); i++)
            {
                int[] mov = movimientosOriginales.get(i);

                int filaDestino = mov[0];
                int colDestino = mov[1];

                Pz piezaMovida = tablero[fila][col];
                Pz piezaCapturada = tablero[filaDestino][colDestino];

                // simular movimiento //
                tablero[filaDestino][colDestino] = piezaMovida;
                tablero[fila][col] = null;

                // verificar si el rey sigue en jaque //
                if (!hayJaque(pieza.esBlanca()))
                {
                    movimientosPosibles.add(new int[]{filaDestino, colDestino});
                }

                // deshacer movimiento //
                tablero[fila][col] = piezaMovida;
                tablero[filaDestino][colDestino] = piezaCapturada;
            }
        }

        dibujarTablero();
    }

   // move la pieza seleccionada a la casilla destino //
    private void moverPieza(int filaDestino, int colDestino)
    {
        // guardar la pieza antes de moverla //
        Pz piezaMovida = tablero[filaSeleccionada][colSeleccionada];

        tablero[filaDestino][colDestino] = piezaMovida;
        tablero[filaSeleccionada][colSeleccionada] = null;

        // promocion del peon (si un peon llega al final del tablero, se convierte en reina automaticamente) //

        if (piezaMovida instanceof Peon)
        {
            // peon blanco llega al final //
            if (piezaMovida.esBlanca() && filaDestino == 0)
            {
                tablero[filaDestino][colDestino] = new Reina(true);
            }

            // peon negro llega al final //
            if (!piezaMovida.esBlanca() && filaDestino == 7)
            {
                tablero[filaDestino][colDestino] = new Reina(false);
            }
        }

        limpiarSeleccion();
        cambiarTurno();

        // Logica del jaque mate //

        // si el jugador actual queda en jaque //
        if (hayJaque(turnoBlancas))
        {
            // si no tiene forma de salir, es jaque mate //
            if (hayJaqueMate(turnoBlancas))
            {
                String ganador;

                // como el turno ya cambio, el contrario es el ganador //
                if (turnoBlancas)
                {
                    ganador = "Negras";
                }
                else
                {
                    ganador = "Blancas";
                }

                // mostrar quien gano //
                actualizarEstado("Victoria " + ganador);

                // cambio de escena //
                try
                {
                    // carga el menu final //
                    javafx.fxml.FXMLLoader carga = new javafx.fxml.FXMLLoader(
                        getClass().getResource("/view/MenF.fxml")
                    );

                    javafx.scene.Parent raiz = carga.load();
                    // obtiene el controlador del menu final //
                    controller.MenuController controller = carga.getController();
                    controller.setGanador(ganador);

                    // obtiene el escenario actual //
                    javafx.stage.Stage escenario = (javafx.stage.Stage) gridTablero.getScene().getWindow();

                    javafx.scene.Scene nuevaEscena = new javafx.scene.Scene(raiz);

                    escenario.setScene(nuevaEscena);
                    escenario.setWidth(700);
                    escenario.setHeight(700);
                    escenario.setResizable(false);
                    escenario.centerOnScreen();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                // detiene el resto del metodo para no seguir dibujando el tablero //
                return;
            }
            else
            {
                // si hay jaque pero aun hay salida //
                actualizarEstado("Jaque");
            }
        }
        else
        {
            // si no hay jaque, el juego sigue normal //
            actualizarEstado("En juego");
        }

        // vuelve a dibujar el tablero //
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

        // revision de si alguna pieza enemiga puede atacar al rey //
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

    //jaque mate (se verifica si el jugador en jaque tiene alguna forma de salir del jaque, si no, es mate) //

    // verifica si el jugador indicado esta en jaque mate //
    private boolean hayJaqueMate(boolean esBlanca)
    {
        // si no hay jaque, no hay mate //
        if (!hayJaque(esBlanca))
        {
            return false;
        }

        // prueba todos los movimientos posibles //
        for (int fila = 0; fila < 8; fila++)
        {
            for (int col = 0; col < 8; col++)
            {
                Pz pieza = tablero[fila][col];

                if (pieza != null && pieza.esBlanca() == esBlanca)
                {
                    List<int[]> movimientos = pieza.obtenerMovimientosValidos(tablero, fila, col);

                    for (int[] mov : movimientos)
                    {
                        int filaDestino = mov[0];
                        int colDestino = mov[1];

                        Pz piezaMovida = tablero[fila][col];
                        Pz piezaCapturada = tablero[filaDestino][colDestino];

                        // simular movimiento //
                        tablero[filaDestino][colDestino] = piezaMovida;
                        tablero[fila][col] = null;

                        boolean sigueEnJaque = hayJaque(esBlanca);

                        // deshacer movimiento //
                        tablero[fila][col] = piezaMovida;
                        tablero[filaDestino][colDestino] = piezaCapturada;

                        // si existe una salida, no es mate //
                        if (!sigueEnJaque)
                        {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}
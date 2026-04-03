package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MainController {

    // grid del fxml (tablero) //
    @FXML
    private GridPane gridTablero;

    // matriz que representa el tablero logico (8x8) // cada casilla puede contener el nombre de la pieza o null si esta vacia
    private String[][] tablero = new String[8][8];

    // se ejecuta cuando carga el fxml //
    @FXML
    public void initialize() {
        inicializarTablero();
        dibujarTablero();
    }

    // Coloca las piezas en su posición inicial //
    private void inicializarTablero() {

        tablero = new String[][]{
            {"torreN", "caballoN", "alfilN", "reinaN", "reyN", "alfilN", "caballoN", "torreN"},
            {"peonN",  "peonN",    "peonN",  "peonN",  "peonN", "peonN",  "peonN",    "peonN"},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {"peonB",  "peonB",    "peonB",  "peonB",  "peonB", "peonB",  "peonB",    "peonB"},
            {"torreB", "caballoB", "alfilB", "reinaB", "reyB",  "alfilB", "caballoB", "torreB"}
        };
    }

    // dibuja todo el tablero en pantalla //
    private void dibujarTablero() {

        gridTablero.getChildren().clear();

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {

                String pieza = tablero[fila][col];

                // cada casilla //
                StackPane celda = new StackPane();

                if (pieza != null) {

                    // crea la imagen de la pieza // (obtenerRuta devuelve la ruta según el nombre de la pieza)
                    ImageView img = new ImageView(
                        new Image(getClass().getResourceAsStream(obtenerRuta(pieza)))
                    );
                    // tamaño de la imagen de las piezas //
                    img.setFitWidth(50);
                    img.setFitHeight(50);
                    img.setPreserveRatio(true);

                    celda.getChildren().add(img);
                }

                // guardar posicion para el click// (necesario porque fila y col cambian en cada iteración)
                final int f = fila;
                final int c = col;

                // evento de click (debug por ahora)
                celda.setOnMouseClicked(e -> {
                    System.out.println("click en: " + f + "," + c);
                });

                // agregar al grid
                gridTablero.add(celda, col, fila);
            }
        }
    }

    // devuelve la ruta de la imagen según la pieza // (la pieza se identifica por su nombre)
    private String obtenerRuta(String pieza) {

        return switch (pieza) {

            // blancas
            case "torreB"   -> "/resources/Img/Blancas/torre.png";
            case "caballoB" -> "/resources/Img/Blancas/caballo.png";
            case "alfilB"   -> "/resources/Img/Blancas/alfil.png";
            case "reinaB"   -> "/resources/Img/Blancas/reina.png";
            case "reyB"     -> "/resources/Img/Blancas/rey.png";
            case "peonB"    -> "/resources/Img/Blancas/peon.png";

            // negras
            case "torreN"   -> "/resources/Img/Negras/torre.png";
            case "caballoN" -> "/resources/Img/Negras/caballo.png";
            case "alfilN"   -> "/resources/Img/Negras/alfil.png";
            case "reinaN"   -> "/resources/Img/Negras/reina.png";
            case "reyN"     -> "/resources/Img/Negras/rey.png";
            case "peonN"    -> "/resources/Img/Negras/peon.png";

            default -> null;
        };
    }
}
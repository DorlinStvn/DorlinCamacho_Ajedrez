package model;

import java.util.ArrayList;
import java.util.List;

public class Reina extends Pz
{

    public Reina(boolean esBlanca)
    {
        super(esBlanca);
    }

    // ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/reina.png";
        }
        else
        {
            return "/resources/Img/Negras/reina.png";
        }
    }

    //  movimientos validos de la reina (Pa todos lados como Trump:) ) //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> movimientos = new ArrayList<>();

        // movimientos como la torre (lineas rectas) //
        recorrer(tablero, movimientos, fila, col, -1, 0); // arriba //
        recorrer(tablero, movimientos, fila, col, 1, 0);  // abajo //
        recorrer(tablero, movimientos, fila, col, 0, -1); // izquierda //
        recorrer(tablero, movimientos, fila, col, 0, 1);  // derecha //

        // movimientos como el alfil (diagonales) //
        recorrer(tablero, movimientos, fila, col, -1, -1); // diagonal superior izquierda //
        recorrer(tablero, movimientos, fila, col, -1, 1);  // diagonal superior derecha //
        recorrer(tablero, movimientos, fila, col, 1, -1);  // diagonal inferior izquierda //
        recorrer(tablero, movimientos, fila, col, 1, 1);   // diagonal inferior derecha //

        return movimientos;
    }

    // recorre una direccion hasta encontrar limite o las del 2001 //
    private void recorrer(Pz[][] tablero, List<int[]> movimientos, int fila, int col, int pasoFila, int pasoCol)
    {
        int nuevaFila = fila + pasoFila;
        int nuevaCol = col + pasoCol;

        while (estaDentro(nuevaFila, nuevaCol))
        {
            // si la casilla esta vacia, se puede mover //
            if (estaVacia(tablero, nuevaFila, nuevaCol))
            {
                movimientos.add(new int[]{nuevaFila, nuevaCol});
            }
            else
            {
                // si hay una pieza enemiga, se asesina //
                if (esEnemiga(tablero, nuevaFila, nuevaCol))
                {
                    movimientos.add(new int[]{nuevaFila, nuevaCol});
                }

                // si hay cualquier otra pieza, se detiene //
                break;
            }

            nuevaFila += pasoFila;
            nuevaCol += pasoCol;
        }
    }
}
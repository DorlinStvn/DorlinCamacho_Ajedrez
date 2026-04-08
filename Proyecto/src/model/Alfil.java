package model;

import java.util.ArrayList;
import java.util.List;

public class Alfil extends Pz
{

    public Alfil(boolean esBlanca)
    {
        super(esBlanca);
    }

    // ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/alfil.png";
        }
        else
        {
            return "/resources/Img/Negras/alfil.png";
        }
    }

    // devuelve el movimiento verificado del alfil //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> movimientos = new ArrayList<>();

        // diagonales //
        recorrer(tablero, movimientos, fila, col, -1, -1);
        recorrer(tablero, movimientos, fila, col, -1, 1);
        recorrer(tablero, movimientos, fila, col, 1, -1);
        recorrer(tablero, movimientos, fila, col, 1, 1);

        return movimientos;
    }

    // recorre en diagonal hasta encontrar limite o rival //
    private void recorrer(Pz[][] tablero, List<int[]> movimientos, int fila, int col, int pasoFila, int pasoCol)
    {
        int nuevaFila = fila + pasoFila;
        int nuevaCol = col + pasoCol;

        while (estaDentro(nuevaFila, nuevaCol))
        {
            if (estaVacia(tablero, nuevaFila, nuevaCol))
            {
                movimientos.add(new int[]{nuevaFila, nuevaCol});
            }
            else
            {
                if (esEnemiga(tablero, nuevaFila, nuevaCol))
                {
                    movimientos.add(new int[]{nuevaFila, nuevaCol});
                }

                break;
            }

            nuevaFila += pasoFila;
            nuevaCol += pasoCol;
        }
    }
}
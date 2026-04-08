package model;

import java.util.ArrayList;
import java.util.List;

public class Caballo extends Pz
{

    public Caballo(boolean esBlanca)
    {
        super(esBlanca);
    }

    // ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/caballo.png";
        }
        else
        {
            return "/resources/Img/Negras/caballo.png";
        }
    }

    // devuelve los movimientos validos del caballo //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> movimientos = new ArrayList<>();

        // posibles movimientos en L //
        int[][] posiciones = {
            {-2, -1}, {-2, 1},
            {-1, -2}, {-1, 2},
            {1, -2},  {1, 2},
            {2, -1},  {2, 1}
        };

        for (int i = 0; i < posiciones.length; i++)
        {
            int nuevaFila = fila + posiciones[i][0];
            int nuevaCol = col + posiciones[i][1];

            if (estaDentro(nuevaFila, nuevaCol))
            {
                // si esta vacio o hay enemigo //
                if (estaVacia(tablero, nuevaFila, nuevaCol) || esEnemiga(tablero, nuevaFila, nuevaCol))
                {
                    movimientos.add(new int[]{nuevaFila, nuevaCol});
                }
            }
        }

        return movimientos;
    }
}
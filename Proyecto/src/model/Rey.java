package model;

import java.util.ArrayList;
import java.util.List;

public class Rey extends Pz
{

    public Rey(boolean esBlanca)
    {
        super(esBlanca);
    }

    // ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/rey.png";
        }
        else
        {
            return "/resources/Img/Negras/rey.png";
        }
    }

    // movimientos validos del rey (De a uno como mi abuelo) //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> movimientos = new ArrayList<>();

        // direcciones posibles (1 pasito) //
        int[][] direcciones = 
        {
            {-1, -1}, {-1, 0}, {-1, 1}, // arriba //
            {0, -1},           {0, 1},  // lados //
            {1, -1},  {1, 0},  {1, 1}   // abajo //
        };

        for (int i = 0; i < direcciones.length; i++)
        {
            int nuevaFila = fila + direcciones[i][0];
            int nuevaCol = col + direcciones[i][1];

            // verificar si esta dentro del tablero //
            if (estaDentro(nuevaFila, nuevaCol))
            {
                // si esta vacio o hay enemigo, puede moverse //
                if (estaVacia(tablero, nuevaFila, nuevaCol) || esEnemiga(tablero, nuevaFila, nuevaCol))
                {
                    movimientos.add(new int[]{nuevaFila, nuevaCol});
                }
            }
        }

        return movimientos;
    }
}
package model;

import java.util.ArrayList;
import java.util.List;

public class Torre extends Pz
{

    public Torre(boolean esBlanca)
    {
        super(esBlanca);
    }

    // devuelve la ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/torre.png";
        }
        else
        {
            return "/resources/Img/Negras/torre.png";
        }
    }

    // devuelve los movimientos validos de la torre //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> movimientos = new ArrayList<>();

        // recorrido hacia arriba //
        recorrerDireccion(tablero, movimientos, fila, col, -1, 0);

        // recorrido hacia abajo //
        recorrerDireccion(tablero, movimientos, fila, col, 1, 0);

        // recorrido hacia la izquierda //
        recorrerDireccion(tablero, movimientos, fila, col, 0, -1);

        // recorrido hacia la derecha //
        recorrerDireccion(tablero, movimientos, fila, col, 0, 1);

        return movimientos;
    }

    // recorre una direccion hasta encontrar limite o una pieza //
    private void recorrerDireccion(Pz[][] tablero, List<int[]> movimientos, int fila, int col, int pasoFila, int pasoCol)
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
                // si hay una pieza enemiga, se puede Matar //
                if (esEnemiga(tablero, nuevaFila, nuevaCol))
                {
                    movimientos.add(new int[]{nuevaFila, nuevaCol});
                }

                // si hay cualquier pieza, se detiene(como al ver 500 en la calle) //
                break;
            }

            nuevaFila += pasoFila;
            nuevaCol += pasoCol;
        }
    }
}
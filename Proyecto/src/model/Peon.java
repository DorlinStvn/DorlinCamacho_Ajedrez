package model;

import java.util.ArrayList;
import java.util.List;

public class Peon extends Pz
{

    public Peon(boolean esBlanca)
    {
        super(esBlanca);
    }

    // devuelve la ruta de la imagen //
    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/peon.png";
        }
        else
        {
            return "/resources/Img/Negras/peon.png";
        }
    }

    // devuelve los movimientos validos del peon //
    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        List<int[]> lista = new ArrayList<>();

        int paso;
        if (esBlanca)
        {
            paso = -1;
        }
        else
        {
            paso = 1;
        }

        int siguienteFila = fila + paso;

        // avance normal //
        if (estaDentro(siguienteFila, col) && estaVacia(tablero, siguienteFila, col))
        {
            lista.add(new int[]{siguienteFila, col});

            // doble avance e el inicio //
            int filaInicio;
            if (esBlanca)
            {
                filaInicio = 6;
            }
            else
            {
                filaInicio = 1;
            }

            if (fila == filaInicio)
            {
                int salto = fila + (2 * paso);

                if (estaDentro(salto, col) && estaVacia(tablero, salto, col))
                {
                    lista.add(new int[]{salto, col});
                }
            }
        }

        // muerte diagonal //
        int[] lados = {-1, 1};

        for (int i = 0; i < lados.length; i++)
        {
            int nuevaCol = col + lados[i];

            if (estaDentro(siguienteFila, nuevaCol) && esEnemiga(tablero, siguienteFila, nuevaCol))
            {
                lista.add(new int[]{siguienteFila, nuevaCol});
            }
        }

        return lista;
    }
}
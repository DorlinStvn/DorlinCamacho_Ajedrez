package model;


import java.util.ArrayList;
import java.util.List; 

public class Rey extends Pz
{

    public Rey(boolean esBlanca)
    {
        super(esBlanca);
    }

    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/Rey.png";
        }
        else
        {
            return "/resources/Img/Negras/Rey.png";
        }
    }

    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        // Vacio aun sin logica (solo para probar) //
        return new ArrayList<>();
    }
}
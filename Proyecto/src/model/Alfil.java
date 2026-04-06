package model;


import java.util.ArrayList;
import java.util.List; 

public class Alfil extends Pz
{

    public Alfil(boolean esBlanca)
    {
        super(esBlanca);
    }

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

    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        // Vacio aun sin logica (solo para probar) //
        return new ArrayList<>();
    }
}
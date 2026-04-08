package model;


import java.util.ArrayList;
import java.util.List; 

public class Reina extends Pz
{

    public Reina(boolean esBlanca)
    {
        super(esBlanca);
    }

    @Override
    public String obtenerRutaImagen()
    {
        if (esBlanca)
        {
            return "/resources/Img/Blancas/Reina.png";
        }
        else
        {
            return "/resources/Img/Negras/Reina.png";
        }
    }

    @Override
    public List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col)
    {
        // Vacio aun sin logica (solo para probar) //
        return new ArrayList<>();
    }
}
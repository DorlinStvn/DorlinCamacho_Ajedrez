package model;

import java.util.List;

public abstract class Pz 
{

    // indica si la pieza es blanca o negra. true si es blanca, false si es negra //
    protected boolean esBlanca;


    public Pz(boolean esBlanca) 
    {
        this.esBlanca = esBlanca;
    }

    // devuelve si la pieza es blanca //
    public boolean esBlanca() 
    {
        return esBlanca;
    }

    // devuelve la ruta de la imagen de la pieza //
    public abstract String obtenerRutaImagen();

    // devuelve una lista de movimientos validos //
    public abstract List<int[]> obtenerMovimientosValidos(Pz[][] tablero, int fila, int col);

    // verifica si una posicion esta dentro del tablero //
    protected boolean estaDentro(int fila, int col)
    {
        return fila >= 0 && fila < 8 && col >= 0 && col < 8;
    }

    // verifica si alguna casilla esta vacia //
    protected boolean estaVacia(Pz[][] tablero, int fila, int col)
    {
        return tablero[fila][col] == null;
    }

    // verifica si hay una pieza enemiga(Nigga) en la casilla //
    protected boolean esEnemiga(Pz[][] tablero, int fila, int col)
    {
        return tablero[fila][col] != null && tablero[fila][col].esBlanca() != this.esBlanca;
    }
}
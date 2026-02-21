package uniandes.dpoo.aerolinea.modelo.cliente;

import java.util.LinkedList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;

public abstract class Cliente
{
    protected String identificador;
    protected List<Tiquete> tiquetes;

    public Cliente(String identificador)
    {
        this.identificador = identificador;
        this.tiquetes = new LinkedList<>();
    }

    public String getIdentificador()
    {
        return identificador;
    }

    public void agregarTiquete(Tiquete tiquete)
    {
        tiquetes.add(tiquete);
    }

    public List<Tiquete> getTiquetes()
    {
        return tiquetes;
    }

    public abstract String getTipoCliente();

    public void usarTiquetes(Vuelo vuelo)
    {
        // temporal
    }

    public int calcularValorTotalTiquetes()
    {
        return 0;
    }
}
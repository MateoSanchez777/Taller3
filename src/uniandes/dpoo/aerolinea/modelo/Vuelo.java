package uniandes.dpoo.aerolinea.modelo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.tarifas.CalculadoraTarifas;
import uniandes.dpoo.aerolinea.tiquetes.Tiquete;
import uniandes.dpoo.aerolinea.tiquetes.GeneradorTiquetes;

public class Vuelo
{
    private String fecha;
    private Ruta ruta;
    private Avion avion;
    private List<Tiquete> tiquetes;

    public Vuelo(Ruta ruta, String fecha, Avion avion)
    {
        this.ruta = ruta;
        this.fecha = fecha;
        this.avion = avion;
        this.tiquetes = new LinkedList<>();
    }

    public Ruta getRuta()
    {
        return ruta;
    }

    public String getFecha()
    {
        return fecha;
    }

    public Avion getAvion()
    {
        return avion;
    }

    public Collection<Tiquete> getTiquetes()
    {
        return tiquetes;
    }

    
    public int venderTiquetes(Cliente cliente,
                              CalculadoraTarifas calculadora,
                              int cantidad)
    {
        if (cantidad <= 0)
            return 0;

        int cuposDisponibles = avion.getCapacidad() - tiquetes.size();

        if (cantidad > cuposDisponibles)
            throw new RuntimeException("Vuelo sobrevendido");

        int total = 0;

        for (int i = 0; i < cantidad; i++)
        {
            int tarifa = calculadora.calcularTarifa(this, cliente);

            Tiquete tiquete = GeneradorTiquetes.generarTiquete(this, cliente, tarifa);

            tiquetes.add(tiquete);
            cliente.agregarTiquete(tiquete);

            total += tarifa;
        }

        return total;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Vuelo))
            return false;

        Vuelo otro = (Vuelo) obj;

        return this.fecha.equals(otro.fecha)
                && this.ruta.getCodigoRuta().equals(otro.ruta.getCodigoRuta());
    }
}
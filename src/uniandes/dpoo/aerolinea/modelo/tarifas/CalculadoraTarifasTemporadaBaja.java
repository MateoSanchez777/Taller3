package uniandes.dpoo.aerolinea.modelo.tarifas;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.modelo.cliente.Cliente;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteCorporativo;
import uniandes.dpoo.aerolinea.modelo.cliente.ClienteNatural;

public class CalculadoraTarifasTemporadaBaja extends CalculadoraTarifas
{
    protected static final int COSTO_POR_KM_NATURAL = 600;
    protected static final int COSTO_POR_KM_CORPORATIVO = 900;

    protected static final double DESCUENTO_PEQ = 0.02;
    protected static final double DESCUENTO_MEDIANAS = 0.1;
    protected static final double DESCUENTO_GRANDES = 0.2;

    @Override
    protected int calcularCostoBase(Vuelo vuelo, Cliente cliente)
    {
        Ruta ruta = vuelo.getRuta();

        int distancia = Aeropuerto.calcularDistancia(
                ruta.getOrigen(),
                ruta.getDestino());

        if (cliente instanceof ClienteNatural)
            return distancia * COSTO_POR_KM_NATURAL;
        else
            return distancia * COSTO_POR_KM_CORPORATIVO;
    }

    @Override
    protected double calcularPorcentajeDescuento(Cliente cliente)
    {
        if (cliente instanceof ClienteCorporativo)
        {
            ClienteCorporativo corp = (ClienteCorporativo) cliente;

            int tam = corp.getTamanoEmpresa();

            if (tam == ClienteCorporativo.PEQUENA)
                return DESCUENTO_PEQ;
            else if (tam == ClienteCorporativo.MEDIANA)
                return DESCUENTO_MEDIANAS;
            else
                return DESCUENTO_GRANDES;
        }

        return 0;
    }
}
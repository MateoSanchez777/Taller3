package uniandes.dpoo.aerolinea.modelo;

import java.util.HashSet;
import java.util.Set;

import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

/**
 * Esta clase encapsula la información sobre los aeropuertos.
 * No puede haber dos aeropuertos con el mismo código.
 */
public class Aeropuerto
{
    // Atributos
    private String nombre;
    private String codigo;
    private String nombreCiudad;
    private double latitud;
    private double longitud;

    private static final int RADIO_TERRESTRE = 6371;
    private static Set<String> codigos = new HashSet<>();

    // Constructor
    public Aeropuerto(String nombre,
                      String codigo,
                      String nombreCiudad,
                      double latitud,
                      double longitud) throws AeropuertoDuplicadoException
    {
        if (codigos.contains(codigo))
        {
            throw new AeropuertoDuplicadoException(codigo);
        }

        this.nombre = nombre;
        this.codigo = codigo;
        this.nombreCiudad = nombreCiudad;
        this.latitud = latitud;
        this.longitud = longitud;

        codigos.add(codigo);
    }

    // Getters
    public String getNombre()
    {
        return nombre;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getNombreCiudad()
    {
        return nombreCiudad;
    }

    public double getLatitud()
    {
        return latitud;
    }

    public double getLongitud()
    {
        return longitud;
    }

    /**
     * Calcula la distancia aproximada entre dos aeropuertos
     */
    public static int calcularDistancia(Aeropuerto aeropuerto1, Aeropuerto aeropuerto2)
    {
        double lat1 = Math.toRadians(aeropuerto1.getLatitud());
        double lon1 = Math.toRadians(aeropuerto1.getLongitud());
        double lat2 = Math.toRadians(aeropuerto2.getLatitud());
        double lon2 = Math.toRadians(aeropuerto2.getLongitud());

        double deltaX = (lon2 - lon1) * Math.cos((lat1 + lat2) / 2);
        double deltaY = (lat2 - lat1);

        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * RADIO_TERRESTRE;

        return (int) Math.round(distancia);
    }
}
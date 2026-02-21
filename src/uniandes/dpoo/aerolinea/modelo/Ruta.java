package uniandes.dpoo.aerolinea.modelo;

/**
 * Esta clase tiene la información de una ruta entre dos aeropuertos que cubre una aerolínea.
 */
public class Ruta
{
    // Atributos
    private String horaSalida;
    private String horaLlegada;
    private String codigoRuta;

    private Aeropuerto origen;
    private Aeropuerto destino;

    // Constructor
    public Ruta(Aeropuerto origen,
                Aeropuerto destino,
                String horaSalida,
                String horaLlegada,
                String codigoRuta)
    {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.codigoRuta = codigoRuta;
    }

    // Getters
    public String getCodigoRuta()
    {
        return codigoRuta;
    }

    public Aeropuerto getOrigen()
    {
        return origen;
    }

    public Aeropuerto getDestino()
    {
        return destino;
    }

    public String getHoraSalida()
    {
        return horaSalida;
    }

    public String getHoraLlegada()
    {
        return horaLlegada;
    }

    public int getDuracion()
    {
        int minutosSalida = getHoras(horaSalida) * 60 + getMinutos(horaSalida);
        int minutosLlegada = getHoras(horaLlegada) * 60 + getMinutos(horaLlegada);

        return minutosLlegada - minutosSalida;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna los minutos.
     */
    public static int getMinutos(String horaCompleta)
    {
        return Integer.parseInt(horaCompleta) % 100;
    }

    /**
     * Dada una cadena con una hora y minutos, retorna las horas.
     */
    public static int getHoras(String horaCompleta)
    {
        return Integer.parseInt(horaCompleta) / 100;
    }
}
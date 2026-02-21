package uniandes.dpoo.aerolinea.persistencia;

import java.io.IOException;

import uniandes.dpoo.aerolinea.exceptions.InformacionInconsistenteException;
import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import java.io.PrintWriter;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import uniandes.dpoo.aerolinea.modelo.Aeropuerto;
import uniandes.dpoo.aerolinea.modelo.Avion;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.modelo.Vuelo;
import uniandes.dpoo.aerolinea.exceptions.AeropuertoDuplicadoException;

public class PersistenciaAerolineaJSON implements IPersistenciaAerolinea
{
	@Override
	public void cargarAerolinea(String archivo, Aerolinea aerolinea)
	        throws IOException, InformacionInconsistenteException
	{
	    String contenido = new String(Files.readAllBytes(new File(archivo).toPath()));
	    JSONObject raiz = new JSONObject(contenido);

	    Map<String, Aeropuerto> mapaAeropuertos = new HashMap<>();

	    // Cargar aeropuertos
	    JSONArray jAeropuertos = raiz.getJSONArray("aeropuertos");
	    for (int i = 0; i < jAeropuertos.length(); i++)
	    {
	        JSONObject jA = jAeropuertos.getJSONObject(i);

	        Aeropuerto a;
	        try
	        {
	            a = new Aeropuerto(
	                    jA.getString("nombre"),
	                    jA.getString("codigo"),
	                    jA.getString("nombreCiudad"),
	                    jA.getDouble("latitud"),
	                    jA.getDouble("longitud")
	            );
	        }
	        catch (AeropuertoDuplicadoException e)
	        {
	            throw new InformacionInconsistenteException("Aeropuerto duplicado: " + jA.getString("codigo"));
	        }

	        mapaAeropuertos.put(a.getCodigo(), a);
	    }

	    // Cargar aviones
	    JSONArray jAviones = raiz.getJSONArray("aviones");
	    for (int i = 0; i < jAviones.length(); i++)
	    {
	        JSONObject jA = jAviones.getJSONObject(i);

	        Avion avion = new Avion(
	                jA.getString("nombre"),
	                jA.getInt("capacidad")
	        );

	        aerolinea.agregarAvion(avion);
	    }

	    // Cargar rutas
	    JSONArray jRutas = raiz.getJSONArray("rutas");
	    for (int i = 0; i < jRutas.length(); i++)
	    {
	        JSONObject jR = jRutas.getJSONObject(i);

	        Aeropuerto origen = mapaAeropuertos.get(jR.getString("origen"));
	        Aeropuerto destino = mapaAeropuertos.get(jR.getString("destino"));

	        Ruta ruta = new Ruta(
	                origen,
	                destino,
	                jR.getString("horaSalida"),
	                jR.getString("horaLlegada"),
	                jR.getString("codigoRuta")
	        );

	        aerolinea.agregarRuta(ruta);
	    }

	    // Cargar vuelos
	    JSONArray jVuelos = raiz.getJSONArray("vuelos");
	    for (int i = 0; i < jVuelos.length(); i++)
	    {
	        JSONObject jV = jVuelos.getJSONObject(i);

	        try
	        {
	            aerolinea.programarVuelo(
	                    jV.getString("fecha"),
	                    jV.getString("codigoRuta"),
	                    jV.getString("avion")
	            );
	        }
	        catch (Exception e)
	        {
	            throw new InformacionInconsistenteException("Error cargando vuelo");
	        }
	    }
	}

    @Override
    public void salvarAerolinea(String archivo, Aerolinea aerolinea)
            throws IOException
    {
        JSONObject raiz = new JSONObject();

        salvarAeropuertos(aerolinea, raiz);
        salvarAviones(aerolinea, raiz);
        salvarRutas(aerolinea, raiz);
        salvarVuelos(aerolinea, raiz);

        PrintWriter pw = new PrintWriter(archivo);
        raiz.write(pw, 2, 0);
        pw.close();
    }
    
    
    private void salvarAeropuertos(Aerolinea aerolinea, JSONObject raiz)
    {
        JSONArray jAeropuertos = new JSONArray();
        Set<String> codigosAgregados = new HashSet<>();

        for (Ruta r : aerolinea.getRutas())
        {
            Aeropuerto origen = r.getOrigen();
            Aeropuerto destino = r.getDestino();

            if (!codigosAgregados.contains(origen.getCodigo()))
            {
                JSONObject jA = new JSONObject();
                jA.put("nombre", origen.getNombre());
                jA.put("codigo", origen.getCodigo());
                jA.put("nombreCiudad", origen.getNombreCiudad());
                jA.put("latitud", origen.getLatitud());
                jA.put("longitud", origen.getLongitud());

                jAeropuertos.put(jA);
                codigosAgregados.add(origen.getCodigo());
            }

            if (!codigosAgregados.contains(destino.getCodigo()))
            {
                JSONObject jA = new JSONObject();
                jA.put("nombre", destino.getNombre());
                jA.put("codigo", destino.getCodigo());
                jA.put("nombreCiudad", destino.getNombreCiudad());
                jA.put("latitud", destino.getLatitud());
                jA.put("longitud", destino.getLongitud());

                jAeropuertos.put(jA);
                codigosAgregados.add(destino.getCodigo());
            }
        }

        raiz.put("aeropuertos", jAeropuertos);
    }
    
    
    private void salvarAviones(Aerolinea aerolinea, JSONObject raiz)
    {
        JSONArray jAviones = new JSONArray();

        for (Avion a : aerolinea.getAviones())
        {
            JSONObject jA = new JSONObject();
            jA.put("nombre", a.getNombre());
            jA.put("capacidad", a.getCapacidad());

            jAviones.put(jA);
        }

        raiz.put("aviones", jAviones);
    }
    
    
    
    private void salvarRutas(Aerolinea aerolinea, JSONObject raiz)
    {
        JSONArray jRutas = new JSONArray();

        for (Ruta r : aerolinea.getRutas())
        {
            JSONObject jR = new JSONObject();
            jR.put("codigoRuta", r.getCodigoRuta());
            jR.put("origen", r.getOrigen().getCodigo());
            jR.put("destino", r.getDestino().getCodigo());
            jR.put("horaSalida", r.getHoraSalida());
            jR.put("horaLlegada", r.getHoraLlegada());

            jRutas.put(jR);
        }

        raiz.put("rutas", jRutas);
    }
    
    
    private void salvarVuelos(Aerolinea aerolinea, JSONObject raiz)
    {
        JSONArray jVuelos = new JSONArray();

        for (Vuelo v : aerolinea.getVuelos())
        {
            JSONObject jV = new JSONObject();
            jV.put("codigoRuta", v.getRuta().getCodigoRuta());
            jV.put("fecha", v.getFecha());
            jV.put("avion", v.getAvion().getNombre());

            jVuelos.put(jV);
        }

        raiz.put("vuelos", jVuelos);
    }
    
    
    
    
}
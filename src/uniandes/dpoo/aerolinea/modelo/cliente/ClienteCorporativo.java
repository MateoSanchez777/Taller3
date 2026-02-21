package uniandes.dpoo.aerolinea.modelo.cliente;

import org.json.JSONObject;

public class ClienteCorporativo extends Cliente
{
    public static final String CORPORATIVO = "Corporativo";
    public static final int GRANDE = 1;
    public static final int MEDIANA = 2;
    public static final int PEQUENA = 3;

    protected String nombreEmpresa;
    protected int tamanoEmpresa;

    public ClienteCorporativo(String nombreEmpresa, int tamanoEmpresa)
    {
        super(nombreEmpresa);  // usamos nombreEmpresa como identificador
        this.nombreEmpresa = nombreEmpresa;
        this.tamanoEmpresa = tamanoEmpresa;
    }

    public String getNombreEmpresa()
    {
        return nombreEmpresa;
    }

    public int getTamanoEmpresa()
    {
        return tamanoEmpresa;
    }

    @Override
    public String getTipoCliente()
    {
        return CORPORATIVO;
    }

    public static ClienteCorporativo cargarDesdeJSON(JSONObject cliente)
    {
        String nombreEmpresa = cliente.getString("nombreEmpresa");
        int tam = cliente.getInt("tamanoEmpresa");
        return new ClienteCorporativo(nombreEmpresa, tam);
    }

    public JSONObject salvarEnJSON()
    {
        JSONObject jobject = new JSONObject();
        jobject.put("nombreEmpresa", this.nombreEmpresa);
        jobject.put("tamanoEmpresa", this.tamanoEmpresa);
        jobject.put("tipo", CORPORATIVO);
        return jobject;
    }
}
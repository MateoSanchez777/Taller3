package uniandes.dpoo.aerolinea.consola;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class PruebaFinal {

    public static void main(String[] args) throws Exception {

        Aerolinea aerolinea = new Aerolinea();

        // Cargar modelo principal
        aerolinea.cargarAerolinea("datos/aerolinea.json", CentralPersistencia.JSON);
        
        
     
        
        System.out.println("Cantidad rutas: " + aerolinea.getRutas().size());
        System.out.println("Cantidad vuelos: " + aerolinea.getVuelos().size());

        // Cargar tiquetes
        aerolinea.cargarTiquetes("datos/tiquetes.json", CentralPersistencia.JSON);

        // Consultar saldo cliente
        System.out.println("Saldo Bob: " +
                aerolinea.consultarSaldoPendienteCliente("Bob"));

        // Vender tiquetes
        int total = aerolinea.venderTiquetes(
                "Bob",
                "2024-11-05",
                "4558",
                1
        );

        System.out.println("Venta realizada: " + total);

        // Guardar aerolínea
        aerolinea.salvarAerolinea("datos/salida_aerolinea.json", CentralPersistencia.JSON);

        // Guardar tiquetes
        aerolinea.salvarTiquetes("datos/salida_tiquetes.json", CentralPersistencia.JSON);

        System.out.println("Lo lograste capitán.");
    }
}




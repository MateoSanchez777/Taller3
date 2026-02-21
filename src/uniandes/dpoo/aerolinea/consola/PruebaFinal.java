package uniandes.dpoo.aerolinea.consola;

import uniandes.dpoo.aerolinea.modelo.Aerolinea;
import uniandes.dpoo.aerolinea.modelo.Ruta;
import uniandes.dpoo.aerolinea.persistencia.CentralPersistencia;

public class PruebaFinal {

    public static void main(String[] args) throws Exception {

        Aerolinea aerolinea = new Aerolinea();

        // 1️⃣ Cargar modelo principal
        aerolinea.cargarAerolinea("datos/aerolinea.json", CentralPersistencia.JSON);
        
        
     
        
        System.out.println("Cantidad rutas: " + aerolinea.getRutas().size());
        System.out.println("Cantidad vuelos: " + aerolinea.getVuelos().size());

        // 2️⃣ Cargar tiquetes
        aerolinea.cargarTiquetes("datos/tiquetes.json", CentralPersistencia.JSON);

        // 3️⃣ Consultar saldo cliente
        System.out.println("Saldo Bob: " +
                aerolinea.consultarSaldoPendienteCliente("Bob"));

        // 4️⃣ Vender tiquetes
        int total = aerolinea.venderTiquetes(
                "Bob",
                "2024-11-05",
                "4558",
                1
        );

        System.out.println("Venta realizada: " + total);

        // 5️⃣ Guardar aerolínea
        aerolinea.salvarAerolinea("datos/salida_aerolinea.json", CentralPersistencia.JSON);

        // 6️⃣ Guardar tiquetes
        aerolinea.salvarTiquetes("datos/salida_tiquetes.json", CentralPersistencia.JSON);

        System.out.println("Prueba completada correctamente.");
    }
}




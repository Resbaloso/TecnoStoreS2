package controller;

import model.logic.GestionCelularesLogica;
import model.logic.GestionReportesLogica;
import model.logic.GestionVentasLogica;
import view.MenuPrincipalAdmin;

// Controlador del menú administrador, ejecuta la opción que el administrador reciba
public class MenuAdminController {

    private final MenuPrincipalAdmin mpa = new MenuPrincipalAdmin();
    private final GestionCelularesLogica gestionCelulares = new GestionCelularesLogica();
    private final GestionVentasLogica gestionVentas = new GestionVentasLogica();
    private final GestionReportesLogica gestionReportes = new GestionReportesLogica();

    public void MenuAdmin() {
        int op;
        do {
            op = mpa.mostrarMenuPrincipalAdmin();
            switch (op) {
                case 1 -> {
                    // Delega al módulo de gestión de celulares (marcas, SO, celulares).
                    gestionCelulares.ejecutar();
                }
                case 2 -> {
                    // Delega al módulo de gestión de ventas y detalles de venta.
                    gestionVentas.ejecutar();
                }
                case 3 -> {
                    // Delega al módulo de reportes (usa DatosReporteVenta, patrón BodegasFileObject).
                    gestionReportes.ejecutar();
                }
                case 4 -> {
                    System.out.println("Saliendo...");
                }
                default -> {
                    System.out.println("Opcion no encontrada!");
                }
            }
        } while (op != 4);
    }
}

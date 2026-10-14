package view;

import view.validations.data.ValidarEntero;

// Vista del menú de reportes del administrador.
public class MenuReportes {

    // Menú principal del módulo de reportes.
    public int mostrarMenuReportes() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            MENÚ REPORTES
                            Seleccione una opción:
                                1. Ver celulares con stock bajo (menos de 5 unidades).
                                2. Ver Top 3 celulares más vendidos.
                                3. Ver ventas totales por mes.
                                4. Generar archivo reporte_ventas.txt.
                                5. Volver al menú principal.
                        """);
    }
}

package view;

import view.validations.data.ValidarEntero;

// Vista del menú de gestión de ventas del administrador.
public class MenuGestionarVentas {

    // Menú principal del módulo de ventas.
    public int mostrarMenuVentas() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            MENÚ GESTIONAR VENTAS
                            Seleccione una opción:
                                1. Gestionar Ventas.
                                2. Gestionar Detalles de Venta.
                                3. Volver al menú principal.
                        """);
    }

    // Submenú CRUD para Ventas.
    public int mostrarMenuCrudVentas() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- GESTIONAR VENTAS --
                            Seleccione una opción:
                                1. Registrar Venta.
                                2. Actualizar Venta.
                                3. Eliminar Venta.
                                4. Listar Ventas.
                                5. Volver.
                        """);
    }

    // Submenú CRUD para Detalles de Venta.
    public int mostrarMenuCrudDetallesVenta() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- GESTIONAR DETALLES DE VENTA --
                            Seleccione una opción:
                                1. Agregar Detalle de Venta.
                                2. Actualizar Detalle de Venta.
                                3. Eliminar Detalle de Venta.
                                4. Listar Detalles de Venta.
                                5. Volver.
                        """);
    }
}

package model.logic;

import dao.CelularesDao;
import dao.DetalleVentaDao;
import dao.MarcaDao;
import dao.UsuarioDao;
import dao.VentaDao;
import java.util.List;
import model.entities.Celulares;
import model.entities.DetalleVenta;
import model.entities.Marca;
import model.entities.Usuario;
import model.entities.Venta;
import view.MenuGestionarVentas;
import view.validations.data.ValidarDecimal;
import view.validations.data.ValidarEntero;
import view.validations.data.ValidarEnteroGrande;

// Lógica de negocio del módulo "Gestionar Ventas" del administrador.
// Maneja los CRUD de Ventas y Detalles de Venta.
public class GestionVentasLogica {

    private final VentaDao ventaDao = new VentaDao();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();
    private final CelularesDao celularesDao = new CelularesDao();
    private final MarcaDao marcaDao = new MarcaDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final MenuGestionarVentas menu = new MenuGestionarVentas();
    private final ValidarEntero vEntero = new ValidarEntero();
    private final ValidarEnteroGrande vEnteroGrande = new ValidarEnteroGrande();
    private final ValidarDecimal vDecimal = new ValidarDecimal();

    // Constante de IVA del 19% según requerimientos del proyecto.
    private static final double IVA = 0.19;

    // ===== MENÚ PRINCIPAL DEL MÓDULO =====
    public void ejecutar() {
        int op;
        do {
            op = menu.mostrarMenuVentas();
            switch (op) {
                case 1 -> ejecutarMenuCrudVentas();
                case 2 -> ejecutarMenuCrudDetallesVenta();
                case 3 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 3);
    }

    // ===== CRUD VENTAS =====
    private void ejecutarMenuCrudVentas() {
        int op;
        do {
            op = menu.mostrarMenuCrudVentas();
            switch (op) {
                case 1 -> registrarVenta();
                case 2 -> actualizarVenta();
                case 3 -> eliminarVenta();
                case 4 -> listarVentas();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    private void registrarVenta() {
        System.out.println("======REGISTRAR VENTA======");
        listarUsuariosClientes();
        int usuarioId = pedirEnteroPositivo("Ingrese el ID del usuario cliente:");

        if (usuarioDao.buscar(usuarioId) == null) {
            System.out.println("El ID del usuario no existe.");
            return;
        }

        double total = pedirDecimalPositivo("Ingrese el total de la venta:");
        Venta v = new Venta(0, usuarioId, null, total);
        ventaDao.crear(v);
    }

    private void actualizarVenta() {
        System.out.println("======ACTUALIZAR VENTA======");
        listarVentas();
        long id = vEnteroGrande.validar("Ingrese el ID de la venta a actualizar:");
        if (ventaDao.buscar(id) == null) {
            System.out.println("El ID de la venta no existe.");
            return;
        }
        listarUsuariosClientes();
        int usuarioId = pedirEnteroPositivo("Ingrese el nuevo ID del usuario:");
        double total = pedirDecimalPositivo("Ingrese el nuevo total:");
        Venta v = new Venta((int) id, usuarioId, null, total);
        ventaDao.actualizar(v);
    }

    private void eliminarVenta() {
        System.out.println("======ELIMINAR VENTA======");
        listarVentas();
        long id = vEnteroGrande.validar("Ingrese el ID de la venta a eliminar:");
        ventaDao.eliminar(id);
    }

    public void listarVentas() {
        System.out.println("======LISTA DE VENTAS======");
        List<Venta> lista = ventaDao.obtenerVentas();
        if (lista.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // ===== CRUD DETALLES DE VENTA =====
    private void ejecutarMenuCrudDetallesVenta() {
        int op;
        do {
            op = menu.mostrarMenuCrudDetallesVenta();
            switch (op) {
                case 1 -> agregarDetalleVenta();
                case 2 -> actualizarDetalleVenta();
                case 3 -> eliminarDetalleVenta();
                case 4 -> listarDetallesVenta();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    private void agregarDetalleVenta() {
        System.out.println("======AGREGAR DETALLE DE VENTA======");
        listarVentas();
        int ventaId = pedirEnteroPositivo("Ingrese el ID de la venta:");
        if (ventaDao.buscar(ventaId) == null) {
            System.out.println("El ID de la venta no existe.");
            return;
        }

        listarCelulares();
        int celularId = pedirEnteroPositivo("Ingrese el ID del celular:");
        Celulares cel = celularesDao.buscar(celularId);
        if (cel == null) {
            System.out.println("El ID del celular no existe.");
            return;
        }

        int cantidad = pedirEnteroPositivo("Ingrese la cantidad:");
        if (cantidad > cel.getStock()) {
            System.out.println("Error: no hay suficiente stock. Stock disponible: " + cel.getStock());
            return;
        }

        // Calcula el subtotal incluyendo IVA del 19%.
        double precioConIva = cel.getPrecio() * (1 + IVA);
        double subtotal = precioConIva * cantidad;

        System.out.println("Subtotal (con IVA 19%): $" + subtotal);

        DetalleVenta dv = new DetalleVenta(0, ventaId, celularId, cantidad, cel.getPrecio(), subtotal);
        detalleVentaDao.crear(dv);

        // Actualiza el stock del celular vendido.
        celularesDao.descontarStock(celularId, cantidad);
        System.out.println("Stock actualizado correctamente.");
    }

    private void actualizarDetalleVenta() {
        System.out.println("======ACTUALIZAR DETALLE DE VENTA======");
        listarDetallesVenta();
        long id = vEnteroGrande.validar("Ingrese el ID del detalle a actualizar:");
        if (detalleVentaDao.buscar(id) == null) {
            System.out.println("El ID del detalle no existe.");
            return;
        }

        int ventaId = pedirEnteroPositivo("Ingrese el ID de la venta:");
        int celularId = pedirEnteroPositivo("Ingrese el ID del celular:");
        int cantidad = pedirEnteroPositivo("Ingrese la nueva cantidad:");
        double precio = pedirDecimalPositivo("Ingrese el nuevo precio unitario:");
        double subtotal = precio * (1 + IVA) * cantidad;

        DetalleVenta dv = new DetalleVenta((int) id, ventaId, celularId, cantidad, precio, subtotal);
        detalleVentaDao.actualizar(dv);
    }

    private void eliminarDetalleVenta() {
        System.out.println("======ELIMINAR DETALLE DE VENTA======");
        listarDetallesVenta();
        long id = vEnteroGrande.validar("Ingrese el ID del detalle a eliminar:");
        detalleVentaDao.eliminar(id);
    }

    public void listarDetallesVenta() {
        System.out.println("======LISTA DE DETALLES DE VENTA======");
        List<DetalleVenta> lista = detalleVentaDao.obtenerDetallesVentas();
        if (lista.isEmpty()) {
            System.out.println("No hay detalles de venta registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // ===== MÉTODOS AUXILIARES =====

    // Muestra la lista de usuarios con rol cliente (rol_fk = 2) para facilitar la selección.
    private void listarUsuariosClientes() {
        System.out.println("-- Usuarios clientes disponibles --");
        usuarioDao.obtenerUsuarios().stream()
                .filter(u -> u.getRoles_fk() == 2)
                .forEach(u -> System.out.println("ID: " + u.getId() + " | Username: " + u.getUsername()));
    }

    private void listarCelulares() {
        System.out.println("-- Celulares disponibles --");
        List<Celulares> lista = celularesDao.obtenerCelulares();
        for (Celulares cel : lista) {
            Marca marca = marcaDao.buscar(cel.getMarcas_fk());
            String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";
            System.out.println("ID: " + cel.getId()
                    + " | Modelo: " + cel.getModelo()
                    + " | Marca: " + nombreMarca
                    + " | Stock: " + cel.getStock()
                    + " | Precio: $" + cel.getPrecio()
                    + " | Gama: " + cel.getGama());
        }
    }

    private int pedirEnteroPositivo(String mensaje) {
        int valor;
        do {
            valor = vEntero.validar(mensaje);
            if (valor <= 0) {
                System.out.println("Error: el valor debe ser mayor a 0.");
            }
        } while (valor <= 0);
        return valor;
    }

    private double pedirDecimalPositivo(String mensaje) {
        double valor;
        do {
            valor = vDecimal.validar(mensaje);
            if (valor <= 0) {
                System.out.println("Error: el valor debe ser mayor a 0.");
            }
        } while (valor <= 0);
        return valor;
    }
}

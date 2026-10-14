package model.logic;

import dao.CelularesDao;
import dao.DetalleVentaDao;
import dao.MarcaDao;
import dao.PersonasDao;
import dao.UsuarioDao;
import dao.VentaDao;
 import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.entities.Celulares;
import model.entities.DetalleVenta;
import model.entities.Marca;
import model.entities.Persona;
import model.entities.ReporteVenta;
import model.entities.Usuario;
import model.entities.Venta;
import view.MenuReportes;

// Lógica del módulo de reportes del administrador.
// Usa Stream API y colecciones como piden los requerimientos.
// Genera el archivo reporte_ventas.txt usando DatosReporteVenta (patrón BodegasFileObject).
public class GestionReportesLogica {

    private final CelularesDao celularesDao = new CelularesDao();
    private final MarcaDao marcaDao = new MarcaDao();
    private final VentaDao ventaDao = new VentaDao();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final PersonasDao personasDao = new PersonasDao();
    private final MenuReportes menu = new MenuReportes();

    // DatosReporteVenta: manejo del archivo de objetos y texto, igual que DatosProducto.
    private final DatosReporteVenta datosReporte = new DatosReporteVenta();

    // ===== MENÚ PRINCIPAL DEL MÓDULO =====
    public void ejecutar() {
        int op;
        do {
            op = menu.mostrarMenuReportes();
            switch (op) {
                case 1 -> mostrarStockBajo();
                case 2 -> mostrarTop3MasVendidos();
                case 3 -> mostrarVentasPorMes();
                case 4 -> generarArchivoReporte();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    // Reporte 1: Celulares con stock bajo (menos de 5 unidades) usando Stream API.
    public void mostrarStockBajo() {
        System.out.println("======CELULARES CON STOCK BAJO (menos de 5 unidades)======");
        List<Celulares> lista = celularesDao.obtenerCelulares();

        List<Celulares> stockBajo = lista.stream()
                .filter(cel -> cel.getStock() < 5)
                .collect(Collectors.toList());

        if (stockBajo.isEmpty()) {
            System.out.println("Todos los celulares tienen stock suficiente.");
        } else {
            stockBajo.forEach(cel -> {
                Marca marca = marcaDao.buscar(cel.getMarcas_fk());
                String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";
                System.out.println("ID: " + cel.getId()
                        + " | Modelo: " + cel.getModelo()
                        + " | Marca: " + nombreMarca
                        + " | Stock: " + cel.getStock()
                        + " | Gama: " + cel.getGama());
            });
        }
    }

    // Reporte 2: Top 3 celulares más vendidos usando Stream API y Collectors.groupingBy.
    public void mostrarTop3MasVendidos() {
        System.out.println("======TOP 3 CELULARES MÁS VENDIDOS======");
        List<DetalleVenta> detalles = detalleVentaDao.obtenerDetallesVentas();

        if (detalles.isEmpty()) {
            System.out.println("No hay ventas registradas aún.");
            return;
        }

        // Agrupa por ID de celular y suma las cantidades vendidas.
        Map<Integer, Integer> ventasPorCelular = detalles.stream()
                .collect(Collectors.groupingBy(
                        DetalleVenta::getCelulares_fk,
                        Collectors.summingInt(DetalleVenta::getCantidad)
                ));

        // Ordena descendente y toma los 3 primeros.
        ventasPorCelular.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> {
                    Celulares cel = celularesDao.buscar(entry.getKey());
                    if (cel != null) {
                        Marca marca = marcaDao.buscar(cel.getMarcas_fk());
                        String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";
                        System.out.println("  Modelo: " + nombreMarca + " " + cel.getModelo()
                                + " | Unidades vendidas: " + entry.getValue());
                    }
                });
    }

    // Reporte 3: Ventas totales por mes usando Stream API.
    public void mostrarVentasPorMes() {
        System.out.println("======VENTAS TOTALES POR MES======");
        List<Venta> ventas = ventaDao.obtenerVentas();

        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas aún.");
            return;
        }

        // Agrupa por "YYYY-MM" usando substring de la fecha formateada.
        Map<String, Double> totalPorMes = ventas.stream()
                .filter(v -> v.getFecha() != null)
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().toString().substring(0, 7),
                        Collectors.summingDouble(Venta::getTotal)
                ));

        totalPorMes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": $" + entry.getValue()));
    }

    // Genera el archivo reporte_ventas.txt regenerando desde la base de datos.
    // Sincroniza los reportes del archivo con los datos actuales de la BD.
    public void generarArchivoReporte() {
        System.out.println("======GENERANDO REPORTE DE VENTAS======");

        List<DetalleVenta> detalles = detalleVentaDao.obtenerDetallesVentas();
        List<Venta> ventas = ventaDao.obtenerVentas();

        if (detalles.isEmpty()) {
            System.out.println("No hay ventas para reportar.");
            return;
        }

        // Construimos la lista completa desde la BD y la persistimos de una sola vez.
        // Usamos un DatosReporteVenta nuevo con lista vacía para evitar duplicados.
        List<ReporteVenta> listaFinal = new ArrayList<>();

        for (DetalleVenta dv : detalles) {
            // Obtenemos los datos relacionados para el reporte.
            Venta venta = ventas.stream()
                    .filter(v -> v.getId() == dv.getVentas_fk())
                    .findFirst().orElse(null);

            Celulares cel = celularesDao.buscar(dv.getCelulares_fk());
            if (venta == null || cel == null) continue;

            Usuario usuario = usuarioDao.buscar(venta.getUsuarios_fk());
            Persona persona = usuario != null ? personasDao.buscarPorId(usuario.getPersonas_fk()) : null;
            Marca marca = marcaDao.buscar(cel.getMarcas_fk());

            String fechaStr = venta.getFecha() != null ? venta.getFecha().toString().substring(0, 10) : "Sin fecha";
            String usernameStr = usuario != null ? usuario.getUsername() : "Desconocido";
            String nombreStr = persona != null ? persona.getNombre() : "Desconocido";
            String modeloStr = cel.getModelo();
            String marcaStr = marca != null ? marca.getNombre() : "Desconocida";
            double precioUnit = cel.getPrecio();
            double subtotal = dv.getSubtotal();
            String metodoPago = "Sistema";

            listaFinal.add(new ReporteVenta(
                    venta.getId(), fechaStr, usernameStr, nombreStr,
                    modeloStr, marcaStr, dv.getCantidad(),
                    precioUnit, subtotal, subtotal, metodoPago
            ));
        }

        // Persistimos la lista completa en el archivo de objetos y generamos el .txt.
        // Usamos try-with-resources al escribir el archivo de objetos (patrón BodegasFileObject).
        try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(
                new java.io.FileOutputStream("reporte_ventas.dat"))) {
            oos.writeObject(listaFinal);
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo de objetos: " + e.getMessage());
        }

        // Generamos el .txt con la lista actualizada usando DatosReporteVenta.
        DatosReporteVenta datos = new DatosReporteVenta();
        datos.generarArchivoTexto();
        System.out.println("Reporte generado correctamente en 'reporte_ventas.txt'.");
    }
}

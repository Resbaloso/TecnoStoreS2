package model.logic;

import dao.CelularesDao;
import dao.DetalleVentaDao;
import dao.MarcaDao;
import dao.PersonasDao;
import dao.SistemaOperativoDao;
import dao.VentaDao;
import java.util.List;
import java.util.Scanner;
import model.entities.Celulares;
import model.entities.DetalleVenta;
import model.entities.Marca;
import model.entities.Persona;
import model.entities.ReporteVenta;
import model.entities.SistemaOperativo;
import model.entities.Usuario;
import model.entities.Venta;
import view.MenuCompraUsuario;
import view.validations.data.ValidarEntero;
import view.validations.data.ValidarEnteroGrande;
import view.validations.range.validarRangoDato;

// Lógica del módulo de compra del usuario cliente.
// Emula el proceso de pago con tarjeta o Nequi, genera el reporte de la venta.
public class CompraUsuarioLogica {

    private final CelularesDao celularesDao = new CelularesDao();
    private final MarcaDao marcaDao = new MarcaDao();
    private final SistemaOperativoDao soDao = new SistemaOperativoDao();
    private final VentaDao ventaDao = new VentaDao();
    private final DetalleVentaDao detalleVentaDao = new DetalleVentaDao();
    private final PersonasDao personasDao = new PersonasDao();
    private final MenuCompraUsuario menu = new MenuCompraUsuario();
    private final ValidarEntero vEntero = new ValidarEntero();
    private final ValidarEnteroGrande vEnteroGrande = new ValidarEnteroGrande();
    private final validarRangoDato vRango = new validarRangoDato();
    private final Scanner scanner = new Scanner(System.in);

    // DatosReporteVenta para persistir el reporte de la compra (patrón BodegasFileObject).
    private final DatosReporteVenta datosReporte = new DatosReporteVenta();

    // Constante de IVA del 19% según requerimientos del proyecto.
    private static final double IVA = 0.19;

    // Flujo principal de compra del usuario.
    public void ejecutar(Usuario usuarioActivo) {
        System.out.println("Bienvenido a la tienda TecnoStore S2.");

        // Paso 1: listar catálogo de celulares disponibles.
        List<Celulares> catalogo = celularesDao.obtenerCelulares().stream()
                .filter(cel -> cel.getStock() > 0)
                .toList();

        if (catalogo.isEmpty()) {
            System.out.println("Lo sentimos, no hay productos disponibles en este momento.");
            return;
        }

        mostrarCatalogo(catalogo);

        // Paso 2: el usuario selecciona el celular por ID.
        long idCelular;
        Celulares celularElegido = null;
        do {
            idCelular = vEnteroGrande.validar("Ingrese el ID del celular que desea comprar (0 para cancelar):");
            if (idCelular == 0) {
                System.out.println("Compra cancelada.");
                return;
            }
            final long idBuscar = idCelular;
            celularElegido = catalogo.stream()
                    .filter(cel -> cel.getId() == idBuscar)
                    .findFirst().orElse(null);
            if (celularElegido == null) {
                System.out.println("El ID no corresponde a un celular disponible. Intente de nuevo.");
            }
        } while (celularElegido == null);

        // Paso 3: validar la cantidad solicitada.
        int cantidad;
        do {
            cantidad = vEntero.validar("Ingrese la cantidad que desea comprar (maximo " + celularElegido.getStock() + "):");
            if (cantidad <= 0) {
                System.out.println("Error: la cantidad debe ser mayor a 0.");
            } else if (cantidad > celularElegido.getStock()) {
                System.out.println("Error: no hay suficiente stock. Stock disponible: " + celularElegido.getStock());
                cantidad = 0;
            }
        } while (cantidad <= 0);

        // Paso 4: calcular totales y mostrar resumen del pedido.
        double subtotalSinIva = celularElegido.getPrecio() * cantidad;
        double totalConIva = subtotalSinIva * (1 + IVA);
        Marca marca = marcaDao.buscar(celularElegido.getMarcas_fk());
        String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";

        System.out.println("Resumen de su pedido:");
        System.out.println("Celular:             " + nombreMarca + " " + celularElegido.getModelo());
        System.out.println("Cantidad:            " + cantidad);
        System.out.println("Precio unitario:     $" + celularElegido.getPrecio());
        System.out.println("Subtotal (sin IVA):  $" + subtotalSinIva);
        System.out.println("IVA (19%):           $" + (totalConIva - subtotalSinIva));
        System.out.println("Total a pagar:       $" + totalConIva);

        // Paso 5: selección del método de pago.
        int metodoPagoOp = menu.mostrarMenuMetodoPago();
        if (metodoPagoOp == 3) {
            System.out.println("Compra cancelada.");
            return;
        }

        String metodoPago;
        boolean pagoExitoso;

        if (metodoPagoOp == 1) {
            // Pago con Tarjeta.
            metodoPago = "Tarjeta";
            pagoExitoso = procesarPagoTarjeta();
        } else {
            // Pago con Nequi.
            metodoPago = "Nequi";
            pagoExitoso = procesarPagoNequi(usuarioActivo);
        }

        if (!pagoExitoso) {
            System.out.println("El pago no pudo ser procesado. Compra cancelada.");
            return;
        }

        // Paso 6: registrar la venta y el detalle en la base de datos.
        Venta venta = new Venta(0, usuarioActivo.getId(), null, totalConIva);
        int idVentaGenerado = ventaDao.crearYRetornarId(venta);

        if (idVentaGenerado == -1) {
            System.out.println("Error al registrar la venta. Intente de nuevo.");
            return;
        }

        DetalleVenta detalle = new DetalleVenta(
                0, idVentaGenerado, celularElegido.getId(),
                cantidad, celularElegido.getPrecio(), totalConIva
        );
        detalleVentaDao.crear(detalle);

        // Paso 7: descontar el stock del celular vendido.
        celularesDao.descontarStock(celularElegido.getId(), cantidad);

        // Paso 8: obtener datos del cliente para el reporte.
        Persona persona = personasDao.buscarPorId(usuarioActivo.getPersonas_fk());
        SistemaOperativo so = soDao.buscar(celularElegido.getSistemas_operativos_fk());
        String fechaStr = java.time.LocalDate.now().toString();
        String nombreCliente = persona != null ? persona.getNombre() : "Desconocido";

        // Paso 9: crear y persistir el reporte de la venta (patrón DatosProducto de BodegasFileObject).
        ReporteVenta reporte = new ReporteVenta(
                idVentaGenerado,
                fechaStr,
                usuarioActivo.getUsername(),
                nombreCliente,
                celularElegido.getModelo(),
                nombreMarca,
                cantidad,
                celularElegido.getPrecio(),
                subtotalSinIva,
                totalConIva,
                metodoPago
        );
        datosReporte.guardar(reporte);

        // Paso 10: mostrar comprobante de compra.
        System.out.println("Compra realizada con exito!");
        System.out.println("ID de su venta:      " + idVentaGenerado);
        System.out.println("Celular:             " + nombreMarca + " " + celularElegido.getModelo());
        System.out.println("Cantidad:            " + cantidad);
        System.out.println("Total pagado:        $" + totalConIva);
        System.out.println("Metodo de pago:      " + metodoPago);
        System.out.println("Fecha:               " + fechaStr);
        System.out.println("Gracias por comprar en TecnoStore S2! xD");
    }

    // Procesamiento de pago con tarjeta (emulado).
    private boolean procesarPagoTarjeta() {
        System.out.println("Pago con tarjeta.");
        System.out.println("Un PIN de verificacion ha sido enviado a su correo registrado.");

        // Validación del número de tarjeta (16 dígitos).
        String numeroTarjeta;
        do {
            System.out.println("Ingrese el numero de su tarjeta (16 digitos):");
            numeroTarjeta = scanner.nextLine().trim();
            if (!(boolean) vRango.validar(numeroTarjeta, 16, 16)) {
                System.out.println("Error: el numero de tarjeta debe tener exactamente 16 digitos.");
                numeroTarjeta = "";
            } else if (!numeroTarjeta.matches("\\d+")) {
                System.out.println("Error: el numero de tarjeta solo debe contener digitos.");
                numeroTarjeta = "";
            }
        } while (numeroTarjeta.isEmpty());

        // Validación del PIN de 6 dígitos (emulado: cualquier PIN de 6 dígitos es válido).
        String pin;
        do {
            System.out.println("Ingrese el PIN de 6 digitos que llego a su correo:");
            pin = scanner.nextLine().trim();
            if (!(boolean) vRango.validar(pin, 6, 6)) {
                System.out.println("Error: el PIN debe tener exactamente 6 digitos.");
                pin = "";
            } else if (!pin.matches("\\d+")) {
                System.out.println("Error: el PIN solo debe contener digitos.");
                pin = "";
            }
        } while (pin.isEmpty());

        System.out.println("Procesando pago con tarjeta...");
        System.out.println("Pago aprobado!");
        return true;
    }

    // Procesamiento de pago con Nequi (emulado).
    private boolean procesarPagoNequi(Usuario usuarioActivo) {
        System.out.println("Pago con Nequi.");

        // Obtiene el teléfono registrado del cliente para validar.
        Persona persona = personasDao.buscarPorId(usuarioActivo.getPersonas_fk());
        String telefonoRegistrado = persona != null ? persona.getTelefono() : "";

        // Validación: el número debe coincidir con el teléfono registrado en la BD.
        String numeroCelular;
        int intentos = 0;
        do {
            System.out.println("Ingrese el numero de celular Nequi registrado en su cuenta:");
            numeroCelular = scanner.nextLine().trim();

            if (!(boolean) vRango.validar(numeroCelular, 10, 14)) {
                numeroCelular = "";
                continue;
            }

            if (!numeroCelular.equals(telefonoRegistrado)) {
                System.out.println("Error: el numero no coincide con el registrado en su cuenta. Intente de nuevo.");
                intentos++;
                numeroCelular = "";
                if (intentos >= 3) {
                    System.out.println("Demasiados intentos fallidos. Pago cancelado.");
                    return false;
                }
            }
        } while (numeroCelular.isEmpty());

        System.out.println("Un PIN de verificacion ha sido enviado a su numero Nequi.");

        // Validación del PIN de 6 dígitos (emulado).
        String pin;
        do {
            System.out.println("Ingrese el PIN de 6 digitos enviado a su Nequi:");
            pin = scanner.nextLine().trim();
            if (!(boolean) vRango.validar(pin, 6, 6)) {
                System.out.println("Error: el PIN debe tener exactamente 6 digitos.");
                pin = "";
            } else if (!pin.matches("\\d+")) {
                System.out.println("Error: el PIN solo debe contener digitos.");
                pin = "";
            }
        } while (pin.isEmpty());

        System.out.println("Procesando pago con Nequi...");
        System.out.println("Pago aprobado!");
        return true;
    }

    // Muestra el catálogo de celulares disponibles con información completa.
    private void mostrarCatalogo(List<Celulares> catalogo) {
        System.out.println("Catalogo de celulares:");
        for (Celulares cel : catalogo) {
            Marca marca = marcaDao.buscar(cel.getMarcas_fk());
            SistemaOperativo so = soDao.buscar(cel.getSistemas_operativos_fk());
            String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";
            String nombreSo = so != null ? so.getNombre() : "Desconocido";
            System.out.println("ID: " + cel.getId()
                    + " | " + nombreMarca + " " + cel.getModelo()
                    + " | SO: " + nombreSo
                    + " | Gama: " + cel.getGama()
                    + " | Stock: " + cel.getStock()
                    + " | Precio: $" + cel.getPrecio());
        }
    }
}

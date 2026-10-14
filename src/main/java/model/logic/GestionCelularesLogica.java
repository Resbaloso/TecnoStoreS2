package model.logic;

import dao.CelularesDao;
import dao.MarcaDao;
import dao.SistemaOperativoDao;
import java.util.List;
import java.util.Scanner;
import model.entities.Celulares;
import model.entities.Marca;
import model.entities.SistemaOperativo;
import view.MenuGestionarCelulares;
import view.validations.data.ValidarDecimal;
import view.validations.data.ValidarEntero;
import view.validations.data.ValidarEnteroGrande;
import view.validations.message.ValidarTexto;
import view.validations.range.validarRangoDato;

// Lógica de negocio del módulo "Gestionar Celulares" del administrador.
// Maneja los CRUD de Marcas, Sistemas Operativos y Celulares.
public class GestionCelularesLogica {

    private final MarcaDao marcaDao = new MarcaDao();
    private final SistemaOperativoDao soDao = new SistemaOperativoDao();
    private final CelularesDao celularesDao = new CelularesDao();
    private final MenuGestionarCelulares menu = new MenuGestionarCelulares();
    private final ValidarEntero vEntero = new ValidarEntero();
    private final ValidarEnteroGrande vEnteroGrande = new ValidarEnteroGrande();
    private final ValidarDecimal vDecimal = new ValidarDecimal();
    private final ValidarTexto vTexto = new ValidarTexto();
    private final validarRangoDato vRango = new validarRangoDato();
    private final Scanner scanner = new Scanner(System.in);

    // MENÚ PRINCIPAL DEL MÓDULO:
    public void ejecutar() {
        int op;
        do {
            op = menu.mostrarMenuCelulares();
            switch (op) {
                case 1 -> ejecutarMenuMarcas();
                case 2 -> ejecutarMenuSistemasOperativos();
                case 3 -> ejecutarMenuCrudCelulares();
                case 4 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 4);
    }

    // CRUD MARCAS:
    private void ejecutarMenuMarcas() {
        int op;
        do {
            op = menu.mostrarMenuMarcas();
            switch (op) {
                case 1 -> agregarMarca();
                case 2 -> actualizarMarca();
                case 3 -> eliminarMarca();
                case 4 -> listarMarcas();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    private void agregarMarca() {
        System.out.println("AGREGAR MARCA:");
        String nombre = pedirNombreUnico("Ingrese el nombre de la marca (ej: Samsung):");
        Marca m = new Marca(0, nombre);
        marcaDao.crear(m);
    }

    private void actualizarMarca() {
        System.out.println("ACTUALIZAR MARCA:");
        listarMarcas();
        long id = vEnteroGrande.validar("Ingrese el ID de la marca a actualizar:");
        if (marcaDao.buscar(id) == null) {
            System.out.println("El ID ingresado no existe.");
            return;
        }
        String nombre = pedirNombreUnico("Ingrese el nuevo nombre de la marca:");
        Marca m = new Marca((int) id, nombre);
        marcaDao.actualizar(m);
    }

    private void eliminarMarca() {
        System.out.println("ELIMINAR MARCA:");
        listarMarcas();
        long id = vEnteroGrande.validar("Ingrese el ID de la marca a eliminar:");
        marcaDao.eliminar(id);
    }

    private void listarMarcas() {
        System.out.println("LISTA DE MARCAS:");
        List<Marca> lista = marcaDao.obtenerMarcas();
        if (lista.isEmpty()) {
            System.out.println("No hay marcas registradas.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // CRUD SISTEMAS OPERATIVOS:
    private void ejecutarMenuSistemasOperativos() {
        int op;
        do {
            op = menu.mostrarMenuSistemasOperativos();
            switch (op) {
                case 1 -> agregarSistemaOperativo();
                case 2 -> actualizarSistemaOperativo();
                case 3 -> eliminarSistemaOperativo();
                case 4 -> listarSistemasOperativos();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    private void agregarSistemaOperativo() {
        System.out.println("AGREGAR SISTEMA OPERATIVO:");
        String nombre = pedirNombreUnico("Ingrese el nombre del sistema operativo (ej: Android):");
        SistemaOperativo so = new SistemaOperativo(0, nombre);
        soDao.crear(so);
    }

    private void actualizarSistemaOperativo() {
        System.out.println("ACTUALIZAR SISTEMA OPERATIVO:");
        listarSistemasOperativos();
        long id = vEnteroGrande.validar("Ingrese el ID del sistema operativo a actualizar:");
        if (soDao.buscar(id) == null) {
            System.out.println("El ID ingresado no existe.");
            return;
        }
        String nombre = pedirNombreUnico("Ingrese el nuevo nombre del sistema operativo:");
        SistemaOperativo so = new SistemaOperativo((int) id, nombre);
        soDao.actualizar(so);
    }

    private void eliminarSistemaOperativo() {
        System.out.println("ELIMINAR SISTEMA OPERATIVO:");
        listarSistemasOperativos();
        long id = vEnteroGrande.validar("Ingrese el ID del sistema operativo a eliminar:");
        soDao.eliminar(id);
    }

    private void listarSistemasOperativos() {
        System.out.println("LISTA DE SISTEMAS OPERATIVOS:");
        List<SistemaOperativo> lista = soDao.obtenerSistemasOperativos();
        if (lista.isEmpty()) {
            System.out.println("No hay sistemas operativos registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    // CRUD CELULARES:
    private void ejecutarMenuCrudCelulares() {
        int op;
        do {
            op = menu.mostrarMenuCrudCelulares();
            switch (op) {
                case 1 -> agregarCelular();
                case 2 -> actualizarCelular();
                case 3 -> eliminarCelular();
                case 4 -> listarCelulares();
                case 5 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción no encontrada!");
            }
        } while (op != 5);
    }

    private void agregarCelular() {
        System.out.println("AGREGAR CELULAr:");

        // SKU: texto alfanumérico con rango.
        String sku = pedirTextoConRango("Ingrese el SKU del celular (ej: SM-A545G-M-128):", 3, 45);

        // Modelo.
        String modelo = pedirTextoConRango("Ingrese el modelo del celular (ej: Samsung A54 5g 128gb):", 2, 45);

        // Marca: se lista y se selecciona por ID.
        listarMarcas();
        int marcaId = seleccionarIdValido("Ingrese el ID de la marca:", marcaDao.obtenerMarcas().size());

        // Stock: entero positivo.
        int stock = pedirEnteroPositivo("Ingrese el stock del celular:");

        // Precio: decimal positivo.
        double precio = pedirDecimalPositivo("Ingrese el precio del celular (ej: 1500000.00):");

        // Sistema operativo: se lista y se selecciona por ID.
        listarSistemasOperativos();
        int soId = seleccionarIdValido("Ingrese el ID del sistema operativo:", soDao.obtenerSistemasOperativos().size());

        // Gama: baja, media, alta.
        String gama = seleccionarGama();

        Celulares celular = new Celulares(0, sku, modelo, marcaId, stock, precio, soId, gama);
        celularesDao.crear(celular);
    }

    private void actualizarCelular() {
        System.out.println("ACTUALIZAR CELULAR:");
        listarCelulares();
        long id = vEnteroGrande.validar("Ingrese el ID del celular a actualizar:");
        if (celularesDao.buscar(id) == null) {
            System.out.println("El ID ingresado no existe.");
            return;
        }

        String sku = pedirTextoConRango("Ingrese el nuevo SKU:", 3, 45);
        String modelo = pedirTextoConRango("Ingrese el nuevo modelo:", 2, 45);
        listarMarcas();
        int marcaId = seleccionarIdValido("Ingrese el ID de la nueva marca:", marcaDao.obtenerMarcas().size());
        int stock = pedirEnteroPositivo("Ingrese el nuevo stock:");
        double precio = pedirDecimalPositivo("Ingrese el nuevo precio:");
        listarSistemasOperativos();
        int soId = seleccionarIdValido("Ingrese el ID del nuevo sistema operativo:", soDao.obtenerSistemasOperativos().size());
        String gama = seleccionarGama();

        Celulares celular = new Celulares((int) id, sku, modelo, marcaId, stock, precio, soId, gama);
        celularesDao.actualizar(celular);
    }

    private void eliminarCelular() {
        System.out.println("ELIMINAR CELULAR:");
        listarCelulares();
        long id = vEnteroGrande.validar("Ingrese el ID del celular a eliminar:");
        celularesDao.eliminar(id);
    }

    public void listarCelulares() {
        System.out.println("LISTA DE CELULARES:");
        List<Celulares> lista = celularesDao.obtenerCelulares();
        if (lista.isEmpty()) {
            System.out.println("No hay celulares registrados.");
        } else {
            // Mostramos el catálogo con los nombres de marca y sisttemas operativos resueltos.
            for (Celulares cel : lista) {
                Marca marca = marcaDao.buscar(cel.getMarcas_fk());
                SistemaOperativo so = soDao.buscar(cel.getSistemas_operativos_fk());
                String nombreMarca = marca != null ? marca.getNombre() : "Desconocida";
                String nombreSo = so != null ? so.getNombre() : "Desconocido";
                System.out.println("ID: " + cel.getId()
                        + " | SKU: " + cel.getSku()
                        + " | Modelo: " + cel.getModelo()
                        + " | Marca: " + nombreMarca
                        + " | Stock: " + cel.getStock()
                        + " | Precio: $" + cel.getPrecio()
                        + " | SO: " + nombreSo
                        + " | Gama: " + cel.getGama());
            }
        }
    }

    // MÉTODOS AUXILIARES DE VALIDACIONES

    // Pide un nombre de texto con validación de rango.
    private String pedirNombreUnico(String mensaje) {
        String nombre;
        do {
            System.out.println(mensaje);
            nombre = scanner.nextLine().trim();
            if (!(boolean) vRango.validar(nombre, 1, 45)) {
                nombre = "";
            }
        } while (nombre.isEmpty());
        return nombre;
    }

    // Pide un texto con rango de caracteres configurable.
    private String pedirTextoConRango(String mensaje, int min, int max) {
        String texto;
        do {
            System.out.println(mensaje);
            texto = scanner.nextLine().trim();
            if (!(boolean) vRango.validar(texto, min, max)) {
                texto = "";
            }
        } while (texto.isEmpty());
        return texto;
    }

    // Pide un entero positivo (precio o stock no puede ser <= 0).
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

    // Pide un decimal positivo.
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

    // Selecciona un ID válido dentro del rango de una lista.
    private int seleccionarIdValido(String mensaje, int totalRegistros) {
        if (totalRegistros == 0) {
            System.out.println("No hay registros disponibles. Registre uno primero.");
            return 1;
        }
        int id;
        do {
            id = vEntero.validar(mensaje);
            if (id <= 0) {
                System.out.println("Error: ingrese un ID válido (mayor a 0).");
            }
        } while (id <= 0);
        return id;
    }

    // Selecciona la gama del celular (baja, media, alta).
    private String seleccionarGama() {
        int op;
        do {
            op = menu.mostrarMenuGama();
            if (op < 1 || op > 3) {
                System.out.println("Opción inválida, seleccione entre 1 y 3.");
            }
        } while (op < 1 || op > 3);
        return switch (op) {
            case 1 -> "baja";
            case 2 -> "media";
            case 3 -> "alta";
            default -> "baja";
        };
    }
}

package model.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.entities.ReporteVenta;

// Clase de lógica para gestionar la persistencia de reportes de ventas en archivo binario y texto.
// Basada en el patrón de DatosProducto de BodegasFileObject: carga, guarda y reemplaza con ObjectStream.
public class DatosReporteVenta {

    // Nombre del archivo de objetos serializados (patrón de BodegasFileObject).
    private final String archivoObjetos = "reporte_ventas.dat";

    // Nombre del archivo de texto legible solicitado en los requerimientos.
    private final String archivoTexto = "reporte_ventas.txt";

    // Lista en memoria con todos los reportes cargados.
    private List<ReporteVenta> reportes = new ArrayList<>();

    // Constructor: carga los datos del archivo al iniciar, igual que DatosProducto.
    public DatosReporteVenta() {
        cargar();
    }

    // Carga la lista de reportes desde el archivo de objetos (ObjectInputStream con try-with-resources).
    public void cargar() {
        File f = new File(archivoObjetos);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoObjetos))) {
                reportes = (List<ReporteVenta>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Advertencia al cargar reportes: " + e.getMessage());
            }
        }
    }

    // Guarda (sobreescribe) el archivo de objetos con la lista actual.
    // Patrón reemplazar() de BodegasFileObject.
    public void reemplazar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoObjetos))) {
            oos.writeObject(reportes);
        } catch (Exception e) {
            System.out.println("Error al guardar el reporte: " + e.getMessage());
        }
    }

    // Agrega un nuevo reporte a la lista y persiste el archivo.
    public void guardar(ReporteVenta reporte) {
        reportes.add(reporte);
        reemplazar();
        // También actualiza el archivo de texto legible.
        generarArchivoTexto();
    }

    // Retorna la lista actual de reportes recargando el archivo.
    public List<ReporteVenta> obtenerLista() {
        cargar();
        return reportes;
    }

    // Genera el archivo reporte_ventas.txt con resumen de todas las ventas.
    // Usa try-with-resources (BufferedWriter) según requerimientos de persistencia.
    public void generarArchivoTexto() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTexto))) {
            bw.write("============================================================");
            bw.newLine();
            bw.write("          REPORTE GENERAL DE VENTAS - TECNOSTORE S2         ");
            bw.newLine();
            bw.write("============================================================");
            bw.newLine();
            bw.newLine();

            if (reportes.isEmpty()) {
                bw.write("No hay ventas registradas aún.");
                bw.newLine();
            } else {
                // ---- DETALLE POR VENTA ----
                bw.write("--- DETALLE DE VENTAS ---");
                bw.newLine();
                for (ReporteVenta rv : reportes) {
                    bw.write(rv.toString());
                    bw.newLine();
                }

                // ---- TOP 3 CELULARES MÁS VENDIDOS (Stream API) ----
                bw.write("--- TOP 3 CELULARES MÁS VENDIDOS ---");
                bw.newLine();
                Map<String, Integer> ventasPorModelo = reportes.stream()
                        .collect(Collectors.groupingBy(
                                rv -> rv.getMarcaCelular() + " " + rv.getModeloCelular(),
                                Collectors.summingInt(ReporteVenta::getCantidad)
                        ));

                ventasPorModelo.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(3)
                        .forEach(entry -> {
                            try {
                                bw.write("  - " + entry.getKey() + ": " + entry.getValue() + " unidades vendidas");
                                bw.newLine();
                            } catch (Exception e) {
                                System.out.println("Error escribiendo top 3: " + e.getMessage());
                            }
                        });
                bw.newLine();

                // ---- VENTAS TOTALES POR MES (Stream API) ----
                bw.write("--- VENTAS TOTALES POR MES ---");
                bw.newLine();
                Map<String, Double> totalPorMes = reportes.stream()
                        .collect(Collectors.groupingBy(
                                rv -> rv.getFechaVenta().substring(0, 7), // Toma "YYYY-MM"
                                Collectors.summingDouble(ReporteVenta::getTotalConIva)
                        ));

                totalPorMes.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            try {
                                bw.write("  - " + entry.getKey() + ": $" + entry.getValue());
                                bw.newLine();
                            } catch (Exception e) {
                                System.out.println("Error escribiendo ventas por mes: " + e.getMessage());
                            }
                        });
                bw.newLine();

                // ---- CELULARES CON STOCK BAJO (Stream API) ----
                // Nota: Este reporte se genera en el módulo de reportes de la vista.
                // ---- TOTAL GENERAL ----
                double totalGeneral = reportes.stream()
                        .mapToDouble(ReporteVenta::getTotalConIva)
                        .sum();
                bw.write("--- TOTAL GENERAL DE VENTAS ---");
                bw.newLine();
                bw.write("  Total acumulado: $" + totalGeneral);
                bw.newLine();
            }

            bw.write("============================================================");
            bw.newLine();
            System.out.println("Reporte guardado en: " + archivoTexto);

        } catch (Exception e) {
            System.out.println("Error al generar el archivo de reporte: " + e.getMessage());
        }
    }
}

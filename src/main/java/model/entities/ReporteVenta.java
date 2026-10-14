package model.entities;

import java.io.Serializable;

// Entidad serializable que representa el resumen en el reportes_ventas.txt.
public class ReporteVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idVenta;
    private String fechaVenta;
    private String usernameCliente;
    private String nombreCliente;
    private String modeloCelular;
    private String marcaCelular;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private double totalConIva;
    private String metodoPago;

    public ReporteVenta(int idVenta, String fechaVenta, String usernameCliente, String nombreCliente,
            String modeloCelular, String marcaCelular, int cantidad, double precioUnitario,
            double subtotal, double totalConIva, String metodoPago) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.usernameCliente = usernameCliente;
        this.nombreCliente = nombreCliente;
        this.modeloCelular = modeloCelular;
        this.marcaCelular = marcaCelular;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.totalConIva = totalConIva;
        this.metodoPago = metodoPago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public String getUsernameCliente() {
        return usernameCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getModeloCelular() {
        return modeloCelular;
    }

    public String getMarcaCelular() {
        return marcaCelular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotalConIva() {
        return totalConIva;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    @Override
    public String toString() {
        return """
               ====================================================
               ID Venta:          %s
               Fecha:             %s
               Cliente:           %s (%s)
               Celular:           %s - %s
               Cantidad:          %s
               Precio unitario:   $%s
               Subtotal:          $%s
               Total con IVA:     $%s
               Metodo de pago:    %s
               ====================================================
               """.formatted(idVenta, fechaVenta, nombreCliente, usernameCliente,
                marcaCelular, modeloCelular, cantidad, precioUnitario, subtotal, totalConIva, metodoPago);
    }
}

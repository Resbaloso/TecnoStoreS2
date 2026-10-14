package model.entities;

// Entidad que representa un detalle de venta.
public class DetalleVenta {

    private int id;
    private int ventas_fk;
    private int celulares_fk;
    private int cantidad;
    private double precio;   // decimal(10,2) en la BD.
    private double subtotal;

    public DetalleVenta(int id, int ventas_fk, int celulares_fk, int cantidad, double precio, double subtotal) {
        this.id = id;
        this.ventas_fk = ventas_fk;
        this.celulares_fk = celulares_fk;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
    }

    public int getId() {
        return id;
    }

    public int getVentas_fk() {
        return ventas_fk;
    }

    public void setVentas_fk(int ventas_fk) {
        this.ventas_fk = ventas_fk;
    }

    public int getCelulares_fk() {
        return celulares_fk;
    }

    public void setCelulares_fk(int celulares_fk) {
        this.celulares_fk = celulares_fk;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Getter con nombre original para no romper compatibilidad con los DAO existentes.
    public double getPrecios_fk() {
        return precio;
    }

    public void setPrecios_fk(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return """
                    Id:                           %s
                    Ventas_fk:                    %s
                    Celulares_fk:                 %s
                    Cantidad:                     %s
                    Precio:                       $%s
                    Subtotal:                     $%s
                    """.formatted(id, ventas_fk, celulares_fk, cantidad, precio, subtotal);
    }
}

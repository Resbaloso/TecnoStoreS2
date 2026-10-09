package model.entities;

public class DetalleVenta {
    private int id;
    private int ventas_fk;
    private int celulares_fk;
    private int cantidad;
    private int precio;
    private Double subtotal;

    public DetalleVenta(int id, int ventas_fk, int celulares_fk, int cantidad, int precio, Double subtotal) {
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

    public int getPrecios_fk() {
        return precio;
    }

    public void setPrecios_fk(int precios_fk) {
        this.precio = precios_fk;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
    return """
                Id:                           %s
                Ventas_fk:                    %s
                Celulares_fk:                 %s
                Cantidad:                     %s
                Precios:                      %s
                Subtotal:                     %s
                """.formatted(id, ventas_fk, celulares_fk, cantidad, precio, subtotal);
    }
    
}

package model.entities;

public class DetalleVenta {
    private Integer id;
    private Integer ventas_fk;
    private Integer celulares_fk;
    private Integer cantidad;
    private Integer precios_fk;
    private Double subtotal;

    public DetalleVenta(Integer id, Integer ventas_fk, Integer celulares_fk, Integer cantidad, Integer precios_fk, Double subtotal) {
        this.id = id;
        this.ventas_fk = ventas_fk;
        this.celulares_fk = celulares_fk;
        this.cantidad = cantidad;
        this.precios_fk = precios_fk;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public Integer getVentas_fk() {
        return ventas_fk;
    }

    public void setVentas_fk(Integer ventas_fk) {
        this.ventas_fk = ventas_fk;
    }

    public Integer getCelulares_fk() {
        return celulares_fk;
    }

    public void setCelulares_fk(Integer celulares_fk) {
        this.celulares_fk = celulares_fk;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecios_fk() {
        return precios_fk;
    }

    public void setPrecios_fk(Integer precios_fk) {
        this.precios_fk = precios_fk;
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
                Precios_fk:                   %s
                Subtotal:                     %s
                """.formatted(id, ventas_fk, celulares_fk, cantidad, precios_fk, subtotal);
    }
    
}

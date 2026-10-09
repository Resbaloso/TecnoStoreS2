
package model.entities;

public class Precio {
    private Integer id;
    private double precio;

    public Precio(Integer id, double precio) {
        this.id = id;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public double getNombre() {
        return precio;
    }

    public void setNombre(double nombre) {
        this.precio = nombre;
    }

    @Override
    public String toString() {
    return """
               Id:                              %s
               Precio:                          %s
               """.formatted(id, precio);
    }
}
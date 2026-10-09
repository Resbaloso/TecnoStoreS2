package model.entities;

public class Marca {
    private Integer id;
    private String nombre;

    public Marca(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
    return """
               Id:                              %s
               Nombre:                          %s
               """.formatted(id, nombre);
    }
}
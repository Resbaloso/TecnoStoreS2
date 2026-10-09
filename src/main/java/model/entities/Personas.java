package model.entities;

public class Personas {
    private Integer id;
    private String nombre;
    private String identificacion;
    private String correo;
    private Integer telefono;

    public Personas(Integer id, String nombre, String identificacion, String correo, Integer telefono) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
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

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
    return """
                Id:                              %s
                Nombre:                          %s
                Identificacion:                  %s
                Correo:                          %s
                Telefono:                        %s
                """.formatted(id, nombre, identificacion, correo, telefono);
    }  
}
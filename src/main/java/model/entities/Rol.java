package model.entities;

public class Rol {
    private Integer id;
    private String rol;

    public Rol(Integer id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    } 

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    @Override
    public String toString() {
    return """
               Id:          %s
               Rol:         %s
               """.formatted(id, rol);
    }
}

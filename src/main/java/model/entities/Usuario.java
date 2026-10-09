package model.entities;

public class Usuario {
    private int id;
    private int personas_fk;
    private String username;
    private String contraseña;
    private int roles_fk;    

    public Usuario(int id, int personas_fk, String username, String contraseña, int roles_fk) {
        this.id = id;
        this.personas_fk = personas_fk;
        this.username = username;
        this.contraseña = contraseña;
        this.roles_fk = roles_fk;
    }

    public int getId() {
        return id;
    }

    public int getPersonas_fk() {
        return personas_fk;
    }

    public void setPersonas_fk(int personas_fk) {
        this.personas_fk = personas_fk;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Integer getRoles_fk() {
        return roles_fk;
    }

    public void setRoles_fk(int roles_fk) {
        this.roles_fk = roles_fk;
    }

    @Override
    public String toString() {
    return """
                Id:                              %s
                Persona_fk:                      %s
                Username:                        %s
                Contraseña:                      %s
                Roles_fk:                        %s
                """.formatted(id, personas_fk, username, contraseña, roles_fk);
    }
}

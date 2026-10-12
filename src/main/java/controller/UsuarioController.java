package controller;

import dao.PersonasDao;
import dao.UsuarioDao;
import model.entities.Persona;
import model.entities.Usuario;

public class UsuarioController {
    private final PersonasDao personasDao = new PersonasDao();
    private final UsuarioDao usuarioDao = new UsuarioDao();

    public boolean existeIdentificacion(String identificacion) {
        return personasDao.existeIdentificacion(identificacion);
    }

    public boolean existeCorreo(String correo) {
        return personasDao.existeCorreo(correo);
    }

    public boolean existeUsername(String username) {
        return usuarioDao.existeUsername(username);
    }

    public Usuario iniciarSesion(String username, String password) {
        return usuarioDao.iniciarSesion(username, password);
    }

    // Coordina la lógica de negocio para registrar persona y luego su usuario
    public boolean registrarUsuarioCompleto(String nombre, String identificacion, String correo, String telefono, String username, String password) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setIdentificacion(identificacion);
        persona.setCorreo(correo);
        persona.setTelefono(telefono);

        boolean personaGuardada = personasDao.insertarPersona(persona);
        if (!personaGuardada) {
            return false;
        }

        int personaId = personasDao.obtenerIdPorIdentificacion(identificacion);
        if (personaId == -1) {
            return false;
        }

        Usuario usuario = new Usuario();
        usuario.setPersonas_fk(personaId);
        usuario.setUsername(username);
        usuario.setContraseña(password);
        usuario.setRoles_fk(2); // Rol predeterminado: Cliente

        return usuarioDao.registrarUsuario(usuario);
    }
}
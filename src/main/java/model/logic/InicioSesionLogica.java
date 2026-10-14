package model.logic;

import dao.PersonasDao;
import dao.UsuarioDao;
import model.entities.Persona;
import model.entities.Usuario;
import view.validations.data.ValidarCorreo;
import view.validations.range.validarRangoDato;

import java.util.Scanner;

public class InicioSesionLogica {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonasDao personasDao = new PersonasDao();
    private static final UsuarioDao usuarioDao = new UsuarioDao();
    private static final ValidarCorreo validadorCorreo = new ValidarCorreo();
    private static final validarRangoDato validadorRango = new validarRangoDato();

//    Se ejecuta el inicio de sesión:
    public static Usuario ejecutarInicioSesion() {
        System.out.println("INICIO DE SESIÓN:");
        System.out.print("Ingrese su usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine().trim();

        Usuario usuario = usuarioDao.iniciarSesion(username, password);
        if (usuario != null) { // Si el usuario existe le da la bienvenida y retorna su objeto.
            System.out.println("¡Bienvenido, " + usuario.getUsername() + "! Inicio de sesión exitoso.");
            return usuario;
        } else {
            System.out.println("Error: Su usuario o contraseña son incorrectos.");
            return null;
        }
    }

    public static void ejecutarRegistroUsuario() {
        System.out.println("REGISTRO DE NUEVO USUARIO:");
        
        // Validacion del Nombre con Rango de 50 caracteres.
        String nombre;
        while (true) {
            System.out.print("Ingrese su nombre: ");
            nombre = scanner.nextLine().trim();
            if (!(boolean) validadorRango.validar(nombre, 1, 50)) {
                continue;
            }
            break;
        }
        
        // Validación de Identificación única con rango.
        String identificacion;
        while (true) {
            System.out.print("Ingrese su número de identificación (ej: 1099405755): ");
            identificacion = scanner.nextLine().trim();
            if (!(boolean) validadorRango.validar(identificacion, 10, 10)) {
                continue;
            }
            if (personasDao.existeIdentificacion(identificacion)) {
                System.out.println("Esta identificación ya está registrada. Ingrese otra.");
            } else {
                break;
            }
        }

        // Validación de Correo con rango, formato (@ y .) y unico.
        String correo;
        while (true) {
            System.out.print("Ingrese su correo electrónico (ej: usuario@dominio.com): ");
            correo = scanner.nextLine().trim();
            
            if (!(boolean) validadorRango.validar(correo, 7, 45)) {
                continue;
            }
            if (!validadorCorreo.validar(correo)) {
                System.out.println("Correo inválido. Debe contener '@' y '.' obligatoriamente.");
                continue;
            }
            if (personasDao.existeCorreo(correo)) {
                System.out.println("Este correo ya se encuentra registrado en el sistema.");
            } else {
                break;
            }
        }

        // Validación de Teléfono con rango.
        String telefono;
        while (true) {
            System.out.print("Ingrese su teléfono (ej: +57 3012345678 o 3012345678): ");
            telefono = scanner.nextLine().trim();
            if (!(boolean) validadorRango.validar(telefono, 10, 14)) {
                continue;
            }
            break;
        }

        // Validación de Username único con rango.
        String username;
        while (true) {
            System.out.print("Cree su nombre de usuario (username): ");
            username = scanner.nextLine().trim();
            if (!(boolean) validadorRango.validar(username, 1, 20)) {
                continue;
            }
            if (usuarioDao.existeUsername(username)) {
                System.out.println("Este nombre de usuario ya está ocupado. Elija otro.");
            } else {
                break;
            }
        }

        // Validación de Contraseña con rango
        String password;
        while (true) {
            System.out.print("Cree su contraseña: ");
            password = scanner.nextLine().trim();
            if (!(boolean) validadorRango.validar(password, 8, 16)) {
                continue;
            }
            break;
        }

        // Guardar Persona
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setIdentificacion(identificacion);
        nuevaPersona.setCorreo(correo);
        nuevaPersona.setTelefono(telefono);

        boolean personaGuardada = personasDao.insertarPersona(nuevaPersona);
        
        if (personaGuardada) { // Si guardar la persona fu exitoso.
            int personaId = personasDao.obtenerIdPorIdentificacion(identificacion);
            
            if (personaId != -1) { // Si el ID es valido entonces se crea el objeto con base a la persona.
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setPersonas_fk(personaId);
                nuevoUsuario.setUsername(username);
                nuevoUsuario.setContraseña(password);
                nuevoUsuario.setRoles_fk(2); // Rol Cliente por defecto.

                boolean registrado = usuarioDao.registrarUsuario(nuevoUsuario);
                if (registrado) {
                    System.out.println("¡Cuenta creada exitosamente! Ahora puede iniciar sesión.");
                } else {
                    System.out.println("Error al registrar las credenciales de usuario.");
                }
            } else {
                System.out.println("Error al obtener el ID de la persona registrada.");
            }
        } else {
            System.out.println("Error al registrar los datos personales.");
        }
    }
}
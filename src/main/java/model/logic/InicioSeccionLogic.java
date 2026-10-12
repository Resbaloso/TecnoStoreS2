package model.logic;

import dao.PersonasDao;
import dao.UsuarioDao;
import model.entities.Persona;
import model.entities.Usuario;
import view.InicioSeccion;
import view.validations.data.ValidarCorreo;

import java.util.Scanner;

public class InicioSeccionLogic {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PersonasDao personasDao = new PersonasDao();
    private static final UsuarioDao usuarioDao = new UsuarioDao();
    private static final ValidarCorreo validadorCorreo = new ValidarCorreo();

    public static Usuario ejecutarInicioSesion() {
        System.out.println("INICIO DE SESIÓN:");
        System.out.print("Ingrese su usuario: ");
        String username = scanner.nextLine().trim();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine().trim();

        Usuario usuario = usuarioDao.iniciarSesion(username, password);
        if (usuario != null) {
            System.out.println("¡Bienvenido, " + usuario.getUsername() + "! Inicio de sesión exitoso.");
            return usuario;
        } else {
            System.out.println("Error: Su usuario o contraseña son incorrectos.");
            return null;
        }
    }

    public static void ejecutarRegistroUsuario() {
        System.out.println("REGISTRO DE NUEVO USUARIO:");
        
        System.out.print("Ingrese su nombre completo: ");
        String nombre = scanner.nextLine().trim();

        // Validación de Identificación única
        String identificacion;
        while (true) {
            System.out.print("Ingrese su número de identificación: ");
            identificacion = scanner.nextLine().trim();
            if (identificacion.isEmpty()) {
                System.out.println("La identificación no puede estar vacía.");
                continue;
            }
            if (personasDao.existeIdentificacion(identificacion)) {
                System.out.println("Esta identificación ya está registrada. Ingrese otra.");
            } else {
                break;
            }
        }

        // Validación de Correo con Stream API (debe contener "@" y ".") y unicidad en BD
        String correo;
        while (true) {
            System.out.print("Ingrese su correo electrónico (ej: usuario@dominio.com): ");
            correo = scanner.nextLine().trim();
            
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

        System.out.print("Ingrese su teléfono (ej: +57 3012345678 o 3012345678): ");
        String telefono = scanner.nextLine().trim();

        // Validación de Username único
        String username;
        while (true) {
            System.out.print("Cree su nombre de usuario (username): ");
            username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("El usuario no puede estar vacío.");
                continue;
            }
            if (usuarioDao.existeUsername(username)) {
                System.out.println("Este nombre de usuario ya está ocupado. Elija otro.");
            } else {
                break;
            }
        }

        System.out.print("Cree su contraseña: ");
        String password = scanner.nextLine().trim();

        // Guardar Persona
        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setIdentificacion(identificacion);
        nuevaPersona.setCorreo(correo);
        nuevaPersona.setTelefono(telefono);

        boolean personaGuardada = personasDao.insertarPersona(nuevaPersona);
        
        if (personaGuardada) {
            int personaId = personasDao.obtenerIdPorIdentificacion(identificacion);
            
            if (personaId != -1) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setPersonas_fk(personaId);
                nuevoUsuario.setUsername(username);
                nuevoUsuario.setContraseña(password);
                nuevoUsuario.setRoles_fk(2); // Rol Cliente por defecto

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
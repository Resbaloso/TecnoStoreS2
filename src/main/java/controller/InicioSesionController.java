package controller;

import model.entities.Usuario;
import model.logic.InicioSesionLogica;
import view.InicioSesion;

public class InicioSesionController {

    public static Usuario iniciarFlujoIngreso() {
        InicioSesion i = new InicioSesion();
        int opcion;
        do {
//              Mostramos el menú de la vista y tomamos la opción.
            opcion = i.mostrarMenuPrincipal();

//             Según la opción registro o inicio de sesión.
            switch (opcion) {
                case 1 -> {
                    Usuario usuarioLogueado = InicioSesionLogica.ejecutarInicioSesion();
                    if (usuarioLogueado != null) {
                        return usuarioLogueado; // Retorna el usuario autenticado para entrar al sistema
                    }
                }
                case 2 -> {
                    InicioSesionLogica.ejecutarRegistroUsuario();
                }
                case 3 -> {
                    System.out.println("Saliendo...");
                }
                default -> {
                    System.out.println("Opción incorrecta, usa el cerebro bro, pon una de las opciones. xd");
                }
            }
        } while (opcion != 3);
            return null; // Retorna nulo si el usuario decidió salir.
    }
}
package controller;

import model.entities.Usuario;
import model.logic.InicioSesionLogic;
import view.InicioSesion;

public class InicioSesionController {

    public static Usuario iniciarFlujoIngreso() {
        InicioSesion i=new InicioSesion();
        int opcion;
        do {
            // Primero mostramos el menú de la vista y tomamos la opción.
            opcion =i.mostrarMenuPrincipal();

            // Según la opción seleccionada: registro o inicio de sección.
            switch (opcion) {
                case 1:
                    Usuario usuarioLogueado = InicioSesionLogic.ejecutarInicioSesion();
                    if (usuarioLogueado != null) {
                        return usuarioLogueado; // Retorna el usuario autenticado para entrar al sistema
                    }
                    break;
                case 2:
                    InicioSesionLogic.ejecutarRegistroUsuario();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);// Cierra y detiene el programa con exito, Su número; Status Code sin errores es (0) y un (1) significa que cerró por errores.
                    break;
                default:
                    System.out.println("Opción incorrecta, usa el cerebro bro, pon una de las opciones. xd");
            }
        } while (true);
    }
}
package controller;

import model.entities.Usuario;
import model.logic.InicioSeccionLogic;
import view.InicioSeccion;

public class InicioSeccionController {

    public static Usuario iniciarFlujoIngreso() {
        InicioSeccion i=new InicioSeccion();
        int opcion;
        do {
            // Primero mostramos el menú de la vista y tomamos la opción.
            opcion =i.mostrarMenuPrincipal();

            // Según la opción seleccionada: registro o inicio de sección.
            switch (opcion) {
                case 1:
                    Usuario usuarioLogueado = InicioSeccionLogic.ejecutarInicioSesion();
                    if (usuarioLogueado != null) {
                        return usuarioLogueado; // Retorna el usuario autenticado para entrar al sistema
                    }
                    break;
                case 2:
                    InicioSeccionLogic.ejecutarRegistroUsuario();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción incorrecta, usa el cerebro bro, pon una de las opciones. xd");
            }
        } while (true);
    }
}
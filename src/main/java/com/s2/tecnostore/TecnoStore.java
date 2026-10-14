package com.s2.tecnostore;

import controller.InicioSesionController;
import controller.MenuAdminController;
import controller.MenuUsuarioController;
import model.entities.Usuario;

// Clase principal de la aplicación TecnoStore S2.
// Login selección de menú según rol.
public class TecnoStore {

    public static void main(String[] args) {

//         Inicia el flujo de login/registro y obtiene el usuario autenticado.
        Usuario usuarioActivo = InicioSesionController.iniciarFlujoIngreso();
//         Despues de terminar el flujo de registro e inicio de sesión valida que tenga rol y si esta activo
//         Nomas como por si las moscas. XD

        if (usuarioActivo == null) {
            System.out.println("No se pudo iniciar sesión. Cerrando el sistema.");
            return;
        }

        System.out.println("Acceso concedido al sistema principal.");

        if (usuarioActivo.getRoles_fk() == null) {
            System.out.println("Acceso denegado: el usuario no tiene un rol válido.");
            return;
        }

//         Selección de menú según el rol del usuario (1 = Admin, 2 = Cliente).
        switch (usuarioActivo.getRoles_fk()) {
            case 1 -> {
                MenuAdminController mac = new MenuAdminController();
                mac.MenuAdmin();
            }
            case 2 -> {
//                 Se pasa el usuarioActivo al controlador para usarlo en las compras.
                MenuUsuarioController muc = new MenuUsuarioController();
                muc.MenuUsuario(usuarioActivo);
            }
            default -> System.out.println("Acceso denegado: rol no reconocido.");
        }
    }
}

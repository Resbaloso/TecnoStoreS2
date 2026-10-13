package com.s2.tecnostore;

import controller.InicioSesionController;
import controller.MenuAdminController;
import controller.MenuUsuarioController;
import model.entities.Usuario;

public class TecnoStore {
    public static void main(String[] args) {
    Usuario usuarioActivo = InicioSesionController.iniciarFlujoIngreso();

        if (usuarioActivo == null) {
            System.out.println("No se pudo iniciar sesión, Credenciales incorrectas.");
            return;
        }
        System.out.println("Acceso concedido al sistema principal.");
        if (usuarioActivo.getRoles_fk() == null) {
            System.out.println("Acceso denegado: Error al acceder al menú, el usuario no es válido.");
            return;
        }
        // Selección de menú según el rol del usuario.
        switch (usuarioActivo.getRoles_fk()) {
            case 1 -> {
                MenuAdminController mac = new MenuAdminController();
                mac.MenuAdmin();
            }
            case 2 -> {
                MenuUsuarioController muc = new MenuUsuarioController();
                muc.MenuUsuario(); // Nota: Asegúrate si en tu clase el método se llama MenuUsuario o MenuUsuairo
            }
            default -> System.out.println("Acceso denegado: Error al acceder al menú, el usuario no es válido.");
        }
    }
}




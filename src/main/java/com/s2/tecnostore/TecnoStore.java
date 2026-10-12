package com.s2.tecnostore;

import controller.InicioSeccionController;
import model.entities.Usuario;

public class TecnoStore {
    public static void main(String[] args) {
        Usuario usuarioActivo = InicioSeccionController.iniciarFlujoIngreso();
        
        if (usuarioActivo != null) {
            System.out.println("\n[Acceso concedido al sistema principal. Rol ID: " + usuarioActivo.getRoles_fk() + "]");
            // Pe pe pe pegate pegate pegate pegate, si tu te pegas yo me vo a pegal maaaaaaa.
        }
    }
}
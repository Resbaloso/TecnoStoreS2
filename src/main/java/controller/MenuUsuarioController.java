package controller;

import model.entities.Usuario;
import model.logic.CompraUsuarioLogica;
import view.MenuPrincipalUsuario;

// Controlador del menú principal del usuario cliente.
// Recibe el usuario activo para pasarlo al módulo de compras.
public class MenuUsuarioController {

    private final MenuPrincipalUsuario mpu = new MenuPrincipalUsuario();
    private final CompraUsuarioLogica compraLogica = new CompraUsuarioLogica();

    public void MenuUsuario(Usuario usuarioActivo) {
        int op;
        do {
            op = mpu.mostrarMenuPrincipalUsuario();
            switch (op) {
                case 1 -> {
                    // Módulo de compra con el usuario activo en sesión.
                    compraLogica.ejecutar(usuarioActivo);
                }
                case 2 -> {
                    System.out.println("Saliendo del sistema...");
                }
                default -> {
                    System.out.println("Opcion no encontrada!");
                }
            }
        } while (op != 2);
    }
}

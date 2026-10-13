package view;

import view.validations.data.ValidarEntero;

public class MenuPrincipalAdmin {
    public int mostrarMenuPrincipalAdmin() {
    ValidarEntero v=new ValidarEntero();
    return v.validar(""" 
                        BIENVENIDO ADMIN
                        Seleccione una opción: 
                            1. Menú Gestionar Celulares.
                            2. Menú ventas.
                            3. Menú reportes.
                            4. Salir.
                    """);
    }
}
package view;

import view.validations.data.ValidarEntero;

public class MenuPrincipalUsuario {
    public int mostrarMenuPrincipalUsuario() {
    ValidarEntero v=new ValidarEntero();
    return v.validar(""" 
                        BIENVENIDO A TECNOSTORE S2
                        Seleccione una opción: 
                            1. Hacer compra.
                            2. Salir
                    """);
    }
}
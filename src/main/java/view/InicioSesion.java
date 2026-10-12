package view;

import java.util.Scanner;
import view.validations.data.ValidarEntero;

public class InicioSesion {
    public int mostrarMenuPrincipal() {
    ValidarEntero v=new ValidarEntero();
    return v.validar(""" 
                        BIENVENIDO A TECNOSTORE S2
                        Seleccione una opción: 
                            1. Iniciar Sesión.
                            2. Registrarse (Crear Cuenta).
                            3. Salir.
                    """);
    }
}
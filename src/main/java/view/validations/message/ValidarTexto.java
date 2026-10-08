/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.validations.message;

import java.util.Scanner;
import view.validations.ValidarConMensaje;

/**
 *
 * @author Usuario
 */
public class ValidarTexto implements ValidarConMensaje{

    @Override
    public Object validar(String mensaje) {
        boolean validacion;
        String texto = "";
        Scanner x = new Scanner(System.in);
        int contador = 0;
        do {
            validacion = true;
            System.out.println(mensaje);
            texto = x.nextLine();
            for (int i = 0; i < texto.length(); i++) {
                contador += texto.charAt(i) == ' ' ? 1 : 0;
                if (!Character.isLetter(texto.charAt(i)) || texto.charAt(i) != ' ') {
                    if (texto.charAt(i) == ' ' && i == 0) {
                        validacion = false;
                        break;
                    }
                }
            }
        } while (validacion == false);
        return texto;
    }
    
}

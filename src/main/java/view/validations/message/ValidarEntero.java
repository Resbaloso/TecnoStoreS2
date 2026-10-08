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
public class ValidarEntero implements ValidarConMensaje{

    @Override
    public Object validar(String mensaje) {
        Scanner x = new Scanner(System.in);
        System.out.println(mensaje);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero.");
            x.next();
        }
        return x.nextInt();
    }
    
}

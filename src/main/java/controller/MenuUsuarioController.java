/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.MenuPrincipalUsuario;

/**
 *
 * @author Usuario
 */
public class MenuUsuarioController {

    MenuPrincipalUsuario mpu= new MenuPrincipalUsuario();
    
    public void MenuUsuario(){
            int op;
            do {
                op = mpu.mostrarMenuPrincipalUsuario();
                switch (op) {
                    case 1 -> {
                        //Menu compra.
                    }
                    case 2 -> {
                        System.out.println("Saliendo...");
                        break;
                    } 

                    default -> {
                        System.out.println("Opcion no encontrada!");
                    }
                }
            } while (op != 2); 
    }
}
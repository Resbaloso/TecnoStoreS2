/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import view.MenuPrincipalAdmin;

/**
 *
 * @author Usuario
 */
public class MenuAdminController {

    MenuPrincipalAdmin mpa= new MenuPrincipalAdmin();
    
    public void MenuAdmin(){
            int op;
            do {
                op = mpa.mostrarMenuPrincipalAdmin();
                switch (op) {
                    case 1 -> {
                        //Menu gestionar celulares.
                    }
                    case 2 -> {
                        //Menu gestionar ventas.
                    } 
                    case 3 -> {
                        //Menu gestionar reportes.
                    }
                    case 4 -> {
                        System.out.println("Saliendo...");
                        break;
                    }
                    default -> {
                        System.out.println("Opcion no encontrada!");
                    }
                }
            } while (op != 4); 
    }
}

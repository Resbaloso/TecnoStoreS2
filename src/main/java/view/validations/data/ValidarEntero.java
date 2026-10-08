package view.validations.data;

import java.util.Scanner;
import view.validations.Validar;

public class ValidarEntero implements Validar<Integer>{

    @Override
    public Integer validar(String entero) {
        Scanner x = new Scanner(System.in);
        System.out.println(entero);
        while (!x.hasNextInt()) {
            System.out.println("Error, se espera un valor entero.");
            x.next();
        }
        return x.nextInt();
    }
    
}

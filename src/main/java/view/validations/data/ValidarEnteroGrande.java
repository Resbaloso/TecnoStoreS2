package view.validations.data;

import java.util.Scanner;
import view.validations.Validar;

public class ValidarEnteroGrande implements Validar<Long>{

    @Override
    public Long validar(String entero_grande) {
        Scanner x = new Scanner(System.in);
        System.out.println(entero_grande);
        while (!x.hasNextLong()) {
            System.out.println("Error, se espera un valor entero.");
            x.next();
        }
        return x.nextLong();
    }
    
}

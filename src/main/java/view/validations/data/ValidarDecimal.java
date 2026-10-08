package view.validations.data;

import java.util.Scanner;
import view.validations.Validar;

public class ValidarDecimal implements Validar<Double>{

    @Override
    public Double validar(String decimal) {
        Scanner x = new Scanner(System.in);
        System.out.println(decimal);
        while (!x.hasNextDouble()) {
            System.out.println("Error, se espera un valor decimal.");
            x.next();
        }
        return x.nextDouble();
    }
    
}

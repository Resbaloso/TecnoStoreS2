package view.validations.option;

import view.validations.ValidarConOpcion;

public class ValidarEnteroMenu implements ValidarConOpcion{

    @Override
    public Object validar(String op) {
        for (int i = 0; i < op.length(); i++) {
            if (!Character.isDigit(op.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
}

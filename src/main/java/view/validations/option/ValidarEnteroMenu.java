package view.validations.option;

import view.validations.Validar;

public class ValidarEnteroMenu implements Validar<Boolean>{

    @Override
    public Boolean validar(String op) {
        for (int i = 0; i < op.length(); i++) {
            if (!Character.isDigit(op.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
}

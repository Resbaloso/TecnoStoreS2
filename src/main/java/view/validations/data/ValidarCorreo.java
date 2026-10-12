package view.validations.data;

import java.util.List;
import view.validations.Validar;

public class ValidarCorreo implements Validar<Boolean> {
    @Override
    public Boolean validar(String correo) {
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        // Validación con Stream API solicitada (verifica si contiene "@.")
        return List.of(correo).stream()
            .anyMatch(c -> c.contains("@") && c.contains("."));
    }
}

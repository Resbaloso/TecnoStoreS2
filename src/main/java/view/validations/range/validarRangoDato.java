package view.validations.range;

import view.validations.ValidarConRango;

public class validarRangoDato implements ValidarConRango{

    @Override
    public Object validar(String dato, int RangoMenor, int RangoMayor) {
    // Validación de nulos
        if (dato == null) {
            System.out.println("Error: El dato no puede ser nulo.");
            return false;
        }

        // Convertimos cualquier tipo de dato (T) a String de forma segura
        String textoDato = String.valueOf(dato);

        // Obtenemos la longitud total del texto/número/correo
        int totalCaracteres = textoDato.length(); 

        // Verificación de rango
        if (totalCaracteres >= RangoMenor && totalCaracteres <= RangoMayor) {
            return true; // Sin error, está dentro del rango
        } else {
            System.out.println("Error, la cantidad de caracteres debe ser de: " + RangoMenor + " a " + RangoMayor);
            return false;
        }
    }

}

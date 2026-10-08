
package view.validations;

// La <T> es un parametro que representa el tipo genérico (Type)
// Este paramatro <T> permite trabajar en clases e interfaces como cualquier tipo de dato de forma segura
// Es como cuando el Object puede representar cualquier tipo de dato, pero con esteroides. XD
public interface Validar <T>{
    T validar(String dato);
}

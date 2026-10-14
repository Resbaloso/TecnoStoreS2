package view;

import view.validations.data.ValidarEntero;

// Vista del menú de compra del usuario cliente.
public class MenuCompraUsuario {

    // Menú de selección del método de pago.
    public int mostrarMenuMetodoPago() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- SELECCIONE SU MÉTODO DE PAGO --
                                1. Tarjeta de crédito/débito.
                                2. Nequi.
                                3. Cancelar compra.
                        """);
    }
}

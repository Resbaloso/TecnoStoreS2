package view;

import view.validations.data.ValidarEntero;

// Vista del menú de gestión de celulares del administrador.
// Incluye submenús para las entidades dependientes (marcas y sistemas operativos).
public class MenuGestionarCelulares {

    // Menú principal del módulo de celulares.
    public int mostrarMenuCelulares() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            MENÚ GESTIONAR CELULARES
                            Seleccione una opción:
                                1. Gestionar Marcas.
                                2. Gestionar Sistemas Operativos.
                                3. Gestionar Celulares.
                                4. Volver al menú principal.
                        """);
    }

    // Submenú CRUD para Marcas.
    public int mostrarMenuMarcas() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- GESTIONAR MARCAS --
                            Seleccione una opción:
                                1. Agregar Marca.
                                2. Actualizar Marca.
                                3. Eliminar Marca.
                                4. Listar Marcas.
                                5. Volver.
                        """);
    }

    // Submenú CRUD para Sistemas Operativos.
    public int mostrarMenuSistemasOperativos() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- GESTIONAR SISTEMAS OPERATIVOS --
                            Seleccione una opción:
                                1. Agregar Sistema Operativo.
                                2. Actualizar Sistema Operativo.
                                3. Eliminar Sistema Operativo.
                                4. Listar Sistemas Operativos.
                                5. Volver.
                        """);
    }

    // Submenú CRUD para Celulares.
    public int mostrarMenuCrudCelulares() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- GESTIONAR PRODUCTOS CELULARES --
                            Seleccione una opción:
                                1. Agregar Celular.
                                2. Actualizar Celular.
                                3. Eliminar Celular.
                                4. Listar Celulares.
                                5. Volver.
                        """);
    }

    // Menú de selección de gama del celular.
    public int mostrarMenuGama() {
        ValidarEntero v = new ValidarEntero();
        return v.validar("""
                            -- SELECCIONE LA GAMA --
                                1. Baja.
                                2. Media.
                                3. Alta.
                        """);
    }
}

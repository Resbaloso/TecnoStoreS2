# Sistema de Ventas de Celulares - TecnoStore S2 🛒📱

Tecnologías y Lenguajes: Java 17, MySQL, JDBC, Maven 🚀🔥

## Descripción: 😉

TecnoStore S2 es un sistema de consola desarrollado en Java que automatiza el control de ventas, inventario y clientes de una tienda minorista de teléfonos celulares. El sistema aplica los principios de Programación Orientada a Objetos, el patrón de diseño MVC, los principios SOLID, manejo de colecciones, excepciones, persistencia en archivos y conexión a base de datos con JDBC.

## ¿Qué soluciona? 🤨

TecnoStore manejaba todo su inventario, clientes y ventas de forma manual en hojas de cálculo, lo que generaba errores, pérdida de información y falta de control. Este sistema centraliza y automatiza esos procesos, permitiendo registrar productos, gestionar clientes, procesar ventas con cálculo de IVA, controlar el stock en tiempo real y generar reportes en archivos de texto de forma inmediata.

## Funcionamiento y estructura: 😃

El proyecto sigue la arquitectura MVC y se organiza en los siguientes paquetes:

### com.s2.tecnostore (Main):

Contiene la clase principal `TecnoStore.java` que orquesta el flujo de la aplicación:

- Inicia el módulo de login y registro.
- Autentica al usuario y determina su rol.
- Redirige al menú correspondiente según si es **Admin** (rol 1) o **Cliente** (rol 2).

### controller:

Contiene los controladores que conectan las vistas con la lógica:

- **`InicioSesionController`:** Gestiona el flujo de login y registro de nuevos usuarios.
- **`MenuAdminController`:** Delega las opciones del admin a los módulos de celulares, ventas y reportes.
- **`MenuUsuarioController`:** Delega la opción de compra al módulo de compra del cliente, pasándole el usuario activo en sesión.
- **`UsuarioController`:** Coordina las validaciones de unicidad y el registro completo de persona + usuario.

### model/entities:

Las entidades representan las tablas de la base de datos:

- **`Celulares`:** id, sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama.
- **`Marca`:** id, nombre.
- **`SistemaOperativo`:** id, nombre.
- **`Venta`:** id, usuarios_fk, fecha, total.
- **`DetalleVenta`:** id, ventas_fk, celulares_fk, cantidad, precio, subtotal.
- **`Persona`:** id, nombre, identificacion, correo, telefono.
- **`Usuario`:** id, personas_fk, username, contraseña, roles_fk.
- **`Rol`:** id, rol.
- **`ReporteVenta`:** Entidad serializable (implements Serializable) para persistir el resumen de cada venta en archivo binario y generar el `reporte_ventas.txt`.

### model/logic:

Contiene toda la lógica de negocio del sistema:

- **`InicioSesionLogica`:** Ejecuta el inicio de sesión y el registro de nuevos usuarios con todas sus validaciones.
- **`GestionCelularesLogica`:** CRUD completo de Marcas, Sistemas Operativos y Celulares. Valida precios y stocks positivos y permite seleccionar la gama (baja, media, alta).
- **`GestionVentasLogica`:** CRUD de Ventas y Detalles de Venta. Calcula el subtotal con IVA del 19% y descuenta el stock automáticamente al registrar un detalle.
- **`GestionReportesLogica`:** Genera en consola los tres reportes requeridos usando Stream API: celulares con stock bajo, top 3 más vendidos y ventas por mes. También dispara la generación del archivo `reporte_ventas.txt`.
- **`CompraUsuarioLogica`:** Maneja el flujo completo de compra del cliente: catálogo, selección, cálculo con IVA, pago emulado (tarjeta o Nequi), registro en BD y generación del reporte.
- **`DatosReporteVenta`:** Gestiona la persistencia del reporte en archivo binario (`reporte_ventas.dat`) y en texto (`reporte_ventas.txt`). Basado en el patrón de `DatosProducto` de BodegasFileObject: usa `ObjectInputStream` y `ObjectOutputStream` con `try-with-resources`, y genera el resumen con Stream API.

### dao:

Cada DAO maneja la comunicación con la base de datos mediante JDBC para su tabla correspondiente:

- **`Conexion`:** Singleton de conexión a MySQL (`tecnostore_s2`).
- **`CelularesDao`:** CRUD + `descontarStock()` para actualizar el inventario tras una venta.
- **`VentaDao`:** CRUD + `crearYRetornarId()` que retorna el ID generado por la BD tras insertar.
- **`DetalleVentaDao`:** CRUD + `listarPorVenta()`.
- **`MarcaDao`**, **`SistemaOperativoDao`**, **`PersonasDao`**, **`UsuarioDao`**, **`RolDao`:** CRUD estándar con validación de llaves foráneas antes de eliminar.

### view:

Las vistas solo se encargan de mostrar los menús y capturar opciones usando los validadores:

- **`InicioSesion`**, **`MenuPrincipalAdmin`**, **`MenuPrincipalUsuario`**
- **`MenuGestionarCelulares`**, **`MenuGestionarVentas`**, **`MenuReportes`**, **`MenuCompraUsuario`**

### view/validations:

Sistema de validaciones reutilizables basado en la interfaz genérica `Validar<T>`:

- **`ValidarEntero`**, **`ValidarEnteroGrande`**, **`ValidarDecimal`**: Garantizan que el tipo de dato ingresado sea el correcto.
- **`ValidarCorreo`**: Verifica que el correo contenga `@` y `.` usando Stream API.
- **`ValidarTexto`**: Valida que el texto no inicie con espacios.
- **`validarRangoDato`**: Valida que la longitud de cualquier dato esté dentro de un rango configurable.

## Estructura de la base de datos (tecnostore_s2): 💾

| Tabla               | Campos principales                                              |
|---------------------|-----------------------------------------------------------------|
| `marcas`            | id, nombre                                                      |
| `sistemas_operativos` | id, nombre                                                    |
| `celulares`         | id, sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama |
| `personas`          | id, nombre, identificacion, correo, telefono                    |
| `roles`             | id, rol                                                         |
| `usuarios`          | id, personas_fk, username, contraseña, roles_fk                 |
| `ventas`            | id, usuarios_fk, fecha, total                                   |
| `detalles_ventas`   | id, ventas_fk, celulares_fk, cantidad, precio, subtotal         |

## Ejemplo de ejecución: 🖥️

```
BIENVENIDO A TECNOSTORE S2
Seleccione una opción:
    1. Iniciar Sesión.
    2. Registrarse (Crear Cuenta).
    3. Salir.

--- Login como Admin ---
BIENVENIDO ADMIN
Seleccione una opción:
    1. Menú Gestionar Celulares.
    2. Menú ventas.
    3. Menú reportes.
    4. Salir.

--- Login como Cliente ---
BIENVENIDO A TECNOSTORE S2
Seleccione una opción:
    1. Hacer compra.
    2. Salir.

Catalogo de celulares:
ID: 1 | Samsung Samsung A37 5g 128gb | SO: Android | Gama: media | Stock: 25 | Precio: $1200000.0
...
Total a pagar: $1428000.0
Seleccione su metodo de pago:
    1. Tarjeta de credito/debito.
    2. Nequi.
    3. Cancelar compra.
```

## Indicaciones para conexión MySQL: 🗄️

1. Ejecutar el script `ScriptTablas.sql` en MySQL para crear la base de datos y cargar los datos iniciales.
2. Verificar que el archivo `dao/Conexion.java` tenga las credenciales correctas:

```java
c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore_s2", "root", "tu_contraseña");
```

3. El proyecto usa Maven. Abrir en NetBeans y ejecutar con **Run Project**.
4. El conector MySQL (`mysql-connector-j 9.7.0`) se descarga automáticamente desde el `pom.xml`.
5. Los archivos `reporte_ventas.dat` y `reporte_ventas.txt` se generan automáticamente en la raíz del proyecto al realizar o reportar ventas.

## Información de contacto: 🧑🏻‍🦱💫✨

**Autor:**

Resbaloso ( ͡° ͜ʖ ͡°)

**Contacto:**

juansetoscano@gmail.com

░░░░░░░░░░░░░░░░░░░░░░█████████░░░░░░░░░
░░███████░░░░░░░░░░███▒▒▒▒▒▒▒▒███░░░░░░░
░░█▒▒▒▒▒▒█░░░░░░░███▒▒▒▒▒▒▒▒▒▒▒▒▒███░░░░
░░░█▒▒▒▒▒▒█░░░░██▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░
░░░░█▒▒▒▒▒█░░░██▒▒▒▒▒██▒▒▒▒▒▒██▒▒▒▒▒███░
░░░░░█▒▒▒█░░░█▒▒▒▒▒▒████▒▒▒▒████▒▒▒▒▒▒██
░░░█████████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██
░░░█▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒██
░██▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒██▒▒▒▒▒▒▒▒▒▒██▒▒▒▒██
██▒▒▒███████████▒▒▒▒▒██▒▒▒▒▒▒▒▒██▒▒▒▒▒██
█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒████████▒▒▒▒▒▒▒██
██▒▒▒▒▒▒▒▒▒▒▒▒▒▒█▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░
░█▒▒▒███████████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒██░░░
░██▒▒▒▒▒▒▒▒▒▒████▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒█░░░░░
░░████████████░░░█████████████████░░░░░░

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa principal de gestión de nóminas.
 */

public class CalculaNominas {

    /** Scanner para leer datos del teclado. */
    private static final Scanner teclado =
            new Scanner(System.in);

    /** Acceso a la base de datos. */
    private static final EmpleadoDAO dao =
            new EmpleadoDAO();

    /**
     * Método principal.
     *
     * @param args argumentos
     */

    public static void main(String[] args) {

        try {

            // Cargar los empleados iniciales.
            cargarEmpleadosIniciales();

            // Mostrar menú.
            menu();

        } catch (SQLException e) {

            System.out.println(
                    "Error de base de datos: "
                            + e.getMessage()
            );

        } catch (IOException e) {

            System.out.println(
                    "Error de fichero: "
                            + e.getMessage()
            );

        } catch (DatosNoCorrectosException e) {

            System.out.println("Datos no correctos");

        } finally {

            teclado.close();
        }
    }

    /**
     * Carga los empleados desde empleados.txt.
     *
     * Si el empleado ya existe, no se vuelve a insertar.
     */

    private static void cargarEmpleadosIniciales()
            throws IOException,
            DatosNoCorrectosException,
            SQLException {

        ArrayList<Empleado> empleados =
                Ficheros.leerEmpleados("empleados.txt");

        for (Empleado empleado : empleados) {

            if (dao.buscarEmpleado(empleado.dni) == null) {

                dao.altaEmpleado(empleado);
            }
        }
    }

    /**
     * Menú principal.
     */

    private static void menu() {

        int opcion;

        do {

            System.out.println();
            System.out.println("==============================");
            System.out.println("     GESTIÓN DE NÓMINAS");
            System.out.println("==============================");
            System.out.println("1. Mostrar todos los empleados");
            System.out.println("2. Mostrar salario por DNI");
            System.out.println("3. Modificar empleado");
            System.out.println("4. Recalcular salario");
            System.out.println("5. Recalcular todos los salarios");
            System.out.println("6. Copia de seguridad");
            System.out.println("7. Alta de empleado");
            System.out.println("8. Alta de empleados por fichero");
            System.out.println("0. Salir");
            System.out.println("==============================");

            opcion = leerEntero("Introduce una opción: ");

            try {

                switch (opcion) {

                    case 1:
                        mostrarEmpleados();
                        break;

                    case 2:
                        mostrarSalario();
                        break;

                    case 3:
                        submenuModificar();
                        break;

                    case 4:
                        recalcularSueldo();
                        break;

                    case 5:
                        recalcularTodos();
                        break;

                    case 6:
                        backup();
                        break;

                    case 7:
                        altaEmpleado();
                        break;

                    case 8:
                        altaPorLotes();
                        break;

                    case 0:
                        System.out.println(
                                "Programa terminado."
                        );
                        break;

                    default:
                        System.out.println(
                                "Opción no válida."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }

        } while (opcion != 0);
    }

    /**
     * Muestra todos los empleados.
     */

    private static void mostrarEmpleados()
            throws SQLException {

        ArrayList<Empleado> empleados =
                dao.obtenerTodos();

        if (empleados.isEmpty()) {

            System.out.println(
                    "No existen empleados."
            );

            return;
        }

        for (Empleado empleado : empleados) {

            System.out.println("------------------------------");

            empleado.imprime();

            Integer sueldo =
                    dao.obtenerSueldo(empleado.dni);

            System.out.println(
                    "Sueldo: " + sueldo
            );
        }
    }

    /**
     * Muestra el salario de un empleado.
     */

    private static void mostrarSalario()
            throws SQLException {

        System.out.print("DNI: ");

        String dni = teclado.nextLine();

        Integer sueldo =
                dao.obtenerSueldo(dni);

        if (sueldo == null) {

            System.out.println(
                    "No existe un salario para ese DNI."
            );

        } else {

            System.out.println(
                    "Salario: " + sueldo
            );
        }
    }

    /**
     * Submenú para modificar empleados.
     */

    private static void submenuModificar() {

        int opcion;

        do {

            System.out.println();
            System.out.println("------------------------------");
            System.out.println("    MODIFICAR EMPLEADO");
            System.out.println("------------------------------");
            System.out.println("1. Modificar nombre");
            System.out.println("2. Modificar sexo");
            System.out.println("3. Modificar categoría");
            System.out.println("4. Modificar años trabajados");
            System.out.println("5. Modificar todos los datos");
            System.out.println("0. Volver");
            System.out.println("------------------------------");

            opcion =
                    leerEntero("Opción: ");

            try {

                switch (opcion) {

                    case 1:
                        modificarNombre();
                        break;

                    case 2:
                        modificarSexo();
                        break;

                    case 3:
                        modificarCategoria();
                        break;

                    case 4:
                        modificarAnyos();
                        break;

                    case 5:
                        modificarTodos();
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println(
                                "Opción no válida."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }

        } while (opcion != 0);
    }

    /**
     * Modifica el nombre.
     */

    private static void modificarNombre()
            throws SQLException {

        Empleado empleado =
                buscarEmpleadoPorTeclado();

        if (empleado == null) {
            return;
        }

        System.out.print("Nuevo nombre: ");

        empleado.nombre =
                teclado.nextLine();

        dao.actualizarEmpleado(empleado);

        System.out.println(
                "Empleado actualizado."
        );
    }

    /**
     * Modifica el sexo.
     */

    private static void modificarSexo()
            throws SQLException {

        Empleado empleado =
                buscarEmpleadoPorTeclado();

        if (empleado == null) {
            return;
        }

        System.out.print("Nuevo sexo (M/F): ");

        empleado.sexo =
                teclado.nextLine().charAt(0);

        dao.actualizarEmpleado(empleado);

        System.out.println(
                "Empleado actualizado."
        );
    }

    /**
     * Modifica la categoría.
     */

    private static void modificarCategoria()
            throws SQLException,
            DatosNoCorrectosException {

        Empleado empleado =
                buscarEmpleadoPorTeclado();

        if (empleado == null) {
            return;
        }

        int categoria =
                leerEntero("Nueva categoría (1-10): ");

        empleado.setCategoria(categoria);

        dao.actualizarEmpleado(empleado);

        System.out.println(
                "Empleado actualizado."
        );
    }

    /**
     * Modifica los años trabajados.
     */

    private static void modificarAnyos()
            throws SQLException,
            DatosNoCorrectosException {

        Empleado empleado =
                buscarEmpleadoPorTeclado();

        if (empleado == null) {
            return;
        }

        int anyos =
                leerEntero("Nuevos años trabajados: ");

        if (anyos < 0) {

            throw new DatosNoCorrectosException(
                    "Los años no pueden ser negativos."
            );
        }

        empleado.anyos = anyos;

        dao.actualizarEmpleado(empleado);

        System.out.println(
                "Empleado actualizado."
        );
    }

    /**
     * Modifica todos los datos salvo el DNI.
     * El salario se recalcula automáticamente.
     */

    private static void modificarTodos()
            throws SQLException,
            DatosNoCorrectosException {

        Empleado empleado =
                buscarEmpleadoPorTeclado();

        if (empleado == null) {
            return;
        }

        System.out.print("Nuevo nombre: ");
        empleado.nombre =
                teclado.nextLine();

        System.out.print("Nuevo sexo (M/F): ");
        empleado.sexo =
                teclado.nextLine().charAt(0);

        int categoria =
                leerEntero(
                        "Nueva categoría (1-10): "
                );

        empleado.setCategoria(categoria);

        int anyos =
                leerEntero(
                        "Nuevos años trabajados: "
                );

        if (anyos < 0) {

            throw new DatosNoCorrectosException(
                    "Los años no pueden ser negativos."
            );
        }

        empleado.anyos = anyos;

        dao.actualizarEmpleado(empleado);

        System.out.println(
                "Empleado actualizado."
        );
    }

    /**
     * Busca un empleado utilizando su DNI.
     *
     * @return empleado encontrado
     * @throws SQLException si ocurre un error
     */

    private static Empleado buscarEmpleadoPorTeclado()
            throws SQLException {

        System.out.print("DNI del empleado: ");

        String dni =
                teclado.nextLine();

        Empleado empleado =
                dao.buscarEmpleado(dni);

        if (empleado == null) {

            System.out.println(
                    "No existe ese empleado."
            );
        }

        return empleado;
    }

    /**
     * Recalcula el sueldo de un empleado.
     */

    private static void recalcularSueldo()
            throws SQLException {

        System.out.print("DNI: ");

        String dni =
                teclado.nextLine();

        dao.recalcularSueldo(dni);

        System.out.println(
                "Sueldo recalculado correctamente."
        );
    }

    /**
     * Recalcula todos los salarios.
     */

    private static void recalcularTodos()
            throws SQLException {

        dao.recalcularTodos();

        System.out.println(
                "Todos los salarios han sido recalculados."
        );
    }

    /**
     * Realiza una copia de seguridad.
     */

    private static void backup()
            throws SQLException,
            IOException {

        ArrayList<Empleado> empleados =
                dao.obtenerTodos();

        Ficheros.realizarBackup(empleados);

        System.out.println(
                "Copia de seguridad realizada."
        );

        System.out.println(
                "Ficheros creados:"
        );

        System.out.println(
                "- empleados_backup.txt"
        );

        System.out.println(
                "- sueldos_backup.dat"
        );
    }

    /**
     * Da de alta un empleado individual.
     */

    private static void altaEmpleado()
            throws SQLException,
            DatosNoCorrectosException {

        System.out.println();
        System.out.println("ALTA DE EMPLEADO");

        System.out.print("Nombre: ");
        String nombre =
                teclado.nextLine();

        System.out.print("DNI: ");
        String dni =
                teclado.nextLine();

        System.out.print("Sexo: ");
        char sexo =
                teclado.nextLine().charAt(0);

        int categoria =
                leerEntero("Categoría: ");

        int anyos =
                leerEntero("Años trabajados: ");

        Empleado empleado =
                new Empleado(
                        nombre,
                        dni,
                        sexo,
                        categoria,
                        anyos
                );

        if (dao.buscarEmpleado(dni) != null) {

            System.out.println(
                    "Ya existe un empleado con ese DNI."
            );

            return;
        }

        dao.altaEmpleado(empleado);

        System.out.println(
                "Empleado dado de alta correctamente."
        );

        System.out.println(
                "Sueldo calculado: "
                        + Nomina.calcularSueldo(empleado)
        );
    }

    /**
     * Alta de empleados a partir de empleadosNuevos.txt.
     */

    private static void altaPorLotes()
            throws IOException,
            DatosNoCorrectosException,
            SQLException {

        ArrayList<Empleado> empleados =
                Ficheros.leerEmpleados(
                        "empleadosNuevos.txt"
                );

        int contador = 0;

        for (Empleado empleado : empleados) {

            if (dao.buscarEmpleado(empleado.dni)
                    == null) {

                dao.altaEmpleado(empleado);

                contador++;
            }
        }

        System.out.println(
                "Empleados dados de alta: "
                        + contador
        );
    }

    /**
     * Lee un número entero desde teclado.
     *
     * @param mensaje mensaje
     * @return entero
     */

    private static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);

                return Integer.parseInt(
                        teclado.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Introduce un número válido."
                );
            }
        }
    }
}
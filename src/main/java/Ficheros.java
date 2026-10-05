import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Gestiona la lectura y escritura de ficheros.
 */
public class Ficheros {

    /**
     * Lee empleados desde un fichero de texto.
     *
     * Formato:
     *
     * nombre;dni;sexo;categoria;anyos
     *
     * @param nombreFichero nombre del fichero
     * @return lista de empleados
     * @throws IOException si ocurre un error
     * @throws DatosNoCorrectosException si los datos no son válidos
     */
    public static ArrayList<Empleado> leerEmpleados(
            String nombreFichero)
            throws IOException, DatosNoCorrectosException {

        ArrayList<Empleado> empleados =
                new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(nombreFichero))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] datos = linea.split(";");

                if (datos.length != 5) {
                    throw new IOException(
                            "Formato incorrecto: " + linea
                    );
                }

                String nombre = datos[0];
                String dni = datos[1];
                char sexo = datos[2].charAt(0);
                int categoria = Integer.parseInt(datos[3]);
                int anyos = Integer.parseInt(datos[4]);

                Empleado empleado =
                        new Empleado(
                                nombre,
                                dni,
                                sexo,
                                categoria,
                                anyos
                        );

                empleados.add(empleado);
            }
        }

        return empleados;
    }

    /**
     * Escribe los empleados en un fichero de texto.
     *
     * @param nombreFichero fichero
     * @param empleados empleados
     * @throws IOException si ocurre un error
     */
    public static void escribirEmpleados(
            String nombreFichero,
            ArrayList<Empleado> empleados)
            throws IOException {

        try (BufferedWriter bw =
                     new BufferedWriter(
                             new FileWriter(nombreFichero))) {

            for (Empleado empleado : empleados) {

                bw.write(
                        empleado.nombre + ";" +
                                empleado.dni + ";" +
                                empleado.sexo + ";" +
                                empleado.getCategoria() + ";" +
                                empleado.anyos
                );

                bw.newLine();
            }
        }
    }

    /**
     * Guarda los DNI y salarios en un fichero binario.
     *
     * Formato:
     * DNI + sueldo
     *
     * @param nombreFichero fichero binario
     * @param empleados lista de empleados
     * @throws IOException si ocurre un error
     */
    public static void escribirSalariosBinario(
            String nombreFichero,
            ArrayList<Empleado> empleados)
            throws IOException {

        try (DataOutputStream dos =
                     new DataOutputStream(
                             new FileOutputStream(
                                     nombreFichero))) {

            for (Empleado empleado : empleados) {

                dos.writeUTF(empleado.dni);
                dos.writeInt(Nomina.calcularSueldo(empleado));
            }
        }
    }

    /**
     * Realiza una copia de seguridad de la base de datos
     * en los ficheros de texto y binario.
     *
     * @param empleados empleados
     * @throws IOException si ocurre un error
     */
    public static void realizarBackup(
            ArrayList<Empleado> empleados)
            throws IOException {

        escribirEmpleados(
                "empleados_backup.txt",
                empleados
        );

        escribirSalariosBinario(
                "sueldos_backup.dat",
                empleados
        );
    }
}
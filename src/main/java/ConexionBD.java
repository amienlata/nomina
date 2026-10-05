import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión con la base de datos.
 */

public class ConexionBD {

    /** Nombre del fichero de la base de datos. */
    private static final String URL = "jdbc:sqlite:nominas.db";

    /**
     * Obtiene una conexión con la base de datos.
     *
     * @return conexión
     * @throws SQLException si ocurre un error
     */

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(URL);
    }

    /**
     * Crea las tablas necesarias.
     *
     * @throws SQLException si ocurre un error
     */

    public static void crearTablas() throws SQLException {

        String sqlEmpleados =
                "CREATE TABLE IF NOT EXISTS Empleados (" +
                        "dni TEXT PRIMARY KEY, " +
                        "nombre TEXT NOT NULL, " +
                        "sexo TEXT NOT NULL, " +
                        "categoria INTEGER NOT NULL, " +
                        "anyos INTEGER NOT NULL" +
                        ")";

        String sqlNominas =
                "CREATE TABLE IF NOT EXISTS Nominas (" +
                        "dni TEXT PRIMARY KEY, " +
                        "sueldo INTEGER NOT NULL, " +
                        "FOREIGN KEY (dni) REFERENCES Empleados(dni)" +
                        ")";

        try (Connection conexion = conectar();
             Statement sentencia = conexion.createStatement()) {

            sentencia.executeUpdate(sqlEmpleados);
            sentencia.executeUpdate(sqlNominas);
        }
    }
}

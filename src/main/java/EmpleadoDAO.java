
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Acceso a datos de empleados y nóminas.
 */

public class EmpleadoDAO {

    /**
     * Inserta un empleado en la base de datos.
     *
     * @param empleado empleado a insertar
     * @throws SQLException si ocurre un error
     */

    public void insertarEmpleado(Empleado empleado)
            throws SQLException {

        String sql =
                "INSERT INTO Empleados " +
                        "(dni, nombre, sexo, categoria, anyos) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.dni);
            ps.setString(2, empleado.nombre);
            ps.setString(3, String.valueOf(empleado.sexo));
            ps.setInt(4, empleado.getCategoria());
            ps.setInt(5, empleado.anyos);

            ps.executeUpdate();
        }
    }

    /**
     * Inserta o actualiza el salario de un empleado.
     *
     * @param empleado empleado
     * @throws SQLException si ocurre un error
     */

    public void guardarSueldo(Empleado empleado)
            throws SQLException {

        int sueldo = Nomina.calcularSueldo(empleado);

        String sql =
                "INSERT OR REPLACE INTO Nominas " +
                        "(dni, sueldo) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.dni);
            ps.setInt(2, sueldo);

            ps.executeUpdate();
        }
    }

    /**
     * Inserta un empleado y calcula automáticamente su sueldo.
     *
     * @param empleado empleado
     * @throws SQLException si ocurre un error
     */

    public void altaEmpleado(Empleado empleado)
            throws SQLException {

        insertarEmpleado(empleado);
        guardarSueldo(empleado);
    }

    /**
     * Busca un empleado por su DNI.
     *
     * @param dni DNI del empleado
     * @return empleado encontrado o null
     * @throws SQLException si ocurre un error
     */

    public Empleado buscarEmpleado(String dni)
            throws SQLException {

        String sql =
                "SELECT nombre, dni, sexo, categoria, anyos " +
                        "FROM Empleados WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    try {
                        return new Empleado(
                                rs.getString("nombre"),
                                rs.getString("dni"),
                                rs.getString("sexo").charAt(0),
                                rs.getInt("categoria"),
                                rs.getInt("anyos")
                        );

                    } catch (DatosNoCorrectosException e) {
                        throw new SQLException(
                                "Datos incorrectos en la base de datos",
                                e
                        );
                    }
                }
            }
        }

        return null;
    }

    /**
     * Obtiene todos los empleados.
     *
     * @return lista de empleados
     * @throws SQLException si ocurre un error
     */

    public ArrayList<Empleado> obtenerTodos()
            throws SQLException {

        ArrayList<Empleado> empleados =
                new ArrayList<>();

        String sql =
                "SELECT nombre, dni, sexo, categoria, anyos " +
                        "FROM Empleados ORDER BY nombre";

        try (Connection conexion = ConexionBD.conectar();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                try {

                    Empleado empleado = new Empleado(
                            rs.getString("nombre"),
                            rs.getString("dni"),
                            rs.getString("sexo").charAt(0),
                            rs.getInt("categoria"),
                            rs.getInt("anyos")
                    );

                    empleados.add(empleado);

                } catch (DatosNoCorrectosException e) {

                    throw new SQLException(
                            "Datos incorrectos en la base de datos",
                            e
                    );
                }
            }
        }

        return empleados;
    }

    /**
     * Obtiene el salario de un empleado.
     *
     * @param dni DNI
     * @return salario
     * @throws SQLException si ocurre un error
     */

    public Integer obtenerSueldo(String dni)
            throws SQLException {

        String sql =
                "SELECT sueldo FROM Nominas WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt("sueldo");
                }
            }
        }

        return null;
    }

    /**
     * Actualiza los datos de un empleado.
     * El sueldo se recalcula automáticamente.
     *
     * @param empleado empleado con los nuevos datos
     * @throws SQLException si ocurre un error
     */

    public void actualizarEmpleado(Empleado empleado)
            throws SQLException {

        String sql =
                "UPDATE Empleados SET " +
                        "nombre = ?, " +
                        "sexo = ?, " +
                        "categoria = ?, " +
                        "anyos = ? " +
                        "WHERE dni = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps =
                     conexion.prepareStatement(sql)) {

            ps.setString(1, empleado.nombre);
            ps.setString(2, String.valueOf(empleado.sexo));
            ps.setInt(3, empleado.getCategoria());
            ps.setInt(4, empleado.anyos);
            ps.setString(5, empleado.dni);

            ps.executeUpdate();
        }

        guardarSueldo(empleado);
    }

    /**
     * Recalcula el sueldo de un empleado.
     *
     * @param dni DNI del empleado
     * @throws SQLException si ocurre un error
     */

    public void recalcularSueldo(String dni)
            throws SQLException {

        Empleado empleado = buscarEmpleado(dni);

        if (empleado == null) {
            throw new SQLException(
                    "No existe un empleado con ese DNI."
            );
        }

        guardarSueldo(empleado);
    }

    /**
     * Recalcula los salarios de todos los empleados.
     *
     * @throws SQLException si ocurre un error
     */

    public void recalcularTodos()
            throws SQLException {

        ArrayList<Empleado> empleados = obtenerTodos();

        for (Empleado empleado : empleados) {
            guardarSueldo(empleado);
        }
    }
}

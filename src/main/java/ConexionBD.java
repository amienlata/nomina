import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión con la base de datos.
 */

public class ConexionBD {

    private static final String URL = "jdbc:mariadb://localhost:3306/nomina";

    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}

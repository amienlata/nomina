public class Nomina {

    private static final int[] sueldoBase = {
            50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000
    };

        public static int calcularSueldo(Empleado empleado) {
            int sueldo = sueldoBase[empleado.getCategoria() - 1] + 5000 * empleado.getAnyos();
            return sueldo;
    }
}

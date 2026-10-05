public class Nomina {

    private static final int[] SUELDO_BASE = {
            50000, 70000, 90000, 110000, 130000,
            150000, 170000, 190000, 210000, 230000
    };

        public static int calcularSueldo(Empleado empleado) {

            int sueldoBase = SUELDO_BASE[empleado.getCategoria() - 1];

            return sueldoBase + 5000 * empleado.anyos;
    }
}

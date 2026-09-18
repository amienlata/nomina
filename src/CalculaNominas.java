public class CalculaNominas {

    public static void main(String[] args) {


        try {
        Empleado empleado1 = new Empleado ("James Cosling", "32000032G", 'M', 4, 7);
        Empleado empleado2= new Empleado ("Ada Lovelace", "32000031R", 'F');

            empleado2.incrAnyo();

            escribe(empleado1);
            escribe(empleado2);

        } catch (DatosNoCorrectosException e) {
            System.out.println(e.getMessage());;
        }

    }

    private static void escribe (Empleado empleado){
        empleado.imprimeEmpleado(empleado);
        int sueldoFinal = Nomina.calcularSueldo(empleado);
        System.out.println("El sueldo del empleado " + empleado.nombre + " es de: " + sueldoFinal + "€.");

    }
}

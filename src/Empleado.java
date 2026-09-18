public class Empleado extends Persona{

    private int categoria;
    int anyos;

    public Empleado(String nombre, String dni, char sexo) {
        super(nombre, dni, sexo);
    }

    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException {
        super(nombre, dni, sexo);
        try {
            setCategoria(categoria);
            setAnyos(anyos);
        } catch (Exception e) {
        throw new DatosNoCorrectosException("Hay datos inválidos");

    }
        }

    public void setCategoria(int categoria) throws Exception {
        if (categoria < 1 || categoria > 10) {
        throw new Exception("La categoría tiene que ser entre 1 y 10");
        }
        this.categoria = categoria;
    }

    public void setAnyos(int anyos) throws Exception {
        if (anyos < 0) {
            throw new Exception("Los años trabajados tienen que ser igual o mayor de cero");
        }
        this.anyos = anyos;
    }

    public int getCategoria() {
        return categoria;
    }

    public int incrAnyo() {
        return anyos++;
    }

    public int getAnyos() {
        return anyos;
    }

    public void imprimeEmpleado(Empleado empleado) {
        System.out.println(empleado.nombre + "," + empleado.dni + "," + empleado.sexo + "," + empleado.categoria + "," + empleado.anyos);
    }
}

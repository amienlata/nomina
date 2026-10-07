public class Empleado extends Persona{

    /** Categoría del empleado, entre 1 y 10. */
    private int categoria;

    /** Número de años trabajados. */
    public int anyos;

    /**
     * Constructor que recibe todos los datos del empleado.
     *
     * @param nombre nombre del empleado
     * @param dni DNI del empleado
     * @param sexo sexo del empleado
     * @param categoria categoría del empleado
     * @param anyos años trabajados
     * @throws DatosNoCorrectosException si los datos no son válidos
     */
    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos)
            throws DatosNoCorrectosException {

        super(nombre, dni, sexo);

        comprobarDatos(categoria, anyos, dni, sexo);

        this.categoria = categoria;
        this.anyos = anyos;
    }

    /**
     * Constructor que recibe nombre, sexo y DNI.
     * La categoría se establece a 1 y los años trabajados a 0.
     *
     * @param nombre nombre del empleado
     * @param sexo sexo del empleado
     * @param dni DNI del empleado
     * @throws DatosNoCorrectosException si los datos no son válidos
     */
    public Empleado(String nombre, char sexo, String dni)
            throws DatosNoCorrectosException {

        super(nombre, dni, sexo);

        this.categoria = 1;
        this.anyos = 0;
    }

    /**
     * Cambia la categoría del empleado.
     *
     * @param categoria nueva categoría
     * @throws DatosNoCorrectosException si la categoría no está entre 1 y 10
     */
    public void setCategoria(int categoria)
            throws DatosNoCorrectosException {

        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException("Datos no correctos");
        }

        this.categoria = categoria;
    }

    private void comprobarDatos(int categoria, int anyos, String dni, char sexo)
            throws DatosNoCorrectosException {

        if (categoria < 1 || categoria > 10 || anyos < 0 || dni.length() < 9 || dni.length() > 9 || (sexo != 'M' && sexo != 'F')) {
            throw new DatosNoCorrectosException("Datos no correctos");
        }
    }

    /**
     * Obtiene la categoría del empleado.
     *
     * @return categoría del empleado
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * Incrementa en uno los años trabajados.
     */
    public void incrAnyo() {
        anyos++;
    }

    /**
     * Imprime todos los datos del empleado.
     */
    @Override
    public void imprime() {
        System.out.println("Nombre: " + nombre);
        System.out.println("DNI: " + dni);
        System.out.println("Sexo: " + sexo);
        System.out.println("Categoría: " + categoria);
        System.out.println("Años trabajados: " + anyos);
    }
}

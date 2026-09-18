public class Persona {

    String nombre;
    String dni;
    char sexo;

    public Persona(String nombre, String dni, char sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    public Persona(String nombre, char sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void imprimeNombreyDni(String nombre, String dni) {
        System.out.println(nombre + " " + dni);
    }


}

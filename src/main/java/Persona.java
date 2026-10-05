public class Persona {

        /** Nombre de la persona. */
        public String nombre;

        /** DNI de la persona. */
        public String dni;

        /** Sexo de la persona. */
        public char sexo;

        /**
         * Constructor con todos los atributos.
         *
         * @param nombre nombre de la persona
         * @param dni DNI de la persona
         * @param sexo sexo de la persona
         */
        public Persona(String nombre, String dni, char sexo) {
            this.nombre = nombre;
            this.dni = dni;
            this.sexo = sexo;
        }

        /**
         * Constructor que recibe nombre y sexo.
         * El DNI queda sin asignar.
         *
         * @param nombre nombre de la persona
         * @param sexo sexo de la persona
         */
        public Persona(String nombre, char sexo) {
            this.nombre = nombre;
            this.sexo = sexo;
            this.dni = "";
        }

        /**
         * Modifica el DNI de la persona.
         *
         * @param dni nuevo DNI
         */
        public void setDni(String dni) {
            this.dni = dni;
        }

        /**
         * Imprime el nombre y el DNI.
         */
        public void imprime() {
            System.out.println("Nombre: " + nombre);
            System.out.println("DNI: " + dni);
        }
    }

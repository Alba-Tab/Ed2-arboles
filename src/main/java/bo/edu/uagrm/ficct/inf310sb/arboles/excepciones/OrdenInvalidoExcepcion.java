package bo.edu.uagrm.ficct.inf310sb.arboles.excepciones;

public class OrdenInvalidoExcepcion extends Exception {
    public OrdenInvalidoExcepcion() {
        super("El orden minimo es 3");
    }

    public OrdenInvalidoExcepcion(String message) {
        super(message);
    }
}

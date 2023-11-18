package bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones;

public class NroVerticesInvalidoExcepcion extends Exception {
    public NroVerticesInvalidoExcepcion() {
        super("Cantidad de vertices debe ser positivos");
    }

    public NroVerticesInvalidoExcepcion(String message) {
        super(message);
    }
}
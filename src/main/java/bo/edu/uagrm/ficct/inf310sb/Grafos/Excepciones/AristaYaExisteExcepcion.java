package bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones;

public class AristaYaExisteExcepcion extends Exception {
    public AristaYaExisteExcepcion() {
        super("Arista ya existe");
    }

    public AristaYaExisteExcepcion(String message) {
        super(message);
    }
}
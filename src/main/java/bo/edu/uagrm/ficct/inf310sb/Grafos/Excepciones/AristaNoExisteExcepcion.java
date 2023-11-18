package bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones;

public class AristaNoExisteExcepcion extends Exception {
    public AristaNoExisteExcepcion() {
        super("Arista no existe");
    }

    public AristaNoExisteExcepcion(String message) {
        super(message);
    }
}
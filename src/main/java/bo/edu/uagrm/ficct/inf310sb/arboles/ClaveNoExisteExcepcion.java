package bo.edu.uagrm.ficct.inf310sb.arboles;

public class ClaveNoExisteExcepcion extends Exception{
    public ClaveNoExisteExcepcion(){
        super("Clave no existe");
    }
    public ClaveNoExisteExcepcion(String message){
        super(message);
    }
}

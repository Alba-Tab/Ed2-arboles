package bo.edu.uagrm.ficct.inf310sb.arboles;

public class AVL <K extends Comparable<K>,V>
    extends ArbolBinarioBusqueda<K,V>{
    //extendido de la clase ArbolBinarioBusqueda
    //static para que a pesar de haber 10 instancias de la clase solo habra un limite maximo
    private static final int LIMITE_MAXIMO=1;
    @Override
    public void insertar(K claveAInsertar,V valorAsociado){
        if (claveAInsertar==null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAsociado==null){
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        super.raiz=insertar(super.raiz,claveAInsertar,valorAsociado);
    }
    private NodoBinario<K,V> insertar(NodoBinario<K,V> nodoActual,K claveAInsertar,V valorAsociado){
        if (NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nodoNuevo = new NodoBinario<K,V>(claveAInsertar,valorAsociado);
            return nodoNuevo;
        }
        K claveDelNodoActual =nodoActual.getClave();
        if (claveAInsertar.compareTo(claveDelNodoActual)<0){
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAsociado);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if
    }
}

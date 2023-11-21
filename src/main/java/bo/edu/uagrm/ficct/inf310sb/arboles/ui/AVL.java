package bo.edu.uagrm.ficct.inf310sb.arboles.ui;
import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
public class AVL <K extends Comparable<K>,V>
    extends ArbolBinarioBusqueda<K,V> {
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
    private NodoBinario<K,V> insertar(NodoBinario nodoActual, K claveAInsertar, V valorAsociado){
        if (NodoBinario.esNodoVacio(nodoActual)){
            NodoBinario<K,V> nodoNuevo = new NodoBinario<K,V>(claveAInsertar,valorAsociado);
            return nodoNuevo;
        }
        K claveDelNodoActual =(K) nodoActual.getClave();
        if (claveAInsertar.compareTo(claveDelNodoActual)<0){
            NodoBinario<K,V> supuestoNuevoHijoIzquierdo = insertar(nodoActual.getHijoIzquierdo(),claveAInsertar,valorAsociado);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAInsertar.compareTo(claveDelNodoActual)>0){
            NodoBinario<K,V> supuestoNodoHijoDerecho = insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAsociado);
            nodoActual.setHijoDerecho(supuestoNodoHijoDerecho);
            return balancear(nodoActual);
        }
        nodoActual.setValor(valorAsociado);
        return nodoActual;
    }
    private NodoBinario<K,V> balancear(NodoBinario<K,V> nodoABalancear){
        int alturaXIzq= super.altura(nodoABalancear.getHijoIzquierdo());
        int alturaXDer= super.altura(nodoABalancear.getHijoDerecho());
        int diferenciaDeAlturas =alturaXIzq-alturaXDer;

        if (diferenciaDeAlturas>LIMITE_MAXIMO){
            NodoBinario<K,V> hijoIzqDelNodoABalancear = nodoABalancear.getHijoIzquierdo();
            alturaXIzq= super.altura(hijoIzqDelNodoABalancear.getHijoIzquierdo());
            alturaXDer= super.altura(hijoIzqDelNodoABalancear.getHijoDerecho());
            if (alturaXDer>alturaXIzq){
                return rotacionDobleADerecha(nodoABalancear);
            }
            return rotacionSimpleAderecha (nodoABalancear);
        } else if (diferenciaDeAlturas<-LIMITE_MAXIMO) {
            NodoBinario<K,V> hijoDerDelNodoABalancear = nodoABalancear.getHijoDerecho();
            alturaXIzq= super.altura(hijoDerDelNodoABalancear.getHijoIzquierdo());
            alturaXDer= super.altura(hijoDerDelNodoABalancear.getHijoDerecho());
            if (alturaXDer<alturaXIzq){
                return rotacionDobleAIzq(nodoABalancear);
            }
            return rotacionSimpleAIzq (nodoABalancear);
        }
        return nodoABalancear;
    }
    private NodoBinario<K,V> rotacionSimpleAderecha(NodoBinario<K,V> nodoActual) {
        if (nodoActual == null || nodoActual.getHijoIzquierdo() == null) {
            return nodoActual; // No se puede rotar, regresar el nodo original
        }

        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nodoQueRota.getHijoDerecho());
        nodoQueRota.setHijoDerecho(nodoActual);
        return nodoQueRota;
    }

    private NodoBinario<K,V> rotacionSimpleAIzq(NodoBinario<K,V> nodoActual ){
        if (nodoActual == null || nodoActual.getHijoDerecho() == null) {
            return nodoActual; // No se puede rotar, regresar el nodo original
        }
        NodoBinario<K,V> nodoQueRota = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nodoQueRota.getHijoIzquierdo());
        nodoQueRota.setHijoIzquierdo(nodoActual);
        return nodoQueRota;
    }
    private NodoBinario<K,V>rotacionDobleAIzq(NodoBinario<K,V> nodoActual){
        nodoActual.setHijoDerecho(rotacionSimpleAderecha(nodoActual.getHijoDerecho()));
        return rotacionSimpleAIzq(nodoActual);
    }
    private NodoBinario<K,V>rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
        nodoActual.setHijoIzquierdo(rotacionSimpleAIzq(nodoActual.getHijoIzquierdo()));
        return rotacionSimpleAderecha(nodoActual);

    }
    public V eliminar(K claveAEliminar) throws ClaveNoExisteExcepcion {
        if (claveAEliminar==null){
            throw new ClaveNoExisteExcepcion("La clave nula, no puede ser nula");
        }
        V valorAEliminar = this.buscar(claveAEliminar);
        if (valorAEliminar==null){
            throw new ClaveNoExisteExcepcion();
        }
        this.raiz =eliminar(this.raiz,claveAEliminar);
        return valorAEliminar;
    }
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual,K claveAEliminar){
        K claveDelNodoActual=nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual)<0){
            NodoBinario<K,V> supuestoHijoIzquierdo =
                    eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return balancear(nodoActual);
        }
        if (claveAEliminar.compareTo(claveDelNodoActual)>0){
            NodoBinario<K,V> supuestoHijoDerecho=
                    eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return balancear(nodoActual);
        }
        //caso 1
        if(nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return balancear(nodoActual.getHijoIzquierdo());
        }
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()){
            return  balancear(nodoActual.getHijoDerecho());
        }
        //caso 3
        //recibe el de la derecha y se mueve a puro izquierda
        NodoBinario<K,V> nodoDelSucesor =this.getSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoHijoDerecho =this.eliminar(nodoActual.getHijoDerecho(),
                nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return balancear(nodoActual);
    }
}

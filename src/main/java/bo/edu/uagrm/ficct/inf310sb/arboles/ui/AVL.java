package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

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
        if (claveAInsertar.compareTo(claveDelNodoActual)>=0){
            NodoBinario<K,V> supuestoNodoHijoDerecho = insertar(nodoActual.getHijoDerecho(),claveAInsertar,valorAsociado);
            nodoActual.setHijoDerecho(supuestoNodoHijoDerecho);
            return balancear(nodoActual);
        }
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
        nodoActual.setHijoDerecho(rotacionSimpleAderecha(nodoActual.getHijoIzquierdo()));
        return rotacionSimpleAIzq(nodoActual);
    }
    private NodoBinario<K,V>rotacionDobleADerecha(NodoBinario<K,V> nodoActual){
        nodoActual.setHijoIzquierdo(rotacionSimpleAIzq(nodoActual.getHijoIzquierdo()));
        return rotacionSimpleAderecha(nodoActual);

    }
}

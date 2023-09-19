package bo.edu.uagrm.ficct.inf310sb.arboles;

import java.util.List;

public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K,V> {
    protected NodoBinario<K,V> raiz;


    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAsociado == null){
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        if(this.esArbolVacio()){
            this.raiz =new NodoBinario<>(claveAInsertar, valorAsociado);
        }else {
            NodoBinario<K,V> nodoAnterior = NodoBinario.nodoVacio();
            NodoBinario<K,V> nodoActual = this.raiz;
            do {
                K claveDelNodoActual = nodoActual.getClave();
                //no asi--> if(claveAInsertar<claveDelNodoActual)
                nodoAnterior =nodoActual;
                if (claveAInsertar.compareTo(claveDelNodoActual)<0){
                    nodoActual = nodoActual.getHijoIzquierdo();
                } else if (claveAInsertar.compareTo(claveDelNodoActual)>0) {
                    nodoActual= nodoActual.getHijoDerecho();
                } else{
                    nodoActual.setValor(valorAsociado);
                    return;
                }
            }while (!NodoBinario.esNodoVacio(nodoActual));

            NodoBinario<K,V> nuevoNodo = new NodoBinario<>(claveAInsertar, valorAsociado);
            if (nodoAnterior.getClave().compareTo(claveAInsertar)<0){
                nodoAnterior.setHijoIzquierdo(nuevoNodo);
            } else{
                nodoAnterior.setHijoDerecho(nuevoNodo);
            }
        }
    }

    @Override
    public V eliminar(K clave) throws ClaveNoExisteExcepcion {
       if (clave == null){
           throw new IllegalArgumentException("Clave no puede ser nula");
       }

       if (this.esArbolVacio()){
           return null;
       }
        return null;
    }

    @Override
    public V buscar(K clave) {
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int altura() {
        return 0;
    }

    @Override
    public void vaciar() {
        this.raiz=(NodoBinario<K, V>) NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        return 0;
    }

    @Override
    public List<K> recorridoEnInOrden() {
        return null;
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        return null;
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        return null;
    }

    @Override
    public List<K> recorridoPorNiveles() {
        return null;
    }
}

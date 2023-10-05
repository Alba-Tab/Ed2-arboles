package bo.edu.uagrm.ficct.inf310sb.arboles;

import java.sql.ClientInfoStatus;
import java.util.*;

public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K,V> {
    protected NodoBinario<K,V> raiz;
    public ArbolBinarioBusqueda(){}

    public ArbolBinarioBusqueda(List<K> clavesInOrden,List<V> valoresInOrden,
                                List<K> clavesNoInOrden,List<V> valoresNoInOrden,
                                boolean conPreOrden){
        //con preorden true
        if (conPreOrden){
            this.raiz=reconstruirConPreOrden(clavesInOrden,valoresInOrden);
        }else {
            this.raiz=reconstruirConPostOrden(clavesNoInOrden, valoresNoInOrden);
        }
    }

    private NodoBinario<K,V> reconstruirConPreOrden(List<K> clavesInOrden,List<V> valoresInOrden){
        NodoBinario<K,V> nodoActual= new NodoBinario<>();

        return nodoActual;
    }

    private NodoBinario<K,V> reconstruirConPostOrden(List<K> clavesNoInOrden,List<V> valoresNoInOrden){
        NodoBinario<K,V> nodoActual= new NodoBinario<>();

        return nodoActual;
    }

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
            if (nodoAnterior.getClave().compareTo(claveAInsertar)>0){
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
        return size(raiz);
    }
    private int size(NodoBinario<K,V> nodoActual){
        if(NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
            int sizeXIzq = size(nodoActual.getHijoIzquierdo());
            int sizeXDer = size(nodoActual.getHijoDerecho());
            return sizeXDer+sizeXIzq+1;

    }
    @Override
    public int altura() {
        return 0;
    }

    @Override
    public void vaciar() {

        this.raiz=(NodoBinario<K,V>) NodoBinario.nodoVacio();
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
        List<K> recorrido = new LinkedList<>();
        recorridoEnInOrden(raiz,recorrido);
        return recorrido;
    }

    private List<K> recorridoEnInOrden(NodoBinario<K,V> nodoActual,List<K> recorrido){
        if (!NodoBinario.esNodoVacio(nodoActual)){
            if (!nodoActual.esVacioHijoIzquierdo()){
                recorridoEnInOrden(nodoActual.getHijoIzquierdo(),recorrido);
            }
            recorrido.add(nodoActual.getClave());
            if (!nodoActual.esVacioHijoDerecho()){
                recorridoEnInOrden(nodoActual.getHijoDerecho(),recorrido);
                //recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }
    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido=new ArrayList<>();
        if (!esArbolVacio()){
            Stack<NodoBinario<K,V>> pilaDeNodos= new Stack<>();//cola
            pilaDeNodos.push(raiz);//añadir a la pila
            do{
                NodoBinario<K,V> nodoActual = pilaDeNodos.pop();//descolar
                recorrido.add(nodoActual.getClave());
                if(!nodoActual.esVacioHijoDerecho()){
                    pilaDeNodos.push(nodoActual.getHijoDerecho());
                }
                if(!nodoActual.esVacioHijoIzquierdo()){
                    pilaDeNodos.push(nodoActual.getHijoIzquierdo());
                }

            }while (!pilaDeNodos.isEmpty());//mientras no este vacia
        }
        return recorrido;
    }

    public List<K> recorridoEnPreOrdenV2(){
        List<K> recorrido= new LinkedList<>();
        recorridoEnPreOrdenV2(raiz,recorrido);
        return recorrido;
    }
    public void recorridoEnPreOrdenV2(NodoBinario<K,V> nodoActual,List<K> recorrido){
        if (!NodoBinario.esNodoVacio(nodoActual)){
            recorrido.add(nodoActual.getClave());
            recorridoEnPreOrdenV2(nodoActual.getHijoDerecho(),recorrido);
            recorridoEnPreOrdenV2(nodoActual.getHijoIzquierdo(),recorrido);
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido=new ArrayList<>();
        if (!esArbolVacio()) {
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            NodoBinario<K,V> nodoActual = this.raiz;
            meterPilaParaPostOrden(nodoActual,pilaDeNodos);
            //iterando sobre la pila
            while (!pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getClave());
            }
        }
        return recorrido;
    }

    private static<K extends Comparable<K>, V> void meterPilaParaPostOrden
            (NodoBinario<K,V> nodoActual,Stack<NodoBinario<K,V>> pilaDeNodos){
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            pilaDeNodos.push(nodoActual);
            meterPilaParaPostOrden(nodoActual.getHijoDerecho(), pilaDeNodos);
            meterPilaParaPostOrden(nodoActual.getHijoIzquierdo(), pilaDeNodos);
        }
    }
    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido=new ArrayList<>();
        if (!esArbolVacio()){
            Queue<NodoBinario<K,V>> colaDeNodos= new LinkedList<>();//cola
            colaDeNodos.offer(raiz);//añadir a la cola
            do{
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();//descolar
                recorrido.add(nodoActual.getClave());
                if(!nodoActual.esVacioHijoIzquierdo()){
                    colaDeNodos.offer(nodoActual.getHijoIzquierdo());
                }
                if(!nodoActual.esVacioHijoDerecho()){
                    colaDeNodos.offer(nodoActual.getHijoDerecho());
                }

            }while (!colaDeNodos.isEmpty());//mientras no este vacia
        }
        return recorrido;
    }
}

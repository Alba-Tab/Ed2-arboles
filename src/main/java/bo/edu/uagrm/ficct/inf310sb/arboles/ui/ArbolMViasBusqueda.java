package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.OrdenInvalidoExcepcion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMViasBusqueda <K extends Comparable<K>,V>
        implements IArbolBusqueda<K,V>{
    protected  NodoMVias<K,V> raiz;
    protected final int orden;
    private static final int ORDEN_MINIMO = 3;
    private static final int POSICION_INVALIDA = -1;
    public ArbolMViasBusqueda(){
        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws OrdenInvalidoExcepcion {
        if (orden<ArbolMViasBusqueda.ORDEN_MINIMO){
            throw new OrdenInvalidoExcepcion();
        }
        this.orden=orden;
    }

    @Override
    public void insertar(K claveAInsertar, V valorAsociado) {
        if (claveAInsertar == null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        if (valorAsociado == null){
            throw new IllegalArgumentException("Valor no puede ser nulo");
        }
        if (this.esArbolVacio()){
            this.raiz=new NodoMVias<>(this.orden,claveAInsertar,valorAsociado);
            return;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        do{
            int posicionDeClaveAInsertar = this.buscarPosicionDeClave(claveAInsertar,nodoActual);
            if (posicionDeClaveAInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClaveAInsertar, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()){
                if (nodoActual.estanClavesLLenas()){
                    int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoActual,claveAInsertar);
                    NodoMVias<K,V> nuevoNodo = new NodoMVias<>(this.orden,claveAInsertar,valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar,nuevoNodo);
                } else {
                    insertarClaveYValorOrdenado(nodoActual,claveAInsertar,valorAsociado);
                }
                nodoActual=NodoMVias.nodoVacio();
            }else{
                //el nodo actual no es hoja
                int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoActual,claveAInsertar);
                if(nodoActual.esHijoVacio(posicionPorDondeBajar)){
                    NodoMVias<K,V> nuevoNodo = new NodoMVias<>(this.orden,claveAInsertar,valorAsociado);
                    nodoActual.setHijo(posicionPorDondeBajar,nuevoNodo);
                    nodoActual=NodoMVias.nodoVacio();
                } else {
                    nodoActual = nodoActual.getHijo(posicionPorDondeBajar);
                }
            }
        }while (!NodoMVias.esNodoVacio(nodoActual));
    }

    @Override
    public V eliminar(K clave) throws ClaveNoExisteExcepcion {
        return null;
    }

    protected int buscarPosicionDeClave(K claveABuscar,NodoMVias<K,V> nodoActual){
        for (int i=0;i<nodoActual.nroClavesNoVacias();i++){
            if ((claveABuscar.compareTo(nodoActual.getClave(i))==0)){
                return i;
            }
        }
        return ArbolMViasBusqueda.POSICION_INVALIDA;
    }

    protected int buscarPosicionPorDondeBajar(NodoMVias<K,V> nodoActual,K claveAInsertar){
        for (int i=0;i<this.orden;i++){
            if (claveAInsertar.compareTo(nodoActual.getClave(i))<0){
                return i;
            }
        }
        return this.orden;
    }

    protected void insertarClaveYValorOrdenado(NodoMVias<K,V> nodoActual, K claveAInsertar, V valorAsociado){
       boolean valorInsertado = false;
        for (int i = 0;i<nodoActual.nroClavesNoVacias();i++){
            if (claveAInsertar.compareTo(nodoActual.getClave(i))<0){
                recorrerClaveYValor(nodoActual,i);
                nodoActual.setClave(i,claveAInsertar);
                nodoActual.setValor(i,valorAsociado);
                return;
            }
        }
        nodoActual.setClave(nodoActual.nroClavesNoVacias(),claveAInsertar);
        nodoActual.setValor(nodoActual.nroClavesNoVacias()-1,valorAsociado);
    }

    private void recorrerClaveYValor(NodoMVias<K,V> nodoActual,int recorrerDesde){
        if (recorrerDesde<nodoActual.nroClavesNoVacias()){
            recorrerClaveYValor(nodoActual,recorrerDesde+1);
            nodoActual.setValor(recorrerDesde+1,nodoActual.getValor(recorrerDesde));
            nodoActual.setClave(recorrerDesde+1,nodoActual.getClave(recorrerDesde));
        }
    }

    @Override
    public V buscar(K claveABuscar) {
        if (claveABuscar==null){
            throw new IllegalArgumentException("En el arbol no hay claves nulas");
        }
        if (!this.esArbolVacio()) {
            NodoMVias<K, V> nodoActual = raiz;
            do {
                boolean cambiarNodoAuxiliar = false;
                for (int i = 0; i < nodoActual.nroClavesNoVacias() &&
                        !cambiarNodoAuxiliar; i++) {
                    K claveaux = nodoActual.getClave(i);
                    if (claveABuscar.compareTo(claveaux) == 0) {
                        return nodoActual.getValor(i);
                    } else if (claveABuscar.compareTo(claveaux) < 0) {
                        nodoActual = nodoActual.getHijo(i);
                        cambiarNodoAuxiliar = true;
                    }
                }
                if (!cambiarNodoAuxiliar) {
                    nodoActual = nodoActual.getHijo(nodoActual.nroClavesNoVacias());
                }
            } while (NodoMVias.esNodoVacio(nodoActual));
        }
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        return this.buscar(clave) != null;
    }

    @Override
    public int size() {
        return size(raiz);
    }
    private int size(NodoMVias<K,V> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int sizeTotal = 0;
        for (int i =0;i< nodoActual.nroClavesNoVacias();i++){
            sizeTotal= size(nodoActual.getHijo(i));
        }
        return sizeTotal+ nodoActual.nroClavesNoVacias();

    }

    @Override
    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaMayor=0;
        for (int i=0; i<this.orden;i++){
            int alturaDelHijoActual = altura(nodoActual.getHijo(i));
            if (alturaDelHijoActual>alturaMayor){
                alturaMayor = alturaDelHijoActual;
            }
        }
        return alturaMayor+1;

    }
    @Override
    public void vaciar() {
        this.raiz=NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
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

    private void recorridoEnInOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (!NodoMVias.esNodoVacio(nodoActual)){
            for (int i=0; i < nodoActual.nroClavesNoVacias();i++){
                recorridoEnInOrden(nodoActual.getHijo(i),recorrido);
                recorrido.add(nodoActual.getClave(i));
            }
            recorridoEnInOrden(nodoActual.getHijo(nodoActual.nroClavesNoVacias()),recorrido);
        }
    }

    @Override
    public List<K> recorridoEnPreOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPreOrden(raiz,recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (!NodoMVias.esNodoVacio(nodoActual)){
            for (int i=0; i < nodoActual.nroClavesNoVacias();i++){
                recorrido.add(nodoActual.getClave(i));
                recorridoEnPreOrden(nodoActual.getHijo(i),recorrido);
            }
            recorridoEnPreOrden(nodoActual.getHijo(nodoActual.nroClavesNoVacias()),recorrido);
        }
    }

    @Override
    public List<K> recorridoEnPostOrden() {
        List<K> recorrido = new LinkedList<>();
        recorridoEnPostOrden(raiz,recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
        if (!NodoMVias.esNodoVacio(nodoActual)){
            recorridoEnPostOrden(nodoActual.getHijo(0),recorrido);
            for (int i=0; i < nodoActual.nroClavesNoVacias();i++){
                recorridoEnPostOrden(nodoActual.getHijo(i+1),recorrido);
                recorrido.add(nodoActual.getClave(i));
            }
        }
    }

    @Override
    public List<K> recorridoPorNiveles() {
        List<K> recorrido=new ArrayList<>();
        if (!esArbolVacio()){
            Queue<NodoMVias<K, V>> colaDeNodos= new LinkedList<>();//cola
            colaDeNodos.offer(raiz);//añadir a la cola

            do{
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();//descolar
                for (int i = 0; i < nodoActual.nroClavesNoVacias(); i++){
                    recorrido.add(nodoActual.getClave(i));
                    if(!nodoActual.esHijoVacio(i)){
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                if(!nodoActual.esHijoVacio(nodoActual.nroClavesNoVacias())){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroClavesNoVacias()));
                }


            }while (!colaDeNodos.isEmpty());//mientras no este vacia
        }
        return recorrido;
    }

    public int cantidadNodosConHijosPar(){
        if(!esArbolVacio()){
            return cantidadNodosConHijosPar(raiz);
        }
        return 0;
    }
    private int cantidadNodosConHijosPar(NodoMVias<K,V> nodoActual){
        if (NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidad = 0;
        for (int i = 0; i<= nodoActual.nroClavesNoVacias();i++){
            cantidad+=cantidadNodosConHijosPar(nodoActual.getHijo(i));
        }
        if (nodoActual.nroClavesNoVacias() % 2 ==0 ){
            return cantidad+1;
        }
        return cantidad;
    }
}

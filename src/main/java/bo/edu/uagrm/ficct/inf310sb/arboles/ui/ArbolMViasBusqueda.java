package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.OrdenInvalidoExcepcion;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.awt.Graphics;

public class ArbolMViasBusqueda <K extends Comparable<K>,V>
        implements IArbolBusqueda<K,V>{
    protected   NodoMVias<K,V> raiz;
    protected final int orden;
    protected static final int ORDEN_MINIMO = 3;
    protected static final int POSICION_INVALIDA = -1;


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
        if (clave == null){
            throw new IllegalArgumentException("Clave no puede ser nula");
        }
        V valorARetornar = this.buscar(clave);
        if (valorARetornar==null){
            throw new ClaveNoExisteExcepcion();
        }
        this.raiz= eliminar(raiz,clave);
        return valorARetornar;
    }

    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual,K claveAEliminar){

        for (int i=0;i<nodoActual.nroClavesNoVacias(); i++){
            K claveEnTurno =nodoActual.getClave(i);
            if (claveAEliminar.compareTo(claveEnTurno)==0){
                if (nodoActual.esHoja()){
                    this.eliminarClaveDeNodoDePosicion(nodoActual,i);
                    if (!nodoActual.hayClavesNoVacias()){
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //caso 2
                K claveDeReemplazo;
                        if (this.hayHijosMasAdelanteDeLaPosicion(nodoActual,i)){
                            claveDeReemplazo = this.obtenerSucesorInOrden(nodoActual,claveAEliminar);
                        } else {
                            claveDeReemplazo = this.obtenerPredecesorInOrden(nodoActual,claveAEliminar);
                        }

                        V valorDeReemplazo= this.buscar(claveDeReemplazo);

                        nodoActual= eliminar(nodoActual,claveDeReemplazo);
                        nodoActual.setClave(i,claveDeReemplazo);
                        nodoActual.setValor(i,valorDeReemplazo);
                        return nodoActual;
            }
            if (claveAEliminar.compareTo(claveEnTurno)<0){
                NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual,claveAEliminar);
                nodoActual.setHijo(i,supuestoNuevoHijo);
                return nodoActual;
            }
        }
        NodoMVias<K,V> supuestoNuevoHijo = this.eliminar(nodoActual.getHijo(nodoActual.nroClavesNoVacias()),
                claveAEliminar);
        nodoActual.setHijo(nodoActual.nroClavesNoVacias(),supuestoNuevoHijo);
        return nodoActual;
    }

    protected void eliminarClaveDeNodoDePosicion(NodoMVias<K,V> nodoActual, int posicion){

        for (int i =posicion;i< nodoActual.nroClavesNoVacias();i++){
            nodoActual.setClave(i,nodoActual.getClave(i+1));
            nodoActual.setValor(i,nodoActual.getValor(i+1));
        }
        nodoActual.setValor(nodoActual.nroClavesNoVacias(), (V)NodoMVias.datoVacio());
        nodoActual.setClave(nodoActual.nroClavesNoVacias(), (K)NodoMVias.datoVacio());
    }

    private boolean hayHijosMasAdelanteDeLaPosicion(NodoMVias<K,V> nodoActual, int posicion){
        return !nodoActual.esHijoVacio(posicion+1);
    }

    protected K obtenerSucesorInOrden (NodoMVias<K,V> nodoActual,K claveDeReferencia){
        boolean hijoEncontrado=false;
        for (int i=0;i< nodoActual.nroClavesNoVacias() && !hijoEncontrado;i++ ){
            if (nodoActual.getClave(i).compareTo(claveDeReferencia)==0){
                nodoActual=nodoActual.getHijo(i+1);
                hijoEncontrado=true;
            }
        }
        while (nodoActual.getHijo(0)!=NodoMVias.nodoVacio()){
            nodoActual=nodoActual.getHijo(0);
        }
        return nodoActual.getClave(0);
    }

    protected K obtenerPredecesorInOrden (NodoMVias<K,V> nodoActual,K claveDeReferencia){
        boolean hijoEncontrado=false;
        for (int i=0;i< nodoActual.nroClavesNoVacias() && !hijoEncontrado;i++ ){
            if (nodoActual.getClave(i).compareTo(claveDeReferencia)==0){
                nodoActual=nodoActual.getHijo(i);
                hijoEncontrado=true;
            }
        }
        while (nodoActual.getHijo(nodoActual.nroClavesNoVacias())!=NodoMVias.nodoVacio()){
            nodoActual=nodoActual.getHijo(nodoActual.nroClavesNoVacias());
        }
        return nodoActual.getClave(nodoActual.nroClavesNoVacias()-1);
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
    public List<V> recorridoValoresEnInOrden() {
        List<V> recorrido = new LinkedList<>();
        recorridoValoresInOrden(raiz,recorrido);
        return recorrido;
    }

    private void recorridoValoresInOrden(NodoMVias<K,V> nodoActual,List<V> recorrido){
        if (!NodoMVias.esNodoVacio(nodoActual)){
            for (int i=0; i < nodoActual.nroClavesNoVacias();i++){
                recorridoValoresInOrden(nodoActual.getHijo(i),recorrido);
                recorrido.add(nodoActual.getValor(i));
            }
            recorridoValoresInOrden(nodoActual.getHijo(nodoActual.nroClavesNoVacias()),recorrido);
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

    public List<V> recorridoValoresEnPorNiveles() {
        List<V> recorrido=new ArrayList<>();
        if (!esArbolVacio()){
            Queue<NodoMVias<K, V>> colaDeNodos= new LinkedList<>();//cola
            colaDeNodos.offer(raiz);//añadir a la cola

            do{
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();//descolar
                for (int i = 0; i < nodoActual.nroClavesNoVacias(); i++){
                    recorrido.add(nodoActual.getValor(i));
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
    public String imprimirArbol(){
        String recorrido="";
        String espacio="└";
        if (!esArbolVacio()){
            Queue<NodoMVias<K, V>> colaDeNodos= new LinkedList<>();//cola
            colaDeNodos.offer(raiz);//añadir a la cola
            int hijo =0;
            do{
                NodoMVias<K, V> nodoActual = colaDeNodos.poll();//descolar
                int nivelNodoActual = nivelActual(nodoActual,raiz,0);

                for (int i = 0; i < nodoActual.nroClavesNoVacias(); i++){
                    recorrido+=espacio+hijo+"├("+i+") "+nodoActual.getClave(i)+"\n";
                    if(!nodoActual.esHijoVacio(i)){
                        colaDeNodos.offer(nodoActual.getHijo(i));
                    }
                }
                hijo++;
                if (nivelNodoActual!=nivelActual(colaDeNodos.peek(),raiz,0) ) {
                    espacio="  "+espacio;
                    hijo=0;
                }
                if(!nodoActual.esHijoVacio(nodoActual.nroClavesNoVacias())){
                    colaDeNodos.offer(nodoActual.getHijo(nodoActual.nroClavesNoVacias()));
                }
            }while (!colaDeNodos.isEmpty());//mientras no este vacia
        }
        return recorrido;
    }

    protected int nivelActual(NodoMVias<K, V> actual, NodoMVias<K, V> nodo, int nivelActual) {
        if (NodoMVias.esNodoVacio(actual)) {
            return -1; // Nodo no encontrado
        }

        if (actual == nodo) {
            return nivelActual;
        }

        for (int i = 0; i < actual.nroClavesNoVacias() + 1; i++) {
            int nivelHijo = nivelActual(actual.getHijo(i), nodo, nivelActual + 1);
            if (nivelHijo != -1) {
                return nivelHijo;
            }
        }
        return -1; // Nodo no encontrado en los hijos
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

    public NodoBinario<K, V> getraiz(){
        return null;
    }
    public NodoMVias<K,V> getraizM(){
        return raiz;
    }

    public static int getOrdenMinimo(){
        return ORDEN_MINIMO;
    }
    public int nroClavesVaciasHastaElNivel(int nivel){
        return nroClavesVaciasHastaElNivel(raiz,nivel);
    }
    private int nroClavesVaciasHastaElNivel(NodoMVias<K,V> nodoActual, int nivel){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return orden;
        }
        int nroClaves=orden-nodoActual.nroClavesNoVacias();
        for(int i=0;i<=nodoActual.nroClavesNoVacias() && nivel!=0;i++){
            nroClaves+=nroClavesVaciasHastaElNivel(nodoActual.getHijo(i),nivel-1);
        }
        return nroClaves;
    }
}

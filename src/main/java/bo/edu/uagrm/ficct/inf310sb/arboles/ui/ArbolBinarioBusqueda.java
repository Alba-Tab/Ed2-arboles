package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;

import bo.edu.uagrm.ficct.inf310sb.arboles.GUI.Dibujo;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ArbolBinarioBusqueda<K extends Comparable<K>, V> implements
        IArbolBusqueda<K,V> {
    public NodoBinario<K,V> raiz;
    public ArbolBinarioBusqueda(){}


    public ArbolBinarioBusqueda(List<K> clavesInOrden, List<V> valoresInOrden,
                                List<K> clavesNoInOrden, List<V> valoresNoInOrden,
                                boolean conPreOrden){
        //con preorden true
        if (conPreOrden){
            this.raiz=reconstruirConPreOrden(clavesInOrden, valoresInOrden,
                                            clavesNoInOrden, valoresNoInOrden);
        }else {
            this.raiz=reconstruirConPostOrden(clavesInOrden, valoresInOrden,
                                            clavesNoInOrden, valoresNoInOrden);
        }
    }

    private NodoBinario<K,V> reconstruirConPreOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                                    List<K> clavesPreOrden,List<V> valoresPreOrden){
       if (clavesInOrden.isEmpty()){
            return NodoBinario.nodoVacio();
        }
        NodoBinario<K,V> nodoActual= new NodoBinario<>();
        int tamañoLista= clavesInOrden.size();
        int posicionRaiz=0;
        K claveRaiz = clavesPreOrden.get(0);
        K posibleRaiz = clavesInOrden.get(posicionRaiz);
        while ( claveRaiz.compareTo(posibleRaiz)!=0 ) {
            posicionRaiz++;
            posibleRaiz = clavesInOrden.get(posicionRaiz);
        }
        nodoActual.setClave(clavesPreOrden.get(0));
        nodoActual.setValor(valoresPreOrden.get(0));
        nodoActual.setHijoIzquierdo(reconstruirConPreOrden(
                clavesInOrden.subList(0,posicionRaiz),
                valoresInOrden.subList(0,posicionRaiz),
                clavesPreOrden.subList(1,posicionRaiz+1),
                valoresPreOrden.subList(1,posicionRaiz+1)        )
        );
        nodoActual.setHijoDerecho(reconstruirConPreOrden(
                clavesInOrden.subList(posicionRaiz+1,tamañoLista),
                valoresInOrden.subList(posicionRaiz+1,tamañoLista),
                clavesPreOrden.subList(posicionRaiz+1,tamañoLista),
                valoresPreOrden.subList(posicionRaiz+1,tamañoLista)        )
        );

        return nodoActual;
    }

    private NodoBinario<K,V> reconstruirConPostOrden(List<K> clavesInOrden,List<V> valoresInOrden,
                                                     List<K> clavesPostOrden,List<V> valoresPostOrden){
       if (clavesInOrden.isEmpty()){
            return NodoBinario.nodoVacio();
        }
        NodoBinario<K,V> nodoActual= new NodoBinario<>();
        int tamañoLista= clavesInOrden.size();
        int posicionRaiz=0;
        K claveRaiz = clavesPostOrden.get(tamañoLista-1);
        K posibleRaiz = clavesInOrden.get(posicionRaiz);
        while ( claveRaiz.compareTo(posibleRaiz)!=0 ) {
            posicionRaiz++;
            posibleRaiz = clavesInOrden.get(posicionRaiz);
        }
        nodoActual.setClave(clavesPostOrden.get(tamañoLista-1));
        nodoActual.setValor(valoresPostOrden.get(tamañoLista-1));
        nodoActual.setHijoIzquierdo(reconstruirConPostOrden(
                clavesInOrden.subList(0,posicionRaiz),
                valoresInOrden.subList(0,posicionRaiz),
                clavesPostOrden.subList(0,posicionRaiz),
                valoresPostOrden.subList(0,posicionRaiz)        )
                );
        nodoActual.setHijoDerecho(reconstruirConPostOrden(
                clavesInOrden.subList(posicionRaiz+1,tamañoLista),
                valoresInOrden.subList(posicionRaiz+1,tamañoLista),
                clavesPostOrden.subList(posicionRaiz,tamañoLista-1),
                valoresPostOrden.subList(posicionRaiz,tamañoLista-1)        )
        );
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
       V valorARetornar = this.buscar(clave);
       if (valorARetornar==null){
           throw new ClaveNoExisteExcepcion();
       }
       this.raiz= eliminar(raiz,clave);
       return valorARetornar;
    }
    private NodoBinario<K,V> eliminar(NodoBinario<K,V> nodoActual,K claveAEliminar){
        K claveDelNodoActual=nodoActual.getClave();
        if (claveAEliminar.compareTo(claveDelNodoActual)<0){
            NodoBinario<K,V> supuestoHijoIzquierdo =
                    eliminar(nodoActual.getHijoIzquierdo(),claveAEliminar);
            nodoActual.setHijoIzquierdo(supuestoHijoIzquierdo);
            return nodoActual;
        }
        if (claveAEliminar.compareTo(claveDelNodoActual)>0){
            NodoBinario<K,V> supuestoHijoDerecho=
                    eliminar(nodoActual.getHijoDerecho(),claveAEliminar);
            nodoActual.setHijoDerecho(supuestoHijoDerecho);
            return nodoActual;
        }
        //caso 1
        if(nodoActual.esHoja()){
            return NodoBinario.nodoVacio();
        }
        //caso 2
        if (!nodoActual.esVacioHijoIzquierdo() && nodoActual.esVacioHijoDerecho()){
            return nodoActual.getHijoIzquierdo();
        }
        if (!nodoActual.esVacioHijoDerecho() && nodoActual.esVacioHijoIzquierdo()){
            return  nodoActual.getHijoDerecho();
        }
        //caso 3
                                        //recibe el de la derecha y se mueve a puro izquierda
        NodoBinario<K,V> nodoDelSucesor =this.getSucesor(nodoActual.getHijoDerecho());
        NodoBinario<K,V> supuestoHijoDerecho =this.eliminar(nodoActual.getHijoDerecho(),
                                                            nodoDelSucesor.getClave());
        nodoActual.setHijoDerecho(supuestoHijoDerecho);
        nodoActual.setClave(nodoDelSucesor.getClave());
        nodoActual.setValor(nodoDelSucesor.getValor());
        return nodoActual;
    }
    protected NodoBinario<K,V> getSucesor(NodoBinario<K,V> nodoActual){
    //while
        // nodo=hijoizquierdo
        if(!nodoActual.esVacioHijoIzquierdo()){
            nodoActual= getSucesor(nodoActual.getHijoIzquierdo());
        }
        return nodoActual;
    }
    @Override
    public V buscar(K clave) {
        NodoBinario<K,V> nodoActual = raiz;
        while ( !NodoBinario.esNodoVacio(nodoActual)){
            K claveNodoActual = nodoActual.getClave();
            if (clave.compareTo(claveNodoActual)>0){
                nodoActual=nodoActual.getHijoDerecho();
            } else if (clave.compareTo(claveNodoActual)<0){
                nodoActual=nodoActual.getHijoIzquierdo();
            } else return nodoActual.getValor();
        }
        return null;
    }

    @Override
    public boolean contiene(K clave) {
        if (!esArbolVacio()){
            NodoBinario<K,V> nodoActual = this.raiz;
            while (!NodoBinario.esNodoVacio(nodoActual)){
                K claveActual = nodoActual.getClave();
                if (claveActual.compareTo(clave)>0){
                    nodoActual=nodoActual.getHijoIzquierdo();
                }else if (claveActual.compareTo(clave)<0){
                    nodoActual=nodoActual.getHijoDerecho();
                } else return true;
            }
        };
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
        return altura(raiz);
    }

    protected int altura(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int alturaXIzq=altura(nodoActual.getHijoIzquierdo());
        int alturaXDer=altura(nodoActual.getHijoDerecho());
        return alturaXIzq>alturaXDer? alturaXIzq+1:alturaXDer+1;

    }

    @Override
    public void vaciar() {

        this.raiz= NodoBinario.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        if(this.altura()==0){
            return 0;
        }
        return this.altura()-1;
    }
    //un metodo que retorna la cantidad de hijos izquierdos no vacio que tiene
    public int cantHijosIzquierdoNoVacios(){
            return cantHijosIzquierdoNoVacios(raiz);
    }
    private int cantHijosIzquierdoNoVacios (NodoBinario<K,V> nodoActual){
        int cantidad=0;
        if (!NodoBinario.esNodoVacio(nodoActual)){
            if (!nodoActual.esVacioHijoIzquierdo()){
                cantidad=cantHijosIzquierdoNoVacios(nodoActual.getHijoIzquierdo())+1;
            }
            if (!nodoActual.esVacioHijoDerecho()){
                cantidad+=cantHijosIzquierdoNoVacios(nodoActual.getHijoDerecho());
            }
        }
        return cantidad;
    }
    //cantidad hijo izquierso en el nivel n
    public int hijosEnNivelN(int n){
        return hijosEnNivelN(n,raiz);
    }
    private int hijosEnNivelN(int n,NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)|| (n<0)){
            return 0;
        }
        int c = hijosEnNivelN(n-1,nodoActual.getHijoDerecho())+
                hijosEnNivelN(n-1,nodoActual.getHijoDerecho());
        if ((!nodoActual.esVacioHijoIzquierdo())&&(n==0)){
            c++;
        }
        return c;
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
    public List<V> recorridoValoresEnInOrden() {
        List<V> recorrido = new LinkedList<>();
        recorridoEnInOrdenValores(raiz,recorrido);
        return recorrido;
    }

    private List<V> recorridoEnInOrdenValores(NodoBinario<K,V> nodoActual,List<V> recorrido){
        if (!NodoBinario.esNodoVacio(nodoActual)){
            if (!nodoActual.esVacioHijoIzquierdo()){
                recorridoEnInOrdenValores(nodoActual.getHijoIzquierdo(),recorrido);
            }
            recorrido.add(nodoActual.getValor());
            if (!nodoActual.esVacioHijoDerecho()){
                recorridoEnInOrdenValores(nodoActual.getHijoDerecho(),recorrido);
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

    public List<V> recorridoEnPreOrdenValores(){
        List<V> recorrido= new LinkedList<>();
        recorridoEnPreOrdenValores(raiz,recorrido);
        return recorrido;
    }
    public void recorridoEnPreOrdenValores(NodoBinario<K,V> nodoActual,List<V> recorrido){
        if (!NodoBinario.esNodoVacio(nodoActual)){
            recorrido.add(nodoActual.getValor());
            recorridoEnPreOrdenValores(nodoActual.getHijoDerecho(),recorrido);
            recorridoEnPreOrdenValores(nodoActual.getHijoIzquierdo(),recorrido);
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

    public List<V> recorridoEnPostOrdenValores() {
        List<V> recorrido=new ArrayList<>();
        if (!esArbolVacio()) {
            Stack<NodoBinario<K,V>> pilaDeNodos = new Stack<>();
            NodoBinario<K,V> nodoActual = this.raiz;
            meterPilaParaPostOrden(nodoActual,pilaDeNodos);
            //iterando sobre la pila
            while (!pilaDeNodos.isEmpty()) {
                nodoActual = pilaDeNodos.pop();
                recorrido.add(nodoActual.getValor());
            }
        }
        return recorrido;
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
    public List<V> recorridoValoresEnPorNiveles() {
        List<V> recorrido=new ArrayList<>();
        if (!esArbolVacio()){
            Queue<NodoBinario<K,V>> colaDeNodos= new LinkedList<>();//cola
            colaDeNodos.offer(raiz);//añadir a la cola
            do{
                NodoBinario<K,V> nodoActual = colaDeNodos.poll();//descolar
                recorrido.add(nodoActual.getValor());
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

    public int nivelActual(K claveABuscar){
        int nivel=0;
        NodoBinario<K,V> nodoActual= raiz;
        while(!NodoBinario.esNodoVacio(nodoActual));{
            K claveNodoActual = nodoActual.getClave();
            if (claveABuscar.compareTo(claveNodoActual)>0){
                nodoActual=nodoActual.getHijoDerecho();
            } else if (claveABuscar.compareTo(claveNodoActual)<0){
                nodoActual=nodoActual.getHijoIzquierdo();
            } else return nivel;
            nivel++;
        }
            return nivel;
    }


    public String imprimirArbol() {
        String espacio = "";
        return imprimir(raiz,espacio);
    }

    protected String imprimir(NodoBinario<K,V> nodoActual,String espacio){
       String arbol ="";
        if (!NodoBinario.esNodoVacio(nodoActual)){
            arbol=espacio+"└"+nodoActual.getClave()+"\n";
            espacio+="  ";
            arbol+=imprimir(nodoActual.getHijoIzquierdo(),espacio);

            arbol+=imprimir(nodoActual.getHijoDerecho(),espacio);
        }
        return arbol;
    }

    public int nroNodosSoloConHijoIzquierdo(){
        return nroNodosSoloConHijoIzquierdo(this.raiz);
    }

    private int nroNodosSoloConHijoIzquierdo(NodoBinario<K,V> nodoActual){
        if (NodoBinario.esNodoVacio(nodoActual)){
            return 0;
        }
        int cantidad=nroNodosSoloConHijoIzquierdo(nodoActual.getHijoIzquierdo())
                +nroNodosSoloConHijoIzquierdo(nodoActual.getHijoDerecho());
        if(NodoBinario.esNodoVacio(nodoActual.getHijoDerecho()) &&
            !NodoBinario.esNodoVacio(nodoActual.getHijoIzquierdo())){
            cantidad++;
        }
        return cantidad;
    }
    public void dibujarArbolBinario(Graphics pizarra, int x1) {
        if (!this.esArbolVacio()) {
            dibujarArbolBinario(pizarra, this.raiz, x1, 30, 1);
            // DibujarArbolBinario(pizarra, arbol, arbol.getraiz(), x1, y1, x2, y2,0);
        }
    }

    private void dibujarArbolBinario(Graphics pizarra, NodoBinario nodoActual, int x, int y, int nivel) {
       int dimensionNodo =40;
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            // Calcula nuevas posiciones para los hijos
            int espacioX = (int) (Dibujo.ESPACIO_HORIZONTAL * Math.pow(2, this.altura() - nivel));
            int hijoIzquierdoX = x - espacioX;
            int hijoDerechoX = x + espacioX;
            int hijoY = y + Dibujo.ESPACIO_VERTICAL;

            // Dibuja líneas hacia los hijos
            if (!nodoActual.esVacioHijoIzquierdo()) {
                Dibujo.hacerLinea(pizarra, x + dimensionNodo / 2, y + dimensionNodo / 2, hijoIzquierdoX + dimensionNodo / 2, hijoY);
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                Dibujo.hacerLinea(pizarra, x + dimensionNodo / 2, y + dimensionNodo / 2, hijoDerechoX + dimensionNodo / 2, hijoY);
            }

            Dibujo.HacerRedondo(pizarra, x, y, dimensionNodo, dimensionNodo);
            Dibujo.hacerLlave(pizarra, nodoActual, x, y, dimensionNodo, dimensionNodo);

            // Llamadas recursivas para los hijos
            dibujarArbolBinario(pizarra, nodoActual.getHijoIzquierdo(), hijoIzquierdoX, hijoY, nivel + 1);
            dibujarArbolBinario(pizarra, nodoActual.getHijoDerecho(), hijoDerechoX, hijoY, nivel + 1);
        }
    }
}

package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.OrdenInvalidoExcepcion;

import java.util.List;
import java.util.Stack;

public class ArbolB<K extends Comparable<K>, V> extends
    ArbolMViasBusqueda<K,V>{
    private int nroMinimoDeHijos;
    private int nroMinimoDeDatos;
    private int nroMaximoDeDatos;
    public ArbolB(){
        super();
        this.calcularAdicionales();
    }

    public ArbolB(int orden) throws OrdenInvalidoExcepcion{
        super(orden);
        this.calcularAdicionales();
    }
    private void calcularAdicionales(){
        this.nroMaximoDeDatos = super.orden-1;
        this.nroMinimoDeDatos = this.nroMaximoDeDatos/2;
        this.nroMinimoDeHijos = this.nroMinimoDeDatos+1;
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
            this.raiz=new NodoMVias<>(this.orden+1,claveAInsertar,valorAsociado);
            return;
        }
        NodoMVias<K,V> nodoActual = this.raiz;
        Stack<NodoMVias<K,V> > ancestros =new Stack<>();
        do{
            int posicionDeClaveAInsertar = this.buscarPosicionDeClave(claveAInsertar,nodoActual);
            if (posicionDeClaveAInsertar!=ArbolMViasBusqueda.POSICION_INVALIDA) {
                nodoActual.setValor(posicionDeClaveAInsertar, valorAsociado);
                nodoActual = NodoMVias.nodoVacio();
            } else if (nodoActual.esHoja()){
                insertarClaveYValorOrdenado(nodoActual,claveAInsertar,valorAsociado);
                if (nodoActual.nroClavesNoVacias()>nroMaximoDeDatos){
                    dividir(nodoActual,ancestros);
                }
                nodoActual=NodoMVias.nodoVacio();
            }else{
                //el nodo actual no es hoja
                int posicionPorDondeBajar = this.buscarPosicionPorDondeBajar(nodoActual,claveAInsertar);
                ancestros.push(nodoActual);
                nodoActual = nodoActual.getHijo(posicionPorDondeBajar);

            }
        }while (!NodoMVias.esNodoVacio(nodoActual));
    }
    public V eliminar(K claveAEliminar) throws NullPointerException, ClaveNoExisteExcepcion{
        if (claveAEliminar == null){
            throw new NullPointerException("Clave a nula, no puede ser nula");
        }
        Stack<NodoMVias<K,V> > pilaDeAncestros = new Stack<>();
        NodoMVias<K,V> nodoActual = this.buscarNodoDeLaClave(claveAEliminar,pilaDeAncestros);

        if (NodoMVias.esNodoVacio(nodoActual)){
            throw new ClaveNoExisteExcepcion();
        }
        int posicionDeClaveAEliminar = super.buscarPosicionDeClave(claveAEliminar,nodoActual);
        V valorARetornar = nodoActual.getValor(posicionDeClaveAEliminar);

        if (nodoActual.esHoja()){
            super.eliminarClaveDeNodoDePosicion(nodoActual,posicionDeClaveAEliminar);
            if (nodoActual.nroClavesNoVacias()<this.nroMinimoDeDatos){
                if (pilaDeAncestros.empty()){//nodo actual es igual a la raiz
                    if (!nodoActual.hayClavesNoVacias()){
                        super.vaciar();
                    }
                }else{
                    this.prestarseOFusionarse(nodoActual,pilaDeAncestros);
                }
            }
        } else { //no es hoja el nodo actual
             pilaDeAncestros.push(nodoActual);
             NodoMVias<K,V> nodoDelPredesesor = this.obtenerNodoDelPredesesor(
                     pilaDeAncestros,nodoActual.getHijo(posicionDeClaveAEliminar)
             );
             int posicionDeClavePredesesora= nodoDelPredesesor.nroClavesNoVacias()-1;
             K clavePredecesora = nodoDelPredesesor.getClave(posicionDeClavePredesesora);
             V valorAsociadoDelPredecesor = nodoDelPredesesor.getValor(posicionDeClavePredesesora);
             super.eliminarClaveDeNodoDePosicion(nodoDelPredesesor, posicionDeClavePredesesora);
             nodoActual.setClave(posicionDeClaveAEliminar,clavePredecesora);
             nodoActual.setValor(posicionDeClaveAEliminar,valorAsociadoDelPredecesor);
             if (nodoDelPredesesor.nroClavesNoVacias()<this.nroMinimoDeDatos){
                 this.prestarseOFusionarse(nodoDelPredesesor,pilaDeAncestros);
             }
        }
        return valorARetornar;
    }

    public void prestarseOFusionarse(NodoMVias<K,V> nodoActual,Stack<NodoMVias<K,V>> pilaDeAncestros){
      /*  NodoMVias<K,V> padre =pilaDeAncestros.pop();
        int indice = this.getPosicionDeHijo(padre,nodoActual);
        int indiceHermanoAnterior=indice-1;
        int indiceHermanoSiguiente=indice+1;

        NodoMVias<K,V> hermanoSiguiente = NodoMVias.nodoVacio();
        int nroClavesNoVaciasHermanoSiguiente = -this.nroMaximoDeDatos;

        if(indiceHermanoSiguiente<padre.nroClavesNoVacias()){
            hermanoSiguiente=padre.getHijo(indiceHermanoSiguiente);
            nroClavesNoVaciasHermanoSiguiente = hermanoSiguiente.nroClavesNoVacias();
        }
        if(!NodoMVias.esNodoVacio(hermanoSiguiente) &&
                nroClavesNoVaciasHermanoSiguiente>this.nroMinimoDeDatos){
            realizarPrestamoDelHermanoSiguiente(nodoActual,padre,hermanoSiguiente);
        } else {
            prestarseDelHermanoAnteriosOFusionarse(nodoActual,padre,her)
        }*/
    }

    public NodoMVias<K,V> obtenerNodoDelPredesesor(Stack<NodoMVias<K,V>> pilaDeAncestros,NodoMVias<K,V> nodoActual){
        // Encontrar el nodo del predecesor
        NodoMVias<K,V> nodoDelPredesesor = null;
        if (nodoActual != null) {
            if (!nodoActual.esHoja()) {
                pilaDeAncestros.push(nodoActual);
                NodoMVias<K,V> nodoAux = nodoActual.getHijo(nodoActual.nroClavesNoVacias());
                while (!nodoAux.esHoja()) {
                    pilaDeAncestros.push(nodoAux);
                    nodoAux = nodoAux.getHijo(nodoAux.nroClavesNoVacias());
                }
                nodoDelPredesesor = nodoAux;
            }
        }
        return nodoDelPredesesor;
    }
    public void dividir (NodoMVias<K,V> nodoActual,Stack<NodoMVias<K,V> > ancestros){
        K claveQueSube = nodoActual.getClave(nroMinimoDeDatos);
        V valorAsociadoQueSube = nodoActual.getValor(nroMinimoDeDatos);
        //dividimos los datos copiandolo a un nuevo nodo
        NodoMVias<K,V> supuestoNodoIzquierdo = new NodoMVias<>(orden+1);
        NodoMVias<K,V> supuestoNodoDerecho = new NodoMVias<>(orden+1);
        for (int i =0 ; i<nroMinimoDeDatos;i++){
            supuestoNodoIzquierdo.setValor(i,nodoActual.getValor(i));
            supuestoNodoIzquierdo.setClave(i,nodoActual.getClave(i));
            supuestoNodoIzquierdo.setHijo(i,nodoActual.getHijo(i));
        }
        supuestoNodoIzquierdo.setHijo(nroMinimoDeDatos,nodoActual.getHijo(nroMinimoDeHijos));
        for (int i =nroMinimoDeDatos+1;i<=nroMaximoDeDatos;i++){
            supuestoNodoDerecho.setValor(i-nroMinimoDeDatos-1,nodoActual.getValor(i));
            supuestoNodoDerecho.setClave(i-nroMinimoDeDatos-1,nodoActual.getClave(i));
            supuestoNodoDerecho.setHijo(i-nroMinimoDeDatos-1,nodoActual.getHijo(i));
        }
        supuestoNodoIzquierdo.setHijo(nroMaximoDeDatos-nroMinimoDeDatos,
                                    nodoActual.getHijo(nroMaximoDeDatos+1));

        if (ancestros.isEmpty()){
            // En caso el nodo sea raiz
            NodoMVias<K,V> nuevoNodoPadre = new NodoMVias<>(this.orden+1,claveQueSube,valorAsociadoQueSube);
            nuevoNodoPadre.setHijo(0,supuestoNodoIzquierdo);
            nuevoNodoPadre.setHijo(1,supuestoNodoDerecho);
            raiz=nuevoNodoPadre;
        } else {
            nodoActual =ancestros.pop();
            int posicion = insertarOrdenadoEnNoHoja(nodoActual,claveQueSube,valorAsociadoQueSube);
            nodoActual.setHijo(posicion,supuestoNodoIzquierdo);
            nodoActual.setHijo(posicion+1,supuestoNodoDerecho);
            if (nodoActual.nroClavesNoVacias()>nroMaximoDeDatos){
                dividir(nodoActual,ancestros);
            }
        }
    }
    private int insertarOrdenadoEnNoHoja(NodoMVias<K,V> nodoActual,K clave, V valor){
        int posicion=0;
        while(clave.compareTo(nodoActual.getClave(posicion))>0){
        posicion++;
        }
        int finClaves=nodoActual.nroClavesNoVacias();
        for (int i=finClaves;i>posicion;i--){
            nodoActual.setValor(i,nodoActual.getValor(i-1));
            nodoActual.setClave(i,nodoActual.getClave(i-1));
            nodoActual.setHijo(i+1,nodoActual.getHijo(i));
        }
        return posicion;
    }

    private NodoMVias<K,V> buscarNodoDeLaClave(K claveABuscar,Stack<NodoMVias<K,V>> pilaDeAncestros){
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
                        return nodoActual;
                    } else if (claveABuscar.compareTo(claveaux) < 0) {
                       pilaDeAncestros.push(nodoActual);
                        nodoActual = nodoActual.getHijo(i);
                        cambiarNodoAuxiliar = true;
                    }
                }
                if (!cambiarNodoAuxiliar) {
                    pilaDeAncestros.push(nodoActual);
                    nodoActual = nodoActual.getHijo(nodoActual.nroClavesNoVacias());
                }
            } while (NodoMVias.esNodoVacio(nodoActual));
        }
        return null;
    }


}

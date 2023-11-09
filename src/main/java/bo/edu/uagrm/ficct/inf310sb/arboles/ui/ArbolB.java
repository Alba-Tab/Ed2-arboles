package bo.edu.uagrm.ficct.inf310sb.arboles.ui;

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
            this.raiz=new NodoMVias<>(this.orden,claveAInsertar,valorAsociado);
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

}

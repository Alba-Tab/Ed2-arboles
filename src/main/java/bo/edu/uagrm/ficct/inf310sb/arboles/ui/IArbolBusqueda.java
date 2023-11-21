package bo.edu.uagrm.ficct.inf310sb.arboles.ui;
import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;

import java.awt.*;
import java.util.List;

import java.awt.Graphics;

public interface IArbolBusqueda <K extends Comparable<K>, V> {

    void insertar(K clave, V valor) ;
    V eliminar(K clave)  throws ClaveNoExisteExcepcion;
    V buscar (K clave) ;
    boolean contiene(K clave) ;
    int size();
    int altura();
    void vaciar();
    boolean esArbolVacio() ;
    int nivel ();
    List<K> recorridoEnInOrden() ;
    List<K> recorridoEnPreOrden() ;
    List<K> recorridoEnPostOrden() ;
    List<K> recorridoPorNiveles() ;
    List<V> recorridoValoresEnPorNiveles();
    NodoBinario<K,V> getraiz();
    NodoMVias<K,V> getraizM();
}
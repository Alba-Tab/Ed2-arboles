package bo.edu.uagrm.ficct.inf310sb.arboles;

import bo.edu.uagrm.ficct.inf310sb.arboles.ui.AVL;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.ArbolBinarioBusqueda;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.ArbolMViasBusqueda;

public class PruebasArbol {
    public static void main(String[] arg){
        // ArbolBinarioBusqueda<Integer, String> arbolPrueba = new ArbolBinarioBusqueda<>();
        //  ArbolMViasBusqueda<Integer,String> arbolPrueba = new ArbolMViasBusqueda<>();
         AVL<Integer,String> arbolPrueba = new AVL<>();
        arbolPrueba.insertar(75,"MM");
        arbolPrueba.insertar(50,"XY");
        arbolPrueba.insertar(90,"A8");
        arbolPrueba.insertar(30,"AT");
        arbolPrueba.insertar(62,"AM");
        arbolPrueba.insertar(81,"BK");
        arbolPrueba.insertar(42,"PP");
        arbolPrueba.insertar(85,"XA");
        arbolPrueba.insertar(76,"PP");
        arbolPrueba.insertar(30,"TT");

        System.out.println(arbolPrueba);
        System.out.println("Recorrido en InOrden\n"+arbolPrueba.recorridoEnInOrden());
        System.out.println("Recorrido en postOrden \n"+arbolPrueba.recorridoEnPostOrden());

        System.out.println("Recorrido en preOrden \n"+arbolPrueba.recorridoEnPreOrden());
        System.out.println("Recorrido por niveles \n"+arbolPrueba.recorridoPorNiveles());
       /*
        ArbolBinarioBusqueda<Integer,String> arbolReconstruidoPreOrden = new ArbolBinarioBusqueda<>(arbolPrueba.recorridoEnInOrden(),arbolPrueba.recorridoEnInOrdenValores(),
                arbolPrueba.recorridoEnPreOrden(),arbolPrueba.recorridoEnPreOrdenValores(),true);
        System.out.println("Reconstruido con preorden"+arbolReconstruidoPreOrden.recorridoEnInOrden() );
        ArbolBinarioBusqueda<Integer,String> arbolReconstruidoPostOrden = new ArbolBinarioBusqueda<>(arbolPrueba.recorridoEnInOrden(),arbolPrueba.recorridoEnInOrdenValores(),
                arbolPrueba.recorridoEnPostOrden(),arbolPrueba.recorridoEnPostOrdenValores(),false);
        System.out.println("Reconstruido con PostOrden"+arbolReconstruidoPreOrden.recorridoEnInOrden() );
        */

    }
}

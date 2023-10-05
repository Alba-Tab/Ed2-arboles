package bo.edu.uagrm.ficct.inf310sb.arboles;

public class PruebasArbol {
    public static void main(String[] arg){
        IArbolBusqueda<Integer,String> arbolPrueba = new ArbolBinarioBusqueda<>();

        arbolPrueba.insertar(75,"MM");
        arbolPrueba.insertar(50,"XY");
        arbolPrueba.insertar(90,"A8");
        arbolPrueba.insertar(30,"AT");
        arbolPrueba.insertar(62,"AM");
        arbolPrueba.insertar(81,"BK");
        arbolPrueba.insertar(42,"PP");
        arbolPrueba.insertar(85,"XA");
        //arbolPrueba.insertar(76,"PP");


        System.out.println(arbolPrueba);
        System.out.println("Recorrido en InOrden\n"+arbolPrueba.recorridoEnInOrden());
        System.out.println("Recorrido en postOrden \n"+arbolPrueba.recorridoEnPostOrden());
        System.out.println("Recorrido en preOrden \n"+arbolPrueba.recorridoEnPreOrden());
        System.out.println("Recorrido por niveles \n"+arbolPrueba.recorridoPorNiveles());




    }
}

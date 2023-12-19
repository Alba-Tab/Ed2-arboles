package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.utileria.UtilRecorido;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class kruskal {
// pagina 24
    //algoritmo de kruskal y de prim
    //GrafoPesado miGrafo;
     GrafoPesado miGrafoAuxiliar;
     List<ConexionConPeso> pesosOrdenados=new ArrayList<>();
    public kruskal(GrafoPesado unGrafo) throws NroVerticesInvalidoExcepcion,
            AristaYaExisteExcepcion, AristaNoExisteExcepcion {
       // unGrafo=miGrafo;
        //creamos un vertice auxiliar con los mismos vertices
        miGrafoAuxiliar=new GrafoPesado(unGrafo.cantidadVertices());
        for (int i=0;i< unGrafo.cantidadVertices();i++){
            List<AdyacenteConPeso> lista = unGrafo.listaDeAdyacencia.get(i);
            for (AdyacenteConPeso elemento:lista){
                ConexionConPeso conexion= new ConexionConPeso(i,
                        elemento.getIndiceVertice(), elemento.getPeso());
                pesosOrdenados.add(conexion);
            }
        }
        this.ordenar();
        for (ConexionConPeso elemento:pesosOrdenados){
            miGrafoAuxiliar.insertarArista(elemento.getOrigen(), elemento.getDestino(), elemento.getPeso());
            BFSPesado comprobacion=new BFSPesado(miGrafoAuxiliar,0);
            if(comprobacion.hayCiclosEnGrafo()){
              miGrafoAuxiliar.eliminarArista(elemento.getOrigen(), elemento.getDestino());
            }
        }
    }

    public void ordenar (){
        for (int i =0;i<pesosOrdenados.size()-1;i++){
            for(int j=0;j<pesosOrdenados.size()-i-1;j++){
                if(pesosOrdenados.get(j).getPeso()>pesosOrdenados.get(j+1).getPeso()){
                    ConexionConPeso auxiliar = pesosOrdenados.get(j);
                    pesosOrdenados.set(j,pesosOrdenados.get(j+1));
                    pesosOrdenados.set(j+1,auxiliar);
                }
            }
        }
    }
    public void prim(GrafoPesado unGrafo) throws AristaYaExisteExcepcion {
        miGrafoAuxiliar=new GrafoPesado();
        Stack<ConexionConPeso> pilaDeConexiones= new Stack<>();
        int i=0;
        UtilRecorido controlMarcados= new UtilRecorido(unGrafo.cantidadVertices());
        controlMarcados.marcarVertice(0);
        for(int k=0;k<unGrafo.cantidadVertices();k++){
            List<AdyacenteConPeso> lista = unGrafo.listaDeAdyacencia.get(i);
            miGrafoAuxiliar.insertarVertice();
            for(AdyacenteConPeso elemento : lista){
                ConexionConPeso conPeso= new ConexionConPeso(i,elemento.getIndiceVertice(),elemento.getPeso());
                pesosOrdenados.add(conPeso);
            }
            this.ordenar();
            ConexionConPeso menorCosto=pilaDeConexiones.pop();
            miGrafoAuxiliar.insertarArista(menorCosto.getOrigen(),menorCosto.getDestino(),menorCosto.getPeso());
            controlMarcados.marcarVertice(menorCosto.getDestino());
            i= menorCosto.getDestino();
        }
        while (!controlMarcados.estanTodosMarcados()){
            ConexionConPeso menorCosto=pilaDeConexiones.pop();
            miGrafoAuxiliar.insertarArista(menorCosto.getOrigen(),menorCosto.getDestino(),menorCosto.getPeso());
            controlMarcados.marcarVertice(menorCosto.getDestino());
        }
    }

}

class ConexionConPeso{
    private int origen=0;
    private int destino=0;
    private double peso=0;
    public ConexionConPeso(int posOrigen,int posDestino, double peso){
        origen=posOrigen;
        destino=posDestino;
        this.peso=peso;
    }

    public int getOrigen() {
        return origen;
    }

    public int getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }
}

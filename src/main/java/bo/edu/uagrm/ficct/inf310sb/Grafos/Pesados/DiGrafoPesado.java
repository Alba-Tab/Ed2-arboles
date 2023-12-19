package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.Collections;
import java.util.List;

public class DiGrafoPesado extends GrafoPesado{

    public DiGrafoPesado(){
        super();
    }

    public int gradoDelVertice(int posDelVertice){
        throw new UnsupportedOperationException("El metodo no esta disponible para digrafo pesado");
    }

    public void EliminarArista(int posOrigen, int posDestino) throws AristaNoExisteExcepcion{
        validarVertice(posOrigen);
        validarVertice(posDestino);
        if (!existeAdyacencia(posOrigen,posDestino)){
            throw new AristaNoExisteExcepcion();
        }
        List<AdyacenteConPeso> lista = listaDeAdyacencia.get(posOrigen);
        AdyacenteConPeso nodo = new AdyacenteConPeso(posOrigen);
        int posicion = lista.indexOf(nodo);
        lista.remove(posicion);
    }

    public int cantidadAristas(){
        int size =0;
        for (int i=0;i<listaDeAdyacencia.size();i++){
            List<AdyacenteConPeso> lista = listaDeAdyacencia.get(i);
            size+=lista.size();
        }
        return size;
    }

    @Override
    public void insertarArista(int posOrigen,int posDestino, double peso) throws AristaYaExisteExcepcion{
        validarVertice(posOrigen);
        validarVertice(posDestino);
        if(existeAdyacencia(posOrigen,posDestino)){
            throw new AristaYaExisteExcepcion();
        }
        AdyacenteConPeso nodo = new AdyacenteConPeso(posDestino,peso);
        List<AdyacenteConPeso> lista = listaDeAdyacencia.get(posOrigen);
        lista.add(nodo);
        Collections.sort(lista);
    }

    public DiGrafoPesado(int nroVertice) throws NroVerticesInvalidoExcepcion{
        super(nroVertice);
    }

    public int gradoDeSalidaDelVertice(int vertice){
        List<AdyacenteConPeso> lista = listaDeAdyacencia.get(vertice);
        return lista.size();
    }
    public int gradoDeEntradaDelVertice (int vertice){
        int cantidad = 0;
        for (int i=0;i<listaDeAdyacencia.size();i++){
            List<AdyacenteConPeso> listaAux=listaDeAdyacencia.get(i);
            for (AdyacenteConPeso elemento:listaAux){
                if (elemento.getIndiceVertice()==vertice){
                    cantidad++;
                }
            }
        }
        return cantidad;
    }
}

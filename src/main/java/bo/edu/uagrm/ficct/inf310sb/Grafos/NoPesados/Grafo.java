package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class Grafo {
    protected List<List<Integer>> listasDeAdyacencia;

    public Grafo(){
        listasDeAdyacencia = new ArrayList<>();
    }

    public  Grafo(int nroVerticesInicial)
            throws NroVerticesInvalidoExcepcion{
        if (nroVerticesInicial<=0){
            throw new NroVerticesInvalidoExcepcion();
        }
        listasDeAdyacencia = new ArrayList<>();
        for (int i = 0; i<nroVerticesInicial;i++){
            this.insertarVertice();
        }
    }

    public final void insertarVertice(){
        List<Integer> adyaventesDelNuevoVertice = new ArrayList<>();
        this.listasDeAdyacencia.add(adyaventesDelNuevoVertice);
    }

    public void validarVertice(int posDeVertice){
        if (posDeVertice<0 || posDeVertice>=listasDeAdyacencia.size()){
            throw new IllegalArgumentException("Vertice No Valido");
        }
    }

    public  boolean existeAdyacencia(int posDeVerticeOrigen, int posDeVerticeDestino){
        validarVertice(posDeVerticeDestino);
        validarVertice(posDeVerticeOrigen);
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posDeVerticeDestino);
        return this.listasDeAdyacencia.contains(posDeVerticeDestino);
    }

    public void insertarArista(int posDeVerticeOrigen, int posDeVerticeDestino)throws AristaYaExisteExcepcion {
        if(this.existeAdyacencia(posDeVerticeOrigen,posDeVerticeDestino)){
            throw new AristaYaExisteExcepcion();
        }
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
        if (posDeVerticeDestino!=posDeVerticeDestino){
            List<Integer> adyacentesDelDestino = this.listasDeAdyacencia.get(posDeVerticeDestino);
            adyacentesDelDestino.add(posDeVerticeOrigen);
            Collections.sort(adyacentesDelDestino);

        }
    }

    public Iterable<Integer> adyacentesDelVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVertice =this.listasDeAdyacencia.get(posDeVertice);
        Iterable<Integer> iterableDeAdyacenciaDelVertice = (Iterable)adyacentesDelVertice;
        return iterableDeAdyacenciaDelVertice;
    }

    public void eliminarArista(int posDeVerticeOrigen,int posDeVerticeDestino) throws AristaNoExisteExcepcion{

    }

    public void eliminarVertice(int posDeVertice){

    }

    public int cantidadDeAristas(){
        return 0;
    }
    public int cantidadDeVertices(){
        return 0;
    }
    public int gradoDelVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVerice = this.listasDeAdyacencia.get(posDeVertice);
        return adyacentesDelVerice.size();
    }
}

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
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posDeVerticeOrigen);
        return this.listasDeAdyacencia.contains(adyacentesDelOrigen);
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
        if (!existeAdyacencia(posDeVerticeOrigen,posDeVerticeDestino)){
            throw new AristaNoExisteExcepcion();
        }
        List<Integer> listaOrigen = listasDeAdyacencia.get(posDeVerticeOrigen);
        int posEliminar = listaOrigen.indexOf(posDeVerticeOrigen);
        listaOrigen.remove(posEliminar);
        if (posDeVerticeOrigen!=posDeVerticeDestino){
            List<Integer> listaDestino = listasDeAdyacencia.get(posDeVerticeDestino);
            posEliminar =listaDestino.indexOf(posDeVerticeOrigen);
            listaDestino.remove(posEliminar);
        }
    }

    public void eliminarVertice(int posDeVertice){
        this.validarVertice(posDeVertice);
        listasDeAdyacencia.remove(posDeVertice);
        for (List<Integer> listaAd:this.listasDeAdyacencia){
            int posicionAEliminarDeAdyacencia = listaAd.indexOf(posDeVertice);
            if (posicionAEliminarDeAdyacencia>=0){
                listaAd.remove(posicionAEliminarDeAdyacencia);
            }
            for (int i=0;i<listaAd.size();i++){
                int posicionAdyacente=listaAd.get(i);
                if(posicionAdyacente>posDeVertice){
                    listaAd.set(i,posicionAdyacente-1);
                }
            }
        }
    }

    public int cantidadDeAristas(){
        int lazos = 0;
        int aristas = 0;
        for (int i =0;i<listasDeAdyacencia.size();i++){
            List<Integer> adyacentes = listasDeAdyacencia.get(i);
            for (Integer elemento:adyacentes){
                if (i==elemento){
                    lazos++;
                } else {
                    aristas++;
                }
            }
        }
        return lazos+(aristas/2);
    }
    public int cantidadDeVertices(){
        return listasDeAdyacencia.size();
    }
    public int gradoDelVertice(int posDeVertice){
        validarVertice(posDeVertice);
        List<Integer> adyacentesDelVerice = this.listasDeAdyacencia.get(posDeVertice);
        return adyacentesDelVerice.size();
    }
    public String mostraElGrafo(){
        String s=" |0|1|2|3"+"\n";
        int [][]matriz=new int[this.cantidadDeVertices()][this.cantidadDeVertices()];
        for(int i=0;i<this.cantidadDeVertices();i++){
            for(int j=0;j<this.cantidadDeVertices();j++){
                matriz[i][j]=0;
            }
        }

        for(int i=0;i<this.listasDeAdyacencia.size();i++){
            List<Integer>adyacentes=listasDeAdyacencia.get(i);
            for(Integer elemento : adyacentes){
                matriz[i][elemento]=1;
            }
        }
        for(int i=0;i<this.cantidadDeVertices();i++){
            s=s+i+"|";
            for(int j=0;j<this.cantidadDeVertices();j++){
                s=s+matriz[i][j]+" ";
            }
            s=s+"\n";
        }
        return s;
    }
}

package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados.Grafo;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados.AdyacenteConPeso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GrafoPesado {
    protected List<List<AdyacenteConPeso>> listaDeAdyacencia;

    public GrafoPesado(){
        listaDeAdyacencia=new ArrayList<>();
    }

    public GrafoPesado(int nroVerticeInicial) throws NroVerticesInvalidoExcepcion{
        if (nroVerticeInicial<0){
            throw new NroVerticesInvalidoExcepcion();
        }
        this.listaDeAdyacencia=new ArrayList<>();
        for(int i=0;i<nroVerticeInicial;i++){
            this.insertarVertice();
        }
    }

    public void insertarVertice(){
        List<AdyacenteConPeso> adyacentes=new ArrayList<>();
        listaDeAdyacencia.add(adyacentes);
    }
    public int cantidadVertices(){
        return listaDeAdyacencia.size();
    }
    public int gradoDelVertice (int posDelVertice){
        this.validarVertice(posDelVertice);
        List<AdyacenteConPeso> listaDeAdyacentes = listaDeAdyacencia.get(posDelVertice);
        return listaDeAdyacentes.size();
    }

    public void validarVertice(int posDelVertice){
        if(posDelVertice<0 || posDelVertice >this.cantidadVertices()){
            throw new IllegalArgumentException("La posicion del vertice es invalida, el vertice no existe");
        }
    }

    public void insertarArista(int posOrigen, int posDestino, double peso)
            throws AristaYaExisteExcepcion {
        validarVertice(posOrigen);
        validarVertice(posDestino);
        if (existeAdyacencia(posOrigen,posDestino)){
            throw new AristaYaExisteExcepcion();
        }
        List<AdyacenteConPeso> listaDeOrigen = listaDeAdyacencia.get(posOrigen);
        AdyacenteConPeso nodo = new AdyacenteConPeso(posDestino,peso);

        listaDeOrigen.add(nodo);
        Collections.sort(listaDeOrigen);
        if (posOrigen!=posDestino){
            List<AdyacenteConPeso> listaDestino = listaDeAdyacencia.get(posDestino);
            AdyacenteConPeso otroNodo = new AdyacenteConPeso(posOrigen,peso);
            listaDestino.add(otroNodo);
            Collections.sort(listaDestino);
        }
    }

    public boolean existeAdyacencia(int posDeOrigen, int posDeDestino){
        this.validarVertice(posDeDestino);
        this.validarVertice(posDeOrigen);
        List<AdyacenteConPeso> listaDeAdyacentes=listaDeAdyacencia.get(posDeOrigen);
        AdyacenteConPeso nuevo = new AdyacenteConPeso(posDeDestino);
        return listaDeAdyacentes.contains(nuevo);
    }

    public Iterable<Integer> adyacentesDelVertice(int posDelVertice){
        this.validarVertice(posDelVertice);
        List<AdyacenteConPeso> listaDeAdyacentes = listaDeAdyacencia.get(posDelVertice);
        List<Integer> soloVertices = new ArrayList<>();
        for (AdyacenteConPeso vertice : listaDeAdyacentes){
            soloVertices.add(vertice.getIndiceVertice());
        }

        Iterable<Integer> iteradorDeAdyacentes = soloVertices;
        return iteradorDeAdyacentes;
    }

    public Iterable<AdyacenteConPeso> adyacentesDelVerticeConPeso(int posVertice){
        this.validarVertice(posVertice);
        List<AdyacenteConPeso> lista = listaDeAdyacencia.get(posVertice);
        return lista;
    }

    public int cantidadDeAristas(){
        int lazos = 0;
        int aristas=0;
        for (int i=0;i<listaDeAdyacencia.size();i++){
            List<AdyacenteConPeso> adyacentes = listaDeAdyacencia.get(i);
            for (AdyacenteConPeso elemento: adyacentes){
                if(i==elemento.getIndiceVertice()){
                    lazos++;
                } else {
                    aristas++;
                }
            }
        }
        return lazos+(aristas/2);
    }

    public void eliminarArista(int posOrigen, int posDestino) throws AristaNoExisteExcepcion{
        validarVertice(posDestino);
        validarVertice(posOrigen);
        if(!existeAdyacencia(posOrigen,posDestino)){
            throw new AristaNoExisteExcepcion();
        }
        List<AdyacenteConPeso> listaOrigen = listaDeAdyacencia.get(posOrigen);
        AdyacenteConPeso aBorrar = new AdyacenteConPeso(posDestino,0);
        int posEliminar = listaOrigen.indexOf(aBorrar);
        listaOrigen.remove(posEliminar);
        if (posOrigen!=posDestino){
            List<AdyacenteConPeso> listaDestino= listaDeAdyacencia.get(posDestino);
            AdyacenteConPeso aBorrar1=new AdyacenteConPeso(posOrigen,0);
            int posAEliminar = listaDestino.indexOf(aBorrar1);
            listaDestino.remove(posAEliminar);
        }
    }

    public void eliminarVertice(int posVertice){
        validarVertice(posVertice);
        listaDeAdyacencia.remove(posVertice);
        AdyacenteConPeso eliminar = new AdyacenteConPeso(posVertice,0);
        for (List<AdyacenteConPeso> listaAdyacentes : listaDeAdyacencia){
            int posicionAEliminar=listaAdyacentes.indexOf(eliminar);
            if(posicionAEliminar>=0){
                listaAdyacentes.remove(eliminar);
            }
            for (int i=0; i <listaAdyacentes.size();i++){
                int posicionAdyacente = listaAdyacentes.get(i).getIndiceVertice();
                if(posicionAdyacente>posicionAdyacente){
                    AdyacenteConPeso poner = listaAdyacentes.get(i);
                    poner.setIndiceVertice(posicionAdyacente-1);
                    listaAdyacentes.set(i,poner);
                }
            }
        }
    }

    public double peso (int verticeOrigen, int verticeDestino) throws AristaNoExisteExcepcion{
        validarVertice(verticeDestino);
        validarVertice(verticeOrigen);
        if(!existeAdyacencia(verticeOrigen,verticeDestino)){
            throw new AristaNoExisteExcepcion();
        }
        List<AdyacenteConPeso> lista = this.listaDeAdyacencia.get (verticeOrigen);
        AdyacenteConPeso nodo = new AdyacenteConPeso(verticeDestino);
        int posicion = lista.indexOf(nodo);
        return lista.get(posicion).getPeso();
    }
    public String mostraElGrafo(){
        String s=" |0|1|2|3"+"\n";
        int [][]matriz=new int[this.cantidadVertices()][this.cantidadVertices()];
        for(int i=0;i<this.cantidadVertices();i++){
            for(int j=0;j<this.cantidadVertices();j++){
                matriz[i][j]=0;
            }
        }

        for(int i=0;i<this.listaDeAdyacencia.size();i++){
            List<AdyacenteConPeso>adyacentes=listaDeAdyacencia.get(i);
            for(AdyacenteConPeso elemento : adyacentes){
                matriz[i][elemento.getIndiceVertice()]=1;
            }
        }
        for(int i=0;i<this.cantidadVertices();i++){
            s=s+i+"|";
            for(int j=0;j<this.cantidadVertices();j++){
                s=s+matriz[i][j]+" ";
            }
            s=s+"\n";
        }
        return s;
    }

}

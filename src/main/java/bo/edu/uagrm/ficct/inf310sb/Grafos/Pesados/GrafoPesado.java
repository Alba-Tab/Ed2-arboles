package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;
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
        if (verticeInicial<0){
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
        List
    }
}

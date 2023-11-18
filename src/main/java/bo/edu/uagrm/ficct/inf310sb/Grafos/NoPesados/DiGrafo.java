package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;

import java.util.Collections;
import java.util.List;

public class DiGrafo extends Grafo{
    public DiGrafo(){
        super();
    }

    public DiGrafo(int nroInicialDeVertices)throws NroVerticesInvalidoExcepcion {
        super(nroInicialDeVertices);
    }

    public void insertarArista(int posDeVerticeOrigen,int posDeVerticeDestino) throws AristaYaExisteExcepcion{
        if(this.existeAdyacencia(posDeVerticeOrigen,posDeVerticeDestino)){
            throw new AristaYaExisteExcepcion();
        }
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posDeVerticeOrigen);
        adyacentesDelOrigen.add(posDeVerticeDestino);
        Collections.sort(adyacentesDelOrigen);
    }

    public void eliminarArista(int posDeVerticeOrigen,int posDeVerticeDestino)throws AristaNoExisteExcepcion{

    }

    public int gradoDelVertice(int posDeVertice){
        throw new UnsupportedOperationException("Metodo no soportado en un grafo dirigido");
    }

    public int gradoDeEntradaDelVertice(int posDeVertice){
        return 0;
    }

    public  int gradoSalidaDelVertice (int posDeVertice){
        return 0;
    }
}

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
        if (!this.existeAdyacencia(posDeVerticeOrigen,posDeVerticeDestino)){
            throw new AristaNoExisteExcepcion();
        }
        List<Integer> listaDeOrigen = listasDeAdyacencia.get(posDeVerticeOrigen);
        int posAEliminar = listaDeOrigen.indexOf(posDeVerticeDestino);
        listaDeOrigen.remove(posAEliminar);
    }

    public int gradoDelVertice(int posDeVertice){
        throw new UnsupportedOperationException("Metodo no soportado en un grafo dirigido");
    }

    public int gradoDeEntradaDelVertice(int posDeVertice){
       super.validarVertice(posDeVertice);
       int entrada = 0;
       for (int i=0; i<listasDeAdyacencia.size();i++){
           List<Integer> listaDeAdyacentes = listasDeAdyacencia.get(i);
           for(Integer elemento: listaDeAdyacentes){
               if (posDeVertice==elemento){
                   entrada++;
               }
           }
       }
       return entrada;
    }

    public  int gradoSalidaDelVertice (int posDeVertice){
        return super.gradoDelVertice(posDeVertice);
    }

    public String mostraElDiGrafo(){
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

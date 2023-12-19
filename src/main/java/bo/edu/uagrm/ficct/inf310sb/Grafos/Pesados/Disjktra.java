package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;

import javax.swing.*;
import java.lang.foreign.SymbolLookup;

public class Disjktra {
    private Boolean[] marcados;
    private double[] costos;
    private int[] predecesores;
    private DiGrafoPesado miDigrafo;
    static final double INFINITO= Double.POSITIVE_INFINITY;
    public Disjktra(DiGrafoPesado diGrafo) throws AristaNoExisteExcepcion{
        this.miDigrafo=diGrafo;
        int vertices = miDigrafo.cantidadVertices();
        costos = new double[vertices];
        marcados = new Boolean[vertices];
        predecesores = new int[vertices];
        for (int i = 0; i<vertices;i++){
            costos[i] = INFINITO;
            marcados[i] = Boolean.FALSE;
            predecesores[i] = -1;
        }

    }

    public double caminoMasCorto (int verticeInicial, int verticeFinal) throws AristaNoExisteExcepcion{
        costos[verticeInicial]=0;
        marcados[verticeInicial]=true;
        int enTurno=verticeInicial;
        while((marcados[verticeFinal]==false) && (costos[enTurno]!=INFINITO)){
            marcados[enTurno]=true;
            Iterable<Integer>listaAdyacentes=miDigrafo.adyacentesDelVertice(enTurno);
            for(Integer elemento:listaAdyacentes){
                if(marcados[elemento]==false){
                    double distancia=miDigrafo.peso(enTurno,elemento);//
                    if((distancia+costos[enTurno])<costos[elemento]){
                        costos[elemento]=distancia+costos[enTurno];
                        predecesores[elemento]=enTurno;
                    }
                }
            }
            enTurno=verticeDeMenorCostoNoMarcado();
        }
        return costos[verticeFinal];
    }
    private int verticeDeMenorCostoNoMarcado(){
        int vertice=0;
        double max=INFINITO;
        for(int i=0;i<costos.length;i++){
            if(!marcados[i] && costos[i]<=max){
                vertice=i;
                max=costos[i];
            }
        }
        return vertice;
    }

}

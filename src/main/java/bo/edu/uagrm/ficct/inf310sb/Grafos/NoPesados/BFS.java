package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.utileria.UtilRecorido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS {
    private final Grafo unGrafo;
    private final UtilRecorido controlMarcados;
    private List<Integer> recorrido;

    public BFS(Grafo grafoBase,int posDeVerticeDePartida){
        grafoBase.validarVertice(posDeVerticeDePartida);
        recorrido= new ArrayList<>();
        this.unGrafo=grafoBase;
        controlMarcados =new UtilRecorido(unGrafo.cantidadDeVertices());
        ejecutarBFS(posDeVerticeDePartida);
    }

    public void ejecutarBFS(int posDeVertice){
        Queue<Integer> colaDeVertices = new LinkedList<>();
        colaDeVertices.offer(posDeVertice);
        controlMarcados.marcarVertice(posDeVertice);
        do{
            int posDeVerticeEnTurno = colaDeVertices.poll();
            this.recorrido.add(posDeVerticeEnTurno);
            Iterable<Integer> adyacentesDelVerticeEnTurno = this.unGrafo.adyacentesDelVertice(posDeVerticeEnTurno);
            for (Integer adyacenteEnTurno: adyacentesDelVerticeEnTurno){
                if (!this.controlMarcados.estaVerticeMarcado(adyacenteEnTurno)){
                    colaDeVertices.offer(adyacenteEnTurno);
                    this.controlMarcados.marcarVertice(adyacenteEnTurno);
                }
            }
        }while (!colaDeVertices.isEmpty());

    }

    public List<Integer> obtenerVerticesVisitados(){
        return recorrido;
    }

    public boolean hayCaminoAVertice(int posDeVerticeDeDestino){
        unGrafo.validarVertice(posDeVerticeDeDestino);
        return this.controlMarcados.estaVerticeMarcado(posDeVerticeDeDestino);
    }

    public boolean hayCaminoATodos(){
        return controlMarcados.estanTodosMarcados();
    }
}

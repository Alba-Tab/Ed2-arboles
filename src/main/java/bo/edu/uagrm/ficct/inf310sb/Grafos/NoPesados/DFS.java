package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.utileria.UtilRecorido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DFS {
    private final Grafo unGrafo;
    private final UtilRecorido controlMarcados;
    private List<Integer> recorrido;

    public DFS(Grafo grafoBase, int posDeVerticeDePartida){
        grafoBase.validarVertice(posDeVerticeDePartida);
        recorrido= new ArrayList<>();
        this.unGrafo=grafoBase;
        controlMarcados =new UtilRecorido(unGrafo.cantidadDeVertices());
        ejecutarDFS(posDeVerticeDePartida);
    }

    public void ejecutarDFS(int posDeVertice){
        unGrafo.validarVertice(posDeVertice);
        controlMarcados.marcarVertice(posDeVertice);
        this.recorrido.add(posDeVertice);
        Iterable<Integer> adyacentesDelVerticeEnTurno = this.unGrafo.adyacentesDelVertice(posDeVertice);
        for (Integer adyacenteEnTurno: adyacentesDelVerticeEnTurno){
            if (!this.controlMarcados.estaVerticeMarcado(adyacenteEnTurno)){
                this.controlMarcados.marcarVertice(adyacenteEnTurno);
            }
        }
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

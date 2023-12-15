package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.AristaYaExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
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

    public ArrayList<Integer> listaNoMarcados(){
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 0; i<unGrafo.cantidadDeVertices();i++){
            if (!controlMarcados.estaVerticeMarcado(i)){
                lista.add(i);
            }
        }
        return lista;
    }
    //--------------------------------------------------------------
    public int cantidadDeIslasEnUnGrafo(Grafo unGrafo){
        BFS bfs=new BFS(unGrafo,0);

        if(bfs.hayCaminoATodos()){
            return 1;
        }
        int islas=0;
        //int cantidad=0;
        ArrayList<Integer>noMarcados=bfs.listaNoMarcados();
        while(!noMarcados.isEmpty()){
            int posicionInicial=noMarcados.get(0);
            bfs.ejecutarBFS(posicionInicial);
                    /*  ArrayList<Integer>noMarcados=bfs.listaDeNoMarcados();
                    int inicial=noMarcados.get(0);
                    bfs.continuarBFS(inicial);
                    cantidad++;*/
            islas++;
            noMarcados=bfs.listaNoMarcados();

        }
        islas++;
        return islas;
    }

    public int cantidadDeIslasEnUnDigrafo(){
        if(this.hayCaminoATodos()){
            return 1;
        }
        int cantidadDeIslas=1;
        while(!this.hayCaminoATodos()){
            //ArrayList<Integer>noMarcados=this.listaDeNoMarcados();
            boolean hayAdya=false;
            int inicial=this.listaNoMarcados().get(0);
            for(int i=0;i<(this.listaNoMarcados().size())&& (hayAdya==false);i++){
                inicial=listaNoMarcados().get(i);
                if(this.hayAdyacentesMarcados(inicial)){
                    inicial=listaNoMarcados().get(i);
                    hayAdya=true;
                }
            }
            if(hayAdya){
                this.ejecutarBFS(inicial);
            }else{
                cantidadDeIslas++;
                this.ejecutarBFS(inicial);
            }
        }
        return cantidadDeIslas;
    }
    private boolean hayAdyacentesMarcados(int inicial) {
        Iterable<Integer>lista=this.unGrafo.adyacentesDelVertice(inicial);
        for(Integer elemento: lista){
            if(controlMarcados.estaVerticeMarcado(elemento)){
                return true;
            }
        }
        return false;
    }

    public boolean hayCiclosEnGrafo() throws NroVerticesInvalidoExcepcion, AristaYaExisteExcepcion {
        int verticeDelGrafo=this.unGrafo.cantidadDeVertices();
        ArrayList<Boolean>marcados=new ArrayList<>();
        ArrayList<Boolean>visitados=new ArrayList<>();
        for(int i=0;i<verticeDelGrafo;i++){
            marcados.add(Boolean.FALSE);
            visitados.add(Boolean.FALSE);
        }
        Grafo auxiliar=new Grafo(verticeDelGrafo);
        int enTurno=0;
        boolean hayCiclos=false;
        marcados.set(enTurno, Boolean.TRUE);
        while(!estanTodosVisitados(visitados)&& hayCiclos==false){
            int verticeActual=-1;
            for(int i=0;i<visitados.size();i++){
                if(visitados.get(i)==false && marcados.get(i)==true){
                    verticeActual=i;
                    break;
                }
            }
            if(verticeActual==-1){
                for(int i=0;i<visitados.size();i++){
                    if(visitados.get(i)==false && marcados.get(i)==false){
                        verticeActual=i;
                        break;
                    }
                }
            }
            marcados.set(verticeActual,Boolean.TRUE);
            visitados.set(verticeActual,Boolean.TRUE);
            Iterable<Integer>adyacentes=unGrafo.adyacentesDelVertice(verticeActual);
            for(Integer elemento:adyacentes){
                if(!marcados.get(elemento)){
                    auxiliar.insertarArista(verticeActual, elemento);
                    //auxiliar.insertarArista(elemento,verticeActual);
                    marcados.set(elemento,Boolean.TRUE);
                }else if((marcados.get(elemento)==true)&&
                        (!auxiliar.existeAdyacencia(elemento,verticeActual)||!auxiliar.existeAdyacencia(verticeActual,elemento))){
                    hayCiclos=true;
                }else{

                }
            }

        }
        return hayCiclos;
    }

    private boolean estanTodosVisitados(List<Boolean>lista){
        for(Boolean elemento:lista){
            if(!elemento){
                return false;
            }
        }
        return true;
    }
}

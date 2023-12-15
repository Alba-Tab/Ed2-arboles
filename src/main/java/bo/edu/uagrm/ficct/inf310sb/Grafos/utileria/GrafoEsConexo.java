package bo.edu.uagrm.ficct.inf310sb.Grafos.utileria;
import bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados.BFS;
import bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados.DiGrafo;
import bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados.Grafo;

public class GrafoEsConexo {
    private BFS bfs;
    private Grafo miGrafo;
    private int nroVertices;
    public GrafoEsConexo(Grafo unGrafo){
        bfs =new BFS(unGrafo,0);
        this.miGrafo=unGrafo;
        this.nroVertices=miGrafo.cantidadDeVertices();
    }
    public boolean esConexo(){
        return bfs.hayCaminoATodos();
    }

    public boolean esDebilmenteConexo() {
        boolean debilmente = false;
        if (bfs.cantidadDeIslasEnUnDigrafo() == 1) {
            for (int i = 1; (i < nroVertices) && (debilmente == false); i++) {
                bfs = new BFS(miGrafo, i);
                for (int j = 0; (j < nroVertices) && (debilmente == false); j++) {
                    if (!bfs.hayCaminoAVertice(j)) {
                        debilmente = true;
                    }
                }
            }
        }
        return debilmente;
    }
    public boolean esFuertementeConexo(){
        boolean fuerte=true;
        if(bfs.cantidadDeIslasEnUnDigrafo()==1){
            for(int i=0;(i<nroVertices) && (fuerte==true);i++){
                bfs=new BFS(miGrafo,i);
                if(!bfs.hayCaminoATodos()){
                    fuerte=false;
                }
            }
        }else{
            fuerte=false;
        }
        return fuerte;
    }
}

package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

public class Warshall {
    private Grafo miGrafo;
    public Warshall(Grafo unGrafo){
        this.miGrafo=unGrafo;
    }
    public String matrizDeCaminos(){
        int n=miGrafo.cantidadDeVertices();
        int[][] p = new int[n][n];
        //ponemos en ceros la matriz
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                p[i][j]=0;
            }
        }
        //actualizamos la matriz de adyacencia
        for(int i=0;i<n;i++){
            Iterable<Integer>lista=miGrafo.adyacentesDelVertice(i);
            for(Integer elemento:lista){
                p[i][elemento]=1;
            }
        }
        String s="";

        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    p[i][j]=Math.min(p[i][j]+p[i][k]*p[k][j], 1);
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                s=s+p[i][j]+" ";
            }
            s=s+"\n";
        }
        return s;
    }
    public boolean hayCiclos(){
        int n=miGrafo.cantidadDeVertices();
        int[][]p=new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                p[i][j]=0;
            }
        }
        for(int i=0;i<n;i++){
            Iterable<Integer>lista=miGrafo.adyacentesDelVertice(i);
            for(Integer elemento:lista){
                p[i][elemento]=1;
            }
        }
        String s="";

        for(int k=0;k<n;k++){
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    p[i][j]=Math.min(p[i][j]+p[i][k]*p[k][j], 1);
                }
            }
        }
        boolean sw=false;
        for(int x=0;x<n;x++){
            if(p[x][x]==1){
                return true;
            }
        }
        return sw;
    }
}

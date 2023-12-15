package bo.edu.uagrm.ficct.inf310sb.Grafos.NoPesados;

public class MatrizCaminos {
    private Grafo miGrafo;
    public MatrizCaminos(Grafo unGrafo){
        this.miGrafo=unGrafo;
    }

    public String matrizCaminosConMultiplicacion(){
        int n =miGrafo.cantidadDeVertices();
        int[][] p = new int [n][n];
        for(int i=0 ;i<n; i++){
            for (int j=0;j<n;j++){
                p[i][j]=0;
            }
        }
        //ponemos en 1 los adyacentes al vertice i
        for(int i=0;i<n;i++){
            Iterable<Integer>lista=miGrafo.adyacentesDelVertice(i);
            for(Integer elemento:lista){
                p[i][elemento] = 1;
            }
        }
        //copiamos la matriz de caminos
        int[][] b = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                b[i][j]=p[i][j];
            }
        }
        //nueva matriz en ceros
        int[][] c = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                c[i][j] = 0;
            }
        }

        String s="";
        int suma=0;
        for(int cantidad=0;cantidad<n;cantidad++){
            for(int x=0;x<n;x++){
                for(int j=0;j<n;j++){
                    for(int i=0;i<n;i++){
                        suma=suma+b[i][j]*p[x][i];

                    }
                    c[x][j]=suma>0?1:0;
                    suma=0;


                }
            }
            for(int t=0;t<n;t++){
                for(int m=0;m<n;m++){
                    b[t][m]=c[t][m];
                }
            }
        }

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                s=s+c[i][j]+" ";
            }
            s=s+"\n";
        }
        return s;

    }
}

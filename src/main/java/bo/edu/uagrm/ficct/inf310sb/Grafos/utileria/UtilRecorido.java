package bo.edu.uagrm.ficct.inf310sb.Grafos.utileria;

import java.util.ArrayList;
import java.util.List;

public class UtilRecorido {
    private List<Boolean> listaDeMarcados;

    public UtilRecorido(int nroDeVertices){
        this.listaDeMarcados =new ArrayList<>();
        for (int i=0;i<nroDeVertices;i++){
            listaDeMarcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodos(){
        for (int i=0;i<listaDeMarcados.size();i++){
            this.desmarcarVertice(i);
        }
    }

    public void desmarcarVertice(int posDeVertice){
        this.listaDeMarcados.set(posDeVertice,Boolean.FALSE);

    }

    public void marcarVertice(int posDeVertice){
        this.listaDeMarcados.set(posDeVertice,Boolean.TRUE);

    }

    public boolean estaVerticeMarcado(int posDeVertice){
        return this.listaDeMarcados.get(posDeVertice);
    }

    public boolean estanTodosMarcados(){
        for(int i=0;i<listaDeMarcados.size();i++){
            if(!this.estaVerticeMarcado(i)){
                return false;
            }
        }
        return true;
    }
}

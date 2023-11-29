package bo.edu.uagrm.ficct.inf310sb.Grafos.Pesados;

import java.util.Objects;

public class AdyacenteConPeso implements  Comparable<AdyacenteConPeso>{
    private int indiceVertice;
    private double peso;
    @Override
    public int compareTo(AdyacenteConPeso o) {
        if (o == null){
            return -1;
        }
        int diferencia = this.indiceVertice - o.indiceVertice;
        return diferencia;
    }

    public AdyacenteConPeso(int indiceVertice) {
        this.indiceVertice = indiceVertice;
    }

    public AdyacenteConPeso(int indiceVertice, double peso) {
        this.indiceVertice = indiceVertice;
        this.peso = peso;
    }

    public int getIndiceVertice() {
        return indiceVertice;
    }

    public void setIndiceVertice(int indiceVertice) {
        this.indiceVertice = indiceVertice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdyacenteConPeso that = (AdyacenteConPeso) o;
        return indiceVertice == that.indiceVertice ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(indiceVertice, peso);
    }
}

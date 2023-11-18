package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.*;

public class Dibujo {
    public Dibujo() {
    }

    public void HacerRedondo(Graphics pizarra, int x1, int y1, int x2, int y2) {
        pizarra.setColor(Color.CYAN);
        pizarra.fillOval(x1, y1, x2, y2);
    }

    public void hacerLinea(Graphics pizarra, int x1, int y1, int x2, int y2) {
        pizarra.setColor(Color.BLACK);
        pizarra.drawLine(x1, y1, x2, y2);
    }

    public void hacerLlave(Graphics pizarra, NodoBinario nodo, int x1, int y1, int x2, int y2) {
        pizarra.setColor(Color.BLACK);
        Font clave = new Font(nodo.getClave().toString(), 2, 25);
        pizarra.setFont(clave);
        pizarra.drawString(nodo.getClave().toString(), x1 + x2 / 4, y1 + y2 / 2);
    }

    public void DibujarArbolBinario(Graphics pizarra, IArbolBusqueda arbol, int x1, int y1, int x2, int y2) {
        if (!arbol.esArbolVacio()) {
            int altura = arbol.altura();
            double incrementoExponencial = 1.6;
            int incrementoLineal = 30;
            if (!arbol.getraiz.esVacioElHijoDerecho()) {
                this.hacerLinea(pizarra, x1 + x2 / 2, y1 + y2 / 2, x1 + x2 / 2 + incrementoLineal * (int)Math.pow(incrementoExponencial, (double)altura), y1 + y2 / 2 + 70);
            }

            if (!arbol.getRaiz().esVacioElHijoIzquierdo()) {
                this.hacerLinea(pizarra, x1 + x2 / 2, y1 + y2 / 2, x1 + x2 / 2 - incrementoLineal * (int)Math.pow(incrementoExponencial, (double)altura), y1 + y2 / 2 + 70);
            }

            this.HacerRedondo(pizarra, x1, y1, x2, y2);
            this.hacerLlave(pizarra, arbol.getRaiz(), x1, y1, x2, y2);
            this.DibujarArbolBinario(pizarra, arbol.subArbolIzquierodo(), x1 - incrementoLineal * (int)Math.pow(incrementoExponencial, (double)altura), y1 + 70, x2, y2);
            this.DibujarArbolBinario(pizarra, arbol.subArbolDerecho(), x1 + incrementoLineal * (int)Math.pow(incrementoExponencial, (double)altura), y1 + 70, x2, y2);
        }


}

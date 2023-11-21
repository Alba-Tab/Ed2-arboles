package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Dibujo {
    private static final int ANCHO_NODO = 40;
    private static final int ALTO_NODO = 40;
    private static final int ESPACIO_VERTICAL = 50;

    private static final int ESPACIO_HORIZONTAL = 10; // Ajustar
    public Dibujo() {
    }

    private void HacerRedondo(Graphics pizarra, int x, int y, int ancho, int alto) {
        pizarra.setColor(Color.CYAN);
        pizarra.fillOval(x, y, ancho, alto);
    }

    private void hacerLinea(Graphics pizarra, int x1, int y1, int x2, int y2) {
        pizarra.setColor(Color.BLACK);
        pizarra.drawLine(x1, y1, x2, y2);
    }

    public void hacerLlave(Graphics pizarra, NodoBinario nodo, int x, int y, int ancho, int alto) {
        pizarra.setColor(Color.BLACK);
        Font clave = new Font(nodo.getClave().toString(), 2, 15);
        pizarra.setFont(clave);
        pizarra.drawString(nodo.getClave().toString(), x + ancho / 4, y + 8 + alto / 2);
    }


    public void dibujarArbolBinario(Graphics pizarra, IArbolBusqueda arbol, int x1) {
        if (!arbol.esArbolVacio()) {
            dibujarArbolBinario(pizarra, arbol, arbol.getraiz(), x1, 30, 1);
            // DibujarArbolBinario(pizarra, arbol, arbol.getraiz(), x1, y1, x2, y2,0);
        }
    }

    private void dibujarArbolBinario(Graphics pizarra, IArbolBusqueda arbol, NodoBinario nodoActual, int x, int y, int nivel) {
        if (!NodoBinario.esNodoVacio(nodoActual)) {
            // Calcula nuevas posiciones para los hijos
            int espacioX = (int) (ESPACIO_HORIZONTAL * Math.pow(2, arbol.altura() - nivel));
            int hijoIzquierdoX = x - espacioX;
            int hijoDerechoX = x + espacioX;
            int hijoY = y + ESPACIO_VERTICAL;

            // Dibuja líneas hacia los hijos
            if (!nodoActual.esVacioHijoIzquierdo()) {
                hacerLinea(pizarra, x + ANCHO_NODO / 2, y + ALTO_NODO / 2, hijoIzquierdoX + ANCHO_NODO / 2, hijoY);
            }
            if (!nodoActual.esVacioHijoDerecho()) {
                hacerLinea(pizarra, x + ANCHO_NODO / 2, y + ALTO_NODO / 2, hijoDerechoX + ANCHO_NODO / 2, hijoY);
            }

            HacerRedondo(pizarra, x, y, ANCHO_NODO, ALTO_NODO);
            hacerLlave(pizarra, nodoActual, x, y, ANCHO_NODO, ALTO_NODO);

            // Llamadas recursivas para los hijos
            dibujarArbolBinario(pizarra, arbol, nodoActual.getHijoIzquierdo(), hijoIzquierdoX, hijoY, nivel + 1);
            dibujarArbolBinario(pizarra, arbol, nodoActual.getHijoDerecho(), hijoDerechoX, hijoY, nivel + 1);
        }
    }

    public void dibujarArbolMVias(Graphics pizarra, IArbolBusqueda arbol, int x) {
        if (!arbol.esArbolVacio()) {
            dibujarArbolMVias(pizarra, arbol, arbol.getraizM(), x, 30, 1);
        }
    }

    private void dibujarArbolMVias(Graphics pizarra, IArbolBusqueda arbol, NodoMVias nodoActual, int x, int y, int nivel) {
        if (!NodoMVias.esNodoVacio(nodoActual)) {
            // Calcula nuevas posiciones para los hijos
            int cantidadClaves = nodoActual.nroClavesNoVacias();
            int espacioX = (int) (ESPACIO_HORIZONTAL * Math.pow(2, arbol.altura() - nivel));
            int primerHijoX = x - espacioX * (cantidadClaves / 2);
            int hijoY = y + ESPACIO_VERTICAL;

            // Dibuja líneas hacia los hijos
            for (int i = 0; i < cantidadClaves; i++) {
                int hijoX = primerHijoX + espacioX * i;
                hacerLinea(pizarra, x + ANCHO_NODO / 2, y + ALTO_NODO / 2, hijoX + ANCHO_NODO / 2, hijoY);
                dibujarArbolMVias(pizarra, arbol, nodoActual.getHijo(i), hijoX, hijoY, nivel + 1);
            }

            // Dibuja el nodo actual
            hacerRectangulo(pizarra, nodoActual,x, y, ANCHO_NODO, ALTO_NODO);
            hacerLlave(pizarra, nodoActual, x, y, ANCHO_NODO, ALTO_NODO);

            // Llamadas recursivas para los hijos
            for (int i = 0; i <= cantidadClaves; i++) {
                dibujarArbolMVias(pizarra, arbol, nodoActual.getHijo(i), primerHijoX + espacioX * i, hijoY, nivel + 1);
            }
        }
    }
        public void hacerLlave(Graphics pizarra, NodoMVias nodo, int x, int y, int ancho, int alto) {
            pizarra.setColor(Color.BLACK);
            Font claveFont = new Font(Font.MONOSPACED, Font.BOLD, 12);
            pizarra.setFont(claveFont);

            int cantidadClaves = nodo.nroClavesNoVacias();
            for (int i = 0; i < cantidadClaves; i++) {
                String claveStr = nodo.getClave(i).toString();
                int anchoTexto = pizarra.getFontMetrics().stringWidth(claveStr);
                int xTexto = x + i * (ancho / cantidadClaves) + (ancho / cantidadClaves - anchoTexto) / 2;
                int yTexto = y + 20; // Ajusta según tu diseño

                pizarra.drawString(claveStr, xTexto, yTexto);
            }
    }
    private void hacerRectangulo(Graphics pizarra, NodoMVias nodo, int x, int y, int ancho, int alto) {
        pizarra.setColor(Color.CYAN);
        int cantidadClaves = nodo.nroClavesNoVacias();
        int anchoBloque = ancho / cantidadClaves;

        for (int i = 0; i < cantidadClaves; i++) {
            pizarra.fillRect(x + i * anchoBloque, y, anchoBloque, alto);
        }
    }
}
package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Dibujo {
    public static final int ESPACIO_VERTICAL = 50;

    public static final int ESPACIO_HORIZONTAL = 10; // Ajustar
    public Dibujo() {
    }

    public static void HacerRedondo(Graphics pizarra, int x, int y, int ancho, int alto) {
        pizarra.setColor(Color.CYAN);
        pizarra.fillOval(x, y, ancho, alto);
    }

    public static void hacerLinea(Graphics pizarra, int x1, int y1, int x2, int y2) {
        pizarra.setColor(Color.BLACK);
        pizarra.drawLine(x1, y1, x2, y2);
    }

    public static void hacerLlave(Graphics pizarra, NodoBinario nodo, int x, int y, int ancho, int alto) {
        pizarra.setColor(Color.BLACK);
        Font clave = new Font(nodo.getClave().toString(), 2, 15);
        pizarra.setFont(clave);
        pizarra.drawString(nodo.getClave().toString(), x + ancho / 4, y + 8 + alto / 2);
    }
}
package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Formulario extends JFrame {
    private IArbolBusqueda<Integer, String> arbol;

    private boolean arbolExiste = false;
    private Dibujo dibujar = new Dibujo();
    private JButton arbolPruebaButton;
    private JPanel Formulario;
    private JButton insertarButton;
    private JButton ABBButton;
    private JButton AVLButton;
    private JButton MViasButton;
    private JButton arbolBButton;
    private JButton eliminarButton;
    private JTextField textClave;
    private JTextField textValor;
    private JPanel pizarra;
    private JLabel textRecorridos;
    private JButton recorridoPreOrdenButton;
    private JButton recorridoPostOrdenButton;
    private JButton recorridoPorNivelesButton;
    private JButton recorridoInOrden;

    public Formulario() {
        // Configurar la ventana
        setContentPane(Formulario);
        setTitle("Arboles");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Hacer visible la ventana
        setVisible(true);
        ABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoPorNiveles();
                        List<String> listaDeValores = arbol.recorridoValoresEnPorNiveles();
                        arbol = new ArbolBinarioBusqueda<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
                        dibujar.dibujarArbolBinario(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);
                        return;
                    }
                }
                arbol = new ArbolBinarioBusqueda<>();
                arbolExiste=true;
            }
        });
        AVLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoPorNiveles();
                        List<String> listaDeValores = arbol.recorridoValoresEnPorNiveles();
                        arbol = new AVL<>();
                        insertar(arbol, listaDeClaves, listaDeValores);

                        dibujar.dibujarArbolBinario(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);

                        return;
                    }
                }
                arbol = new AVL<>();
                arbolExiste=true;
            }
        });
        MViasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoPorNiveles();
                        List<String> listaDeValores = arbol.recorridoValoresEnPorNiveles();
                        arbol = new ArbolMViasBusqueda<>();
                        insertar(arbol, listaDeClaves, listaDeValores);

                        dibujar.dibujarArbolMVias(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);

                        return;
                    }
                }
                arbol = new ArbolMViasBusqueda<>();
                arbolExiste=true;

            }
        });
        arbolBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoPorNiveles();
                        List<String> listaDeValores = arbol.recorridoValoresEnPorNiveles();
                        arbol = new ArbolB<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
                        pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                        dibujar.dibujarArbolMVias(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);
                        return;
                    }
                }
                arbol = new ArbolB<>();
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                arbolExiste=true;
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol.insertar(Integer.parseInt(textClave.getText()),textValor.getText() );
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                dibujar.dibujarArbolBinario(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);
                textClave.setText("");
                textValor.setText("");
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    arbol.eliminar(Integer.parseInt(textClave.getText()));
                    textClave.setText("");
                } catch (ClaveNoExisteExcepcion ex) {
                    throw new RuntimeException(ex);
                }
                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                dibujar.dibujarArbolBinario(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);
            }
        });
        arbolPruebaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    arbol.insertar(77,"MM");
                    arbol.insertar(50,"XY");
                    arbol.insertar(90,"A8");
                    arbol.insertar(30,"AT");
                    arbol.insertar(62,"AM");
                    arbol.insertar(81,"BK");
                    arbol.insertar(42,"PP");
                    arbol.insertar(85,"XA");
                    arbol.insertar(76,"PP");
                    arbol.insertar(30,"TT");

                pizarra.getGraphics().clearRect(0, 0, pizarra.getWidth(), pizarra.getHeight());
                dibujar.dibujarArbolBinario(pizarra.getGraphics(), arbol, pizarra.getWidth()/2);
            }
        });
        recorridoInOrden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    textRecorridos.setText("Recorrido en InOrden: "+arbol.recorridoEnInOrden());
                }else{
                    textRecorridos.setText("El arbol no existe");
                }

            }
        });
        recorridoPreOrdenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    textRecorridos.setText("Recorrido en PreOrden: "+arbol.recorridoEnPreOrden());
                }else{
                    textRecorridos.setText("El arbol no existe");
                }

            }
        });
        recorridoPostOrdenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    textRecorridos.setText("Recorrido en PostOrden: "+arbol.recorridoEnPostOrden());
                }else{
                    textRecorridos.setText("El arbol no existe");
                }
            }
        });
        recorridoPorNivelesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    textRecorridos.setText("Recorrido por niveles: "+arbol.recorridoPorNiveles());
                }else{
                    textRecorridos.setText("El arbol no existe");
                }
            }
        });
    }

    public static void main(String[] args) {
        // Crear una instancia de la clase que extiende JFrame
        Formulario miVentana = new Formulario();
        miVentana.setResizable(false);
    }

    private static int mostrarVentanaEmergente() {
        // Mostrar el diálogo con dos opciones: "Sí" y "No"
        return JOptionPane.showOptionDialog(
                null,
                "¿Desea cambiar de arbol con las mismas claves y valores?",
                "Confirmacion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     // Use el icono predeterminado
                new Object[]{"Sí", "No"},  // Las opciones disponibles
                "Sí"      // Opción predeterminada
        );
    }

    public void insertar(IArbolBusqueda<Integer,String> arbol,List<Integer> listaDeClaves,
                            List<String> listaDeValores ){
        for (int i=0;i<listaDeClaves.size();i++){
            arbol.insertar(listaDeClaves.get(i),listaDeValores.get(i));
        }
    }
}

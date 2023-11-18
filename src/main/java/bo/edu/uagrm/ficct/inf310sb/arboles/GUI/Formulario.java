package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;

import bo.edu.uagrm.ficct.inf310sb.arboles.excepciones.ClaveNoExisteExcepcion;
import bo.edu.uagrm.ficct.inf310sb.arboles.ui.*;

import javax.swing.*;
        ;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JLabel labelprueba;

    public Formulario() {
        // Configurar la ventana
        setContentPane(Formulario);
        setTitle("Arboles");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // Hacer visible la ventana
        setVisible(true);
        ABBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoEnInOrden();
                        List<String> listaDeValores = arbol.recorridoValoresEnInOrden();
                        arbol = new ArbolBinarioBusqueda<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
                        labelprueba.setText("funciona");
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
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoEnInOrden();
                        List<String> listaDeValores = arbol.recorridoValoresEnInOrden();
                        arbol = new AVL<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
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
                if (arbolExiste){
                    int respuesta = mostrarVentanaEmergente();
                    if (respuesta == JOptionPane.YES_OPTION) {
                        List<Integer> listaDeClaves = arbol.recorridoEnInOrden();
                        List<String> listaDeValores = arbol.recorridoValoresEnInOrden();
                        arbol = new ArbolMViasBusqueda<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
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
                        List<Integer> listaDeClaves = arbol.recorridoEnInOrden();
                        List<String> listaDeValores = arbol.recorridoValoresEnInOrden();
                        arbol = new ArbolB<>();
                        insertar(arbol, listaDeClaves, listaDeValores);
                        return;
                    }
                }
                arbol = new ArbolB<>();
                arbolExiste=true;
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol.insertar(Integer.parseInt(textClave.getText()),textValor.getText() );
            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    arbol.eliminar(Integer.parseInt(textClave.getText()));
                } catch (ClaveNoExisteExcepcion ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        // Crear una instancia de la clase que extiende JFrame
        Formulario miVentana = new Formulario();


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

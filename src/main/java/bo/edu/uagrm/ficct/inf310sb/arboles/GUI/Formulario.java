package bo.edu.uagrm.ficct.inf310sb.arboles.GUI;

import javax.swing.*;

public class Formulario {
    private JPanel Panel1;

    public Formulario(){
        JFrame frame = new JFrame("Formulario");

        frame.setContentPane(Panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        new Formulario();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

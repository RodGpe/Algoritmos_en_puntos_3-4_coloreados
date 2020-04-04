
package javagraphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

public class ArregloLineas extends Frame {

    public static void main(String[] args) {

        ArregloLineas ventana = new ArregloLineas();
    }

    ArregloLineas() {
        super("Define polygon vertices by clicking");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(1366, 768); //original 500, 300
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        add("Center", new CvArregloLineas());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setVisible(true);
    }
}

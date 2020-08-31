/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cuadrilateroconvexo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

/**
 *
 * @author Rodrigo
 */
public class CuadrilateroHeterocromatico extends Frame {

    public static void main(String[] args) {

        CuadrilateroHeterocromatico ventana = new CuadrilateroHeterocromatico();
    }

    CuadrilateroHeterocromatico() {
        super("Cuadrilatero Heterocromatico convexo");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(1366, 768); //original 500, 300
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        add("Center", new CvCuadrilateroHeterocromatico());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setVisible(true);
    }
}

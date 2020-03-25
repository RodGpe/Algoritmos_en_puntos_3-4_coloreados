/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagraphics;

/**
 *
 * @author Rodrigo
 */
// DefPoly.java: Drawing a polygon.
// Uses: CvDefPoly (discussed below).
// Copied from Section 1.5 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DefPoly extends Frame {

    public static void main(String[] args) {

        DefPoly ventana = new DefPoly();
        ventana.exportar(ventana);
    }

    DefPoly() {
        super("Define polygon vertices by clicking");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(1366, 768); //original 500, 300
        setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
        add("Center", new CvDefPoly());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setVisible(true);
    }

    public void exportar(DefPoly ventana) {
        BufferedImage awtImage = new BufferedImage(ventana.getWidth(), ventana.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = awtImage.createGraphics();
        ventana.printAll(g);

        try {
            //String caminhoImagem = System.getProperty("user.home") + "\\temps\\assinatura.jpg";
            File outputfile = new File("saved.png");
            ImageIO.write(awtImage, "png", outputfile );
        } catch(Exception e){
        }finally{
            System.out.println("guardé la imagen");
        }
    }
}

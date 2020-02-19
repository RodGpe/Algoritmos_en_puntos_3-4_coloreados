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
// CvDefPoly.java: To be used in other program files.
// Copied from Section 1.5 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.
// A class that enables the user to define
// a polygon by clicking the mouse.
// Uses: Point2D (discussed below).
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CvDefPoly extends Canvas {

    Vector<Point2D> v = new Vector<Point2D>();
    Vector<Point2D> vBlue = new Vector<Point2D>();
    float x0, y0, rWidth = 10.0F, rHeight = 10.0F, pixelSize; //originalmete rHeight = 7.5F
    boolean ready = true;
    int centerX, centerY;
    int numerodePuntos = 30;
    float separacionMinima = .1f;

    CvDefPoly() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                float xA = fx(evt.getX()), yA = fy(evt.getY());
                if (ready) {
                    v.removeAllElements();
                    x0 = xA;
                    y0 = yA;
                    ready = false;
                }
                float dx = xA - x0, dy = yA - y0;
                if (v.size() > 0
                        && dx * dx + dy * dy < 20 * pixelSize * pixelSize) // Previously 4 instead of 20 .........................
                {
                    ready = true;
                } else {
                    v.addElement(new Point2D(xA, yA));
                }

                // Added December 2016:
                if (evt.getModifiers() == InputEvent.BUTTON3_MASK) {
                    ready = true;
                }
                //aqui empieza desmadre mio
//                System.out.println("x = " + xA);
//                System.out.println("y = " + yA);
//                Random r = new Random();
//                float minY = -3.5f;
//                float maxY = 3.5f;
//                float randomY = minY + r.nextFloat() * (maxY - minY);
//                System.out.println("random y = " + randomY);
//                r = new Random();
//                float minX = -5f;
//                float maxX = 5f;
//                float randomX = minX + r.nextFloat() * (maxX - minX);
//                System.out.println("random x = " + randomX);
                //aqui acaba desmadre mio
                repaint();
            }
        });
    }

    void initgr() {
        Dimension d = getSize();
        int maxX = d.width - 1, maxY = d.height - 1;
        pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
        centerX = maxX / 2;
        centerY = maxY / 2;
    }

    int iX(float x) {
        return Math.round(centerX + x / pixelSize);
    }

    int iY(float y) {
        return Math.round(centerY - y / pixelSize);
    }

    float fx(int x) {
        return (x - centerX) * pixelSize;
    }

    float fy(int y) {
        return (centerY - y) * pixelSize;
    }

    public void paint(Graphics g) {
        initgr();
        int left = iX(-rWidth / 2), right = iX(rWidth / 2),
                bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
        g.drawRect(left, top, right - left, bottom - top);
        //AQUI hago todo mi desmadre para generar puntos aleatorios----------------------
        v = new Vector<Point2D>();
        vBlue = new Vector<Point2D>();
        Random r;//= new Random();
        for (int i = 0; i < numerodePuntos; i++) {
            float minY = -(rHeight / 2);
            float maxY = rHeight / 2;
            r = new Random();
            float randomRedY = minY + r.nextFloat() * (maxY - minY);
            r = new Random();
            float randomBlueY = minY + r.nextFloat() * (maxY - minY);
            System.out.println("random red y = " + randomRedY);
            float minX = -(rWidth / 2);
            float maxX = rWidth / 2;
            r = new Random();
            float randomRedX = minX + r.nextFloat() * (maxX - minX);
            r = new Random();
            float randomBlueX = minX + r.nextFloat() * (maxX - minX);
            System.out.println("random red x = " + randomRedX);

            System.out.println("random blue y = " + randomBlueY);
            System.out.println("random blue x = " + randomBlueX);
            Point2D aVerificarBlue = new Point2D(randomBlueX, randomBlueY);
            while (verificarAlineacion(v, vBlue, aVerificarBlue)) {
                System.out.println("estan alineados alv");
                r = new Random();
                randomBlueX = minX + r.nextFloat() * (maxX - minX);
                r = new Random();
                randomBlueY = minY + r.nextFloat() * (maxY - minY);
                aVerificarBlue = new Point2D(randomBlueX, randomBlueY);
            }
            vBlue.addElement(new Point2D(randomBlueX, randomBlueY));
            Point2D aVerificarRed = new Point2D(randomRedX, randomRedY);
            while (verificarAlineacion(v, vBlue, aVerificarRed)) {
                System.out.println("estan alineados alv");
                r = new Random();
                randomRedX = minX + r.nextFloat() * (maxX - minX);
                r = new Random();
                randomRedY = minY + r.nextFloat() * (maxY - minY);
                aVerificarRed = new Point2D(randomRedX, randomRedY);
            }
            v.addElement(new Point2D(randomRedX, randomRedY));
        }
        // y aqui acabo todo mi desmadre para generar los aleatorios--------------------------------------
        int n = v.size();
        if (n == 0) {
            return;
        }
        Point2D a = (Point2D) (v.elementAt(0));
        // Show tiny rectangle around first vertex:
        g.setColor(Color.red);
        g.drawRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
        for (int i = 1; i <= n; i++) {
            if (i == n && !ready) {
                break;
            }
            Point2D b = (Point2D) (v.elementAt(i % n));
            //g.setColor(Color.red);
            //g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
            g.fillRect(iX(b.x) - 2, iY(b.y) - 2, 4, 4); // Tiny rectangle; added
            a = b;
            //g.drawString("" + (i % n), iX(b.x), iY(b.y));// to test.......
        }

        //desmadre para pintar los azules------------------------------------
        n = vBlue.size();
        if (n == 0) {
            return;
        }
        a = (Point2D) (vBlue.elementAt(0));
        // Show tiny rectangle around first vertex:
        g.setColor(Color.BLUE);
        g.drawRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
        for (int i = 1; i <= n; i++) {
            if (i == n && !ready) {
                break;
            }
            Point2D b = (Point2D) (vBlue.elementAt(i % n));
            //g.setColor(Color.red);
            //g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
            g.fillRect(iX(b.x) - 2, iY(b.y) - 2, 4, 4); // Tiny rectangle; added
            //a = b;
            //g.drawString("" + (i % n), iX(b.x), iY(b.y));// to test.......
        }
        //this.verificarAlineacion(vBlue, a);
        //FIN desmadre para pintar los azules------------------------------------
    }

    public boolean verificarAlineacion(Vector<Point2D> v, Vector<Point2D> vBlue, Point2D a) {
        for (int i = 0; i <= v.size() - 1; i++) { //primero comparamos cooredanas X de Red
            //System.out.print("Punto " + i + " :  ");
            if (Math.abs(a.x - v.elementAt(i).x) <= separacionMinima) {
                //System.out.println(a.x + " esta cerca de " + v.elementAt(i).x);
                return true;
            } else {
                //System.out.println(a.x + " esta lejos de " + v.elementAt(i).x);
            }
        }
        for (int i = 0; i <= v.size() - 1; i++) { //despues comparamos cooredanas Y de Red
            //System.out.print("Punto " + i + " :  ");
            if (Math.abs(a.y - v.elementAt(i).y) <= separacionMinima) {
                //System.out.println(a.y + " esta cerca de " + v.elementAt(i).y);
                return true;
            } else {
                //System.out.println(a.y + " esta lejos de " + v.elementAt(i).y);
            }
        }
        for (int i = 0; i <= vBlue.size() - 1; i++) { //primero comparamos cooredanas X de Blue
            //System.out.print("Punto " + i + " :  ");
            if (Math.abs(a.x - vBlue.elementAt(i).x) <= separacionMinima) {
                //System.out.println(a.x + " esta cerca de " + vBlue.elementAt(i).x);
                return true;
            } else {
                //System.out.println(a.x + " esta lejos de " + vBlue.elementAt(i).x);
            }
        }
        for (int i = 0; i <= vBlue.size() - 1; i++) { //despues comparamos cooredanas Y de Blue
            //System.out.print("Punto " + i + " :  ");
            if (Math.abs(a.y - vBlue.elementAt(i).y) <= separacionMinima) {
                //System.out.println(a.y + " esta cerca de " + vBlue.elementAt(i).y);
                return true;
            } else {
                //System.out.println(a.y + " esta lejos de " + vBlue.elementAt(i).y);
            }
        }

        return false;
    }
}

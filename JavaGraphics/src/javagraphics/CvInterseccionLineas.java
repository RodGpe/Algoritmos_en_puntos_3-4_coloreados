package javagraphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CvInterseccionLineas extends Canvas {

    Vector<Point2D> v = new Vector<Point2D>();
    Vector<Point2D> vBlue = new Vector<Point2D>();
    float x0, y0, rWidth = 20F, rHeight = 20.0F, pixelSize; //originalmete rHeight = 7.5F
    boolean ready = true;
    int centerX, centerY;
    int numerodePuntos = 3;
    float separacionMinima = .5f;

    CvInterseccionLineas() {
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
        g.drawRect(left, top, right - left, bottom - top); //dibuja el rectangulo grandote
        v = new Vector<Point2D>();
        //v = this.generarPuntosAleatorios(); //llena el vector de puntos aleatorios
        v.add(new Point2D(1F, 0));
        v.add(new Point2D(2, -1));
//        //System.out.println(v.elementAt(0).x);
//        float y1 = 0.0F;
//        float y2 = 0.0F;
//        float x1 = 20, x2 = -20;
//
//        //y1 = m*x1-b2
//        y1 = v.elementAt(0).x * x1 - v.elementAt(0).y;
//        //y2 = m*x2-b2
//        y2 = v.elementAt(0).x * x2 - v.elementAt(0).y;
//        System.out.println(x1 + " " + y1);
//        System.out.println(x2 + " " + y2);
//        g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
        this.calcularDual(v.elementAt(0), g);
        this.calcularDual(v.elementAt(1), g);
        for (Point2D next : v) {
            this.calcularDual(next, g);
        }

        //calcular las intersecciones---------------------------
        Point2D punto = new Point2D(x0, y0);
        punto = this.calcularInterseccionDual(v.elementAt(0), v.elementAt(1), g);
        for (int i = 0; i < v.size(); i++) {
            for (int j = 0; j < v.size(); j++) {
                punto = this.calcularInterseccionDual(v.elementAt(i), v.elementAt(j), g);
            }

        }
        //fin calcular las intersecciones------------------------
        //this.calcularDual(punto, g);
        //para dibujar los puntos--------------------------------------
        int n = v.size();
        if (n == 0) {
            return;
        }
        Point2D a = (Point2D) (v.elementAt(0));
        // Show tiny rectangle around first vertex:
        g.setColor(Color.red);
        g.fillRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
        for (int i = 1; i <= n; i++) {
            if (i == n && !ready) {
                break;
            }
            Point2D b = (Point2D) (v.elementAt(i % n));
            //g.setColor(Color.red);
            //g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
            g.fillRect(iX(b.x) - 2, iY(b.y) - 2, 4, 4); // Tiny rectangle; added
            a = b;
            g.drawString("" + (i % n), iX(b.x), iY(b.y));// to test.......
        }
        //termina para dibujar los puntos------------------------------
//        int n = v.size();
//        if (n == 0) {
//            return;
//        }
//        Point2D a = (Point2D) (v.elementAt(0));
//        // Show tiny rectangle around first vertex:
//        g.setColor(Color.red);
//        g.fillRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
//        for (int i = 1; i <= n; i++) {
//            if (i == n && !ready) {
//                break;
//            }
//            Point2D b = (Point2D) (v.elementAt(i % n));
//            g.setColor(Color.red);
//            g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
//            g.fillRect(iX(b.x) - 2, iY(b.y) - 2, 4, 4); // Tiny rectangle; added
//            a = b;
//            g.drawString("" + (i % n), iX(b.x), iY(b.y));// to test.......
//        }
    }

    public boolean verificarAlineacion(Vector<Point2D> v, Point2D a) {
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
        return false;
    }

    public void calcularDual(Point2D punto, Graphics g) {
        float y1 = 0.0F;
        float y2 = 0.0F;
        float x1 = 20, x2 = -20;

        //y1 = m*x1-b2
        y1 = punto.x * x1 - punto.y;
        //y2 = m*x2-b2
        y2 = punto.x * x2 - punto.y;
        System.out.println(x1 + " " + y1);
        System.out.println(x2 + " " + y2);
        g.setColor(Color.yellow);
        g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
    }

    public Point2D calcularInterseccionDual(Point2D punto1, Point2D punto2, Graphics g) {
        float y1 = 0.0F;
        float y2 = 0.0F;
        float m1 = punto1.x;
        float m2 = punto2.x;
        float b1 = punto1.y;
        float b2 = punto2.y;
        float x = 0.0F;
        x = (b1 - b2) / (m1 - m2);
        y1 = (m1 * x) - b1;
        y2 = (m2 * x) - b2;
        System.out.println("x" + x);
        System.out.println("interseccion en" + x + " " + y1);
        g.setColor(Color.blue);
        g.fillRect(iX(x) - 2, iY(y1) - 2, 4, 4); // Tiny rectangle; 
        return new Point2D(x, y1);
    }

    public Vector generarPuntosAleatorios() {
        v = new Vector<Point2D>();
        Random r;//= new Random();
        for (int i = 0; i < numerodePuntos; i++) {
            float minY = (-(rHeight / 2)) + .1f;
            float maxY = (rHeight / 2) - .1f;
            r = new Random();
            float randomRedY = minY + r.nextFloat() * (maxY - minY);
            System.out.println("random red y = " + randomRedY);
            float minX = (-(rWidth / 2)) + .1f;
            float maxX = (rWidth / 2) - .1f;
            r = new Random();
            float randomRedX = minX + r.nextFloat() * (maxX - minX);
            System.out.println("random red x = " + randomRedX);
            Point2D aVerificarRed = new Point2D(randomRedX, randomRedY);
            while (verificarAlineacion(v, aVerificarRed)) {
                //System.out.println("quise poner un rojo pero esta alineado alv");
                r = new Random();
                randomRedX = minX + r.nextFloat() * (maxX - minX);
                r = new Random();
                randomRedY = minY + r.nextFloat() * (maxY - minY);
                aVerificarRed = new Point2D(randomRedX, randomRedY);
            }
            v.addElement(new Point2D(randomRedX, randomRedY));
        }
        return v;
    }
}

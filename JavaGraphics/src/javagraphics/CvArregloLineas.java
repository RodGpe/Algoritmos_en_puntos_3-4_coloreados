package javagraphics;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CvArregloLineas extends Canvas {

    Vector<Point2D> v = new Vector<Point2D>();
    Vector<Point2D> vBlue = new Vector<Point2D>();
    float x0, y0, rWidth = 10F, rHeight = 10.0F, pixelSize; //originalmete rHeight = 7.5F
    boolean ready = true;
    int centerX, centerY;
    int numerodePuntos = 3;
    float separacionMinima = .5f;
    ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
    ArrayList<HalfEdge> edgeList = new ArrayList<HalfEdge>();
    ArrayList<HalfEdge> pruebas = new ArrayList<HalfEdge>();
    ArrayList<Face> faceList = new ArrayList<Face>();

    CvArregloLineas() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                DCEList dcel = new DCEList();
                Vertex v10 = new Vertex(-5, 5, null);
                Vertex v20 = new Vertex(7, -4, null);
                HalfEdge linea = new HalfEdge(null, null, null, null, null);
                HalfEdge e2010 = new HalfEdge(null, null, null, null, null);
                linea.origin = v10;
                linea.next = e2010;
                linea.prev = e2010;
                linea.twin = e2010;

                e2010.origin = v20;
                e2010.next = linea;
                e2010.prev = linea;
                e2010.twin = linea;
                dcel.crearBoundingBox(4, -4, 4, -4, edgeList, vertexList, faceList);
             
                //HalfEdge nuevaLinea2 = dcel.crearArista(-6, 4, 7, -2);
                //dcel.agregarLineaArreglo(edgeList, vertexList, faceList, nuevaLinea2);
                //HalfEdge nuevaLinea3 = dcel.crearArista(-5, -4, 2, 6);
                //dcel.agregarLineaArreglo(edgeList, vertexList, faceList, nuevaLinea3);
                 pruebas = edgeList;
                repaint();
                ArrayList<HalfEdge> rectasDuales = transformarPuntosaRectas(generarPuntos());
                for (Iterator<HalfEdge> iterator = rectasDuales.iterator(); iterator.hasNext();) {
                    HalfEdge next = iterator.next();
                    //dcel.crearArista(next.origin.x, next.origin.y, next.twin.origin.x, next.twin.origin.y);
                    dcel.agregarLineaArreglo(edgeList, vertexList, faceList,dcel.crearArista(next.origin.x, next.origin.y, next.twin.origin.x, next.twin.origin.y));
                }
//                System.out.println(unBounded.inner);
//                repaint();
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
        for (Iterator<HalfEdge> iterator = pruebas.iterator(); iterator.hasNext();) {
            HalfEdge next = iterator.next();
            g.setColor(Color.getHSBColor(next.origin.x, next.origin.y * 20, next.twin.origin.y * 30));
            g.drawLine(iX(next.origin.x), iY(next.origin.y), iX(next.twin.origin.x), iY(next.twin.origin.y));
        }
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

    public HalfEdge calcularDual(Point2D punto, Graphics g) {
        float y1 = 0.0F;
        float y2 = 0.0F;
        float x1 = 20, x2 = -20;

        //y1 = m*x1-b2
        y1 = punto.x * x1 - punto.y;
        //y2 = m*x2-b2
        y2 = punto.x * x2 - punto.y;
        System.out.println(x1 + " " + y1);
        System.out.println(x2 + " " + y2);
//        g.setColor(Color.yellow);
//        g.drawLine(iX(x1), iY(y1), iX(x2), iY(y2));
        DCEList dcel = new DCEList ();
        return dcel.crearArista(x1, y1, x2, y2);
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
    
    public Vector generarPuntos(){
        v = new Vector<Point2D>();
        v.add(new Point2D(1.5f, -3.34f));
        v.add(new Point2D(-0.78f, -1.4f));
        v.add(new Point2D(0.24f, 0.74f));
        v.add(new Point2D(-0.44f, -1.44f));
        return v;
    }
    
    public ArrayList transformarPuntosaRectas(Vector<Point2D> v){
        ArrayList<HalfEdge> rectasDuales = new ArrayList<HalfEdge>();
        for (Iterator<Point2D> iterator = v.iterator(); iterator.hasNext();) {
            Point2D next = iterator.next();
            DCEList dcel = new DCEList();
            rectasDuales.add(this.calcularDual(next, null));
            System.out.println("" + next.x);
        }
        return rectasDuales;
    }
}

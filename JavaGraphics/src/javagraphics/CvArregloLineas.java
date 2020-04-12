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
                Face unBounded = faceList.get(1);
                ArrayList<HalfEdge> frontera = dcel.recorrerFrontera(edgeList, unBounded); //indice 1 de cara porque es la no acotada e indice 1 de arista porque es la CCW
                System.out.println("-----");
                //dcel.intersectarLineaFronteraPorIzq(frontera, linea);
                System.out.println("----");
                ArrayList<Object> resultado = dcel.intersectarLineaFronteraPorIzq(frontera, linea);
                Vertex interseccion = (Vertex) resultado.get(0);
                HalfEdge aristaInterseccion = (HalfEdge) resultado.get(1);
                aristaInterseccion = dcel.partirArista(interseccion, aristaInterseccion, edgeList, vertexList, unBounded); //la asigno a la variable para tenerlo actualizado
                System.out.println("----");
                //dcel.recorerCara(edgeList.get(edgeList.size() - 1)); //indice 1 porque es CCW
                System.out.println("-----");
                ArrayList<Object> resultadoNuevo;//= dcel.buscarSiguienteInterseccion(aristaInterseccion.next, e1020); //como parti la arista anterior entonces su punto next sera parte de la otra cara
                Vertex interseccionNueva;//= (Vertex) resultadoNuevo.get(0);
                HalfEdge aristaInterseccionNueva;//= (HalfEdge) resultadoNuevo.get(1);
                //aristaInterseccionNueva = dcel.partirArista(interseccionNueva, aristaInterseccionNueva, edgeList, vertexList);//la asigno a la variable para tenerlo actualizado
                System.out.println("----");
                //dcel.imprimirLista(edgeList);
                //dcel.recorerCara(edgeList.get(edgeList.size()-2));
                System.out.println("----");
                //dcel.partirCara(aristaInterseccion, aristaInterseccionNueva, edgeList, faceList);
                //dcel.recorerCara(faceList.get(0).outer);
                //System.out.println(faceList.get(1).inner);
                System.out.println("----");
                dcel.recorerCara(faceList.get(1).inner);
                dcel.imprimirLista(edgeList);
                System.out.println(unBounded);
                while (true) {
                    resultadoNuevo = dcel.buscarSiguienteInterseccion(aristaInterseccion.next, linea);
                    interseccionNueva = (Vertex) resultadoNuevo.get(0);
                    aristaInterseccionNueva = (HalfEdge) resultadoNuevo.get(1);
                    aristaInterseccionNueva = dcel.partirArista(interseccionNueva, aristaInterseccionNueva, edgeList, vertexList, unBounded);
                    dcel.partirCara(aristaInterseccion, aristaInterseccionNueva, edgeList, faceList);
                    System.out.println("");
                    if (aristaInterseccionNueva.twin.face == unBounded) {
                        break;
                    }
                    aristaInterseccion = aristaInterseccionNueva.twin.prev;
                }

                Vertex v30 = new Vertex(-8, -3, null);
                Vertex v40 = new Vertex(6, 1, null);
                HalfEdge e3040 = new HalfEdge(null, null, null, null, null);
                HalfEdge e4030 = new HalfEdge(null, null, null, null, null);
                e3040.origin = v30;
                e3040.next = e4030;
                e3040.prev = e4030;
                e3040.twin = e4030;

                e4030.origin = v40;
                e4030.next = e3040;
                e4030.prev = e3040;
                e4030.twin = e3040;
                frontera = dcel.recorrerFrontera(edgeList, unBounded);

                resultado = dcel.intersectarLineaFronteraPorIzq(frontera, e3040);
                interseccion = (Vertex) resultado.get(0);
                aristaInterseccion = (HalfEdge) resultado.get(1);
                aristaInterseccion = dcel.partirArista(interseccion, aristaInterseccion, edgeList, vertexList, unBounded);
                //pruebas = edgeList;
                int contador = 1;
                while (true) {
                    resultadoNuevo = dcel.buscarSiguienteInterseccion(aristaInterseccion.next, e3040);
                    interseccionNueva = (Vertex) resultadoNuevo.get(0);
                    aristaInterseccionNueva = (HalfEdge) resultadoNuevo.get(1);
                    aristaInterseccionNueva = dcel.partirArista(interseccionNueva, aristaInterseccionNueva, edgeList, vertexList, unBounded);
//                    if (contador == 2) {
//                        break;
//                    }
                    System.out.println("parti arista");

                    dcel.partirCara(aristaInterseccion, aristaInterseccionNueva, edgeList, faceList);
                    if (aristaInterseccionNueva.twin.face == unBounded) {
                        break;
                    }
                    //pruebas = dcel.recorerCara(faceList.get(2).outer);
                    aristaInterseccion = aristaInterseccionNueva.twin.prev;
                    contador++;
                    //break;
                }
                //pruebas = dcel.recorerCara(faceList.get(2).outer);
                //pruebas = dcel.recorrerFrontera(edgeList, unBounded);
                //pruebas = edgeList;
//                HalfEdge nuevaLinea = dcel.crearArista(-5, -3, 5, 2);
//                dcel.agregarLineaArreglo(edgeList, vertexList, faceList, nuevaLinea);
                HalfEdge nuevaLinea2 = dcel.crearArista(-6, 4, 7, -2);
                dcel.agregarLineaArreglo(edgeList, vertexList, faceList, nuevaLinea2);
                HalfEdge nuevaLinea3 = dcel.crearArista(-5, -4, 2, 6);
                dcel.agregarLineaArreglo(edgeList, vertexList, faceList, nuevaLinea3);
                 pruebas = edgeList;
                repaint();
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

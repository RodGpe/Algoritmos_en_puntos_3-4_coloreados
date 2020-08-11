package triangulomaximal;

import javagraphics.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

class CvArregloLineas extends Canvas {

    Vector<Point2D> v = new Vector<Point2D>();
    Vector<Point2D> vBlue = new Vector<Point2D>();
    float x0, y0, rWidth = 10F, rHeight = 10.0F, pixelSize; //originalmete rHeight = 7.5F
    boolean ready = true;
    int centerX, centerY;
    int numerodePuntos = 3;
    float separacionMinima = .5f;
    // declaro como variable de clase todo lo que su use para pintar
    ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
    ArrayList<HalfEdge> edgeList = new ArrayList<HalfEdge>();
    ArrayList<HalfEdge> pruebas = new ArrayList<HalfEdge>();
    ArrayList<Face> faceList = new ArrayList<Face>();
    ArrayList<Linea> rectasDuales = new ArrayList<>();
    ArrayList<HalfEdge> orden = new ArrayList<>();
    ArrayList<HalfEdge> primeras = new ArrayList<HalfEdge>();

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

                rectasDuales = transformarPuntosaRectas(generarPuntos());
                for (Linea dual : rectasDuales) {
                    dual.primerArista = dcel.agregarLineaArreglo(edgeList, vertexList, faceList, dual);
                    primeras.add(dual.primerArista);
                }
                ArrayList<HalfEdge> segmentosLinea = new ArrayList<>();
                segmentosLinea = dcel.recorrerLinea(primeras.get(1), faceList.get(1));
                //pruebas = primeras; //para pintar las primeras aristas de las rectas
                //pruebas = edgeList; //para pintar todo 
                ArrayList<HalfEdge> conLinea = new ArrayList<>();
                for (HalfEdge conLine : edgeList) {
                    if (conLine.line != null) {
                        conLinea.add(conLine);
                    }
                }
                dcel.imprimirLista(edgeList);
                orden = dcel.obtenerOrden(primeras.get(0), faceList.get(1));
                //pruebas = segmentosLinea; //para pintar una linea en segmentos 
                //pruebas = primeras;
                //pruebas = conLinea; //para pintar una linea en segmentos 
                pruebas = edgeList;
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
        BufferedImage bufferedImage = new BufferedImage(1366, 768, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();    //para dibujar sobre la imagen que se exporta

        initgr();
        int left = iX(-rWidth / 2), right = iX(rWidth / 2);
        int bottom = iY(-rHeight / 2), top = iY(rHeight / 2);

//        codigo para dibujar rectas
//        for (Linea recta : rectasDuales) {
//            g.drawLine(iX(recta.origin.x), iY(recta.origin.y), iX(recta.twin.origin.x), iY(recta.twin.origin.y));
//        }

        //codigo para dibujar aristas
        g.drawRect(left, top, right - left, bottom - top); //dibuja el rectangulo grandote
        g2d.drawRect(left, top, right - left, bottom - top); //dibuja el rectangulo grandote
        for (HalfEdge next : pruebas) {
            g.setColor(Color.getHSBColor(next.origin.x, next.origin.y * 20, next.twin.origin.y * 30));
            g2d.setColor(Color.getHSBColor(next.origin.x, next.origin.y * 20, next.twin.origin.y * 30));
            g.drawLine(iX(next.origin.x), iY(next.origin.y), iX(next.twin.origin.x), iY(next.twin.origin.y));
            g2d.drawLine(iX(next.origin.x), iY(next.origin.y), iX(next.twin.origin.x), iY(next.twin.origin.y));
        }

        //codigo para dibujar los puntos
        int i = 0;
        for (Point2D a : v) {
            g.setColor(Color.red);
            g.fillRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
            //g.drawString("" + (++i), iX(a.x), iY(a.y));
        }
        //codigo para dibujar un orden
        for (HalfEdge halfEdge : orden) {
            Point2D puntoRef = primeras.get(0).line.puntoPrimal;
            System.out.println("punto ref" + puntoRef.x + " " + puntoRef.y);
            System.out.println(""+ halfEdge.line.puntoPrimal.x + " " + halfEdge.line.puntoPrimal.y);
            g.drawLine(iX(puntoRef.x), iY(puntoRef.y), iX(halfEdge.line.puntoPrimal.x), iY(halfEdge.line.puntoPrimal.y));
            g.drawString("" + (++i), iX(halfEdge.line.puntoPrimal.x), iY(halfEdge.line.puntoPrimal.y));
        }
        //para exportar imagen
        g2d.dispose();
        File file = new File("myimage.png");
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(CvArregloLineas.class.getName()).log(Level.SEVERE, null, ex);
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

    public Linea calcularDual(Point2D punto, Graphics g) {
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
        DCEList dcel = new DCEList();
        return dcel.crearLinea(x1, y1, x2, y2);
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

    public Vector generarPuntos() {
        v = new Vector<Point2D>();
        v.add(new Point2D(1.5f, -3.34f));
        v.add(new Point2D(-0.78f, -1.4f));
        v.add(new Point2D(0.24f, 0.74f));
        v.add(new Point2D(-0.44f, -1.44f));
        return v;
    }

    public ArrayList transformarPuntosaRectas(Vector<Point2D> v) {
        ArrayList<Linea> rectasDuales = new ArrayList<Linea>();
        for (Point2D punto : v) {
            //DCEList dcel = new DCEList();
            Linea auxiliar = this.calcularDual(punto, null);
            auxiliar.puntoPrimal = punto;   //para que la linea a que punto pertenece
            punto.linea = auxiliar;         //para que sepa el punto a que linea se mapea
            rectasDuales.add(auxiliar);
        }
        return rectasDuales;
    }
}

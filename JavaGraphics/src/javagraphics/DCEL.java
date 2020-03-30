/*
Clase de prueba para crear una doubly connected edge list
para manejar subdivisiones del plano
 */
package javagraphics;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Rodrigo
 */
public class DCEL {
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        System.out.println("hola");
        DCEL dcel = new DCEL();
        Vertex v1 = new Vertex(1, 1, null);
        Vertex v2 = new Vertex(2, 2, null);
        Vertex v3 = new Vertex(2, 0, null);
        Vertex v4 = new Vertex(3, 1, null);
        HalfEdge e13 = new HalfEdge(null, null, null, null);
        HalfEdge e31 = new HalfEdge(null, null, null, null);
        HalfEdge e21 = new HalfEdge(null, null, null, null);
        HalfEdge e12 = new HalfEdge(null, null, null, null);
        HalfEdge e34 = new HalfEdge(null, null, null, null);
        HalfEdge e43 = new HalfEdge(null, null, null, null);
        HalfEdge e42 = new HalfEdge(null, null, null, null);
        HalfEdge e24 = new HalfEdge(null, null, null, null);
        HalfEdge e32 = new HalfEdge(null, null, null, null);
        HalfEdge e23 = new HalfEdge(null, null, null, null);

        Face f1 = new Face(e13, null);
        Face f2 = new Face(e34, null);
        Face fu = new Face(null, e24); //como es la cara no acotada el outer es null

        e13.origin = v1;
        e13.next = e32;
        e13.prev = e21;
        e13.twin = e31;

        e31.origin = v3;
        e31.next = e12;
        e31.prev = e43;
        e31.twin = e13;

        e21.origin = v2;
        e21.next = e13;
        e21.prev = e32;
        e21.twin = e12;

        e12.origin = v1;
        e12.next = e24;
        e12.prev = e31;
        e12.twin = e21;

        e34.origin = v3;
        e34.next = e42;
        e34.prev = e23;
        e34.twin = e43;

        e43.origin = v4;
        e43.next = e31;
        e43.prev = e24;
        e43.twin = e34;

        e42.origin = v4;
        e42.next = e23;
        e42.prev = e34;
        e42.twin = e24;

        e24.origin = v2;
        e24.next = e43;
        e24.prev = e12;
        e24.twin = e42;

        e32.origin = v3;
        e32.next = e21;
        e32.prev = e13;
        e32.twin = e23;

        e23.origin = v2;
        e23.next = e34;
        e23.prev = e42;
        e23.twin = e32;

//        HalfEdge iterador = e12;
//        while (!iterador.next.equals(e12)) {
//            System.out.println(iterador.origin.x + " " + iterador.origin.y);
//            iterador = iterador.next;
//        }
        //dcel.listarAristas(e23);
        //-----prueba para interseccion de aristas
        Vertex v10 = new Vertex(-5, 5, null);
        Vertex v20 = new Vertex(6, -8, null);
        HalfEdge e1020 = new HalfEdge(null, null, null, null);
        HalfEdge e2010 = new HalfEdge(null, null, null, null);
        e1020.origin = v10;
        e1020.next = e2010;
        e1020.prev = e2010;
        e1020.twin = e2010;

        e2010.origin = v20;
        e2010.next = e1020;
        e2010.prev = e1020;
        e2010.twin = e1020;

        Vertex v30 = new Vertex(-8, -3, null);
        Vertex v40 = new Vertex(6, 1, null);
        HalfEdge e3040 = new HalfEdge(null, null, null, null);
        HalfEdge e4030 = new HalfEdge(null, null, null, null);
        e3040.origin = v30;
        e3040.next = e4030;
        e3040.prev = e4030;
        e3040.twin = e4030;

        e4030.origin = v40;
        e4030.next = e3040;
        e4030.prev = e3040;
        e4030.twin = e3040;

        dcel.intersectarAristas(e1020, e3040);

        //ahora vamos a crear la bounding box de ejemplo-----------
        ArrayList<Vertex> vBounding = new ArrayList<Vertex>();
        vBounding.add(new Vertex(4, 4, null)); //0
        vBounding.add(new Vertex(4, -4, null)); //1
        vBounding.add(new Vertex(-4, -4, null)); //2
        vBounding.add(new Vertex(-4, 4, null)); //3
        ArrayList<HalfEdge> eBounding = new ArrayList<HalfEdge>();
        Iterator itr = vBounding.iterator();
        while (itr.hasNext()) {
            Vertex next = (Vertex) itr.next();
            System.out.println(next.x + " " + next.y);
        }
        HalfEdge eb12 = new HalfEdge(null, null, null, null);
        HalfEdge eb21 = new HalfEdge(null, null, null, null);
        HalfEdge eb23 = new HalfEdge(null, null, null, null);
        HalfEdge eb32 = new HalfEdge(null, null, null, null);
        HalfEdge eb34 = new HalfEdge(null, null, null, null);
        HalfEdge eb43 = new HalfEdge(null, null, null, null);
        HalfEdge eb41 = new HalfEdge(null, null, null, null);
        HalfEdge eb14 = new HalfEdge(null, null, null, null);

        Face bf1 = new Face(eb21, null);
        Face bfu = new Face(null, eb12);

        eb12.origin = vBounding.get(0);
        eb12.prev = eb41;
        eb12.next = eb23;
        eb12.twin = eb21;

        eb21.origin = vBounding.get(1);
        eb21.prev = eb32;
        eb21.next = eb14;
        eb21.twin = eb12;

        eb23.origin = vBounding.get(1);
        eb23.prev = eb12;
        eb23.next = eb34;
        eb23.twin = eb32;

        eb32.origin = vBounding.get(2);
        eb32.prev = eb43;
        eb32.next = eb21;
        eb32.twin = eb23;

        eb34.origin = vBounding.get(2);
        eb34.prev = eb23;
        eb34.next = eb41;
        eb34.twin = eb43;

        eb43.origin = vBounding.get(3);
        eb43.prev = eb14;
        eb43.next = eb32;
        eb43.twin = eb34;

        eb41.origin = vBounding.get(3);
        eb41.prev = eb34;
        eb41.next = eb12;
        eb41.twin = eb14;

        eb14.origin = vBounding.get(0);
        eb14.prev = eb21;
        eb14.next = eb43;
        eb14.twin = eb41;

        System.out.println("cara interna bBox");
        HalfEdge iterador = eb21;
        while (!iterador.next.equals(eb21)) {
            dcel.intersectarAristas(iterador, e1020);
            System.out.println(iterador.origin.x + " " + iterador.origin.y);
            iterador = iterador.next;
        }
        dcel.intersectarAristas(iterador, e1020);

    }

    public void listarAristas(HalfEdge buscarAristas) {
        HalfEdge aristaInicial = buscarAristas;
        HalfEdge bucarAristas = aristaInicial;
        while (!bucarAristas.twin.next.equals(aristaInicial)) {
            //System.out.println(bucarAristas.origin.x + " " + bucarAristas.origin.y);   
            System.out.println(bucarAristas.toString());
            bucarAristas = bucarAristas.twin.next;
        }
        System.out.println(bucarAristas.toString());
    }

    public void intersectarAristas(HalfEdge e1, HalfEdge e2) {
        //primero pasamos a forma y = ax +b
        //para eso primero encontramos la pendiente
        System.out.println(e1.toString());
        System.out.println(e2.toString());
        float m1 = (e1.origin.y - e1.twin.origin.y) / (e1.origin.x - e1.twin.origin.x); //(y1-y2) / (x1-x2)
        float m2 = (e2.origin.y - e2.twin.origin.y) / (e2.origin.x - e2.twin.origin.x); //(y1-y2) / (x1-x2)
        System.out.println("m1 " + m1);
        System.out.println("m2 " + m2);
        //y - y1 = m(x-x1) => y mx - mx1 + y1
        float b1 = (-m1 * (e1.origin.x)) + e1.origin.y; //b1 de la forma y = xm1 + b1
        float b2 = (-m2 * (e2.origin.x)) + e2.origin.y; //b2 de la forma Y = xm2 + b2
        System.out.println("b1 " + b1);
        System.out.println("b2 " + b2);
        float x = (b2 - b1) / (m1 - m2);
        if (Double.isInfinite(m1)) {    //si esto pasa es vertical la linea
            x = e1.origin.x;
        }
        float y2 = (m2 * x) + b2;
        System.out.println("x " + x);
        System.out.println("y1 " + y2);
        System.out.println("interseccion en " + x + " " + y2);
        if (Math.min(e1.origin.x, e1.twin.origin.x) <= x && x <= Math.max(e1.origin.x, e1.twin.origin.x)
                && Math.min(e2.origin.x, e2.twin.origin.x) <= x && x <= Math.max(e2.origin.x, e2.twin.origin.x)) {
            if (e1.origin.x == e1.twin.origin.x) {
                System.out.println("necesita verificacion en y");
                if (Math.min(e1.origin.y, e1.twin.origin.y) <= y2 && y2 <= Math.max(e1.origin.y, e1.twin.origin.y)
                        && Math.min(e2.origin.y, e2.twin.origin.y) <= y2 && y2 <= Math.max(e2.origin.y, e2.twin.origin.y)) {
                    System.out.println(ANSI_GREEN+"caso vertical - se intersecan"+ANSI_GREEN);
                    return;
                }else{
                    System.out.println("caso vertical - no se intersecan");
                    return;
                }
                
            }
            System.out.println(ANSI_GREEN+"los segmentos se intersecan"+ANSI_GREEN);
        } else {
            System.out.println("no se intersecan");
        }
        System.out.println("");
        System.out.println("");
    }
}

class Vertex {

    float x;
    float y;
    HalfEdge incident;

    public Vertex(int x, int y, HalfEdge incident) {
        this.x = x;
        this.y = y;
        this.incident = incident;
    }

}

class HalfEdge {

    Vertex origin;
    Face face;
    HalfEdge next;
    HalfEdge prev;
    HalfEdge twin;

    public HalfEdge(Vertex origin, HalfEdge next, HalfEdge prev, HalfEdge twin) {
        this.origin = origin;
        this.next = next;
        this.prev = prev;
        this.twin = twin;
    }

    @Override
    public String toString() {
        return "HalfEdge{" + "origin=" + origin.x + " " + origin.y
                + ", next=" + next.origin.x + " " + next.origin.y + '}';
    }

}

class Face {

    HalfEdge outer;
    HalfEdge inner; //como en mi caso no tengo hoyos entonces no hace falta
    //guardar más de una arista para recorrer la cara 

    public Face(HalfEdge outer, HalfEdge inner) {
        this.outer = outer;
        this.inner = inner;
    }

}

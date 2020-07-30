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
        HalfEdge e13 = new HalfEdge(null, null, null, null, null);
        HalfEdge e31 = new HalfEdge(null, null, null, null, null);
        HalfEdge e21 = new HalfEdge(null, null, null, null, null);
        HalfEdge e12 = new HalfEdge(null, null, null, null, null);
        HalfEdge e34 = new HalfEdge(null, null, null, null, null);
        HalfEdge e43 = new HalfEdge(null, null, null, null, null);
        HalfEdge e42 = new HalfEdge(null, null, null, null, null);
        HalfEdge e24 = new HalfEdge(null, null, null, null, null);
        HalfEdge e32 = new HalfEdge(null, null, null, null, null);
        HalfEdge e23 = new HalfEdge(null, null, null, null, null);

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
        Vertex v20 = new Vertex(7, -4, null);
        HalfEdge e1020 = new HalfEdge(null, null, null, null, null);
        HalfEdge e2010 = new HalfEdge(null, null, null, null, null);
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

        //dcel.intersectarAristas(e1020, e3040);
        //ahora vamos a crear la bounding box de ejemplo-----------
        //INICIA declaracion de bounding box------------------------
        ArrayList<Vertex> vBounding = new ArrayList<Vertex>();
        vBounding.add(new Vertex(-4, 4, null)); //0
        vBounding.add(new Vertex(4, 4, null)); //1
        vBounding.add(new Vertex(4, -4, null)); //2
        vBounding.add(new Vertex(-4, -4, null)); //3
        ArrayList<HalfEdge> eBounding = new ArrayList<HalfEdge>();
        Iterator itr = vBounding.iterator();
        while (itr.hasNext()) {
            Vertex next = (Vertex) itr.next();
            System.out.println(next.x + " " + next.y);
        }
        HalfEdge eb12 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb21 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb23 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb32 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb34 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb43 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb41 = new HalfEdge(null, null, null, null, null);
        HalfEdge eb14 = new HalfEdge(null, null, null, null, null);

        Face bf1 = new Face(eb21, null);
        Face bfu = new Face(null, eb12);

        eb12.origin = vBounding.get(0);
        eb12.prev = eb41;
        eb12.next = eb23;
        eb12.twin = eb21;
        eb12.face = bfu;

        eb21.origin = vBounding.get(1);
        eb21.prev = eb32;
        eb21.next = eb14;
        eb21.twin = eb12;
        eb21.face = bf1;

        eb23.origin = vBounding.get(1);
        eb23.prev = eb12;
        eb23.next = eb34;
        eb23.twin = eb32;
        eb23.face = bfu;

        eb32.origin = vBounding.get(2);
        eb32.prev = eb43;
        eb32.next = eb21;
        eb32.twin = eb23;
        eb32.face = bf1;

        eb34.origin = vBounding.get(2);
        eb34.prev = eb23;
        eb34.next = eb41;
        eb34.twin = eb43;
        eb34.face = bfu;

        eb43.origin = vBounding.get(3);
        eb43.prev = eb14;
        eb43.next = eb32;
        eb43.twin = eb34;
        eb43.face = bf1;

        eb41.origin = vBounding.get(3);
        eb41.prev = eb34;
        eb41.next = eb12;
        eb41.twin = eb14;
        eb41.face = bfu;

        eb14.origin = vBounding.get(0);
        eb14.prev = eb21;
        eb14.next = eb43;
        eb14.twin = eb41;
        eb14.face = bf1;

        //TERMINA declaracion de bounding box----------
        // INICIA buscar la interseccion con la bounding box----------
        System.out.println("cara interna bBox");
        Vertex interIzquierda = new Vertex(5, 0, null);
        HalfEdge aristaInterIzq = new HalfEdge(null, null, null, null, null);
        HalfEdge iterador = eb21;
        while (!iterador.next.equals(eb21)) {
            if (dcel.intersectarAristas(iterador, e1020) != null) {
                //INICIA compara el más izquierdo
                if (dcel.intersectarAristas(iterador, e1020).x < interIzquierda.x) {
                    interIzquierda = dcel.intersectarAristas(iterador, e1020);
                    aristaInterIzq = iterador;
                }
                //TERMINA comparar mas izquierdo
            }
            System.out.println(iterador.origin.x + " " + iterador.origin.y);
            iterador = iterador.next;
        }
        System.out.println(iterador.origin.x + " " + iterador.origin.y);
        //dcel.intersectarAristas(iterador, e1020);
        if (dcel.intersectarAristas(iterador, e1020) != null) {
            //INICIA compara el más izquierdo
            if (dcel.intersectarAristas(iterador, e1020).x < interIzquierda.x) {
                interIzquierda = dcel.intersectarAristas(iterador, e1020);
                aristaInterIzq = iterador;
            }
            //TERMINA comparar mas izquierdo
        }

        System.out.println("el mas izquierdo es " + interIzquierda.x);
        System.out.println("con " + aristaInterIzq.toString());
        //TERMINA buscar la interseccion con la bounding box--------
        //INICIA partir la arista inicial de bBox-----
        Vertex c = new Vertex(interIzquierda.x, interIzquierda.y, null);
        HalfEdge ac = new HalfEdge(null, null, null, null, null);
        HalfEdge ca = new HalfEdge(null, null, null, null, null);
        HalfEdge bc = new HalfEdge(null, null, null, null, null);
        HalfEdge cb = new HalfEdge(null, null, null, null, null);

        ac.origin = vBounding.get(0);
        ac.prev = eb41;
        ac.next = cb;
        ac.twin = ca;
        ac.face = bfu;

        ca.origin = c;
        ca.prev = bc;
        ca.next = eb14;
        ca.twin = ac;
        ca.face = bf1;

        bc.origin = vBounding.get(1);
        bc.prev = eb32;
        bc.next = ca;
        bc.twin = cb;
        bc.face = bf1;

        cb.origin = c;
        cb.prev = ac;
        cb.next = eb23;
        cb.twin = bc;
        cb.face = bfu;

        eb32.next = bc;
        eb23.prev = cb;

        eb41.next = ac;
        eb14.prev = ca;
        //TERMINA partir la arista inicial de bBox-----

        //INICIA partir la cara -----------
        System.out.println("");
        System.out.println("caras de una arista");
        System.out.println("");
        ArrayList<HalfEdge> cara = dcel.recorerCara(ca);
        Iterator iter = cara.iterator();
        while (iter.hasNext()) {
            HalfEdge next = (HalfEdge) iter.next();
            System.out.println(next.face);
            System.out.println("aritsta a probar ".toUpperCase() + next.toString());
            if (dcel.intersectarAristas(next, e1020) != null) {
                //crear vertices 
                vBounding.add(dcel.intersectarAristas(next, e1020));
                HalfEdge mitad1CCW = new HalfEdge(null, null, null, null, null);
                HalfEdge mitad1CW = new HalfEdge(null, null, null, null, null);
                HalfEdge mitad2CCW = new HalfEdge(null, null, null, null, null);
                HalfEdge mitad2CW = new HalfEdge(null, null, null, null, null);
                HalfEdge mc = new HalfEdge(null, null, null, null, null);
                HalfEdge cm = new HalfEdge(null, null, null, null, null);

                Face f3 = new Face(null, null);

                mitad1CCW.origin = next.origin;
                mitad1CCW.prev = next.prev;
                mitad1CCW.next = mc;
                mitad1CCW.twin = mitad1CW;
                mitad1CCW.face = next.face;

                System.out.println(next.face);
                bf1.outer = mitad1CCW;//actualizamos por donde recorrer la cara 
                mitad1CCW.face.outer = mitad1CCW;//actualizamos por donde recorrer la cara 

                mitad1CW.origin = vBounding.get(vBounding.size() - 1); //el ultimo vertice que agregué
                mitad1CW.prev = cm;
                mitad1CW.next = next.twin.next;
                mitad1CW.twin = mitad1CCW;
                mitad1CW.face = next.twin.face;

                mitad2CCW.origin = vBounding.get(vBounding.size() - 1); //el ultimo vertice que agregué
                mitad2CCW.prev = cm;
                mitad2CCW.next = next.next;
                mitad2CCW.twin = mitad2CW;
                mitad2CCW.face = f3;

                f3.outer = mitad2CCW; //decimos por  donde inicia la nueva cara

                mitad2CW.origin = next.twin.origin;
                mitad2CW.prev = next.next.twin;
                mitad2CW.next = mc;
                mitad2CW.twin = mitad2CCW;
                mitad2CW.face = next.twin.face;

                mc.origin = vBounding.get(vBounding.size() - 1);
                mc.prev = mitad1CCW;
                mc.next = ca; // INFORMACION QUE TIENE QUE SER GUARDADA EN CADA ITERACION 
                mc.twin = cm;
                mc.face = mitad1CCW.face;

                cm.origin = c;
                cm.prev = bc;
                cm.next = mitad2CCW; // INFORMACION QUE TIENE QUE SER GUARDADA EN CADA ITERACION 
                cm.twin = mc;
                cm.face = mitad2CCW.face;

                //actualizar lo que hay atras y delante de next
                next.prev.next = mitad1CCW;
                next.prev.next.twin = mitad1CW;
                next.next.prev = mitad2CCW;
                next.next.prev.twin = mitad2CW;

                bc.next = cm;
                bc.next.twin = mc;
                //dcel.recorerCara(mitad1CCW);
                dcel.recorerCara(mitad2CCW);
                break;
            }
        }
        //TERMINA partir la cara ----------
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

    public ArrayList<HalfEdge> recorerCara(HalfEdge edge) {
        ArrayList<HalfEdge> face = new ArrayList<HalfEdge>();
        HalfEdge iterador = edge;
        iterador = iterador.next;
        while (!iterador.equals(edge)) {
            face.add(new HalfEdge(iterador.origin, iterador.next, iterador.prev, iterador.twin, iterador.face));
            System.out.println(iterador.toString());
            iterador = iterador.next;
        }
        System.out.println(iterador.toString());
        return face;
    }

    /**
     *
     * @param e1 puede ser recta vertical
     * @param e2 falla si el segmento es vertical
     * @return
     */
    public Vertex intersectarAristas(HalfEdge e1, HalfEdge e2) {
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
                    System.out.println(ANSI_GREEN + "caso vertical - se intersecan" + ANSI_GREEN);
                    return new Vertex(x, y2, null);
                } else {
                    System.out.println("caso vertical - no se intersecan");
                    return null;
                }
            }
            System.out.println(ANSI_GREEN + "los segmentos se intersecan" + ANSI_GREEN);
            return new Vertex(x, y2, null);
        } else {
            System.out.println("no se intersecan");
            return null;
        }
    }
}

class Vertex {

    float x;
    float y;
    HalfEdge incident;

    public Vertex(float x, float y, HalfEdge incident) {
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

    public HalfEdge(Vertex origin, HalfEdge next, HalfEdge prev, HalfEdge twin, Face face) {
        this.origin = origin;
        this.next = next;
        this.prev = prev;
        this.twin = twin;
        this.face = face;
    }

    @Override
    public String toString() {
        return "HalfEdge{" + "origin=" + origin.x + " " + origin.y
                + ", next=" + twin.origin.x + " " + twin.origin.y + '}';
        //+ ", next=" + next.origin.x + " " + next.origin.y + '}';
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

class Linea extends HalfEdge{
    HalfEdge primerArista;
    public Linea(Vertex origin, HalfEdge next, HalfEdge prev, HalfEdge twin, Face face, HalfEdge primerArista) {
        super(origin, next, prev, twin, face);
        this.primerArista= primerArista;
    }

}

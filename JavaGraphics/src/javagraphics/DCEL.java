/*
Clase de prueba para crear una doubly connected edge list
para manejar subdivisiones del plano
 */
package javagraphics;

/**
 *
 * @author Rodrigo
 */
public class DCEL {

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
        dcel.listarAristas(e23);

        //-----prueba para interseccion de aristas
        Vertex v10 = new Vertex(-5, 5, null);
        Vertex v20 = new Vertex(7, -4, null);
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
        
        //ahora vamos a crear la bounding box de ejemplo
        
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
        float m1 = (e1.origin.y - e1.twin.origin.y) / (e1.origin.x - e1.twin.origin.x);
        float m2 = (e2.origin.y - e2.twin.origin.y) / (e2.origin.x - e2.twin.origin.x);
        System.out.println("m1 " + m1);
        //y - y1 = m(x-x1) => y mx - mx1 + y1
        float b1 = (-m1 * (e1.origin.x)) + e1.origin.y;
        float b2 = (-m2 * (e2.origin.x)) + e2.origin.y;

        float x = (b2 - b1) / (m1 - m2);
        float y1 = (m1 * x) + b1;
        System.out.println("x" + x);
        System.out.println("interseccion en " + x + " " + y1);
        if(Math.min(e1.origin.x, e1.twin.origin.x) < x && x < Math.max(e1.origin.x, e1.twin.origin.x)
                && Math.min(e2.origin.x, e2.twin.origin.x) < x && x < Math.max(e2.origin.x, e2.twin.origin.x) ){
            System.out.println("los segmentos se intersecan");
        }else{
            System.out.println("no se intersecan");
        }
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

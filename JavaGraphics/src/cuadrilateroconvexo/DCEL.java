/*
Clase de prueba para crear una doubly connected edge list
para manejar subdivisiones del plano
 */
package cuadrilateroconvexo;

import triangulomaximal.*;
import javagraphics.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Rodrigo
 */
public class DCEL {
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
    Linea line;

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
    Point2D puntoPrimal;
    public Linea(Vertex origin, HalfEdge next, HalfEdge prev, HalfEdge twin, Face face, HalfEdge primerArista) {
        super(origin, next, prev, twin, face);
        this.primerArista= primerArista;
    }
    public Linea regresarObjeto (){
        return this;
    }
}

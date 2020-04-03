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
public class DCEList {

    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {
        System.out.println("hola");
        DCEList dcel = new DCEList();
        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        ArrayList<HalfEdge> edgeList = new ArrayList<HalfEdge>();
        ArrayList<Face> faceList = new ArrayList<Face>();
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
        vertexList.add(new Vertex(-4, 4, null));

        vBounding.add(new Vertex(4, 4, null)); //1
        vertexList.add(new Vertex(4, 4, null));

        vBounding.add(new Vertex(4, -4, null)); //2
        vertexList.add(new Vertex(4, -4, null));

        vBounding.add(new Vertex(-4, -4, null)); //3
        vertexList.add(new Vertex(-4, -4, null)); //3

        ArrayList<HalfEdge> eBounding = new ArrayList<HalfEdge>();
        Iterator itr = vBounding.iterator();
        while (itr.hasNext()) {
            Vertex next = (Vertex) itr.next();
            System.out.println(next.x + " " + next.y);
        }
        HalfEdge eb12 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//0 indice arista SENTIDO HORARIO (CW)

        HalfEdge eb21 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//1  SENTIDO ANTIHORARIO (CCW)

        HalfEdge eb23 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//2

        HalfEdge eb32 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//3

        HalfEdge eb34 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//4

        HalfEdge eb43 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//5

        HalfEdge eb41 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//6

        HalfEdge eb14 = new HalfEdge(null, null, null, null, null);
        edgeList.add(new HalfEdge(null, null, null, null, null));//7

        Face bf1 = new Face(eb21, null);
        faceList.add(new Face(edgeList.get(1), null)); //  0  indice cara

        Face bfu = new Face(null, eb12);
        faceList.add(new Face(null, edgeList.get(0))); // 1  CARA NO ACOTADA

        edgeList.get(0).origin = vBounding.get(0);
        edgeList.get(0).prev = edgeList.get(6);
        edgeList.get(0).next = edgeList.get(2);
        edgeList.get(0).twin = edgeList.get(1);
        edgeList.get(0).face = faceList.get(1);

        edgeList.get(1).origin = vBounding.get(1);
        edgeList.get(1).prev = edgeList.get(3);
        edgeList.get(1).next = edgeList.get(7);
        edgeList.get(1).twin = edgeList.get(0);
        edgeList.get(1).face = faceList.get(0);

        edgeList.get(2).origin = vBounding.get(1);
        edgeList.get(2).prev = edgeList.get(0);
        edgeList.get(2).next = edgeList.get(4);
        edgeList.get(2).twin = edgeList.get(3);
        edgeList.get(2).face = faceList.get(1);

        edgeList.get(3).origin = vBounding.get(2);
        edgeList.get(3).prev = edgeList.get(5);
        edgeList.get(3).next = edgeList.get(1);
        edgeList.get(3).twin = edgeList.get(2);
        edgeList.get(3).face = faceList.get(0);

        edgeList.get(4).origin = vBounding.get(2);
        edgeList.get(4).prev = edgeList.get(2);
        edgeList.get(4).next = edgeList.get(6);
        edgeList.get(4).twin = edgeList.get(5);
        edgeList.get(4).face = faceList.get(1);

        edgeList.get(5).origin = vBounding.get(3);
        edgeList.get(5).prev = edgeList.get(7);
        edgeList.get(5).next = edgeList.get(3);
        edgeList.get(5).twin = edgeList.get(4);
        edgeList.get(5).face = faceList.get(0);

        edgeList.get(6).origin = vBounding.get(3);
        edgeList.get(6).prev = edgeList.get(4);
        edgeList.get(6).next = edgeList.get(0);
        edgeList.get(6).twin = edgeList.get(7);
        edgeList.get(6).face = faceList.get(1);

        edgeList.get(7).origin = vBounding.get(0);
        edgeList.get(7).prev = edgeList.get(1);
        edgeList.get(7).next = edgeList.get(5);
        edgeList.get(7).twin = edgeList.get(6);
        edgeList.get(7).face = faceList.get(0);

        //TERMINA declaracion de bounding box----------
        // INICIA buscar la interseccion con la bounding box----------
        System.out.println("cara interna bBox");

        ArrayList<HalfEdge> frontera = dcel.recorrerFrontera(edgeList, faceList.get(1)); //indice 1 de cara porque es la no acotada e indice 1 de arista porque es la CCW
        System.out.println("-----");
        dcel.intersectarLineaFronteraPorIzq(frontera, e1020);
        System.out.println("----");
        ArrayList<Object> resultado = dcel.intersectarLineaFronteraPorIzq(frontera, e1020);
        Vertex interseccion = (Vertex) resultado.get(0);
        HalfEdge aristainterseccion = (HalfEdge) resultado.get(1);
        dcel.partirArista(interseccion, aristainterseccion, edgeList, vertexList);
        System.out.println("----");
        dcel.recorerCara(edgeList.get(1)); //indice 1 porque es CCW
        //dcel.imprimirLista(edgeList);
        //dcel.imprimirAristaDetallado(edgeList.get(1));

//        Vertex interIzquierda = new Vertex(5, 0, null);
//        HalfEdge aristaInterIzq = new HalfEdge(null, null, null, null, null);
//        HalfEdge iterador = eb21;
//        while (!iterador.next.equals(eb21)) {
//            if (dcel.intersectarAristas(iterador, e1020) != null) {
//                //INICIA compara el más izquierdo
//                if (dcel.intersectarAristas(iterador, e1020).x < interIzquierda.x) {
//                    interIzquierda = dcel.intersectarAristas(iterador, e1020);
//                    aristaInterIzq = iterador;
//                }
//                //TERMINA comparar mas izquierdo
//            }
//            System.out.println(iterador.origin.x + " " + iterador.origin.y);
//            iterador = iterador.next;
//        }
//        System.out.println(iterador.origin.x + " " + iterador.origin.y);
//        //dcel.intersectarAristas(iterador, e1020);
//        if (dcel.intersectarAristas(iterador, e1020) != null) {
//            //INICIA compara el más izquierdo
//            if (dcel.intersectarAristas(iterador, e1020).x < interIzquierda.x) {
//                interIzquierda = dcel.intersectarAristas(iterador, e1020);
//                aristaInterIzq = iterador;
//            }
//            //TERMINA comparar mas izquierdo
//        }
//
//        System.out.println("el mas izquierdo es " + interIzquierda.x);
//        System.out.println("con " + aristaInterIzq.toString());
//        //TERMINA buscar la interseccion con la bounding box--------
//        //INICIA partir la arista inicial de bBox-----
//        Vertex c = new Vertex(interIzquierda.x, interIzquierda.y, null);
//        HalfEdge ac = new HalfEdge(null, null, null, null, null);
//        HalfEdge ca = new HalfEdge(null, null, null, null, null);
//        HalfEdge bc = new HalfEdge(null, null, null, null, null);
//        HalfEdge cb = new HalfEdge(null, null, null, null, null);
//
//        ac.origin = vBounding.get(0);
//        ac.prev = eb41;
//        ac.next = cb;
//        ac.twin = ca;
//        ac.face = bfu;
//
//        ca.origin = c;
//        ca.prev = bc;
//        ca.next = eb14;
//        ca.twin = ac;
//        ca.face = bf1;
//
//        bc.origin = vBounding.get(1);
//        bc.prev = eb32;
//        bc.next = ca;
//        bc.twin = cb;
//        bc.face = bf1;
//
//        cb.origin = c;
//        cb.prev = ac;
//        cb.next = eb23;
//        cb.twin = bc;
//        cb.face = bfu;
//
//        eb32.next = bc;
//        eb23.prev = cb;
//
//        eb41.next = ac;
//        eb14.prev = ca;
//        //TERMINA partir la arista inicial de bBox-----
//
//        //INICIA partir la cara -----------
//        System.out.println("");
//        System.out.println("caras de una arista");
//        System.out.println("");
//        ArrayList<HalfEdge> cara = dcel.recorerCara(ca);
//        Iterator iter = cara.iterator();
//        while (iter.hasNext()) {
//            HalfEdge next = (HalfEdge) iter.next();
//            System.out.println(next.face);
//            System.out.println("aritsta a probar ".toUpperCase() + next.toString());
//            if (dcel.intersectarAristas(next, e1020) != null) {
//                //crear vertices 
//                vBounding.add(dcel.intersectarAristas(next, e1020));
//                HalfEdge mitad1CCW = new HalfEdge(null, null, null, null, null);
//                HalfEdge mitad1CW = new HalfEdge(null, null, null, null, null);
//                HalfEdge mitad2CCW = new HalfEdge(null, null, null, null, null);
//                HalfEdge mitad2CW = new HalfEdge(null, null, null, null, null);
//                HalfEdge mc = new HalfEdge(null, null, null, null, null);
//                HalfEdge cm = new HalfEdge(null, null, null, null, null);
//
//                Face f3 = new Face(null, null);
//
//                mitad1CCW.origin = next.origin;
//                mitad1CCW.prev = next.prev;
//                mitad1CCW.next = mc;
//                mitad1CCW.twin = mitad1CW;
//                mitad1CCW.face = next.face;
//
//                System.out.println(next.face);
//                bf1.outer = mitad1CCW;//actualizamos por donde recorrer la cara 
//                mitad1CCW.face.outer = mitad1CCW;//actualizamos por donde recorrer la cara 
//
//                mitad1CW.origin = vBounding.get(vBounding.size() - 1); //el ultimo vertice que agregué
//                mitad1CW.prev = cm;
//                mitad1CW.next = next.twin.next;
//                mitad1CW.twin = mitad1CCW;
//                mitad1CW.face = next.twin.face;
//
//                mitad2CCW.origin = vBounding.get(vBounding.size() - 1); //el ultimo vertice que agregué
//                mitad2CCW.prev = cm;
//                mitad2CCW.next = next.next;
//                mitad2CCW.twin = mitad2CW;
//                mitad2CCW.face = f3;
//
//                f3.outer = mitad2CCW; //decimos por  donde inicia la nueva cara
//
//                mitad2CW.origin = next.twin.origin;
//                mitad2CW.prev = next.next.twin;
//                mitad2CW.next = mc;
//                mitad2CW.twin = mitad2CCW;
//                mitad2CW.face = next.twin.face;
//
//                mc.origin = vBounding.get(vBounding.size() - 1);
//                mc.prev = mitad1CCW;
//                mc.next = ca; // INFORMACION QUE TIENE QUE SER GUARDADA EN CADA ITERACION 
//                mc.twin = cm;
//                mc.face = mitad1CCW.face;
//
//                cm.origin = c;
//                cm.prev = bc;
//                cm.next = mitad2CCW; // INFORMACION QUE TIENE QUE SER GUARDADA EN CADA ITERACION 
//                cm.twin = mc;
//                cm.face = mitad2CCW.face;
//
//                //actualizar lo que hay atras y delante de next
//                next.prev.next = mitad1CCW;
//                next.prev.next.twin = mitad1CW;
//                next.next.prev = mitad2CCW;
//                next.next.prev.twin = mitad2CW;
//
//                bc.next = cm;
//                bc.next.twin = mc;
//                //dcel.recorerCara(mitad1CCW);
//                dcel.recorerCara(mitad2CCW);
//                break;
//            }
//        }
//        //TERMINA partir la cara ----------
    }

    public void partirArista(Vertex interseccion, HalfEdge aristaIntersecada, ArrayList<HalfEdge> edgeList, ArrayList<Vertex> vertexList) {
        //INICIA partir la arista inicial de bBox-----
        Vertex c = new Vertex(interseccion.x, interseccion.y, null);
        HalfEdge ac = new HalfEdge(null, null, null, null, null);
        HalfEdge ca = new HalfEdge(null, null, null, null, null);

        ac.origin = aristaIntersecada.twin.origin;
        ac.prev = aristaIntersecada.next.twin;
        ac.next = aristaIntersecada.twin;
        ac.twin = ca;
        ac.face = aristaIntersecada.twin.face;

        ca.origin = c;
        ca.prev = aristaIntersecada;
        ca.next = aristaIntersecada.next;
        ca.twin = ac;
        ca.face = aristaIntersecada.face;

        // es muy importante que acualizemos primero lo que hay en la segunda mitad de la arista
        //porque aun hace referencia los next del otro lado
        aristaIntersecada.next.twin.next = ac;
        aristaIntersecada.next.prev = ca;

        aristaIntersecada.origin = aristaIntersecada.origin;
        aristaIntersecada.prev = aristaIntersecada.prev;
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(edgeList.get(0).next);
        System.out.println(edgeList.get(1).next);
        System.out.println(edgeList.get(2).next);
        System.out.println(edgeList.get(3).next);
        System.out.println(edgeList.get(4).next);
        System.out.println(edgeList.get(5).next);
        System.out.println(edgeList.get(6).next);
        System.out.println(edgeList.get(7).next);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        aristaIntersecada.prev.next.next = ca;
        aristaIntersecada.next = ca;
        System.out.println(edgeList.get(0).next);
        System.out.println(edgeList.get(1).next);
        System.out.println(edgeList.get(2).next);
        System.out.println(edgeList.get(3).next);
        System.out.println(edgeList.get(4).next);
        System.out.println(edgeList.get(5).next);
        System.out.println(edgeList.get(6).next);
        System.out.println(edgeList.get(7).next);

        System.out.println("cccccccccccccccccccccccccccccccccccccccc");
        System.out.println(aristaIntersecada.next);
        aristaIntersecada.twin = aristaIntersecada.twin;
        aristaIntersecada.face = aristaIntersecada.face;

        aristaIntersecada.twin.origin = c;
        aristaIntersecada.twin.prev = ac;
        aristaIntersecada.twin.next = aristaIntersecada.twin.next;
        aristaIntersecada.twin.twin = aristaIntersecada;
        aristaIntersecada.twin.face = aristaIntersecada.twin.face;

        //es importante que estas referencias sean actualizadas despues de actualizar la arista 
        aristaIntersecada.prev.next = aristaIntersecada;
        aristaIntersecada.twin.next.prev = aristaIntersecada.twin;

        vertexList.add(c);
        edgeList.add(ac);
        edgeList.add(ca);
//        edgeList.add(bc);
//        edgeList.add(cb);
        //TERMINA partir la arista inicial de bBox-----
    }

    /**
     *
     * @param arreglo - lista de aristas
     * @param unbounded - cara no acotada
     * @return Lista de aristas en la frontera
     */
    public ArrayList<HalfEdge> recorrerFrontera(ArrayList<HalfEdge> arreglo, Face unbounded) {
        ArrayList<HalfEdge> faces = new ArrayList<HalfEdge>();
        HalfEdge edge = unbounded.inner.twin;
        HalfEdge iterador = edge;
        iterador = iterador.next;
        while (!iterador.equals(edge)) {
            if (edge.twin.face.equals(unbounded)) { //si su atista twin tiene a la no acotada a su izquierda entoces todo bien
                faces.add(new HalfEdge(iterador.origin, iterador.next, iterador.prev, iterador.twin, iterador.face));
                System.out.println(iterador.toString());
            } else { //si no tiene a la acotada se regresa por su twin una arista que si tiene la unbounded
                iterador = iterador.twin;
            }
            iterador = iterador.next;
        }
        if (edge.twin.face.equals(unbounded)) { //si su atista twin tiene a la no acotada a su izquierda entoces todo bien
            faces.add(new HalfEdge(iterador.origin, iterador.next, iterador.prev, iterador.twin, iterador.face));
            System.out.println(iterador.toString());
        } else { //si no tiene a la acotada se regresa por su twin una arista que si tiene la unbounded
            iterador = iterador.twin;
        }
        //System.out.println(iterador.toString());
        return faces;
    }

    public ArrayList<Object> intersectarLineaFronteraPorIzq(ArrayList<HalfEdge> frontera, HalfEdge linea) {
        Vertex interIzquierda = new Vertex(5, 0, null);
        HalfEdge aristaInterIzq = new HalfEdge(null, null, null, null, null);
        Iterator iter = frontera.iterator();
        while (iter.hasNext()) {
            HalfEdge next = (HalfEdge) iter.next();
            if (this.intersectarAristas(next, linea) != null) {
                //INICIA compara el más izquierdo
                if (this.intersectarAristas(next, linea).x < interIzquierda.x) {
                    interIzquierda = this.intersectarAristas(next, linea);
                    aristaInterIzq = next;
                }
                //TERMINA comparar mas izquierdo
                //System.out.print(next.origin.x + " " + next.origin.y);
                //System.out.println("  " + next.twin.origin.x + " " + next.twin.origin.y);
            }
        }
        System.out.println("izquierdo " + interIzquierda.x + " " + interIzquierda.y);
        System.out.println(aristaInterIzq);
        ArrayList<Object> resultado = new ArrayList<Object>();
        resultado.add(interIzquierda);
        resultado.add(aristaInterIzq);
        return resultado;
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
//        System.out.println(e1.toString());
//        System.out.println(e2.toString());
        float m1 = (e1.origin.y - e1.twin.origin.y) / (e1.origin.x - e1.twin.origin.x); //(y1-y2) / (x1-x2)
        float m2 = (e2.origin.y - e2.twin.origin.y) / (e2.origin.x - e2.twin.origin.x); //(y1-y2) / (x1-x2)
//        System.out.println("m1 " + m1);
//        System.out.println("m2 " + m2);
        //y - y1 = m(x-x1) => y mx - mx1 + y1
        float b1 = (-m1 * (e1.origin.x)) + e1.origin.y; //b1 de la forma y = xm1 + b1
        float b2 = (-m2 * (e2.origin.x)) + e2.origin.y; //b2 de la forma Y = xm2 + b2
//        System.out.println("b1 " + b1);
//        System.out.println("b2 " + b2);
        float x = (b2 - b1) / (m1 - m2);
        if (Double.isInfinite(m1)) {    //si esto pasa es vertical la linea
            x = e1.origin.x;
        }
        float y2 = (m2 * x) + b2;
//        System.out.println("x " + x);
//        System.out.println("y1 " + y2);
//        System.out.println("interseccion en " + x + " " + y2);
        if (Math.min(e1.origin.x, e1.twin.origin.x) <= x && x <= Math.max(e1.origin.x, e1.twin.origin.x)
                && Math.min(e2.origin.x, e2.twin.origin.x) <= x && x <= Math.max(e2.origin.x, e2.twin.origin.x)) {
            if (e1.origin.x == e1.twin.origin.x) {
//                System.out.println("necesita verificacion en y");
                if (Math.min(e1.origin.y, e1.twin.origin.y) <= y2 && y2 <= Math.max(e1.origin.y, e1.twin.origin.y)
                        && Math.min(e2.origin.y, e2.twin.origin.y) <= y2 && y2 <= Math.max(e2.origin.y, e2.twin.origin.y)) {
//                    System.out.println(ANSI_GREEN + "caso vertical - se intersecan" + ANSI_GREEN);
                    return new Vertex(x, y2, null);
                } else {
//                    System.out.println("caso vertical - no se intersecan");
                    return null;
                }
            }
//            System.out.println(ANSI_GREEN + "los segmentos se intersecan" + ANSI_GREEN);
            return new Vertex(x, y2, null);
        } else {
//            System.out.println("no se intersecan");
            return null;
        }
    }

    public void imprimirLista(ArrayList<HalfEdge> arreglo) {
        HalfEdge edge;
        for (int i = 0; i < arreglo.size(); i++) {
            edge = arreglo.get(i);
            //System.out.println(edge.toString());
            //System.out.println(edge.face);
            this.imprimirAristaDetallado(edge);
        }
    }

    public void imprimirAristaDetallado(HalfEdge edge) {
        System.out.println("Datos de :" + edge + "{");
        System.out.println("\t next" + edge.next);
        System.out.println("\t origin " + edge.origin.x + " " + edge.origin.y);
        System.out.println("\t prev" + edge.prev);
        System.out.println("\t twin" + edge.twin);
        System.out.println("}");
    }
}
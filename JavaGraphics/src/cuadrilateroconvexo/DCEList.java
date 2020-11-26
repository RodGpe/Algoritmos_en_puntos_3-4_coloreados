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
public class DCEList {

    public static final String ANSI_GREEN = "\u001B[32m";
    float masALaDerecha; //se declara de clase porque se accede en varios metodos

    public static void main(String[] args) {

        DCEList dcel2 = new DCEList();
        ArrayList<Vertex> vertexList2 = new ArrayList<Vertex>();
        ArrayList<HalfEdge> edgeList2 = new ArrayList<HalfEdge>();
        ArrayList<Face> faceList2 = new ArrayList<Face>();
        dcel2.crearBoundingBox(4, -4, 4, -4, edgeList2, vertexList2, faceList2);
        //HalfEdge linea2 = dcel2.crearArista(-5, 5, 7, -4);
        Linea linea2 = dcel2.crearLinea(-5, 5, 7, -4);
        dcel2.agregarLineaArreglo(edgeList2, vertexList2, faceList2, linea2);
        Linea linea3 = dcel2.crearLinea(-8, -3, 6, 1);
        dcel2.agregarLineaArreglo(edgeList2, vertexList2, faceList2, linea3);

    }

    public void crearBoundingBox(float extDer, float extIzq, float extSup, float extInf, ArrayList<HalfEdge> edgeList, ArrayList<Vertex> vertexList, ArrayList<Face> faceList) {

        masALaDerecha = extDer; //se actualiza aqui porque la bounding box tiene el punto más a la izquierda
        vertexList.add(new Vertex(extIzq, extSup, null));

        vertexList.add(new Vertex(extDer, extSup, null));

        vertexList.add(new Vertex(extDer, extInf, null));

        vertexList.add(new Vertex(extIzq, extInf, null)); //3

        edgeList.add(new HalfEdge(null, null, null, null, null));//0 indice arista SENTIDO HORARIO (CW)

        edgeList.add(new HalfEdge(null, null, null, null, null));//1  SENTIDO ANTIHORARIO (CCW)

        edgeList.add(new HalfEdge(null, null, null, null, null));//2

        edgeList.add(new HalfEdge(null, null, null, null, null));//3

        edgeList.add(new HalfEdge(null, null, null, null, null));//4

        edgeList.add(new HalfEdge(null, null, null, null, null));//5

        edgeList.add(new HalfEdge(null, null, null, null, null));//6

        edgeList.add(new HalfEdge(null, null, null, null, null));//7

        faceList.add(new Face(edgeList.get(1), null)); //  0  indice cara

        faceList.add(new Face(null, edgeList.get(0))); // 1  CARA NO ACOTADA
        Face unBounded = faceList.get(1);

        edgeList.get(0).origin = vertexList.get(0);
        edgeList.get(0).prev = edgeList.get(6);
        edgeList.get(0).next = edgeList.get(2);
        edgeList.get(0).twin = edgeList.get(1);
        edgeList.get(0).face = faceList.get(1);

        edgeList.get(1).origin = vertexList.get(1);
        edgeList.get(1).prev = edgeList.get(3);
        edgeList.get(1).next = edgeList.get(7);
        edgeList.get(1).twin = edgeList.get(0);
        edgeList.get(1).face = faceList.get(0);

        edgeList.get(2).origin = vertexList.get(1);
        edgeList.get(2).prev = edgeList.get(0);
        edgeList.get(2).next = edgeList.get(4);
        edgeList.get(2).twin = edgeList.get(3);
        edgeList.get(2).face = faceList.get(1);

        edgeList.get(3).origin = vertexList.get(2);
        edgeList.get(3).prev = edgeList.get(5);
        edgeList.get(3).next = edgeList.get(1);
        edgeList.get(3).twin = edgeList.get(2);
        edgeList.get(3).face = faceList.get(0);

        edgeList.get(4).origin = vertexList.get(2);
        edgeList.get(4).prev = edgeList.get(2);
        edgeList.get(4).next = edgeList.get(6);
        edgeList.get(4).twin = edgeList.get(5);
        edgeList.get(4).face = faceList.get(1);

        edgeList.get(5).origin = vertexList.get(3);
        edgeList.get(5).prev = edgeList.get(7);
        edgeList.get(5).next = edgeList.get(3);
        edgeList.get(5).twin = edgeList.get(4);
        edgeList.get(5).face = faceList.get(0);

        edgeList.get(6).origin = vertexList.get(3);
        edgeList.get(6).prev = edgeList.get(4);
        edgeList.get(6).next = edgeList.get(0);
        edgeList.get(6).twin = edgeList.get(7);
        edgeList.get(6).face = faceList.get(1);

        edgeList.get(7).origin = vertexList.get(0);
        edgeList.get(7).prev = edgeList.get(1);
        edgeList.get(7).next = edgeList.get(5);
        edgeList.get(7).twin = edgeList.get(6);
        edgeList.get(7).face = faceList.get(0);

    }

    /**
     * Recorre la cara externa en sentido horario
     *
     * @param arreglo - lista de aristas
     * @param unbounded - cara no acotada
     * @return Lista de aristas en la frontera en sentido horario
     */
    public ArrayList<HalfEdge> recorrerFrontera(ArrayList<HalfEdge> arreglo, Face unbounded) {
        ArrayList<HalfEdge> faces = new ArrayList<HalfEdge>();
        HalfEdge edge = unbounded.inner;
        HalfEdge iterador = edge;
        faces.add(iterador);
        iterador = iterador.next;
        while (!iterador.equals(edge)) {
            faces.add(iterador);
            iterador = iterador.next;
        }
        return faces;
    }

    /**
     *
     * @param frontera
     * @param linea
     * @return una matriz de objetos primero [0] objeto es un Vertice y el
     * segundo [1] la arista intersecad
     */
    public ArrayList<Object> intersectarLineaFronteraPorIzq(ArrayList<HalfEdge> frontera, HalfEdge linea) {
        //Vertex interIzquierda = new Vertex(5, 0, null); //tiene que tener un valor en x mayor que todos los puntos del arreglo
        Vertex interIzquierda = new Vertex(masALaDerecha, 0, null); //tiene que tener un valor en x mayor que todos los puntos del arreglo
        HalfEdge aristaInterIzq = null;// new HalfEdge(null, null, null, null, null);
        Iterator iter = frontera.iterator();
        while (iter.hasNext()) {
            HalfEdge next = (HalfEdge) iter.next();
            System.out.println("parte de la frontera " + next);
            if (this.intersectarAristas(next, linea) != null) {
                //INICIA compara el más izquierdo
                if (this.intersectarAristas(next, linea).x < interIzquierda.x) {
                    interIzquierda = this.intersectarAristas(next, linea);
                    aristaInterIzq = next;
                }
                //TERMINA comparar mas izquierdo
            }
        }
        System.out.println("izquierdo " + interIzquierda.x + " " + interIzquierda.y);
        System.out.println("arista intersecada del bounding" + aristaInterIzq);
        ArrayList<Object> resultado = new ArrayList<Object>();
        resultado.add(interIzquierda);
        resultado.add(aristaInterIzq);
        return resultado;
    }

    public void listarAristas(HalfEdge buscarAristas) {
        HalfEdge aristaInicial = buscarAristas;
        HalfEdge bucarAristas = aristaInicial;
        while (!bucarAristas.twin.next.equals(aristaInicial)) {
            bucarAristas = bucarAristas.twin.next;
        }
    }

    /**
     *
     * @param edge
     * @return Una lista con las aristas de la cara menos edge. Se revorre en
     * CCW
     */
    public ArrayList<HalfEdge> recorerCara(HalfEdge edge) {
        ArrayList<HalfEdge> face = new ArrayList<HalfEdge>();
        HalfEdge iterador = edge;
        iterador = iterador.next;
        while (!iterador.equals(edge)) {
            face.add(iterador);
            iterador = iterador.next;
        }
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
        float m1 = (e1.origin.y - e1.twin.origin.y) / (e1.origin.x - e1.twin.origin.x); //(y1-y2) / (x1-x2)
        float m2 = (e2.origin.y - e2.twin.origin.y) / (e2.origin.x - e2.twin.origin.x); //(y1-y2) / (x1-x2)
        //y - y1 = m(x-x1) => y mx - mx1 + y1
        float b1 = (-m1 * (e1.origin.x)) + e1.origin.y; //b1 de la forma y = xm1 + b1
        float b2 = (-m2 * (e2.origin.x)) + e2.origin.y; //b2 de la forma Y = xm2 + b2

        float x = (b2 - b1) / (m1 - m2);
        if (Double.isInfinite(m1)) {    //si esto pasa es vertical la linea
            x = e1.origin.x;
        }
        float y2 = (m2 * x) + b2;
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
        System.out.println("\t face" + edge.face);
        System.out.println("\t line" + edge.line);
        System.out.println("}");
    }

    public ArrayList<Object> buscarSiguienteInterseccion(HalfEdge edgeInicial, HalfEdge linea) {
        ArrayList<HalfEdge> cara = this.recorerCara(edgeInicial);
        for (HalfEdge next : cara) {
            if (this.intersectarAristas(next, linea) != null) {
                System.out.print("la sig interseccion es en " + this.intersectarAristas(next, linea).x + "  ");
                System.out.println(this.intersectarAristas(next, linea).y);
                System.out.println("sig arista intersecada " + next);
                ArrayList<Object> resultados = new ArrayList<Object>();
                resultados.add(intersectarAristas(next, linea));
                resultados.add(next);
                return resultados;
            }
        }
        return null;
    }

    /**
     * a------c(interseccion))------b(aristaintersecada origin)
     *
     * @param interseccion
     * @param aristaIntersecada Puede ser la arista o su .twin el resultado no
     * cambia
     * @param edgeList
     * @param vertexList
     * @param unbounded
     * @return HalfEdge La primer mitad de arista intersecada, i.e., la que
     * mantiene sin cambios el atributo .origin
     */
    public HalfEdge partirArista(Vertex interseccion, HalfEdge aristaIntersecada, ArrayList<HalfEdge> edgeList, ArrayList<Vertex> vertexList, Face unbounded) {
        //INICIA partir la arista inicial de bBox-----
        Vertex c = new Vertex(interseccion.x, interseccion.y, null);
        HalfEdge ac = new HalfEdge(null, null, null, null, null);
        HalfEdge ca = new HalfEdge(null, null, null, null, null);
        vertexList.add(c);
        edgeList.add(ac); //CW
        edgeList.add(ca); //CCW

        if (aristaIntersecada.face.equals(unbounded)) {
            aristaIntersecada = aristaIntersecada.twin;
        }
        //aristaIntersecada = aristaIntersecada.prev.next;// SUPER IMPORTANTE HACERSE "AUTO-REFERENCIA"
        //porque arista intersecada fue creada como un nuevo objeto en otro metodo
        //entoces para que sea la misma nos referimo primedio a su prev y luego a su next
        //para que entonces sí sea el objeto  arista original

        ac.origin = aristaIntersecada.twin.origin;
        ac.prev = aristaIntersecada.twin.prev;
        //ac.prev = prevAC;
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
        aristaIntersecada.twin.prev.next = ac;
        aristaIntersecada.next.prev = ca;

        aristaIntersecada.origin = aristaIntersecada.origin;
        aristaIntersecada.prev = aristaIntersecada.prev;
        aristaIntersecada.next = ca;
        aristaIntersecada.twin = aristaIntersecada.twin;
        aristaIntersecada.face = aristaIntersecada.face;

        aristaIntersecada.twin.origin = c;
        aristaIntersecada.twin.prev = ac;
        aristaIntersecada.twin.next = aristaIntersecada.twin.next;
        aristaIntersecada.twin.twin = aristaIntersecada;
        aristaIntersecada.twin.face = aristaIntersecada.twin.face;

        //es importante que estas referencias sean actualizadas despues de actualizar la arista AUN TENGO DUDAS DE ESTO
        //aristaIntersecada.prev.next = aristaIntersecada;
        //aristaIntersecada.twin.next.prev = aristaIntersecada.twin;
//        vertexList.add(c);
//        edgeList.add(ac); //CW
//        edgeList.add(ca); //CCW
//        edgeList.add(bc);
//        edgeList.add(cb);
        //falta actulizar el ultimo que apunta a la segunda mitad partida
        //TERMINA partir la arista inicial de bBox-----
        return aristaIntersecada;
    }

    /**
     *
     * @param primeraInterseccion
     * @param segundaInterseccion
     * @param edgeList
     * @param faceList
     * @return regresa la half edge que parte la cara (la half edge va de
     * izquierda a derecha)
     */
    public HalfEdge partirCara(HalfEdge primeraInterseccion, HalfEdge segundaInterseccion, ArrayList<HalfEdge> edgeList, ArrayList<Face> faceList) {
        //primeraInterseccion = primeraInterseccion.prev.next; //para apuntar al objeto original
        //segundaInterseccion = segundaInterseccion.prev.next; //para apuntar al objeto original
        HalfEdge corte = new HalfEdge(null, null, null, null, null);
        HalfEdge corteInv = new HalfEdge(null, null, null, null, null);
        Face caraNueva = new Face(corte, null);
        edgeList.add(corte);
        edgeList.add(corteInv);
        faceList.add(caraNueva);
        corte.origin = primeraInterseccion.next.origin;
        corte.prev = primeraInterseccion;
        corte.next = segundaInterseccion.next;
        corte.twin = corteInv;
        corte.face = caraNueva;

        corteInv.origin = segundaInterseccion.next.origin;
        corteInv.prev = segundaInterseccion;
        corteInv.next = primeraInterseccion.next;
        corteInv.twin = corte;
        corteInv.face = segundaInterseccion.face;

//        primeraInterseccion.next.prev = corte;
        primeraInterseccion.next.prev = corteInv;
        primeraInterseccion.next = corte;

        segundaInterseccion.next.prev = corte;
        segundaInterseccion.next = corteInv;

        segundaInterseccion.face.outer = corteInv; //SE DEBE ACTUALIZAR LA CARA PARA GANTIZAR QUE VA A RECORRER LA CARA CORRECTA
        //actualizar el ultimo vertice que apuntaba a la arista partida

        //falta actualizar .face de las aristas de la cara
        ArrayList<HalfEdge> carasActualizar;
        carasActualizar = this.recorerCara(corte); //la primer cara
        for (HalfEdge next : carasActualizar) {
            next.face = corte.face;
        }
        carasActualizar = null;
        carasActualizar = this.recorerCara(corteInv); //la segunda cara
        for (HalfEdge next : carasActualizar) {
            //System.out.println("caraActualizar " + next);
            next.face = corteInv.face;
        }
        return corte;
    }

//    public HalfEdge crearArista(float x1, float y1, float x2, float y2) {
//        Vertex v10 = new Vertex(x1, y1, null);
//        Vertex v20 = new Vertex(x2, y2, null);
//        HalfEdge linea = new HalfEdge(null, null, null, null, null);
//        HalfEdge e2010 = new HalfEdge(null, null, null, null, null);
//        linea.origin = v10;
//        linea.next = e2010;
//        linea.prev = e2010;
//        linea.twin = e2010;
//
//        e2010.origin = v20;
//        e2010.next = linea;
//        e2010.prev = linea;
//        e2010.twin = linea;
//        return linea;
//    }
    public Linea crearLinea(float x1, float y1, float x2, float y2) {
        Vertex v10 = new Vertex(x1, y1, null);
        Vertex v20 = new Vertex(x2, y2, null);
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
        Linea recta = new Linea(v10, e2010, e2010, e2010, null, linea);
        //recta.primerArista = linea;
        return recta;
    }

    public HalfEdge agregarLineaArreglo(ArrayList<HalfEdge> edgeList, ArrayList<Vertex> vertexList, ArrayList<Face> faceList, Linea linea) {
        boolean guardarPrimer = true;
        Face unBounded = faceList.get(1);
        HalfEdge primer = null;
        System.out.println("buscando frontera " + unBounded.inner);
        ArrayList<HalfEdge> frontera = this.recorrerFrontera(edgeList, unBounded); //indice 1 de cara porque es la no acotada e indice 1 de arista porque es la CCW
        ArrayList<Object> resultado = this.intersectarLineaFronteraPorIzq(frontera, linea);
        Vertex interseccion = (Vertex) resultado.get(0);
        HalfEdge aristaInterseccion = (HalfEdge) resultado.get(1);
        aristaInterseccion = this.partirArista(interseccion, aristaInterseccion, edgeList, vertexList, unBounded); //la asigno a la variable para tenerlo actualizado
        ArrayList<Object> resultadoNuevo;//= dcel.buscarSiguienteInterseccion(aristaInterseccion.next, e1020); //como parti la arista anterior entonces su punto next sera parte de la otra cara
        Vertex interseccionNueva;//= (Vertex) resultadoNuevo.get(0);
        HalfEdge aristaInterseccionNueva;//= (HalfEdge) resultadoNuevo.get(1);
        while (true) {
            resultadoNuevo = this.buscarSiguienteInterseccion(aristaInterseccion.next, linea);
            interseccionNueva = (Vertex) resultadoNuevo.get(0);
            aristaInterseccionNueva = (HalfEdge) resultadoNuevo.get(1);
            aristaInterseccionNueva = this.partirArista(interseccionNueva, aristaInterseccionNueva, edgeList, vertexList, unBounded);
            this.partirCara(aristaInterseccion, aristaInterseccionNueva, edgeList, faceList);
            aristaInterseccion.next.line = linea.regresarObjeto();          //codigo para
            aristaInterseccion.next.twin.line = linea.regresarObjeto();     //asignar la linea a la arista 
            //....................para actualizar las aristas que fueron partidas
            aristaInterseccion.next.twin.next.line = aristaInterseccion.line;
            aristaInterseccion.next.twin.next.twin.line = aristaInterseccion.line;
            aristaInterseccionNueva.next.twin.next.line = aristaInterseccionNueva.line;
            aristaInterseccionNueva.next.twin.next.twin.line = aristaInterseccionNueva.line;
            //---------la siguiente cadena de if es para manejar el caso donde se intersecan una primerArista con la linea 
            //if (linea.puntoPrimal.x > 0) {
            //System.out.println(ANSI_GREEN+"si es positivo"+ANSI_GREEN);
            if (aristaInterseccionNueva.twin.line != null) {
                //System.out.println(ANSI_GREEN+"line no es null"+ANSI_GREEN);
                //if (aristaInterseccionNueva.twin.line.primerArista != null) {
                if (aristaInterseccionNueva.line.primerArista == aristaInterseccionNueva.twin) {
                    //System.out.println(ANSI_GREEN+"primer arista no es null"+ANSI_GREEN);
                    //System.out.println(ANSI_GREEN+aristaInterseccionNueva.line.primerArista+ANSI_GREEN);
                    //System.out.println(ANSI_GREEN+aristaInterseccionNueva.next.twin.next.twin+ANSI_GREEN);
                    aristaInterseccionNueva.twin.line.primerArista = aristaInterseccionNueva.next.twin.next.twin;
                    //System.out.println(ANSI_GREEN+aristaInterseccionNueva.line.primerArista+ANSI_GREEN);
                }
            }
            //}
            //....................
            System.out.println(linea.puntoPrimal);
            if (guardarPrimer) {        //para guardar el primer segmento de la linea
                //linea.primerArista = aristaInterseccion.next;   //en este momento aristaIterseccion.next es la arista de corte
                guardarPrimer = false;
                primer = aristaInterseccion.next;
            }
            if (aristaInterseccionNueva.twin.face == unBounded) {
                break;
            }
            aristaInterseccion = aristaInterseccionNueva.twin.prev;
            aristaInterseccion = aristaInterseccionNueva.next.twin.next.twin;
        }
        System.out.println("termine de agregar linea------------------------------");
        System.out.println("primer arista de la linea es " + linea.primerArista + "-------------");
        return primer;
    }

    /**
     * Toma un HalfEdge de una linea y regresa la siguiente HalfEdge sobre la misma linea
     * @param edge
     * @return 
     */
    public HalfEdge nextEdge(HalfEdge edge) {
        return edge.next.twin.next;
    }

    /**
     * Recibe la primer arista (interseca bounding box por la izquierda) y
     * devuelve uns lista de las aristas que conforman la linea
     *
     * @param primera primer arista (por la izquierda)
     * @param unbounded es la cara no acotada
     * @return
     */
    public ArrayList<HalfEdge> recorrerLinea(HalfEdge primera, Face unbounded) {
        ArrayList<HalfEdge> segmentos = new ArrayList<HalfEdge>();
        HalfEdge iterador = primera;
        while (iterador.next.twin.face != unbounded) {
            segmentos.add(iterador);
            iterador = nextEdge(iterador);
        }
        segmentos.add(iterador);
        return segmentos;
    }

    /**
     * 
     * @param primera Es la primer HalfEdge de una linea, dual del punto respeto al cual se obtiene el orden,
     * que interseca a la bounding box
     * @param unbounded Es la cara no acotada
     * @return Una lista de HalfEdges que representa el orden angular respecto al punto primal
     */
    public ArrayList<HalfEdge> obtenerOrden(HalfEdge primera, Face unbounded) {
        ArrayList<HalfEdge> segmentos = new ArrayList<HalfEdge>();
        ArrayList<HalfEdge> orden = new ArrayList<HalfEdge>();
        segmentos = this.recorrerLinea(primera, unbounded);
        for (HalfEdge segmento : segmentos) {
            if (segmento.next.twin.face != unbounded) {
                if (segmento.next.line.puntoPrimal.x > primera.line.puntoPrimal.x) {
                    //System.out.println(segmento.next.twin);
                    //System.out.println(segmento.next.twin.line);
                    orden.add(segmento.next);
                }
            }
        }
        for (HalfEdge segmento : segmentos) {
            if (segmento.next.twin.face != unbounded) {
                if (segmento.next.line.puntoPrimal.x < primera.line.puntoPrimal.x) {
                    orden.add(segmento.next);
                }
            }
        }
        return orden;
    }
}

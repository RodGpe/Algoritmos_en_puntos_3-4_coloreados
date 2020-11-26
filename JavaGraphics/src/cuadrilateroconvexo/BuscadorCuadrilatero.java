/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuadrilateroconvexo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

/**
 * Color1 es el color del convexo a la izquierda. color2 es el color del convexo
 * a la derecha color3 es el que buscamos por encima de la recta de soporte
 *
 * @author Rodrigo
 */
public class BuscadorCuadrilatero {

    int cardinalidadColor1;
    int cardinalidadColor2;
    PuntoInfoConvex ultimoInsertadoColor1;
    PuntoInfoConvex ultimoInsertadoColor2;
    PuntoInfoConvex inicioBusquedaColor1;
    PuntoInfoConvex inicioBusquedaColor2;
    PuntoInfoConvex referencia; //los demás puntos estan ordenados con respecto a este
    Color[] coloresDisponibles = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA};
    ArrayList<Color> coloresUtiles = new ArrayList<>();
    ArrayList<ArrayList<Color>> permutaciones = new ArrayList();
    SegmentoSoporte soporte = null;
    Cuadrilatero convexo = null;
    //Triangulo minimo = null;

    /**
     * Función driver de BuscadorCuadrilatero
     *
     * @param args
     */
    public static void main(String[] args) {
        BuscadorCuadrilatero buscador = new BuscadorCuadrilatero();
        buscador.referencia = new PuntoInfoConvex(0, 0, Color.magenta);
        ArrayList<PuntoInfoConvex> puntos = buscador.crearOrden();
        Color color1 = Color.blue; //convexo izquirdo
        Color color2 = Color.red; //convexo derecho
        Color color3 = Color.green; //punto por encima de la recta de soporte
        buscador.crearConvexoColor2(puntos, color2);
        for (PuntoInfoConvex p : puntos) {
            if (p.color.equals(color1)) {
                buscador.agregarPuntoColor1(p);
            } else if (p.color.equals(color2)) {
                buscador.eliminarPuntoColor2(p);
            } else if (p.color.equals(color3)) { //entonces el punto es de color3
                //si existe recta de soporte y si el punto esta arriba de esa recta
                if (buscador.soporte != null) {
                    if (buscador.esGiroIzquierdo(buscador.soporte.color1, buscador.soporte.color2, p)) { //giro a la izquierda
                        buscador.convexo = new Cuadrilatero(buscador.soporte.color1, p, buscador.soporte.color2, buscador.referencia);
                        System.out.println("encontre un cuadrilatero");
                        System.exit(0);
                    }
                }
                //return convexo;
            }

        }

    }

    /**
     * Función principal, busca un cuadrilatero heterocromatico convexo
     *
     * @param linea que ya contenga la información de la ordenación angular de
     * los puntos
     * @return
     */
    public Cuadrilatero buscarCuadrilatero(Linea linea) {
        referencia = (PuntoInfoConvex) linea.puntoPrimal;
        eliminarColor(linea); //no se considera el color para las permutaciones
        PermutadorColores permutador = new PermutadorColores();
        permutaciones = permutador.permute(new Color[]{coloresUtiles.get(0), coloresUtiles.get(1), coloresUtiles.get(2)}); // las guarda en el atributo de clase permutaciones
        ArrayList<PuntoInfoConvex> puntosOrdenados = convertirPuntos(linea);
        for (ArrayList<Color> permutacione : permutaciones) { //para buscar en todas las permutaciones de colres
            Color color1 = permutacione.get(0); //convexo izquirdo
            Color color2 = permutacione.get(1); //convexo derecho
            Color color3 = permutacione.get(2); //punto por encima de la recta de soporte
            crearConvexoColor2(puntosOrdenados, color2); //el algoritmo primere construye el convexo del lado derecho
            for (PuntoInfoConvex p : puntosOrdenados) {
                if (p.color.equals(color1)) {
                    agregarPuntoColor1(p);
                } else if (p.color.equals(color2)) {
                    eliminarPuntoColor2(p);
                } else if (p.color.equals(color3)) { //entonces el punto es de color3
                    //si existe recta de soporte y si el punto esta arriba de esa recta
                    if (soporte != null && cardinalidadColor2 != 0) {
                        //if (area2(soporte.color1, soporte.color2, p) > 0) { //giro a la izquierda con respecto a la recta de soporte
                        if (esGiroIzquierdo(soporte.color1, soporte.color2, p)) { //giro a la izquierda con respecto a la recta de soporte
                            convexo = new Cuadrilatero(soporte.color1, p, soporte.color2, referencia); //creamos el convexo y lo retornamos
                            return convexo;
                        }
                    }
                    //return convexo;
                }

            }
//            buscarYMIN(ultimoInsertado); //
//            reverseOrder(ordenPoli, color1, color2);
            limpiarClase(); //reiniciamos las variables para buscar el convexo en otra permutación
        }
        return convexo;
    }

    //< 0) { //mientras el giro sea a la derecha
    //> 0) { //mientras el giro sea a la izquierda
    public void buscarPrimerSoporte() {
        if (cardinalidadColor2 == 1) {
            soporte = new SegmentoSoporte(ultimoInsertadoColor1, inicioBusquedaColor2);
            return;
        }
        PuntoInfoConvex a = ultimoInsertadoColor1;
        PuntoInfoConvex b = inicioBusquedaColor2;
        PuntoInfoConvex c = inicioBusquedaColor2.next.peek();
        PuntoInfoConvex d = inicioBusquedaColor2.prev.peek();
        while (!((esGiroIzquierdo(a, b, c)) && esGiroIzquierdo(a, b, d))) {
            d = b;
            b = c;
            c = c.next.peek();
        }
        soporte = new SegmentoSoporte(a, b);
    }

    public void crearConvexoColor2(ArrayList<PuntoInfoConvex> puntos, Color color2) {
        Collections.reverse(puntos); //se crea en sentido antihorario
        cardinalidadColor2 = 0;
        for (PuntoInfoConvex p : puntos) {
            if (p.color.equals(color2)) {
                //if (p.color.equals(Color.blue)) {
                switch (cardinalidadColor2) {
                    case 0:
                        ultimoInsertadoColor2 = p;
                        cardinalidadColor2 = 1;
                        break;
                    case 1:
                        ultimoInsertadoColor2.next.push(p);
                        ultimoInsertadoColor2.prev.push(p);
                        p.next.push(ultimoInsertadoColor2);
                        p.prev.push(ultimoInsertadoColor2);
                        ultimoInsertadoColor2 = p;
                        cardinalidadColor2 = 2;
                        break;
                    default:
                        agregarPuntoColor2(p);
                        break;
                }
                inicioBusquedaColor2 = ultimoInsertadoColor2;
            }
        }
        Collections.reverse(puntos); //se regresa al orden original
    }

    public void agregarPuntoColor2(PuntoInfoConvex p) {
        PuntoInfoConvex prevAux;
        PuntoInfoConvex nextAux;

        prevAux = buscarPrevColor2(p);
        nextAux = buscarNextColor2(p);

        p.prev.push(prevAux);
        p.prev.peek().next.push(p);
        //p.next.push(buscarNext(p));
        p.next.push(nextAux);
        p.next.peek().prev.push(p);
        ultimoInsertadoColor2 = p;
        cardinalidadColor2 = cardinalidadColor2 + 1;
    }

    public void agregarPuntoColor1(PuntoInfoConvex p) {
        switch (cardinalidadColor1) {
            case 0:
                ultimoInsertadoColor1 = p;
                cardinalidadColor1 = 1;
                break;
            case 1:
                ultimoInsertadoColor1.next.push(p);
                ultimoInsertadoColor1.prev.push(p);
                p.next.push(ultimoInsertadoColor1);
                p.prev.push(ultimoInsertadoColor1);
                ultimoInsertadoColor1 = p;
                cardinalidadColor1 = 2;
                break;
            default:
                PuntoInfoConvex prevAux;
                PuntoInfoConvex nextAux;
                prevAux = buscarPrevColor1(p);
                nextAux = buscarNextColor1(p);
                p.prev.push(prevAux);
                p.prev.peek().next.push(p);
                //p.next.push(buscarNext(p));
                p.next.push(nextAux);
                p.next.peek().prev.push(p);
                ultimoInsertadoColor1 = p;
                cardinalidadColor1 = cardinalidadColor1 + 1;
                break;
        }
        //--------verificacion de update de segmento soporte-------------
        if (soporte != null) { // && p esta debajo de soporte
            if (cardinalidadColor2 != 0) {
                if (esGiroDerecho(soporte.color1, soporte.color2, p)) { // giro sea a la derecha
                    soporte.color1 = ultimoInsertadoColor1; //por lo tanto el punto azul actualiza la recta.color1 de soporte
                    //actualizo recta de soporte
                    if (cardinalidadColor2 != 1) {
                        PuntoInfoConvex a = soporte.color1;
                        PuntoInfoConvex b = soporte.color2;
                        PuntoInfoConvex c = soporte.color2.next.peek();
                        while (esGiroDerecho(a, b, c)) { //giro derecha
                            b = c;
                            c = c.next.peek();
                        }
                        soporte.color2 = b;
                    } else {
                        soporte.color2 = soporte.color2;
                    }
                }
            }
        } else if (soporte == null && (cardinalidadColor2 != 0)) {
            buscarPrimerSoporte();//buscar primer segmento soporte
        }
        //si este punto esta por debajo de la recta de soporte
    }

    public void eliminarPuntoColor2(PuntoInfoConvex p) {
        //System.out.println("cardinalidadC2 " + cardinalidadColor2);
        if (soporte != null) {
            if (cardinalidadColor2 == 1) {
                //soporte.color2 = null;
                cardinalidadColor2 = 0;
                return;
            }
            PuntoInfoConvex aux = soporte.color2.prev.peek();
            p.prev.peek().next.pop();// boto su next para tener el cierre actualizado
            p.next.peek().prev.pop();
            if (p == soporte.color2) { //si el punto que elimino es parte de la recta de soporte entoces debo actualizar la recta de 
                if (cardinalidadColor2 == 2) {
                    soporte.color2 = aux;
                    cardinalidadColor2 = cardinalidadColor2 - 1;
                    return;
                }
                //aqui arreglamos color 2
                PuntoInfoConvex a = soporte.color1; //porque es parte del segmento soporte
                PuntoInfoConvex b = aux;
                PuntoInfoConvex c = aux.next.peek();
                while (esGiroDerecho(a, b, c)) { //mientras el giro sea a la derecha      
                    b = c;
                    c = c.next.peek();
                }
                soporte.color2 = b;
                if (cardinalidadColor1 == 1) {
                    //no hacemos nada porque no hace falta reparar
                } else {
                    while (!((esGiroIzquierdo(soporte.color1, soporte.color2, soporte.color1.next.peek()))
                            && (esGiroIzquierdo(soporte.color1, soporte.color2, soporte.color2.next.peek())))) {     //> 0) { //mientras el giro sea a la izquierda
                        //AHORA ARREGLAMOS EL COLOR1
                        a = soporte.color2;
                        b = soporte.color1;
                        c = soporte.color1.next.peek();
                        while (esGiroIzquierdo(a, b, c)) { //mientras el giro sea a la izquierda              
                            b = c;
                            c = c.next.peek();
                        }
                        soporte.color1 = b;

                        //ARREGLAMOS EL COLOR2
                        a = soporte.color1;
                        b = soporte.color2;
                        c = soporte.color2.next.peek();
                        while (esGiroDerecho(a, b, c)) { //mientras el giro sea a la derecha      
                            b = c;
                            c = c.next.peek();
                        }
                        soporte.color2 = b;
                    }
                }

                //inicioBusquedaColor2 = p.prev;
            }
        } else {
            if (cardinalidadColor2 == 1) {
                //soporte.color2 = null;
                cardinalidadColor2 = 0;
                return;
            }
            p.prev.peek().next.pop();// boto su next para tener el cierre actualizado
            p.next.peek().prev.pop();
        }
        cardinalidadColor2 = cardinalidadColor2 - 1;
        if (cardinalidadColor2 == 0) {
            return;
        }
        inicioBusquedaColor2 = p.prev.peek();
    }

    public PuntoInfoConvex buscarPrevColor2(PuntoInfoConvex p) {
        PuntoInfoConvex a, b, c;
        a = p;
        b = ultimoInsertadoColor2;
        c = ultimoInsertadoColor2.prev.peek();
        while (esGiroIzquierdo(a, b, c)) { //mientras el giro sea a la izquierda
            b = c;
            c = c.prev.peek();
        }
        return b;
    }

    public PuntoInfoConvex buscarNextColor2(PuntoInfoConvex p) {
        PuntoInfoConvex a, b, c;
        a = p;
        b = ultimoInsertadoColor2;
        c = ultimoInsertadoColor2.next.peek();
        while (esGiroDerecho(a, b, c)) { //mientras el giro sea a la derecha
            b = c;
            c = c.next.peek();
        }
        return b;
    }

    public PuntoInfoConvex buscarPrevColor1(PuntoInfoConvex p) {
        PuntoInfoConvex a, b, c;
        a = p;
        b = ultimoInsertadoColor1;
        c = ultimoInsertadoColor1.prev.peek();
        while (esGiroIzquierdo(a, b, c)) { //mientras el giro sea a la izquierda
            b = c;
            c = c.prev.peek();
        }
        return b;
    }

    public PuntoInfoConvex buscarNextColor1(PuntoInfoConvex p) {
        PuntoInfoConvex a, b, c;
        a = p;
        b = ultimoInsertadoColor1;
        c = ultimoInsertadoColor1.next.peek();
        while (esGiroDerecho(a, b, c)) { //mientras el giro sea a la derecha
            b = c;
            c = c.next.peek();
        }
        return b;
    }

    public float area2(PuntoInfoConvex a, PuntoInfoConvex b, PuntoInfoConvex c) {
        float area2 = 0;
        area2 = (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
        return area2;
    }

    public boolean esGiroIzquierdo(PuntoInfoConvex a, PuntoInfoConvex b, PuntoInfoConvex c) {
        return area2(a, b, c) > 0; //es giro izquierdo
    }

    public boolean esGiroDerecho(PuntoInfoConvex a, PuntoInfoConvex b, PuntoInfoConvex c) {
        return area2(a, b, c) < 0; //es giro derecho
    }

    public void eliminarColor(Linea linea) {
        for (Color coloresDisponible : coloresDisponibles) {
            if (coloresDisponible != linea.puntoPrimal.color) {
                coloresUtiles.add(coloresDisponible);
            }
        }
    }

    public ArrayList<PuntoInfoConvex> convertirPuntos(Linea linea) {
        ArrayList<PuntoInfoConvex> puntos = new ArrayList<PuntoInfoConvex>();
        for (Point2D point2D : linea.puntoPrimal.orden) {
            PuntoInfoConvex puntoP = (PuntoInfoConvex) point2D;
            puntoP.prev = new Stack<PuntoInfoConvex>();
            puntoP.next = new Stack<PuntoInfoConvex>();
            if (point2D.color != linea.puntoPrimal.color) {
                puntos.add(puntoP);
            }
        }
        return puntos;
    }

    public ArrayList crearOrden() {
        ArrayList<PuntoInfoConvex> puntos = new ArrayList<PuntoInfoConvex>();
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.red, -8, 1));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.blue, -8, 2));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.blue, -9, 3));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.blue, -7, 4));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.blue, -6, 4));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.red, -6, 5));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.red, -6, 6));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.red, -4, 5));
        puntos.add(new PuntoInfoConvex(new Stack<PuntoInfoConvex>(), new Stack<PuntoInfoConvex>(), Color.blue, -2, 4));
        return puntos;
    }

    /**
     * Función que reinicia las variables de la clase para una nueva busqueda
     */
    public void limpiarClase() {
        cardinalidadColor1 = 0;
        cardinalidadColor2 = 0;
        ultimoInsertadoColor1 = null;
        ultimoInsertadoColor2 = null;
        inicioBusquedaColor1 = null;
        inicioBusquedaColor2 = null;
        soporte = null;

    }
}

/**
 * Clase que representa los puntos que pueden ser parte del convexo
 *
 * @author root
 */
class PuntoInfoConvex extends Point2D {

    Stack<PuntoInfoConvex> prev;
    Stack<PuntoInfoConvex> next;

    public PuntoInfoConvex(float x, float y, Color color) {
        super(x, y, color);
    }

    public PuntoInfoConvex(Stack<PuntoInfoConvex> prev, Stack<PuntoInfoConvex> next, Color color, float x, float y) {
        super(x, y, color);
        this.prev = prev;
        this.next = next;
    }

}

/**
 * clase que representa la recta de soporte. color1 esta a la izquierda y color
 * 2 a la derecha
 */
class SegmentoSoporte {

    PuntoInfoConvex color1;
    PuntoInfoConvex color2;

    public SegmentoSoporte(PuntoInfoConvex color1, PuntoInfoConvex color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

}

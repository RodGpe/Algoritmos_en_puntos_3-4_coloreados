/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulomaximal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

/**
 * El poligono cierre convexo se recorre en sentido antihorario
 *
 * @author Rodrigo
 */
public class Poligono {

    int cardinalidad;
    PuntoPoligono ultimoInsertado;
    PuntoPoligono referencia = new PuntoPoligono(0, 0);
    PuntoPoligono tangente;
    PuntoPoligono inicioBusqueda;

    public static void main(String[] args) {
        Poligono poli = new Poligono();
        ArrayList<PuntoPoligono> puntos = poli.crearOrden();
        poli.CrearArregloConvexo(puntos);
        poli.recorerConvexo(poli.ultimoInsertado);
        poli.reverseOrder(puntos);
        double angle = poli.calcDegrees(poli.ultimoInsertado, poli.ultimoInsertado.next.peek());
        System.out.println(angle);
    }

    public void CrearArregloConvexo(ArrayList<PuntoPoligono> puntos) {
        cardinalidad = 0;
        for (PuntoPoligono p : puntos) {
            //if (p.color.equals(Color.blue) || cardinalidad != 0) {
            if (p.color.equals(Color.blue)) {
                if (cardinalidad == 0) {
                    ultimoInsertado = p;
                    cardinalidad = 1;
                } else if (cardinalidad == 1) {
                    ultimoInsertado.next.push(p);
                    ultimoInsertado.prev = p;
                    p.next.push(ultimoInsertado);
                    p.prev = ultimoInsertado;
                    ultimoInsertado = p;
                    cardinalidad = 2;
                } else {
                    agregarPunto(p);
                }
            }
        }
    }

    public void agregarPunto(PuntoPoligono p) {
        PuntoPoligono prevAux;
        PuntoPoligono nextAux;

        prevAux = buscarPrev(p);
        nextAux = buscarNext(p);

        p.prev = prevAux;
        p.prev.next.push(p);
        //p.next.push(buscarNext(p));
        p.next.push(nextAux);
        ultimoInsertado = p;
        cardinalidad = cardinalidad + 1;
    }

    /**
     * regresa el area de un triangulo por -2 si abc es sentido horario o por 2
     * si esta sentido antohorario (usar operador mayor o menor que) ----------
     * positivo - antihorario - izquierda -------------------------------------
     * negativo - horario -derecha
     *
     * @param a
     * @param b
     * @param c
     */
    public float area2(PuntoPoligono a, PuntoPoligono b, PuntoPoligono c) {
        float area2 = 0;
        area2 = (a.x - c.x) * (b.y - c.y) - (a.y - c.y) * (b.x - c.x);
        return area2;
    }

    public PuntoPoligono buscarPrev(PuntoPoligono p) {
        PuntoPoligono a, b, c;
        a = p;
        b = ultimoInsertado;
        c = ultimoInsertado.prev;
        while (area2(a, b, c) > 0) {
            b = c;
            c = c.prev;
        }
        return b;
    }

    public PuntoPoligono buscarNext(PuntoPoligono p) {
        PuntoPoligono a, b, c;
        a = p;
        b = ultimoInsertado;
        c = ultimoInsertado.next.peek();
        while (area2(a, b, c) < 0) {
            b = c;
            c = c.next.peek();
        }
        return b;
    }

    public void recorerConvexo(PuntoPoligono p) {
        PuntoPoligono iterador;
        PuntoPoligono minY;
        iterador = p;
        minY = iterador;
        do {
            System.out.println("convexo" + iterador.x + " " + iterador.y);
            if (iterador.y < minY.y) {
                minY = iterador;
            }
            iterador = iterador.next.peek();
        } while (iterador != p);
        System.out.println("menor es " + minY.x + " " + minY.y);
        inicioBusqueda = minY;
    }

    public void reverseOrder(ArrayList<PuntoPoligono> puntos) {
        PuntoPoligono p;
        for (int i = puntos.size() - 1; i >= 0; i--) {
            p = puntos.get(i);
            if (p.color.equals(Color.blue)) {
                //eliminar punto
                eliminarPunto(p);
            } else if (p.color.equals(Color.red)) {

                if (cardinalidad == 1) {
                    tangente = inicioBusqueda;
                    System.out.println("rojo " + p.x + " " + p.y);
                    System.out.println("tangente " + tangente.x + " " + tangente.y);
                    System.out.println("area " + (area2(referencia, p, tangente)) / 2);
                } else if (cardinalidad == 0) {

                } else {
                    buscarTangente(p);
                    System.out.println("rojo " + p.x + " " + p.y);
                    System.out.println("tangente " + tangente.x + " " + tangente.y);
                    System.out.println("area " + (area2(referencia, p, tangente)) / 2);
                }

            }
        }
    }

    public void buscarTangente(PuntoPoligono rojo) {
        PuntoPoligono tangenteAux;
        tangenteAux = inicioBusqueda;

        while (calcDegrees(tangenteAux, tangenteAux.next.peek()) < calcDegrees(referencia, rojo)) {
            tangenteAux = tangenteAux.next.peek();
        }
        inicioBusqueda = tangenteAux;
        tangente = tangenteAux;
        return;
        //return ;

    }

    public void eliminarPunto(PuntoPoligono p) {
        if (p == inicioBusqueda) {
            inicioBusqueda = p.prev;
        } else {
            inicioBusqueda = inicioBusqueda; //asignacion para clarificar
        }
        cardinalidad = cardinalidad - 1;
        p.prev.next.pop();
    }

    public double calcDegrees(PuntoPoligono centerPt, PuntoPoligono targetPt) {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.  
        // NOTE: By preserving Y and X param order to atan2,  we are expecting 
        // a CLOCKWISE angle direction.  
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);

        // rotate the theta angle clockwise by 90 degrees 
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.  
        // subtracting would rotate it counter-clockwise
        //theta += (Math.PI/2.0)*2;
        theta += 0;

        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public ArrayList crearOrden() {
        ArrayList<PuntoPoligono> puntos = new ArrayList<PuntoPoligono>();
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.red, -8, 1));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -8, 2));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -9, 3));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -7, 4));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -6, 4));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.red, -6, 5));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -6, 6));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.red, -4, 5));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.blue, -2, 4));
        return puntos;
    }
}

/**
 * next y prev son en sentido antihorario
 *
 * @author Rodrigo
 */
class PuntoPoligono extends Point2D {

    //PuntoPoligono next;
    PuntoPoligono prev;
    Stack<PuntoPoligono> next;
    Color color;
//    Stack<PuntoPoligono> next;
//    Stack<PuntoPoligono> prev;

    public PuntoPoligono(float x, float y) {
        super(x, y);
    }

    public PuntoPoligono(PuntoPoligono prev, Stack<PuntoPoligono> next, Color color, float x, float y) {
        super(x, y);
        this.prev = prev;
        this.next = next;
        this.color = color;
    }

}

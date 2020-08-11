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

    public static void main(String[] args) {
        Poligono poli = new Poligono();
        ArrayList<PuntoPoligono> puntos = poli.crearOrden();
        poli.CrearArregloConvexo(puntos);
        PuntoPoligono x = poli.ultimoInsertado;
        for (int i = 0; i < 10; i++) {
            System.out.println("" + x.x + "  " + x.y);
            x = x.next.peek();
            //x = x.prev;
        }
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

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
    //PuntoPoligono referencia;
    PuntoPoligono tangente;
    PuntoPoligono inicioBusqueda;
    Color[] coloresDisponibles = {Color.RED, Color.BLUE, Color.GREEN};
    ArrayList<Color> coloresUtiles = new ArrayList<>();
    ArrayList<ArrayList<Color>> permutaciones = new ArrayList();
    Triangulo minimo = null;

    public static void main(String[] args) {
        Poligono poli = new Poligono();
//        poli.permute(poli.coloresDisponibles);
//        System.out.println("------");
//        System.out.println("------");
//        for (ArrayList<Color> permutacione : poli.permutaciones) {
//            for (Color color : permutacione) {
//                System.out.print(color);
//            }
//            System.out.print("\n");
//        }
//        poli.referencia = new PuntoPoligono(0, 0);
//        ArrayList<PuntoPoligono> puntos = poli.crearOrden();
//        poli.CrearArregloConvexo(puntos, Color.blue);
//        poli.buscarYMIN(poli.ultimoInsertado); //
//        poli.reverseOrder(puntos, Color.blue, Color.red);
        poli.referencia = new PuntoPoligono(1, 2);
        ArrayList<PuntoPoligono> puntos = poli.crearOrden2();
        poli.CrearArregloConvexo(puntos, Color.red);
        poli.buscarYMIN(poli.ultimoInsertado); //
        poli.reverseOrder(puntos, Color.red, Color.blue);
        //double angle = poli.calcDegrees(poli.ultimoInsertado, poli.ultimoInsertado.next.peek());
        //System.out.println(angle);
    }

    /**
     * Metodo para encontrar el triangulo de menor area heterocromático dados el
     * punto y su orden
     *
     * @param linea
     * @return
     */
    public Triangulo buscarTrianguloMin(Linea linea) {
        referencia = (PuntoPoligono) linea.puntoPrimal;
        eliminarColor(linea);
        permute(new Color[]{coloresUtiles.get(0), coloresUtiles.get(1)}); // las guarda en el atributo de clase permutaciones
        ArrayList<PuntoPoligono> ordenPoli = convertirPuntos(linea);
        for (ArrayList<Color> permutacione : permutaciones) { //para buscar en todas las permutaciones de colres
            Color color1 = permutacione.get(0);
            Color color2 = permutacione.get(1);
            CrearArregloConvexo(ordenPoli, color1);
            buscarYMIN(ultimoInsertado); //
            reverseOrder(ordenPoli, color1, color2);
            limpiarClase();
        }
        return minimo;
    }

    /**
     * de los colores a permutar elimina el color del punto primal de la linea
     *
     * @param linea
     */
    public void eliminarColor(Linea linea) {
        for (Color coloresDisponible : coloresDisponibles) {
            if (coloresDisponible != linea.puntoPrimal.color) {
                coloresUtiles.add(coloresDisponible);
            }
        }
    }

    /**
     * reinicializa los atributos de la clase
     */
    public void limpiarClase() {
        cardinalidad = 0;
        ultimoInsertado = null;
        tangente = null;
        inicioBusqueda = null;

    }

    public void CrearArregloConvexo(ArrayList<PuntoPoligono> puntos, Color color1) {
        cardinalidad = 0;
        for (PuntoPoligono p : puntos) {
            if (p.color.equals(color1)) {
                //if (p.color.equals(Color.blue)) {
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

    public void buscarYMIN(PuntoPoligono ultimo) {
        PuntoPoligono iterador;
        PuntoPoligono minY;
        iterador = ultimo;
        minY = iterador;
        if (cardinalidad == 0) {
            return;
        }
        if (cardinalidad == 1) {
            inicioBusqueda = minY;
            return;
        }
        do {
            System.out.println("convexo" + iterador.x + " " + iterador.y);
            if (iterador.y < minY.y) {
                minY = iterador;
            }
            iterador = iterador.next.peek();
        } while (iterador != ultimo);
        System.out.println("menor es " + minY.x + " " + minY.y);
        inicioBusqueda = minY;
    }

    public void reverseOrder(ArrayList<PuntoPoligono> puntos, Color color1, Color color2) {
        PuntoPoligono p;
        for (int i = puntos.size() - 1; i >= 0; i--) { //para recorrerlo el Array list en reversa
            p = puntos.get(i);
            //if (p.color.equals(Color.blue)) {
            if (p.color.equals(color1)) {
                //eliminar punto
                System.out.println("elimino punto");
                eliminarPunto(p);
                //} else if (p.color.equals(Color.red)) {
            } else if (p.color.equals(color2)) {
                //System.out.println("busco tangente");
                if (cardinalidad == 1) {
                    tangente = inicioBusqueda;
                    System.out.println(color2.toString() + p.x + " " + p.y);
                    System.out.println("tangente " + tangente.x + " " + tangente.y);
                    System.out.println("area " + (area2(referencia, p, tangente)) / 2);
                    verificarTamano(p);
                } else if (cardinalidad == 0) {
                    System.out.println("no busco nada");

                } else {
                    buscarTangente(p);
                    System.out.println(color2.toString() + p.x + " " + p.y);
                    System.out.println("tangente " + tangente.x + " " + tangente.y);
                    System.out.println("area " + (area2(referencia, p, tangente)) / 2);
                    verificarTamano(p);
                }

            }
        }
    }

    public void verificarTamano(PuntoPoligono p) {
        if (minimo == null || (area2(referencia, p, tangente)) / 2 < minimo.area) {
            minimo = new Triangulo(referencia, p, tangente, (area2(referencia, p, tangente)) / 2);
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
        if (cardinalidad == 0) {
            return;
        }
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

    public ArrayList crearOrden2() {
        ArrayList<PuntoPoligono> puntos = new ArrayList<PuntoPoligono>();
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.red, 3, 5));
        puntos.add(new PuntoPoligono(null, new Stack<PuntoPoligono>(), Color.green, 5, 4));

        return puntos;
    }

    /**
     * metodo para el casteo de los puntos Point2D a PuntoPoligono
     *
     * @param linea
     * @return
     */
    public ArrayList<PuntoPoligono> convertirPuntos(Linea linea) {
        ArrayList<PuntoPoligono> puntos = new ArrayList<PuntoPoligono>();
        for (Point2D point2D : linea.puntoPrimal.orden) {
            PuntoPoligono puntoP = (PuntoPoligono) point2D;
            puntoP.prev = null;
            puntoP.next = new Stack<PuntoPoligono>();
            if (point2D.color != linea.puntoPrimal.color) {
                puntos.add(puntoP);
            }
        }
        return puntos;
    }

    /**
     * Genera las permutaciones
     *
     * @param arr
     */
    public void permute(Color[] arr) {
        permuteHelper(arr, 0);
    }

    private void permuteHelper(Color[] arr, int index) {
        if (index >= arr.length - 1) { //If we are at the last element - nothing left to permute
            //System.out.println(Arrays.toString(arr));
            //Print the array
            ArrayList elementosPermutacion = new ArrayList();
            System.out.print("[");
            for (int i = 0; i < arr.length - 1; i++) {
                elementosPermutacion.add(arr[i]);
                System.out.print(arr[i] + ", ");
            }
            if (arr.length > 0) {
                elementosPermutacion.add(arr[arr.length - 1]);
                System.out.print(arr[arr.length - 1]);
            }
            System.out.println("]");
            permutaciones.add(elementosPermutacion);
            return;
        }

        for (int i = index; i < arr.length; i++) { //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            Color t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index + 1);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
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
    //Color color;
//    Stack<PuntoPoligono> next;
//    Stack<PuntoPoligono> prev;

    public PuntoPoligono(float x, float y) {
        super(x, y);
    }

    public PuntoPoligono(float x, float y, Color color) {
        super(x, y, color);
    }

    public PuntoPoligono(PuntoPoligono prev, Stack<PuntoPoligono> next, Color color, float x, float y) {
        super(x, y);
        this.prev = prev;
        this.next = next;
        this.color = color;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulomaximal;

/**
 *
 * @author Rodrigo
 */
public class PoligonoMax extends BuscadorTriangulo {

    public static void main(String[] args) {
        PoligonoMax poliM = new PoligonoMax();

    }

    @Override
    public void buscarTangente(PuntoPoligono rojo) {
        PuntoPoligono tangenteAux;
        tangenteAux = inicioBusqueda;

        while (calcDegrees(tangenteAux, tangenteAux.next.peek()) < calcDegrees(rojo, referencia)) {
            System.out.println("busco tangente");
            System.out.println(calcDegrees(tangenteAux, tangenteAux.next.peek()));
            System.out.println(calcDegrees(rojo, referencia));
            System.out.println("--------");
            tangenteAux = tangenteAux.next.peek();

            System.out.println("busco tangente");
            System.out.println(calcDegrees(tangenteAux, tangenteAux.next.peek()));
            System.out.println(calcDegrees(rojo, referencia));
            System.out.println("--------");
        }
        inicioBusqueda = tangenteAux;
        tangente = tangenteAux;
        return;
    }

    @Override
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
        theta += (Math.PI / 2.0) * 2; //PARA CALCULAR DESDE LA HORIZONTAL A LA IZQUIERDA DEL PUNTO CENTRO
        //theta += 0;

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

    @Override
    public void buscarYMIN(PuntoPoligono ultimo) {
        PuntoPoligono iterador;
        PuntoPoligono maxY;
        iterador = ultimo;
        maxY = iterador;
        if (cardinalidad == 0) {
            return;
        }
        if (cardinalidad == 1) {
            inicioBusqueda = maxY;
            return;
        }
        do {
            System.out.println("convexo" + iterador.x + " " + iterador.y);
            if (iterador.y > maxY.y) {
                maxY = iterador;
            }
            iterador = iterador.next.peek();
        } while (iterador != ultimo);
        System.out.println("mayor es " + maxY.x + " " + maxY.y);
        inicioBusqueda = maxY;
    }

    @Override
    public void verificarTamano(PuntoPoligono p) {
        if (minimo == null || (area2(referencia, p, tangente)) / 2 > minimo.area) {
            minimo = new Triangulo(referencia, p, tangente, (area2(referencia, p, tangente)) / 2);
        }
    }

}

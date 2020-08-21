/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulomaximal;

/**
 * Clase que guarda los tres puntos de un triangulo y su area
 *
 * @author Rodrigo
 */
public class Triangulo {

    Point2D a;
    Point2D b;
    Point2D c;
    float area;

    public Triangulo(Point2D a, Point2D b, Point2D c, float area) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.area = area;
    }

}

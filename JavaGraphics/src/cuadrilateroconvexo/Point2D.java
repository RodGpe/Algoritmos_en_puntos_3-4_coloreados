  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuadrilateroconvexo;

import triangulomaximal.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
// Point2D.java: Class for points in logical coordinates.
// Copied from Section 1.5 of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.
class Point2D {

    float x, y;
    Linea linea;
    Color color;
    ArrayList<Point2D> orden;

    Point2D(float x, float y) {
        this.x = x;
        this.y = y;
        this.orden = new ArrayList<Point2D>();
    }

    public Point2D(float x, float y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.orden = new ArrayList<Point2D>();
    }

}

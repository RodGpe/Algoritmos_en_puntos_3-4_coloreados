/*
Clase para probar como recibir un objeto, usarlo como sun hijo y regresar el 
objeto como el que era al entrar al metodo
 */

package triangulomaximal;

import java.awt.Color;

/**
 *
 * @author Rodrigo
 */
public class PruebaPunto {
    public static void main(String[] args) {
        PuntoP puntoP = new PuntoP(0, 0);
        PuntoH puntoH = new PuntoH(0, 0);
        puntoP = (PuntoP) puntoH.regresarPuntoP(puntoP);
        
        //puntoH = (PuntoH) puntoP;
        System.out.println(puntoH.color);
        System.out.println(puntoP.getClass().getName());
        puntoP = new PuntoP(20, 20);
        System.out.println(puntoP.getClass().getName());
    }
    

}

class PuntoP{
    int x;
    int y;

    public PuntoP(int x, int y) {
        this.x = x;
        this.y = y;
    }
    

}

class PuntoH extends PuntoP{
    Color color;
    public PuntoH(int x, int y) {
        super(x, y);
    }
    
    public PuntoP regresarPuntoP(PuntoP pH){
        PuntoH punto ;
        pH.x = 100;
        punto = (PuntoH) pH;
        punto.color = Color.red;
        System.out.println(punto.color);
        return pH;
    }
    
}
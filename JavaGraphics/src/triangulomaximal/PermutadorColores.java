/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triangulomaximal;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Rodrigo
 */
public class PermutadorColores {
    
//    public static void main(String[] args) {
//        PermutadorColores permu = new PermutadorColores();
//        permu.permute(new Color[]{Color.red,Color.blue,Color.green});
//    }

    /**
     * Genera las permutaciones
     *
     * @param arr
     */
    public ArrayList<ArrayList<Color>> permute(Color[] arr) {
        ArrayList<ArrayList<Color>> permutaciones = new ArrayList<ArrayList<Color>>();
        permuteHelper(arr, 0, permutaciones);
        return permutaciones;
    }

    private void permuteHelper(Color[] arr, int index, ArrayList<ArrayList<Color>> permutaciones) {
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
            permuteHelper(arr, index + 1, permutaciones);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
    }
}

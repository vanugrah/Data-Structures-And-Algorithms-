import java.io.*;
import java.util.*;
import java.lang.Math;

public class Solution {
    
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> cords = new ArrayList<Integer>();
        Integer[] squirrel = new Integer[2];
        Integer[] tree = new Integer[2];
        Integer[] nut = new Integer[2];
        int nuts = 0;
        int moves = 0;
        int x_distance = 0;
        int y_distance = 0;
        int counter = 0;
        
        // store co-ordinates in ArrayList
        while (scan.hasNext() ) {
            int j = scan.nextInt();
            cords.add(j);
        }
        
        // starting point of squirrel
        squirrel[0] = cords.get(0);
        squirrel[1] = cords.get(1);
        
        // position of tree
        tree[0] = cords.get(2);
        tree[1] = cords.get(3);
        
        // number of nuts remaining 
        nuts = cords.get(4);
        
        // location of nut that we are seeking 
        nut[0] = cords.get(5);
        nut[1] = cords.get(6);
        
        // update counter
        counter = 6;
        

        
        while (nuts != 0) {
            // distance needed to get to nut
            x_distance = Math.abs(squirrel[0] - nut[0]);
            y_distance = Math.abs(squirrel[1] - nut[1]);

            
            // distance needed to get to tree
            x_distance += Math.abs(nut[0] - tree[0]);
            y_distance += Math.abs(nut[1] - tree[1]);

           
            // update squirrel location to tree
            squirrel[0] = tree[0];
            squirrel[1] = tree[1];
            
            // update moves taken and nuts remaining and reset distances and new nut location
            moves = moves + x_distance + y_distance;
            nuts--;
            x_distance = 0;
            y_distance = 0;
            
            counter++;
            if (counter < cords.size() && nuts != 0) {
                nut[0] = cords.get(counter);
                counter++;
            }
            if (counter < cords.size() && nuts != 0) {
                nut[1] = cords.get(counter);   
            }
            
        }
         System.out.print(moves);
    }
}
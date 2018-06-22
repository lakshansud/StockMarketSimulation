
import GameHandler.PriceChangeStock;
import GameHandler.ThreadForValChange;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hasnat
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        
      
        int turnRounds = 5;
        int turnTime = 10000;
        
        for (int i = 1; i <= turnRounds; i++) {
                ThreadForValChange tf = new ThreadForValChange(i);
                tf.sleep(turnTime);
                System.out.println("Turn " + i);
                tf.start();
            }
        }

}

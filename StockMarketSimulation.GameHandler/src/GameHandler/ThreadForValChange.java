/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hasnat
 */
public class ThreadForValChange extends Thread {

    public ThreadForValChange() {
    }

    @Override
    public void run() {

        try {
            Thread.sleep(50);

            //call analyst prediction here
            //update stock prices
            ValChangeStock valChangestock = new ValChangeStock();

            valChangestock.updatestock();
            valChangestock.update();

        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadForValChange.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

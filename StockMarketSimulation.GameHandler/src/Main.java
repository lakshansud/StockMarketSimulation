
import DatabaseConnection.DB;
import GameHandler.ActiveUsers;
import GameHandler.EventGenerator;
import GameHandler.PlayerAI;
import GameHandler.PriceChangeStock;
import GameHandler.ThreadForValChange;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    static TimerTask staticTask;

    public static void main(String[] args) throws InterruptedException {

        PlayerAI ai = new PlayerAI();
        ai.botPlayerInteraction();

        TimerTask task = new TimerTask() {

            @Override

            public void run() {
                try {
                    startRound();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };

        staticTask = task;

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 1 * 2000;

        // schedules the task to be run in an interval 
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

    }

    public static void startRound() throws InterruptedException {

        ActiveUsers ac = new ActiveUsers();
        
        int turnNo = 5;
        int turnTime = 10000;
        if (ac.checkActivatedUsers()) {
            System.out.println("Active users");
            for (int i = 1; i <= turnNo; i++) {
                ThreadForValChange tf = new ThreadForValChange(i);   
                tf.sleep(turnTime);
                System.out.println("Turn " + i);
                tf.start();
                staticTask.cancel();
                if (i == turnNo) {
                    ac.deactivateAllUsers();
                }
            }
        } else {
            System.out.println("No active users");
        }
    }



}

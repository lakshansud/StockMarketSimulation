/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameHandler;

import DatabaseConnection.DB;
import java.util.Random;

/**
 *
 * @author Hasnat
 */
public class Testvalchange {

    /**
     * @return
     */
    public int graphgeneratortrendvalues() //check from database values for increment or decrement
    {
        int randomInt1 = 0;
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 4; ++idx) {
            randomInt1 = randomGenerator.nextInt(4);
        }
        int trend = 0;

        switch (randomInt1) {
            case 1:
                trend = 1;
                break;
            case 2:
                trend = -1;
                break;
            case 3:
                trend = 0;
                break;
            case 4:
                trend = 0;

        }
        return trend;
    }

    public int getLimit(String type) {
        int limit = 0;
        switch (type) {
            case "random":
                limit = 2;
                break;
            case "sector":
                limit = 3;
                break;
            case "market":
                limit = 3;
                break;
        }
        return limit;

    }

    /**
     * public void saveTrendData(type, turn) {
     *
     * int limit = this.getLimit(type);
     *
     *
     * int trend = this.graphgeneratortrendvalues();
     *
     * // save pseudo code
     * 
     * if ( trend > 0 ) {
     * if (trend > limit) 
     * { save limit; }
     * 
     * else {
     * save trend; }
     * 
     * } else { 
     * int negetiveLimit = limit*-1; //( get it
     * from above limit value )
     * if (result < negativeLimit) {
     * save * negativelimit; } else { save trend; }
     *
     * }      *
     *
     * }
     *
     *
     */
    ////////////////////////////////////////////////////////////////////////
        public Testvalchange(String type,int turn) {

        int limit = this.getLimit(type);

        int trend = this.graphgeneratortrendvalues();

        boolean isSaved = true;
      

        try {
            
            //// how to save values in database ---- 1.) +1, -3 etc 
            ////or ---- 2.) as additions to the previous value??
            
            
            
            
//check with database previously saved value to compare with the limit
            if (trend > 0) {
                if (trend > limit)
                
                {
                    
                    // if(database last value >= limit){save limit}
                    //else{save trend}
                    
                    // Get new price and value
                    // Select * from stock where stockId = 0
                    //if(rs.next())
                    
                    
                    // rs.close();
                    
                    // Update stock with current value
                    
                    // save limit;

                    String insertQry = "INSERT INTO Values(Value) values ('" +limit +"' )";
                    isSaved = DB.save(insertQry);

                } else {
                    // save trend;

                    String insertQry = "INSERT INTO Values(Value) values ('" +trend +"' )";
                    isSaved = DB.save(insertQry);

                }
            } else {
                int negetiveLimit = limit * -1; //( get it from above limit value )
                if (trend < negetiveLimit) {
                    
                    
                     
                    // if(database last value <= limit){save limit}
                    //else{save trend}
                    
                    
                    
                    
                    
                    //save negativelimit;

                    String insertQry = "INSERT INTO Values(Value) values ('" +negetiveLimit +"' )";
                    isSaved = DB.save(insertQry);
                } else {
                    //  save trend;

                    String insertQry = "INSERT INTO Values(Value) values ('" +trend +"' )";
                    isSaved = DB.save(insertQry);
                }

            }

            

      

        } catch (Exception e) {
            isSaved = false;
            e.printStackTrace();
        } finally {
           
                }
          
        }
      
    }

    /////////////////////////////////////////////////////////////////


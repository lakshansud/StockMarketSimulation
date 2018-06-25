package GameHandler;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hasnat
 */
public class EventGenerator {

    public String getEventType() {

        int randomInt1 = 0;
        Random randomGenerator = new Random();
         randomInt1 = randomGenerator.nextInt(3) + 1;
        
        String type = null;

        switch (randomInt1) {
            case 1:
                type = "sector";
                break;
            case 2:
                type = "stock";
                break;
            case 3:
                type = "stock";

        }

        return type;

    }

    public ReturnEventDetailsViewmodel getsectoreventtype() {

        Random randomGenerator = new Random();
        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();

        String type = null;
        int duration = randomGenerator.nextInt(5) + 1;
        
        int range = randomGenerator.nextInt(5) + 1;

        int randomInt = randomGenerator.nextInt(2) + 1;
        switch (randomInt) {
            case 1:
                type = "boom";
                break;

            case 2:
                type = "bust";
                break;

        }

        returnEventDetailsViewmodel.type = type;
        returnEventDetailsViewmodel.duration = duration;

        if (type == "bust") {
            range = range * -1;
        }

        returnEventDetailsViewmodel.range = range;

        return returnEventDetailsViewmodel;

    }

    public ReturnEventDetailsViewmodel getstockeventtype() {

        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();
        int range = 0;
        
        Random randomGenerator = new Random();
        int duration = randomGenerator.nextInt(7) + 1;

        int randomInt = randomGenerator.nextInt(4) + 1; 
        String type = null;

        switch (randomInt) {

            case 1:
                type = "profit warning";
                range = randomGenerator.nextInt(3) + 2; 
                break;

            case 2:
                type = "profit warning";
                range = randomGenerator.nextInt(3) + 2; 
                break;

            case 3:
                type = "take over";
                range = -1 * (randomGenerator.nextInt(5) + 1); 
                break;

            case 4:
                type = "scandal";
                range = -1 * (randomGenerator.nextInt(6) + 1);
                break;

        }

        returnEventDetailsViewmodel.type = type;
        returnEventDetailsViewmodel.duration = duration;
        returnEventDetailsViewmodel.range = range;
        return returnEventDetailsViewmodel;

    }
    
    public ReturnEventDetailsViewmodel generateEvent() {
        
        String type =  this.getEventType();
        ReturnEventDetailsViewmodel event = new ReturnEventDetailsViewmodel();
        if (type == "sector") {
            event = this.getsectoreventtype();
        } else if (type == "stock") {
            event = this.getstockeventtype();
        }
        return event;
    }

}

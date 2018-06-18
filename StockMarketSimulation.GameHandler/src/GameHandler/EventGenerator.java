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
        for (int idx = 1; idx <= 3; ++idx) {
            randomInt1 = randomGenerator.nextInt(3);
        }
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

        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();

        int[] sector_event_duration = new int[]{2, 3, 4, 5};

        String type = null;
        int rnd_duration = new Random().nextInt(sector_event_duration.length);

        int[] sector_event = new int[]{1,2, 3, 4, 5};
        int rnd = new Random().nextInt(sector_event.length);

        int randomInt1 = 0;
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 2; ++idx) {
            randomInt1 = randomGenerator.nextInt(2);
        }

        switch (randomInt1) {
            case 1:
                type = "boom";
                break;

            case 2:
                type = "bust";
                break;

        }

        returnEventDetailsViewmodel.type = type;
        returnEventDetailsViewmodel.duration = rnd_duration;

        if (type == "bust") {
            rnd = rnd * -1;
        }

        returnEventDetailsViewmodel.range = rnd;

        return returnEventDetailsViewmodel;

    }

    public ReturnEventDetailsViewmodel getstockeventtype() {

        ReturnEventDetailsViewmodel returnEventDetailsViewmodel = new ReturnEventDetailsViewmodel();

        int[] stock_event_duration = new int[]{1, 2, 3, 4, 5, 6, 7};
        int rnd_duration = new Random().nextInt(stock_event_duration.length);

        int randomInt = 0;
        int rnd = 0;

        Random randomGenerator = new Random();
        for (int idx = 1; idx <= 4; ++idx) {
            randomInt = randomGenerator.nextInt(4);
        }
        String type = null;

        switch (randomInt) {

            case 1:
                type = "profit warning";
                int[] stock_event = new int[]{2, 3};
                rnd = new Random().nextInt(stock_event.length);
                break;

            case 2:
                type = "profit warning";
                int[] stock_event2 = new int[]{2, 3};
                rnd = new Random().nextInt(stock_event2.length);
                break;

            case 3:
                type = "take over";
                int[] stock_event3 = new int[]{-1, -2, -3, -4, -5};
                rnd = new Random().nextInt(stock_event3.length);
                break;

            case 4:
                type = "scandal";
                int[] stock_event4 = new int[]{-3, -4, -5, -6};
                rnd = new Random().nextInt(stock_event4.length);
                break;

        }

        returnEventDetailsViewmodel.type = type;
        returnEventDetailsViewmodel.duration = rnd_duration;
        returnEventDetailsViewmodel.range = rnd;

        return returnEventDetailsViewmodel;

    }

}

package SB_RasPi_Robotics;

/**
 * Created by seb on 24/12/16.
 */
public class RDICrashControl extends Thread {



    private boolean run;



    public void run() {

        run = true;
       tick();


    }

    public void cancel() {

        run = false;
    }


    public void tick() {

        while (run) {


            // Measure current distance.

            float distance = RDIServer.distanceMonitor.getDistance();

            // If distance is less then the min override the motor's speed.


            if ((distance < 20) && (RDIServer.robotControlManager.getMotorSpeed() > 0)) {

                System.out.println("<<CRASH CONTROL>> STOPPING! ");

                RDIServer.robotControlManager.setMotorSpeed(0);


            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            tick();
        }

    }


}

package SB_RasPi_Robotics;


import SB_RasPi_Robotics.CodeBase.PWMServoController;
import SB_RasPi_Robotics.CodeBase.ServoController;

public class EggInc {

    /**
     *  This is a fun little project where i'm trying to get a servo with a stylus taped to its arm to tap an iPad screen
     *  The arm just goes up and down repeatedly while the program runs.
     */


    private static PWMServoController mastServo;
    private static int position = 0;
    private static int direction = 1;

    private static int min = 70;
    private static int max = 80;

    public static void main(String args[] ) {



        System.out.println("EGGS!!!!");

        mastServo = new PWMServoController(3,0,0,180,0);

        mastServo.setPosition(min);


        while (true) {

            tick();

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void tick() {


        if (position >= max) {

            direction = -1;
            position = min;

        } else if (position <= min) {

            direction = 1;
            position = max;

        }


        mastServo.setPosition(position);
        System.out.println("Position: " + position);

    }
}

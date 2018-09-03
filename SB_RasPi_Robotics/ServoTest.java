package SB_RasPi_Robotics;


import SB_RasPi_Robotics.CodeBase.PWMServoController;
import SB_RasPi_Robotics.CodeBase.ServoController;

public class ServoTest {


    private static ServoController steeringServo;
    private static int steeringServoValue = 0;
    private static PWMServoController mastServo;
    private static int position = 0;
    private static int direction = 1;

    public static void main(String args[] ) {


        System.out.println("Start");

        steeringServo = new ServoController(22,0,90,140,115);
        mastServo = new PWMServoController(3,0,0,180,0);

        while (true) {

            tick();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void tick() {


        if (position >= 100) {
            direction = -1;
        } else if (position <= 0) {
            direction = 1;
        }

        position = position + 1 * direction;

        steeringServo.setPosition(-position);
        mastServo.setPosition(position);
        System.out.println("Mast: " + position);

    }
}

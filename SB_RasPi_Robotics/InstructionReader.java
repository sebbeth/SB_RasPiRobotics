package SB_RasPi_Robotics;


import SB_RasPi_Robotics.CodeBase.RobotControlManager;
import SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor;

public class InstructionReader {

    static  RobotControlManager robotControl;
    static int maxSpeed = 7;

    public static void main(String[] args) {

        robotControl = new RobotControlManager();


        System.out.println("********* INSTRUCTION READER *********");
        System.out.println("\n \n Press front bumper to start...");
    }



    private static void hang(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /****  Skills  ****/

    private static void threePointTurn() {

        System.out.println("Three point Turn");
        robotControl.setMotorSpeed(0);
        hang(50);
        robotControl.setMotorSpeed(-maxSpeed);
        robotControl.setSteering(-100);
        hang(1000);
        robotControl.setMotorSpeed(0);
        robotControl.setSteering(100);
        hang(800);
        robotControl.setSteering(100);
        robotControl.setMotorSpeed(maxSpeed);
        hang(1000);
        robotControl.setSteering(0);
        robotControl.setMotorSpeed(0);

        hang(1000);
    }

}



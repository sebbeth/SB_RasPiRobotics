package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;
import SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor;

public class PathFinder
{
    
    static RobotControlManager robotControl;
    static UltraSoundDistanceMonitor distanceMonitor;
    static private boolean hitWall;
    static private boolean active = false;

    
	
    public static void main(String[] args) {
        
        System.out.println("Hello :) \n \n Press front bumper to start");
        
        
        robotControl = new RobotControlManager();
        distanceMonitor = new UltraSoundDistanceMonitor();
        
        new RunnablePushButtonMonitor().start();
    
        
        activate();


        
    }
    
    
    
    
    public static void activate() {
        
        try {
            
            active = true;

            
            robotControl.increaseHeadlights();
            robotControl.increaseHeadlights();
            robotControl.setMastPan(20);

            Thread.sleep(300);

            robotControl.setMastPan(50);

            robotControl.decreaseHeadlights();
            robotControl.decreaseHeadlights();

            /*

            robotControl.setMotorSpeed(0);
            Thread.sleep(50);
         
            robotControl.setSteering(-100);
            Thread.sleep(500);
            robotControl.setSteering(100);
            Thread.sleep(500);
            robotControl.setSteering(0);
            Thread.sleep(50);

            */


            shakeHead();
            reverseTurn();

            
        } catch (Exception e) {
            
        }
        
        
        
        
    }





    

    public static void pushButtonPressed() {
        
        System.out.println("Button!!");

        
    
    }

    private static void hang(int time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /****  Skills    **/


    private static int[] scan() {

        int measurements[] = new int[5];

        int iterations = 3;
        int points[] = {0,25,50,75,100};

        for (int i = 0; i < 5; i++ ) {

            robotControl.setMastPan(points[i]);

            hang(200);

            measurements[i] = Math.round(distanceMonitor.getAveragedDistance(iterations));

            hang(200);


        }




        robotControl.setMastPan(50);


        String output = "";

        for (int i : measurements) {
            output = output + "," + i;
        }

        System.out.println("OUTPUT " + output);


        return measurements;

    }


    private static void turnToLeft() {


    }

    private static void turnToRight() {


    }

    private static void driveWhileMeasuring() {


    }



    private static void reverseTurn() {


        robotControl.increaseHeadlights();

        robotControl.setMotorSpeed(0);
            hang(50);
        robotControl.setMotorSpeed(-20);
            hang(800);

        robotControl.setSteering(-100);
        robotControl.setMotorSpeed(-20);
            hang(800);
        robotControl.setMotorSpeed(0);
            hang(50);
        robotControl.setSteering(100);
        robotControl.setMotorSpeed(20);
            hang(500);
        robotControl.setSteering(0);
        robotControl.setMotorSpeed(0);


        
        robotControl.decreaseHeadlights();

        
        
    }


    private static void shakeHead() {


        robotControl.setMastPan(50);
        hang(1000);
        robotControl.setMastPan(20);
        hang(100);

        robotControl.setMastPan(80);
        hang(100);

        robotControl.setMastPan(20);
        hang(100);

        robotControl.setMastPan(80);
        hang(100);

        robotControl.setMastPan(20);
        hang(100);
        robotControl.setMastPan(50);




    }
    
    private static void stop() {
        
        robotControl.setMotorSpeed(0);
        
        
    }


}


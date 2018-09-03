package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;
import SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor;

public class PathFinder
{
    
    static RobotControlManager robotControl;
    static UltraSoundDistanceMonitor distanceMonitor;
    static private boolean hitWall;
    static private boolean active = false;
    final static int driveSpeed = 12;
    final static int burstSpeed = 30;



    public static void main(String[] args) {
        
        System.out.println("********* PATHFINDER *********");
        
        
        robotControl = new RobotControlManager();
        distanceMonitor = new UltraSoundDistanceMonitor();
        
        new RunnablePushButtonMonitor().start();
    
        
        activate();


        
    }


    private static void panMastServo() {


        // Pan the mast and the steering servo

        boolean panning = true;
        int position = 0;

        while (panning) {
            robotControl.setMastPan(position);
            hang(20);
            position++;
            if (position == 100) {
                panning = false;
            }
        }

        panning = true;

        while (panning) {
            robotControl.setMastPan(position);
            hang(20);
            position--;
            if (position == 50) {
                panning = false;
            }
        }
    }
    
    
    
    
    public static void activate() {
        
        try {
            
            active = true;
            robotControl.increaseHeadlights();
            robotControl.increaseHeadlights();
            robotControl.setSteering(100);

            Thread.sleep(300);

         //   panMastServo();
            robotControl.decreaseHeadlights();
            robotControl.decreaseHeadlights();
            robotControl.setSteering(0);

            scanSuroundingsBeforeMoving();
            
        } catch (Exception e) {    }
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


    private static int averageOf(int[] input) {

        int sum = 0;
        for (int i = 0; i < input.length; i++ ) {
            sum = sum + input[i]; // Sum the inputs
        }

        // Now average the result
        return sum / input.length;
    }

    /****  Skills  ****/

    private static void scanSuroundingsBeforeMoving() {


        /*
            This function takes values from scan() and evaluates the best direction to move in.

            logic

            if centre measurement == 0
                three point turn

            if   averageOf([1] + [2] + [3] >= 100
                Go forward

            else if averageOf([0] + [1]) > averageOf([2] + [3] + [4])
                turnToRight


            else if averageOf([0] + [1] + [2]) < averageOf( [3] + [4])
                turnToLeft
         */

        int measurements[] = scan();

        if (measurements[2] <= 50) {

            //three point turn
            hang(1000);
            threePointTurn();

        } else if (averageOf(new int[]{measurements[1],measurements[2],measurements[3]}) >= 70) {

            // Go forward
            hang(1000);
            driveWhileMeasuring();

        } else if (averageOf(new int[]{measurements[0],measurements[1]}) >
                averageOf(new int[]{measurements[3],measurements[4]})) {


            hang(1000);
            turnToRight();


        } else if (averageOf(new int[]{measurements[3],measurements[4]}) >
                averageOf(new int[]{measurements[0],measurements[1]})) {

            hang(1000);
            turnToLeft();


        }


            hang(1000);

       // threePointTurn();

        //turnToLeft();

        turnToRight();

    }

    private static int[] scan() {

        int measurements[] = new int[5];

        int iterations = 3;
        int points[] = {0,25,50,75,100};

        for (int i = 0; i < 5; i++ ) {

            robotControl.setMastPan(points[i]);

            hang(200);

            measurements[i] = Math.round(distanceMonitor.getDistance());

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

        robotControl.setSteering(100);
        hang(100);
        drive(1);

        hang(200);
        drive(1);
        hang(500);
        robotControl.setMotorSpeed(0);
        robotControl.setSteering(0);




        hang(1000);

        driveWhileMeasuring();

    }

    private static void turnToRight() {

        robotControl.setSteering(-100);
        hang(100);
        drive(1);

        hang(200);
        drive(1);
        hang(500);
        robotControl.setMotorSpeed(0);
        robotControl.setSteering(0);

        hang(1000);

        driveWhileMeasuring();

    }

    private static void driveWhileMeasuring() {

        System.out.println("Driving forward while measuring ");
        boolean running = true;

        drive(1);
        robotControl.setSteering(0);

        while (running) {

            int forwardDistance = Math.round(distanceMonitor.getDistance());
            if (forwardDistance < 50 ) {
                // Stop
                System.out.println("Obstacle in path");
                running = false;
                stop();
            }

            hang(100);
        }

        scanSuroundingsBeforeMoving();

    }

    private static void drive(int direction) {

        int modifier = -1;
        if (direction == 1) { // forward
            modifier = 1;
        }
        robotControl.setMotorSpeed(modifier * 30); // Go fast,
        hang(200);
        robotControl.setMotorSpeed(modifier * 20); // then slower,
        hang(200);
        robotControl.setMotorSpeed(modifier * driveSpeed); // Then proceed at crusing speed.
    }


    private static void threePointTurn() {

        System.out.println("Three point Turn");
        robotControl.setMotorSpeed(0);
        hang(50);
        robotControl.setSteering(-100);
        drive(-1);
        hang(1000);
        robotControl.setMotorSpeed(0);
        robotControl.setSteering(100);
        hang(800);
        robotControl.setSteering(100);
        drive(-1);
        hang(1000);
        robotControl.setSteering(0);
        robotControl.setMotorSpeed(0);

        hang(1000);

        scanSuroundingsBeforeMoving();
    }


    private static void reverseTurn() {


        robotControl.increaseHeadlights();

        robotControl.setMotorSpeed(0);
            hang(50);
        drive(-1);
            hang(800);

        robotControl.setSteering(-100);
        drive(-1);
            hang(800);
        robotControl.setMotorSpeed(0);
            hang(50);
        robotControl.setSteering(100);
        drive(-1);
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


        robotControl.setMastPan(50);




    }
    
    private static void stop() {
        
        robotControl.setMotorSpeed(0);
        
        
    }


}


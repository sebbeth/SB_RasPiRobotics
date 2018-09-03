package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.ObservableButton;
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

        new PathFinder();
    }

    public PathFinder() {

        System.out.println("********* PATHFINDER *********");

        robotControl = new RobotControlManager();
        distanceMonitor = new UltraSoundDistanceMonitor();

        flash();

        System.out.println("Press bumper to start...");



        new RunnablePushButtonMonitor(this).start();
        active = false;
        waiting();

    }

    private static void waiting() {
        System.out.println("Waiting...");
        while(true) {
            if (active) {
                activate();
                return;
            }
        }
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
            shakeHead();
            scanSuroundingsBeforeMoving();
            
        } catch (Exception e) {    }
    }

    

    public static void pushButtonPressed() {
        System.out.println("Button :O" + active);

        if (active) {
          //  activate();
            active = false;
        } else {
            active = true;
        }
        flash();

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

        if (!active) {
            waiting();
            return;
        }

            int measurements[] = scan();

            if (measurements[2] <= 50) {

                //three point turn
                hang(1000);
                threePointTurn();

            } else if (averageOf(new int[]{measurements[1], measurements[2], measurements[3]}) >= 70) {

                // Go forward
                hang(1000);
                driveWhileMeasuring();

            } else if (averageOf(new int[]{measurements[0], measurements[1]}) >
                    averageOf(new int[]{measurements[3], measurements[4]})) {


                hang(1000);
                turnToRight();


            } else if (averageOf(new int[]{measurements[3], measurements[4]}) >
                    averageOf(new int[]{measurements[0], measurements[1]})) {

                hang(1000);
                turnToLeft();


            }

            hang(1000);
            turnToRight();

    }

    private static void flash() {
        try {
            robotControl.increaseHeadlights();
            robotControl.increaseHeadlights();
            robotControl.increaseHeadlights();

            Thread.sleep(1000);

            robotControl.decreaseHeadlights();
            robotControl.decreaseHeadlights();
            robotControl.decreaseHeadlights();

        } catch (Exception e) {    }
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
        if (!active) {
            waiting();
            return;
        }

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
        if (!active) {
            waiting();
            return;
        }

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
        if (!active) {
            waiting();
            return;
        }
            System.out.println("Driving forward while measuring ");
            boolean running = true;

            drive(1);
            robotControl.setSteering(0);

            while (running) {

                int forwardDistance = Math.round(distanceMonitor.getDistance());
                if (forwardDistance < 50) {
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
        if (!active) {
            waiting();
            return;
        }
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
            drive(1);
            hang(1000);
            robotControl.setSteering(0);
            robotControl.setMotorSpeed(0);

            hang(1000);

            scanSuroundingsBeforeMoving();
    }


    private static void reverseTurn() {
        if (active) {
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


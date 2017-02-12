package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;
import SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor;

public class AutoSenseWalls1
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
    
        
        //activate();
        
        logicLoop();


        
    }
    
    
    
    
    public static void activate() {
        
        try {
            
            active = true;

            
            robotControl.increaseHeadlights();
            robotControl.increaseHeadlights();

            Thread.sleep(300);

            robotControl.decreaseHeadlights();
            robotControl.decreaseHeadlights();


            robotControl.setMotorSpeed(0);
            Thread.sleep(50);
         
            robotControl.setSteering(-100);
            Thread.sleep(500);
            robotControl.setSteering(100);
            Thread.sleep(500);
            robotControl.setSteering(0);
            Thread.sleep(50);

            
            
        } catch (Exception e) {
            
        }
        
        
        
        
    }
    

    
    
    
    private static void logicLoop() {
        
        while(true) {
         
            if (active) {
            
            if(hitWall) {
                
                
                
                
                // If the robot has hit a wall stop it.
                
                
                try {
                    robotControl.setMotorSpeed(0);
                    robotControl.setSteering(0);
                    
                    Thread.sleep(2000);
                    
                    robotControl.setMotorSpeed(-20);
                    
                    Thread.sleep(800);
                    
                    robotControl.setMotorSpeed(0);
                    
                    
                    
                    
                    Thread.sleep(2000);
                    
                    hitWall = false;

                    
                    
                } catch (Exception e) {
                    
                }
                
                
            } else {
                
                // Just drive straight
                
                int distance = Math.round(distanceMonitor.getDistance());
                
                
                //  System.out.println("d:" + distance);
                
                if (distance < 40) {
                    
                    
                    robotControl.increaseHeadlights();
                    reverseTurn();
                    
                    
                } else if (distance < 150) {
                    
                    robotControl.setMotorSpeed(20);
                    
                    
                } else if (distance > 150) {
                    
                    robotControl.setMotorSpeed(20);
                    
                    
                }
                
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    
                }
                
            }
            
            
            }
            
        }
    }
    
    // ACTIONS
    
    public static void pushButtonPressed() {
        
        System.out.println("Button!!");

        
        
        
        if (hitWall == false) {
            System.out.println("fa!!");

            hitWall = true;

            
        }
        
       
        if (active == false) {
            
            
            activate();
            
            
            
        }
        
        
    
    }
    
    
    private static void reverseTurn() {
        
        try {

        robotControl.setMotorSpeed(0);
        Thread.sleep(50);
        robotControl.setMotorSpeed(-20);
        Thread.sleep(800);

        robotControl.setSteering(-100);
        robotControl.setMotorSpeed(-20);
        Thread.sleep(800);
        robotControl.setMotorSpeed(0);
        Thread.sleep(50);
        robotControl.setSteering(100);
        robotControl.setMotorSpeed(20);
        Thread.sleep(500);
        robotControl.setSteering(0);


        } catch (Exception e) {
            
        }
        
        robotControl.decreaseHeadlights();

        
        
    }
    
    private static void stop() {
        
        robotControl.setMotorSpeed(0);
        
        
    }


}


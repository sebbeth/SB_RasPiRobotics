package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;

import java.util.*;


public class CommandInterface implements Runnable {

    
    
    private Thread t;
    private String threadName;

  
    public void start()
    {
        //   System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, "newThread");
            t.start ();
        }
    }
    
    
    public void run() {
        Scanner s= new Scanner(System.in);
        
        while(true) {
            System.out.println("input:");
            char input = s.next().charAt(0);
            if (input == 'w') {
                RobotControlManager.increaseMotorSpeed(10);
                System.out.println("f:" + RobotControlManager.getMotorSpeed() + "s:" + RobotControlManager.getSteerValue());
                
            }
            if (input == 's') {
                RobotControlManager.increaseMotorSpeed(-10);
                System.out.println("f:" + RobotControlManager.getMotorSpeed() + "s:" + RobotControlManager.getSteerValue());
                
                
            }
            if (input == 'a') {
                RobotControlManager.steerLeftOrRight(10);
                System.out.println("f:" + RobotControlManager.getMotorSpeed() + "s:" + RobotControlManager.getSteerValue());
                
            }
            if (input == 'd') {
                RobotControlManager.steerLeftOrRight(-10);
                System.out.println("f:" + RobotControlManager.getMotorSpeed() + "s:" + RobotControlManager.getSteerValue());
                
                
                
            }
            if (input == '/') {
                RobotControlManager.setMotorSpeed(0);
                
            }
            if (input == '1') {
                RobotControlManager.increaseHeadlights();
                
            }
            if (input == '2') {
                RobotControlManager.decreaseHeadlights();
                
            }
            if (input == '=') {
                System.out.println("f:" + RobotControlManager.getMotorSpeed() + "s:" + RobotControlManager.getSteerValue());
                
            }
            
        }
        
        
    }
    


 

}

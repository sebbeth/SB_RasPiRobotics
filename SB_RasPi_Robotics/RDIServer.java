package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.RobotControlManager;
import SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by seb on 6/06/2016.
 */
public class RDIServer {

    private final static int tcpPortNumber = 8890;
    public static RobotControlManager robotControlManager;
    public static UltraSoundDistanceMonitor distanceMonitor;
    public static RDICrashControl rdiCrashControl = null;
    private static boolean crashControlMode = false;

    public static void main(String[] args) {

        System.out.println("SB_RasPi_Robotics.RDIServer listening for connections");
// change

        
        
        robotControlManager = new RobotControlManager();
        distanceMonitor = new UltraSoundDistanceMonitor();


        
        robotControlManager.setHeadlightBrightness(50);




        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(tcpPortNumber);
        } catch (IOException e) {
            e.printStackTrace();

        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // create a new thread for a client
            new NetworkConnectionManager(socket).start();
        }


    }


    public static void executeCommand(String input) {

        if (input.equals("left")) {
            	robotControlManager.steerLeftOrRight(10);
            //	steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());
            
        } else if (input.equals("right")) {
            	robotControlManager.steerLeftOrRight(-10);
            //	steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());
            
        } else if (input.equals("up")) {
            robotControlManager.increaseMotorSpeed(10);
            //speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());

        } else if (input.equals("down")) {
            robotControlManager.increaseMotorSpeed(-10);
            //speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());
            
        } else if (input.equals("space")) {
            robotControlManager.setMotorSpeed(0);
            //speedLabel.setText("Motor: " + robotControlManager.getMotorSpeed());
            
        } else if (input.equals("one")) {
            robotControlManager.increaseHeadlights();

        } else if (input.equals("two")) {
             robotControlManager.decreaseHeadlights();

        } else if (input.equals("a")) {
            robotControlManager.steerLeftOrRight(100);
            //steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());

        } else if (input.equals("^^crashControlOn")) {

            crashControlMode = true;

            if (rdiCrashControl == null) {
                rdiCrashControl = new RDICrashControl();
                rdiCrashControl.start();
            }

        } else if (input.equals("^^crashControlOff")) {

            crashControlMode = false;


            if (rdiCrashControl != null) {
                rdiCrashControl.cancel();
                rdiCrashControl = null;
            }


        } else if (input.equals("d")) {
            robotControlManager.steerLeftOrRight(-100);
            //steeringLabel.setText("Steering: " + robotControlManager.getSteerValue());
        } else if (input.contains("^^steeringValue~")) {



            String[] inputParts;
            
            try {
                
                if (input != null) {

                inputParts = input.split("~");
                
                if (inputParts.length == 2) {
                    
                    double value = Double.parseDouble(inputParts[1]);
                    
                    value = -(value * 100);
                    
                    int steerValue = ((int)value);
                    
                   // System.out.println("steerLeftOrRight " + steerValue);

                     robotControlManager.setSteering(steerValue);
                    
                }
            }
                
                
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (input.contains("^^headlightValue~")) {
            
            

            
            String[] inputParts;
            
            try {
                
                if (input != null) {
                    
                    inputParts = input.split("~");
                    
                    if (inputParts.length == 2) {
                        
                        double value = Double.parseDouble(inputParts[1]);
                        
                        value = (value * 100);
                        
                        int levelValue = ((int)value);
                        
                       // System.out.println("level " + levelValue);

                        
                        robotControlManager.setHeadlightBrightness(levelValue);
                        
                    }
                }
                
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }else if (input.contains("^^motorValue~")) {
            
            
            String[] inputParts;
            
            try {
                
                if (input != null) {
                    
                    inputParts = input.split("~");
                    
                    if (inputParts.length == 2) {
                        
                        double value = Double.parseDouble(inputParts[1]);
                        
                        value = (value * 100);
                        
                        int levelValue = ((int)value);
                        
                      //  System.out.println("level " + levelValue);
                        
                        
                        robotControlManager.setMotorSpeed(levelValue);
                        
                    }
                }
                
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}

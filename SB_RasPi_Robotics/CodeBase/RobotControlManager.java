package SB_RasPi_Robotics.CodeBase;

public class RobotControlManager
{
    
    private static int forwardMovement; // a value between -100 and 100 representing a percentage
    private static int steeringValue; // a value between -100 and 100 representing a percentage
    private static int leftSteerLimit;
    private static int rightSteerLimit;
    private static LEDControl headlights;
    private static MotorControl motor;
    private static UltraSoundDistanceMonitor distanceMonitor;
    private static ServoController servo;


    public RobotControlManager() {
        
        headlights = new LEDControl();
        motor = new MotorControl();
        servo = new ServoController(0);
        
     //   distanceMonitor = new SB_RasPi_Robotics.CodeBase.UltraSoundDistanceMonitor();
        
        
        
        
        
    }
    
    



    public static void setMotorSpeed(int input) {
        
        if (input >= 100) {
            forwardMovement = 100;
        } else if (input <= -100) {
                    forwardMovement = -100;
        } else {
                    forwardMovement = input;
        }
        
        motor.setSpeed(forwardMovement);
        
        
    }    
    
    public static void increaseMotorSpeed(int input) {
        
            setMotorSpeed(forwardMovement + input);
    }
    
    public static void setSteering(int input) {
        
            if (input >= 100) {
            steeringValue = 100;
        } else if (input <= -100) {
                    steeringValue = -100;
        } else {
                    steeringValue = input;
        }
    
        servo.setPosition(steeringValue);
    
    }
    

    
    public static void steerLeftOrRight(int input) {
        
          setSteering(steeringValue + input);
    }
    
    public static int getMotorSpeed() {
    
        return forwardMovement;
    }
    
    public static int getSteerValue() {
        return steeringValue;
        }
    
    
    public static void setHeadlightBrightness(int input) {
        
        headlights.setBrightness(input);
        
    }
           
           
      public static void increaseHeadlights() {
        
        headlights.increaseBrightness(10);
        }     
        
    public static void decreaseHeadlights() {
        
        headlights.increaseBrightness(-10);
        }



}

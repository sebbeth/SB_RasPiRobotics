package SB_RasPi_Robotics.CodeBase;

public class RobotControlManager
{
    
    private  int forwardMovement; // a value between -100 and 100 representing a percentage
    private  int steeringValue; // a value between -100 and 100 representing a percentage
    private  int mastPanValue;
    private  int leftSteerLimit;
    private  int rightSteerLimit;
    private  LEDControl headlights;
    private  MotorControl motor;
    private  UltraSoundDistanceMonitor distanceMonitor;
    private  ServoController steeringServo;
    private  PWMServoController mastPanServo;


    public RobotControlManager() {
        
        headlights = new LEDControl();
        motor = new MotorControl();
        steeringServo = new ServoController(22,0,90,140,115);
        mastPanServo = new PWMServoController(3,0,0,180,0);

        
        
        
        
    }
    
    



    public void setMotorSpeed(int input) {
        
        if (input >= 100) {
            forwardMovement = 100;
        } else if (input <= -100) {
                    forwardMovement = -100;
        } else {
                    forwardMovement = input;
        }
        
        motor.setSpeed(forwardMovement);
        
        
    }    
    
    public void increaseMotorSpeed(int input) {
        
            setMotorSpeed(forwardMovement + input);
    }
    
    public  void setSteering(int input) {
        
            if (input >= 100) {
            steeringValue = 100;
        } else if (input <= -100) {
                    steeringValue = -100;
        } else {
                    steeringValue = input;
        }
    
        steeringServo.setPosition(steeringValue);
    
    }


    public  void setMastPan(int input) {

        if (input >= 100) {
            mastPanValue = 100;
        } else if (input <= 0) {
            mastPanValue = 0;
        } else {
            mastPanValue = input;
        }

        mastPanServo.setPosition(mastPanValue);

    }
    

    
    public void steerLeftOrRight(int input) {
        
          setSteering(steeringValue + input);
    }
    
    public int getMotorSpeed() {
    
        return forwardMovement;
    }
    
    public int getSteerValue() {
        return steeringValue;
        }
    
    
    public void setHeadlightBrightness(int input) {

        headlights.setBrightness(input);
        
    }
           
           
      public void increaseHeadlights() {
        
        headlights.increaseBrightness(10);
        }     
        
    public void decreaseHeadlights() {
        
        headlights.increaseBrightness(-10);
        }



}

package SB_RasPi_Robotics.CodeBase;

import java.io.*;
import com.pi4j.wiringpi.SoftPwm;

public class MotorControl
{
    
    public int max = 60;
    
    private int speed;
    private int softPWMPin_ONE_number = 4;
    private int softPWMPin_TWO_number = 5;

    
	public MotorControl() {
	    
	    
	      // initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();

        // create soft-pwm pins (min=0 ; max=100)
        SoftPwm.softPwmCreate(softPWMPin_ONE_number, 0, 100);
        SoftPwm.softPwmCreate(softPWMPin_TWO_number, 0, 100);


	    
        }   
        
        public void setSpeed(int input) {
            
             if (input >= max) {
                  speed = max;
             } else if (input <= -max) {
                speed = -max;
             } else {
                speed = input;
            }
            
            setPWM(speed);
        }
        
        
        public void increaseSpeed(int input) {
         
         setSpeed(speed + input);
            
        } 
        
        
        private void setPWM(int input) {
            
            
            if (input >= 0) {
                
                SoftPwm.softPwmWrite(softPWMPin_ONE_number, input);
                SoftPwm.softPwmWrite(softPWMPin_TWO_number, 0);

            } else {
                
                SoftPwm.softPwmWrite(softPWMPin_ONE_number, 0);
                SoftPwm.softPwmWrite(softPWMPin_TWO_number, -input);
                
            }
            


            
        }


}

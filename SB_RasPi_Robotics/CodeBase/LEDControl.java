package SB_RasPi_Robotics.CodeBase;

import java.io.*;
import com.pi4j.wiringpi.SoftPwm;

public class LEDControl
{
    
        private int brightness;
        private int softPWMPinNumber = 1;

    
	public LEDControl() {
	    
	    
	      // initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();

        // create soft-pwm pins (min=0 ; max=100)
        SoftPwm.softPwmCreate(softPWMPinNumber, 0, 100);


	    
        }   
        
        public void setBrightness(int input) {
            
             if (input >= 100) {
                  brightness = 100;
             } else if (input <= 0) {
                brightness = 0;
             } else {
                brightness = input;
            }
            
            setPWM(brightness);
        }
        
        
        public void increaseBrightness(int input) {
         
         setBrightness(brightness + input);
            
        } 
        
        
        private void setPWM(int input) {
            
             SoftPwm.softPwmWrite(softPWMPinNumber, input);

            
        }


}

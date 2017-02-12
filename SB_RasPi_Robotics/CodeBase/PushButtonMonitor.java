package SB_RasPi_Robotics.CodeBase;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;


public class PushButtonMonitor {
    
  
    private final static GpioController gpio = GpioFactory.getInstance();
    
    private final GpioPinDigitalInput echoPin;
    
    
    
    
    public PushButtonMonitor() {
        
        Pin echoPin = RaspiPin.GPIO_02;
        
        
        this.echoPin = gpio.provisionDigitalInputPin( echoPin );
    
        
    }
    
    
     
    public void listenForInput() {
        
        
        while (true) {
            
            if (this.echoPin.isHigh()) {
                
                System.out.println("HIGH!");
            }
            
        }
        
    }
    
    public boolean getState() {
        
        // Returns true if button is depressed.
        return this.echoPin.isHigh();
        
    }
 
    
}
package SB_RasPi_Robotics.CodeBase;

import com.pi4j.io.gpio.*;
import java.util.Observable;

public class ObservableButton extends Observable {
    private final static GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalInput echoPin;

    public ObservableButton() {
        Pin echoPin = RaspiPin.GPIO_02;
        this.echoPin = gpio.provisionDigitalInputPin( echoPin );
    }


    public boolean getState() {
        // Returns true if button is depressed.
        return this.echoPin.isHigh();
    }
}

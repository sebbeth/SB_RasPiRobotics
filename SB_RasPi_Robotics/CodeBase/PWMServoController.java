package SB_RasPi_Robotics.CodeBase;

import com.pi4j.wiringpi.SoftPwm;

/**
 * Created by seb on 16/2/17.
 */
public class PWMServoController {

    private int minValue;
    private int maxValue;
    private int offset;


    private int value;

    private int softPWMPinNumber;


    public PWMServoController(int pinNumber, int startingPosition, int minValueInput, int maxValueInput, int offsetInput) {


        // initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();

        softPWMPinNumber = pinNumber;

        // create soft-pwm pins (min=0 ; max=100)
        SoftPwm.softPwmCreate(softPWMPinNumber, 0, 100);

        setPosition(startingPosition);

    }

    public void setPosition(int input) {




        input = (int)(input / 4.2);


        if (input != value ) {

            value = input;
            setPWM(value);


        }




    }

    private void setPWM(int input) {


        SoftPwm.softPwmWrite(softPWMPinNumber, input);


    }




}
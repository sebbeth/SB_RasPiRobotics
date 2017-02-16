package SB_RasPi_Robotics.CodeBase; /**
 * Class to monitor distance measured by an HC-SR04 distance sensor on a
 * Raspberry Pi.
 *
 * The main method assumes the trig pin is connected to the pin # 7 and the echo
 * pin is connected to pin # 11.  Output of the program are comma separated lines
 * where the first value is the number of milliseconds since unix epoch, and the
 * second value is the measured distance in centimeters.
 */

import SB_RasPi_Robotics.CodeBase.DistanceMonitor;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * SB_RasPi_Robotics.CodeBase.DistanceMonitor class to monitor distance measured by sensor
 *
 * @author Rutger Claes <rutger.claes@cs.kuleuven.be>
 */
public class UltraSoundDistanceMonitor {
    
    private final static float SOUND_SPEED = 340.29f;  // speed of sound in m/s
    
    private final static int TRIG_DURATION_IN_MICROS = 10; // trigger duration of 10 micro s
    private final static int WAIT_DURATION_IN_MILLIS = 60; // wait 60 milli s
    
    private final static int TIMEOUT = 400;
    
    private final static GpioController gpio = GpioFactory.getInstance();
    
    private final GpioPinDigitalInput echoPin;
    private final GpioPinDigitalOutput trigPin;
    
    
    
    
    public UltraSoundDistanceMonitor() {
        
        Pin echoPin = RaspiPin.GPIO_00; // PI4J custom numbering (pin 11)
        Pin trigPin = RaspiPin.GPIO_07; // PI4J custom numbering (pin 7)
        
        
        this.echoPin = gpio.provisionDigitalInputPin( echoPin );
        this.trigPin = gpio.provisionDigitalOutputPin( trigPin );
        this.trigPin.low();
    }
    
    /*
     * This method returns the distance measured by the sensor in cm
     *
     * @throws TimeoutException if a timeout occurs
     */
    private float measureDistance() throws TimeoutException {
        
        
        
        this.triggerSensor();
        this.waitForSignal();
        long duration = this.measureSignal();
        
        return duration * SOUND_SPEED / ( 2 * 10000 );
    }
    
    public float getDistance() {
        
        float output;
        
        try {
            
            
            output = measureDistance();
           
            
        } catch (Exception e) {
            System.err.println("Distance check timeout");
            output = 0;
        }
        return output;
    }

    public float getAveragedDistance(int iterations) {


        /*
        A function that takes a number of readings then averages them and returns that value
         */

        if (iterations > 0) {

            float numerator = 0;

            for (int i = 0; i < iterations; i++ ) { // First get all the values

                numerator = numerator + getDistance();

            }

            // Now divide numerator by iterations

            return numerator / iterations;

        } else {

            return 0;
        }


    }
    
    /**
     * Put a high on the trig pin for TRIG_DURATION_IN_MICROS
     */
    private void triggerSensor() {
        try {
            this.trigPin.high();
            Thread.sleep( 0, TRIG_DURATION_IN_MICROS * 1000 );
            this.trigPin.low();
        } catch (InterruptedException ex) {
            System.err.println( "Interrupt during trigger" );
        }
    }
    
    /**
     * Wait for a high on the echo pin
     *
     * @throws DistanceMonitor.TimeoutException if no high appears in time
     */
    private void waitForSignal() throws TimeoutException {
        int countdown = TIMEOUT;
        
        while( this.echoPin.isLow() && countdown > 0 ) {
            countdown--;
        }
        
        if( countdown <= 0 ) {
            throw new TimeoutException( "Timeout waiting for signal start" );
        }
    }
    
    /**
     * @return the duration of the signal in micro seconds
     * @throws DistanceMonitor.TimeoutException if no low appears in time
     */
    private long measureSignal() throws TimeoutException {
        int countdown = TIMEOUT;
        long start = System.nanoTime();
        while( this.echoPin.isHigh() && countdown > 0 ) {
            countdown--;
        }
        long end = System.nanoTime();
        
        if( countdown <= 0 ) {
            throw new TimeoutException( "Timeout waiting for signal end" );
        }
        
        return (long)Math.ceil( ( end - start ) / 1000.0 );  // Return micro seconds
    }
    
    
    
    
    /**
     * Exception thrown when timeout occurs
     */
    private static class TimeoutException extends Exception {
        
        private final String reason;
        
        public TimeoutException( String reason ) {
            this.reason = reason;
        }
        
        @Override
        public String toString() {
            return this.reason;
        }
    }
    
}
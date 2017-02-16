package SB_RasPi_Robotics.CodeBase;//SB_RasPi_Robotics.CodeBase.ServoController

public class ServoController
{
    
    private int minValue;
    private int maxValue;
    private int offset;

    public ServoController(int pinNumber, int startingPosition, int minValueInput, int maxValueInput, int offsetInput) {
        

        minValue= minValueInput;
        maxValue = maxValueInput;
        offset = offsetInput;

        // Setup Servoblaster


/*
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(pinNumber);
        String argument = sb.toString();
*/
        try
        {
            String[] cmd = new String[]{"/bin/sh", "SB_RasPi_Robotics/CodeBase/SBServoSetup.sh"};
            Process pr = Runtime.getRuntime().exec(cmd);
        }catch (Exception e)
        {
            e.printStackTrace();
        }




        setPosition(startingPosition);
        
        
        
          }
    
    public void setPosition(int value) {
        
        // input expected to be between -100 and 100
        
        value = value/4;
        

        if ((value + offset) < minValue ) {
            
            
            value = minValue;
            
            
            
            
        } else if ((value + offset) > maxValue ) {

            value = maxValue;
            
        } else {
            
            value = value + offset;

        }
        
       
        
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(value);
        String argument = sb.toString();
        
        try
        {
            String[] cmd = new String[]{"/bin/sh", "SB_RasPi_Robotics/CodeBase/SBServoSetter.sh", argument};
            Process pr = Runtime.getRuntime().exec(cmd);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}

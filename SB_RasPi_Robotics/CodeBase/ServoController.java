package SB_RasPi_Robotics.CodeBase;//SB_RasPi_Robotics.CodeBase.ServoController

public class ServoController
{
    
    private final int minValue = 90;
    private final int maxValue = 140;
    private final int offset = 115;

    public ServoController(int value) {
        
        
        // Setup Servoblaster
        
        try
        {
            String[] cmd = new String[]{"/bin/sh", "SB_RasPi_Robotics/CodeBase/SBServoSetup.sh"};
            Process pr = Runtime.getRuntime().exec(cmd);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        
        
        
        setPosition(value);
        
        
        
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

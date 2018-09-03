package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.PushButtonMonitor;
import javafx.scene.Parent;

import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;




public class RunnablePushButtonMonitor extends Thread {

    
    private PushButtonMonitor pushButtonMonitor;
    private PathFinder parent;
    private Timer longHoldTimer;

    public RunnablePushButtonMonitor(PathFinder p) {
        parent = p;
    }


    @Override
    public void run() {
        
        pushButtonMonitor = new PushButtonMonitor();
        Timer timer = null;


        while (true) {
            
            if (pushButtonMonitor.getState()) {

                    parent.pushButtonPressed();

            }
            
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                
            }
        }
        
    }
    
    private void triggerLongHoldTest() {
        
        
        
        
    }
    

}

package SB_RasPi_Robotics;

import SB_RasPi_Robotics.CodeBase.PushButtonMonitor;

import java.util.Timer;
import java.util.TimerTask;




public class RunnablePushButtonMonitor extends Thread {

    
    private PushButtonMonitor pushButtonMonitor;

    private Timer longHoldTimer;

    public RunnablePushButtonMonitor() {
        
    }
    
    
    public void run() {
        
        pushButtonMonitor = new PushButtonMonitor();
        Timer timer = null;


        while (true) {
            
            if (pushButtonMonitor.getState()) {
                
                AutoSenseWalls1.pushButtonPressed();

                if (timer == null) {
                    
                     timer = new Timer();
                    
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                           
                            if (pushButtonMonitor.getState()) {
                                // This is a long hold
                                
                                System.out.println("Terminate");
                                
                                System.exit(0);

                            }
                       
                        }
                    }, 4000);
                }
                
                
              
              //  longHoldTimer.schedule(new RunnablePushButtonMonitor_check(), 2000);
                
            } else {
                timer = null;
            }

        
            
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                
            }
        }
        
    }
    
    private void triggerLongHoldTest() {
        
        
        
        
    }
    

}

package SB_RasPi_Robotics;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by seb on 26/05/2016.
 */

public class NetworkConnectionManager extends Thread {

    private Thread t;

    public int portNumber;
    private DataInputStream is;
    //private DataOutputStream os;
    private PrintStream os;
    private ServerSocket socketServer;
    private Socket clientSocket;
    public boolean runnining;



    public NetworkConnectionManager(Socket clientSocket) {

        this.clientSocket = clientSocket;
        

        runnining = true;

    }


    public void run() {


        socketServer = null;

        try {
            socketServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            
            System.out.println(e);
        }

        try {

            is = new DataInputStream(clientSocket.getInputStream());

           // os = new DataOutputStream(clientSocket.getOutputStream());
            os = new PrintStream(clientSocket.getOutputStream());


            System.out.println("Connection established");


            listenForInput();



        } catch (IOException e) {
            System.out.println(e);
        }


    }


    private void listenForInput() {

        String line = "";

        if (runnining == false) {

            return;
        }

        while (runnining) {
          
           // os.println("disance^" + SB_RasPi_Robotics.RDIServer.distanceMonitor.getDistance());


            try {
                

                line = is.readLine();


                if (line != null) {





                   RDIServer.executeCommand(line);
                    
                 

                }

            } catch (IOException e) {
                System.out.println( e);
                closeConnection();
                
            }


        }


    }






    public void closeConnection() {

        try {
            socketServer.close();
            clientSocket.close();
            t = null;
        } catch (IOException e) {

        }

        runnining = false;


    }


}

package com.mygdx.game.network;
import com.mygdx.game.DownFall;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * The Server opens a socket for server-socket communication.
 */
public class Server extends Thread {

    private static final String getIPAddressURL = "google.pt";
    private static final int getIPAddressPort = 80;

    private static String serverIP;
    ServerSocket listener;
    DownFall game;

    /**
     * Creates a Server
     * @param game Game to be affected during the connection
     */
    public Server (DownFall game) throws  Exception{
        getIPAddress();
        listener = new ServerSocket(8050);
        this.game = game;
    }
    /**
     * Creates 2 ServerThreads, one for each player
     */
    public void run(){
        for(int i = 0; i < 2; i++) {
            System.out.println("i:" +i);
            try {
                ServerThread st = new ServerThread(this.game, listener.accept(), i);
                System.out.println("recognized");
                st.start();
            } catch (Exception e) {
            }
        }

        try {
            listener.close();
        } catch (Exception e) {
        }
        game.startGame = true;
    }

    /**
     * Gets the IP address of the server
     * @return the  server address
     */

    public static String getServerIP() {
        return serverIP;
    }


    /**
     * Establishes a connection to google.pt to know its IP address
     */

    public void getIPAddress(){

        try {
            Socket s = new Socket(getIPAddressURL, getIPAddressPort);
            serverIP = s.getLocalAddress().getHostAddress();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}

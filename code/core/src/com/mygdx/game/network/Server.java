package com.mygdx.game.network;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String getIPAddressURL = "google.pt";
    private static final int getIPAddressPort = 80;
    /** Port to be used when checking what IP is being used by the machine. **/

    private static String serverIP;

    public Server() throws Exception {
        System.out.println("The capitalization server is running.");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(8500);

        try {
            ServerThread st = new ServerThread(listener.accept(), clientNumber++);
            //st.start();
        } finally {
            listener.close();
        }
    }

    public void print(){
        System.out.println("Current IP address : " + serverIP);
    }

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

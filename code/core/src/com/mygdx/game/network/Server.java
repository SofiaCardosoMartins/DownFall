package com.mygdx.game.network;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String getIPAddressURL = "google.pt";
    private static final int getIPAddressPort = 80;

    private static String serverIP;
    int clientNumber = 0;
    ServerSocket listener;

    public Server() throws  Exception{
        getIPAddress();
        listener = new ServerSocket(8500);
    }

    public void run(){
        try {
            ServerThread st = new ServerThread(listener.accept(), clientNumber++);
            //st.start();
        } catch (Exception e) {
        } finally {
            try {
                listener.close();
            }
            catch (Exception e){

            }
        }
    }

    public static String getServerIP() {
        return serverIP;
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

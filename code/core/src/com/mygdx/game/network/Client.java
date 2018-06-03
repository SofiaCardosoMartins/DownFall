package com.mygdx.game.network;

import com.mygdx.game.DownFall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * The Client opens a socket for client-server communication
 */
public class Client {
    private static BufferedReader in;
    private static PrintWriter out;
    static String serverAddress = "";

    /**
     * Creates a Client
     * @param game Game to be affected during the connection
     * @param serverAddress IP address to start connection with
     */

    public Client(DownFall game, String serverAddress) {
        // Make connection and initialize streams
        this.serverAddress = serverAddress;

        try {
            Socket socket = new Socket(serverAddress, 8050);
            ClientThread ct = new ClientThread(game, socket);
            ct.start();
        }  catch(Exception e) {
            e.printStackTrace();
        }

    }

}

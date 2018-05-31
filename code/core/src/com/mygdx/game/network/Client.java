package com.mygdx.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static BufferedReader in;
    private static PrintWriter out;
    static String serverAddress = "";

    public static void Client(String serverAddress) throws IOException {
        // Make connection and initialize streams
        Socket socket = new Socket(serverAddress, 8050);
        try {
            ClientThread ct = new ClientThread();
            ct.start();
        } finally {
            socket.close();
        }

    }

}

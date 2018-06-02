package com.mygdx.game.network;

import com.mygdx.game.DownFall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static BufferedReader in;
    private static PrintWriter out;
    static String serverAddress = "";

    public Client(DownFall game, String serverAddress) throws IOException {
        // Make connection and initialize streams
        this.serverAddress = serverAddress;
        System.out.println("server:" + serverAddress);
            Socket socket = new Socket(serverAddress, 8050);

        try {
            ClientThread ct = new ClientThread(game, socket);
            ct.start();
        }  finally {
            socket.close();
        }

    }

}

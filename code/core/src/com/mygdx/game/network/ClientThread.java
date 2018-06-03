package com.mygdx.game.network;

import com.mygdx.game.DownFall;
import java.io.*;
import java.net.Socket;
/**
 * The ClientThread starts threads for the receiving and sending of input themselves
 */

public class ClientThread extends Thread {

    Socket socket;
    DownFall game;

    /**
     * Created the Client Thread
     * @param game game on which the thread will operate
     * @param socket socket on which the info will be (mainly) sent
     */
    public ClientThread(DownFall game, Socket socket){
        this.game = game;
        this.socket = socket;
    }

    /**
     *  Opens the Write Thread to write info
     *  Closes the socket afterwards
     */

    public void run(){
        ReadThread rd;
        WriteThread wr;
        try {
            wr = new WriteThread(socket);
        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        while(!game.createdGame){

        }

        wr.start();

        try {
            socket.close();
        } catch (IOException e){
           e.printStackTrace();
        }
    }
}

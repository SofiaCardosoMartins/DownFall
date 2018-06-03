package com.mygdx.game.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;

import java.io.*;
import java.net.Socket;

/**
 * The ClientThread starts threads for the receiving and sending of input themselves
 */

public class ServerThread extends Thread {
    private Socket socket;
    private int clientNumber;
    private DownFall game;
    int playerNum;

    /**
     * Created the Server Thread
     * @param game game on which the thread will operate
     * @param socket socket on which the info will be (mainly) read
     */
    ServerThread(DownFall game, Socket socket, int playerNum){
        this.socket = socket;
        System.out.println("New connection with client# " + playerNum + " at " + socket);
        this.game = game;
        this.playerNum = playerNum;
        this.playerNum++;

    }

    /**
     *  Opens the Write Thread to write info
     *  Closes the socket afterwards
     */

    public void run(){
        ReadThread rd;

        try {
            rd = new ReadThread(socket, playerNum);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Lost connection with write server");
            return;
        }

        rd.start();

        try {
            socket.close();
        } catch (IOException e){
            System.out.println("Couldn't close a socket, what's going on?");
        }
    }

}

package com.mygdx.game.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameModel;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    Socket socket;
    DownFall game;

    public ClientThread(DownFall game, Socket socket){
        this.game = game;
        this.socket = socket;
        System.out.println("running client");
    }
    public void run(){
        game.startGame = true;
        ReadThread rd;
        WriteThread wr;
        try {
            wr = new WriteThread(socket, 2);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Lost connection with write client");
            return;
        }

        try {
            rd = new ReadThread(socket, 2);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Lost connection with read client");
            return;
        }


        while(!game.createdGame){

        }

        wr.start();
        rd.start();

        try {
            socket.close();
        } catch (IOException e){
            System.out.println("Couldn't close a socket, what's going on?");
        }
    }
}

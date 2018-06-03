package com.mygdx.game.network;

import com.mygdx.game.controller.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * A ReadThread deals with reading info from a socket
 */

public class ReadThread extends Thread {
    BufferedReader in;
    int myPlayer;

    public ReadThread(Socket socket, int myPlayer) {
        try {
           in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.myPlayer = myPlayer;
    }

    public void  run(){
        while (true) {
            String input = null;
            try {
                input = in.readLine();
            } catch (IOException e) {
                //e.printStackTrace();
            } finally {
                if (input != null) {
                    System.out.println("input:" + input);
                    if (input.equals("END"))
                        break;
                    if (input.equals("LEFT"))
                        GameController.getInstance().handleInput(GameController.Direction.LEFT, myPlayer);
                    else if (input.equals("RIGHT"))
                        GameController.getInstance().handleInput(GameController.Direction.RIGHT, myPlayer);
                    else if (input.equals("UP"))
                        GameController.getInstance().handleInput(GameController.Direction.UP, myPlayer);
                }
            }
        }
    }

}

package com.mygdx.game.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.DownFall;
import com.mygdx.game.controller.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    Socket socket;
    DownFall game;

    public ClientThread(DownFall game){
        this.game = game;
    }
    public void run(){
        try {
            /* in and out opening might throw IOException */
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
                if (accelerometerAvail) {
                    float acceX = Gdx.input.getAccelerometerX();
                    float acceY = Gdx.input.getAccelerometerY();
                    float acceZ = Gdx.input.getAccelerometerZ();
                    if (acceX < 0) {
                        out.write("RIGHT");
                        GameController.getInstance().handleInput(GameController.Direction.RIGHT, 2);
                    }
                    else if (acceX > 0) {
                        out.write("LEFT");
                        GameController.getInstance().handleInput(GameController.Direction.LEFT, 2);
                    }
                }
                if (Gdx.input.isTouched()) {
                    out.write("UP");
                    GameController.getInstance().handleInput(GameController.Direction.UP, 2);
                }

                String input = in.readLine();
                if (input.equals("END"))
                    break;

                out.println(input.toUpperCase());

                if (input.equals("LEFT"))
                    GameController.getInstance().handleInput(GameController.Direction.LEFT, 1);
                else if (input.equals("RIGHT"))
                    GameController.getInstance().handleInput(GameController.Direction.RIGHT, 1);
                else if (input.equals("UP"))
                    GameController.getInstance().handleInput(GameController.Direction.UP, 1);

            }
        } catch (IOException e){
        System.out.println("Error handling input/output streams");
        } finally{
            try {
                socket.close();
            } catch (IOException e){
            System.out.println("Couldn't close a socket, what's going on?");
                }
        //System.out.println("Connection with client# " + clientNumber + " closed");
    }
}
}

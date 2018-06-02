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

public class ServerThread extends Thread {
    private Socket socket;
    private int clientNumber;
    private DownFall game;


    ServerThread(DownFall game, Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("New connection with client# " + clientNumber + " at " + socket);
        this.game =game;
        game.switchToGameView(2);
    }

    public void run(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Hello, you are client #" + clientNumber + ".");
            out.println("Enter a line with only a period to quit\n");

            while(true){
                boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
                if (accelerometerAvail) {
                    float acceX = Gdx.input.getAccelerometerX();
                    float acceY = Gdx.input.getAccelerometerY();
                    float acceZ = Gdx.input.getAccelerometerZ();
                    if (acceX < 0) {
                        GameController.getInstance().handleInput(GameController.Direction.RIGHT, 1);
                        out.write("RIGHT");
                    }
                    else if (acceX > 0) {
                        GameController.getInstance().handleInput(GameController.Direction.LEFT, 1);
                        out.write("LEFT");
                    }
                }
                if (Gdx.input.isTouched()) {
                    out.write("UP");
                    GameController.getInstance().handleInput(GameController.Direction.UP, 1);
                }

                String input = in.readLine();
                if (input == null)
                    continue;
                if (input.equals("END"))
                    break;

                out.println(input.toUpperCase());

                if (input.equals("LEFT"))
                    GameController.getInstance().handleInput(GameController.Direction.LEFT, 2);
                else if (input.equals("RIGHT"))
                    GameController.getInstance().handleInput(GameController.Direction.RIGHT, 2);
                else if (input.equals("UP"))
                    GameController.getInstance().handleInput(GameController.Direction.UP, 2);
            }
        }catch (IOException e){
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally{
            try {
                socket.close();
            } catch (IOException e){
                System.out.println("Couldn't close a socket, what's going on?");
            }
            System.out.println("Connection with client# " + clientNumber + " closed");
        }
    }

}

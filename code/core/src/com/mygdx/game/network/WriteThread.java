package com.mygdx.game.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.controller.GameController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A WriteThread deals with writing info from a socket
 */

public class WriteThread extends Thread {

    int myPlayer;
    PrintWriter out;

    public WriteThread(Socket socket, int myPlayer) {
            try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.myPlayer = myPlayer;
    }

    public void run() {
        while (true) {
            boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
            if (accelerometerAvail) {
                float acceX = Gdx.input.getAccelerometerX();
                float acceY = Gdx.input.getAccelerometerY();
                float acceZ = Gdx.input.getAccelerometerZ();

                if (acceX < -0.1) {
                    System.out.println("sent right");
                    out.write("RIGHT");
                    GameController.getInstance().handleInput(GameController.Direction.RIGHT,  myPlayer);
                } else if (acceX > 0.1) {
                    System.out.println("sent left");
                    out.write("LEFT");
                    GameController.getInstance().handleInput(GameController.Direction.LEFT,  myPlayer);
                }
            }
            if (Gdx.input.isTouched()) {
                System.out.println("sent up");
                out.write("UP");
                GameController.getInstance().handleInput(GameController.Direction.UP, myPlayer);
            }
        }
    }

}

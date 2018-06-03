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

    PrintWriter out;

    /**
     * Creates a thread to write input info to server
     * @param socket on which the info will be written
     */
    public WriteThread(Socket socket) {
            try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes info on a socket when new input arrives
     */
    public void run() {
        while (true) {
            boolean accelerometerAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
            if (accelerometerAvail) {
                float acceX = Gdx.input.getAccelerometerX();
                float acceY = Gdx.input.getAccelerometerY();
                float acceZ = Gdx.input.getAccelerometerZ();

                if (acceX < 0) {
                    System.out.println("sent right");
                    out.write("RIGHT");
                } else if (acceX > 0) {
                    System.out.println("sent left");
                    out.write("LEFT");
                }
            }
            if (Gdx.input.isTouched()) {
                System.out.println("sent up");
                out.write("UP");
            }
        }
    }

}

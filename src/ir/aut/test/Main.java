package ir.aut.test;

import ir.aut.test.testProgram.ClientThread;
import ir.aut.test.testProgram.ServerThread;

public class Main {

    public static void main(String[] args) {
        int port = 5226;
        ServerThread serverThread = new ServerThread(port);
        ClientThread clientThread = new ClientThread(port);
        clientThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverThread.sentMessage("amirPHL", "123654");
        clientThread.sendMessage("mohsenPHL", "987456");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientThread.closeConnection();
        serverThread.sentMessage("amirPHL", "123654");
    }
}

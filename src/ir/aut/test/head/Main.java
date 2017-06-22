package ir.aut.test.head;

import ir.aut.test.testProgram.ClientThread;
import ir.aut.test.testProgram.ServerThread;


public class Main {
    public static void main(String[] args) {
        new Thread() {
            public void run() {
                Manager manager = new Manager();
            }
        }.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                Manager manager = new Manager();
            }
        }.start();
    }

    private static void testNetwork() {
        int port = 5227;
        ServerThread serverThread = new ServerThread(port, "AmirPHL");
        ClientThread clientThread = new ClientThread(port, "MohsenPHL");
        clientThread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serverThread.start();
//        serverThread.sentMessage("amirPHL", "123654");
//        clientThread.sendMessage("mohsenPHL", "987456");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        clientThread.closeConnection();
//        serverThread.sentMessage("amirPHL", "123654");
    }
}

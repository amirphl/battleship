package ir.aut.test;

import ir.aut.test.logic.MessageManager;
import ir.aut.test.testProgram.ClientThread;
import ir.aut.test.testProgram.ServerThread;
import ir.aut.test.view.Frame;


public class Main {
    public static void main(String[] args) {
        testNetwork();

//        new Thread() {
//            public void run() {
//                a = new MessageManager(5325);
//            }
//        }.start();
//
//        new Thread() {
//            public void run() {
//                b = new MessageManager("127.0.0.1", 5325);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        a.acceptRequest(0);
//        b.sendReadinessCondition(true, "hi");
//        b.sendReadinessCondition(true, "hi");
//        b.sendReadinessCondition(true, "hi");
//        b.sendReadinessCondition(true, "hi");
//        b.sendReadinessCondition(true, "hi");
//        b.sendReadinessCondition(true, "hi");
//        String me1 = "1";
//        String me2 = "2";
//        for (int i = 0; i < 20; i++) {
//            if (i % 2 == 0) {
//                a.sendRequestLogin(me1, me2);
//            } else {
//                b.sendReadinessCondition(true, me1);
//            }
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
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

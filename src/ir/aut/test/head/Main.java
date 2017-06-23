package ir.aut.test.head;

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
}

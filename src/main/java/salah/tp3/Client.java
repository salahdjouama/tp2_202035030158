package salah.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader console;
    String userInput;
    HelloController controller;






    public Client(String serverAddress, int port ,HelloController cntr) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
            controller = cntr;
            new Thread(new IncomingReader()).start();

            System.out.println("Connected to chat server.");

            sendmsg(HelloController.infos[0]);
            //sendmsg(HelloController.infos[3]);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendmsg(String msg){
        userInput = msg;
        out.println(userInput);
    }

    public static String text;
    private class IncomingReader implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    controller.logsarea.appendText(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

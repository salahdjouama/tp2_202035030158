package salah.tp3;

import java.io.*;
import java.net.*;

public class Client {



    Client(String srv_add, int port) {
        try (Socket socket = new Socket(srv_add, port)) {
            System.out.println("Connected to the chat server");
            new Thread(new Receiver(socket)).start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String msg;

            while ((msg = input.readLine()) != null) {
                out.println(msg);
            }
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    private static class Receiver implements Runnable {
        private Socket socket;

        public Receiver(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                System.err.println("Error receiving message: " + e.getMessage());
            }
        }
    }
}

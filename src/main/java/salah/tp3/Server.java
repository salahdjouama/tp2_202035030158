package salah.tp3;


import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    public static String client;

    public static void main(String[] args) {
        System.out.println("Chat server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void sendPrivateMessage(String message, String recipient, ClientHandler sender) {
        for (ClientHandler client : clientHandlers) {
            if (client.getClientName().equals(recipient)) {
                client.sendMessage("prv "+ sender.getClientName() + ": " + message);
                return;
            }
        }
        sender.sendMessage("User " + recipient + " not found.");
    }

    static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        broadcast(clientHandler.getClientName() + " has left the chat.", clientHandler);
    }



    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String clientName;


        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

               // out.print("Enter your name: ");
                clientName = in.readLine();


                Server.broadcast(clientName + " has joined the chat.", this);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/p")) {
                        String[] tokens = message.split(" ", 3);
                        if (tokens.length == 3) {
                            String recipientName = tokens[1];
                            String privateMessage = tokens[2];
                            Server.sendPrivateMessage(privateMessage, recipientName, this);
                        }
                    } else {
                        Server.broadcast(clientName + ": " + message, this);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Server.removeClient(this);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Server.removeClient(this);
            }
        }

        public String getClientName() {
            return clientName;
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

}

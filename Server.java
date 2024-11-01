import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

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
                client.sendMessage("prv: " sender.getClientName() + ": " + message);
                return;
            }
        }
        sender.sendMessage("User " + recipient + " not found.");
    }

    static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        broadcast(clientHandler.getClientName() + " has left the chat.", clientHandler);
    }
}

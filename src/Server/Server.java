package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 12323;
    private DatabaseHandler databaseHandler;
    private List<ClientHandler> clientHandlers;

    public Server() {
        databaseHandler = new DatabaseHandler();
        clientHandlers = new ArrayList<>();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);
            while (true) {
                try {Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler(clientSocket, databaseHandler);
                clientHandlers.add(clientHandler);
                Thread thread = new Thread(clientHandler);
                thread.start();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            stop();
        }
    }

 public void stop() {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.stop();
        }
        clientHandlers.clear();
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

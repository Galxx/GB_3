package HW8;


import java.util.logging.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private static final Logger logger = Logger.getLogger(MyServer.class.getName());

    private final int PORT = 8189;
    private List<ClientHandler> clients;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {

        logger.setLevel(Level.ALL);

        try {
            Handler h = new FileHandler("1.txt");
            h.setFormatter(new SimpleFormatter());
            h.setLevel(Level.ALL);
            logger.addHandler(h);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ServerSocket server = new ServerSocket(PORT)) {
            authService = new AuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                //System.out.println("Server awaits clients");
                logger.log(Level.INFO, "Server awaits clients");
                Socket socket = server.accept();
                //System.out.println("Client connected");
                logger.log(Level.INFO, "Client connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException ex) {
            //System.out.println("Server error");
            logger.log(Level.SEVERE, "Server error");
        } catch (Exception ex) {
            //System.out.println("Server error");
            logger.log(Level.SEVERE, "Server error");
        } finally {
            if(authService!=null) {
                authService.stop();
            }
        }
    }


    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientsList();
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientsList();
    }

    public synchronized void broadcast(String s) {
        for(ClientHandler client: clients) {
            client.sendMsg(s);
        }
    }

    public synchronized void broadcastClientsList() {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getName() + " ");
        }
        broadcast(sb.toString());
    }

    public synchronized boolean isNickLogged(String nick) {
        for(ClientHandler client: clients) {
            if (client.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void sendDirect(String nick, String message) {
        for (ClientHandler client: clients) {
            if (client.getName().equals(nick)) {
                client.sendMsg(message);
                return;
            }
        }
        //System.out.println("Unknown nick - message not sent");
        logger.log(Level.WARNING, "Server awaits clients");
    }

    public String  getWords(String nameTable) {
        String stringWords = "";

        try {
            Connection connection = AuthService.getConnection();
            try (Statement Statement = connection.createStatement()) {
                ResultSet rs = Statement.executeQuery("SELECT word FROM "+nameTable);
                while (rs.next()) {
                    stringWords += ";" + rs.getString("word");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return stringWords;
    }


}

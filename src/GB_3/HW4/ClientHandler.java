package GB_3.HW4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;
    private String login;

    private long TIMEOUT = 120000;
    private long timeCreate;

    private String badWords  = "";
    private String goodWords = "";

    ExecutorService executorService;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        this.myServer = myServer;
        this.socket = socket;
        this.name = "";
        timeCreate = System.currentTimeMillis();

        executorService = Executors.newFixedThreadPool(2);
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            Thread t = new Thread(()-> {
                try {
                    authenticate();
                    readMessages();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    closeConnection();
                }
            });
            executorService.execute(t);
        } catch (IOException ex) {
            throw new RuntimeException("Client creation error");
        }
    }

    private void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
            executorService.shutdown();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        myServer.unsubscribe(this);
        myServer.broadcast("User " + name + "left");
    }

    private void readMessages() throws IOException {
        while (true) {
            if (in.available()>0) {
                String message = in.readUTF();
                System.out.println("From " + name + ":" + message);
                if (message.equals("/end")) {
                    return;
                }
                if (message.startsWith("/w ")) {
                    String[] parts = message.split("\\s");
                    myServer.sendDirect(parts[1],name+ ": "+ parts[2]);
                }else if(message.startsWith("/goodwords")){
                    goodWords = myServer.getWords("goodwords");
                    sendMsg("/goodwords " +goodWords);
                }else if(message.startsWith("/badwords")){
                    badWords = myServer.getWords("badwords");
                    sendMsg("/badwords " +badWords);
                }else if(message.startsWith("/c ")){
                    String[] parts = message.split("\\s");
                    ChangeNick(parts[1]);
                    myServer.broadcast(name + ": change nick to " + parts[1]);
                    name = parts[1];
                } else myServer.broadcast(name + ": " + message);
            }
        }
    }

    private void ChangeNick(String newNick){

        if(!newNick.isEmpty() && !login.isEmpty()){

            myServer.getAuthService().ChangeNick(login,newNick);

        }

    }

    private void authenticate() throws IOException {
        while(true) {

            if (in.available()>0){
                String str = in.readUTF();
                if (str.startsWith("/auth")) {
                    String[] parts = str.split("\\s");
                    String nick = myServer.getAuthService().getNickByLoginAndPwd(parts[1], parts[2]);
                    if (nick != null) {
                        if (!myServer.isNickLogged(nick)) {
                            System.out.println(nick + " logged into chat");
                            name  = nick;
                            login = parts[1];
                            sendMsg("/authOk " + nick);
                            myServer.broadcast(nick + " is in chat");
                            myServer.subscribe(this);
                            return;
                        } else {
                            System.out.println("User " + nick + " tried to re-enter");
                            sendMsg("User already logged in");
                        }
                    } else {
                        System.out.println("Wrong login/password");
                        sendMsg("Incorrect login attempted");
                    }
                }
            }

        }
    }

    public void sendMsg(String s) {
        try {
            out.writeUTF(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

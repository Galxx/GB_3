package HW8;

import HW8.multiscene.ChatSceneApp;
import HW8.multiscene.SceneFlow;
import HW8.multiscene.Stageable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatController implements Stageable {
    private Stage stage;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private HashMap<String,String> badwords;
    private HashMap<String,String> goodwords;
    private  FileOutputStream fos;


    @FXML
    TextArea messageArea;

    @FXML
    TextField newMessage;

    @FXML
    Button sendButton;

    @FXML
    ListView nickList;

    @FXML
    TextField Nick;

    public void initialize() throws IOException {

        fos = new FileOutputStream("history.txt",true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.available()>0) {
                            String strFromServer = in.readUTF();
                            System.out.println("From server: " + strFromServer);

                            if (strFromServer.equalsIgnoreCase("/end")) {
                                break;
                            }
                            if (strFromServer.startsWith("/badwords")) {
                                badWordsHandler(strFromServer);
                            } else if (strFromServer.startsWith("/goodwords")) {
                                goodWordsHandler(strFromServer);
                            }else{
                                Platform.runLater(()->{ messageArea.appendText(strFromServer + System.lineSeparator());});
                                writeHistory(strFromServer + "\n");
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        ObservableList<String> nickListItems = FXCollections.observableArrayList();
        nickListItems.add("All");
        socket = ChatSceneApp.getScenes().get(SceneFlow.CHAT).getSocket();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        String myNick = ChatSceneApp.getScenes().get(SceneFlow.CHAT).getNick();
        while (true) {
            if(in.available()>0) {
                String strFromServer = in.readUTF();
                if (strFromServer.startsWith("/clients")) {
                    String[] parts = strFromServer.split("\\s");
                    for(int i=1; i<parts.length; i++) {
                        if (!parts[i].equals(myNick)) nickListItems.add(parts[i]);
                    }
                    System.out.println("Authorized on server");
                    break;
                }
            }
        }
        nickList.setItems(nickListItems);
        nickList.getSelectionModel().select(0);
        Nick.setText(myNick);

        out.writeUTF("/badwords");
        out.writeUTF("/goodwords");

        loadHistory();

    }

    private void loadHistory() {

        try (FileInputStream fin = new FileInputStream("history.txt")) {

            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            ArrayList<String>  arr = new ArrayList<>();
            String str1;
            while ((str1 = br.readLine()) != null) {
                arr.add(str1);
            }

            int lineCounter = 0;
            int totalLine = 100;
            StringBuilder sb = new StringBuilder();

            while(lineCounter < arr.size()){
                if (lineCounter == totalLine){
                    break;
                }
                sb.append(arr.get(arr.size() - 1 - lineCounter)+"\n");
                lineCounter++;
            }
            messageArea.appendText(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeHistory(String strFromServer)  {

        try {
            byte[] buffer = strFromServer.getBytes();
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void goodWordsHandler(String strFromServer) {
        goodwords = new HashMap<>();
        String[] parts = strFromServer.split("[[;]]");
        for (int i = 1; i <= parts.length-1; i++) {
            goodwords.put(parts[i], Character.toUpperCase(parts[i].charAt(0)) +parts[i].substring(1,parts[i].length()));
        }

    }

    private void badWordsHandler(String strFromServer) {
        badwords = new HashMap<>();
        String[] parts = strFromServer.split("[[;]]");
        for (int i = 1; i <= parts.length-1; i++) {
            badwords.put(parts[i],parts[i].charAt(0)+"########################".substring(1,parts[i].length() -1)+ parts[i].charAt(parts[i].length()-1));
        }

    }

    public void sendMessageTypeAction(ActionEvent actionEvent) {
        int selectedIndex = (Integer) nickList.getSelectionModel().getSelectedIndices().get(0);
        String messageText = newMessage.getText().trim();

        if(!messageText.isEmpty()) {

            String replacedStr = messageText;

            String[] parts = messageText.split("[ [!#$%&'()*+,\\-./:;<=>?@^_`{|}~]]");

            for (String stringPart:parts) {
                String badwordreplace = badwords.get(stringPart.toLowerCase());
                if(!(badwordreplace == null)){
                    replacedStr = replacedStr.replaceAll(stringPart,badwordreplace);
                }

                String goodwordreplace = goodwords.get(stringPart.toLowerCase());
                if(!(goodwordreplace == null)){
                    replacedStr = replacedStr.replaceAll(stringPart,goodwordreplace);
                }

            }

            messageText = replacedStr;

            messageArea.appendText(messageText + System.lineSeparator());

            if(selectedIndex!=0) {
                messageText = "/w " + nickList.getSelectionModel().getSelectedItems().get(0) + " " +messageText;
                System.out.println("message sent: " + messageText);
            }
            try {
                out.writeUTF(messageText);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            messageArea.appendText(messageText+System.lineSeparator());
            newMessage.clear();
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void ChangeNick(ActionEvent actionEvent)  {

        String newnick = Nick.getText().trim();
        if(!newnick.isEmpty()) {
            try {
                out.writeUTF("/c " +newnick);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
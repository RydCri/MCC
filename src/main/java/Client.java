import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;


    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
        } catch (IOException e){
            closeEverything(socket, bufferedWriter, bufferedReader);
        }
        System.out.println(username + "(You)" + " connected to Server " + socket.getLocalSocketAddress());
    }

    public void sendMessage() {
            try {
                bufferedWriter.write(username);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                Scanner scanner = new Scanner(System.in);
                while (socket.isConnected()) {
                    String msg = scanner.nextLine();
                    bufferedWriter.write(username + ": " + msg);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeEverything(socket, bufferedWriter, bufferedReader);
            }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String msgFromGroup;
            while(socket.isConnected()) {
                try {
                    msgFromGroup = bufferedReader.readLine();
                    System.out.println(msgFromGroup);
                } catch (IOException e){
                    closeEverything(socket, bufferedWriter, bufferedReader);
                }
            }

        }).start();
    }

    public void closeEverything(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, NullPointerException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username for group chat");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }

}

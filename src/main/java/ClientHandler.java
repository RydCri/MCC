import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    ClientHandler(Socket socket){
        try {
            this.socket = socket;
        } catch (IOException e){

        }
    }
    @Override
    public void run(){

    }
}

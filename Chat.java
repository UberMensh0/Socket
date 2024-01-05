import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Chat {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true){
            startMessage();
            String input = scanner.nextLine();
            if (input.equals("exit")){
                System.out.println("Exiting...");
                return;
            }
            if (input.contains(":")){
                startClient(input);
            }else {
                startServer(input);
            }

        }
    }

    private static void startMessage(){
        System.out.println("write 'exit' to clone the program \n"
                +"write 'port' to create server\n"
                +"write 'host':'port' \n");
    }

    private static void startServer(String input){
        int port = Integer.parseInt(input.trim());

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            System.out.println("Client connected, enter 'exit' to close the program");
            chatSession(sc, out, in, "Client");
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private static void startClient(String input){
        String[] arr = input.trim().split(":");
        String host = arr[0];
        int port = Integer.parseInt(arr[1]);

        try {
            Socket client = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            System.out.println("connected to server, enter 'exit' to close the program");
            chatSession(sc, out, in, "Server");
        } catch (IOException e) {
            System.out.println("Error" + e.getMessage());
        }
    }


    private static void chatSession(Scanner sc, PrintWriter out, BufferedReader in, String name) throws IOException {
        while (true){
            if (sc.hasNextLine()){
                String message = sc.nextLine();
                if (message.equalsIgnoreCase("exit")){
                    break;
                }
                out.println(message);
            }
            if (in.ready()){
                String response = in.readLine();
                if (response != null){
                    System.out.println(name + ": " + response);
                }
            }
        }
    }
}

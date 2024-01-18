package serverFTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class FTPSetup {

    private int port;

    public FTPSetup(int port) {
        this.port = port;
    }
    // méthode qui lance un serveuret vérifie le login de client
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
    	 
            System.out.println("FTP Server is running on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from "+ clientSocket.getInetAddress());
             
                if (authenticate(clientSocket)) {
                    handleClient(clientSocket);
                } else {
                 System.out.println("Authentication failed. Closing connection.");
                 clientSocket.close();
                }
            }    
        } catch (IOException e) {
         e.printStackTrace();
        }
    }
 
    private static boolean authenticate(Socket socket) throws IOException {
	  
	    InputStream input = socket.getInputStream();
	    OutputStream output = socket.getOutputStream();
	    Scanner scanner = new Scanner(input);
	    PrintWriter writer = new PrintWriter(output, true);

	    // Ask for username       // probléme rencontré ici au niveau de lecture d'inputs
	    writer.println("Enter username:");
	    String username = scanner.nextLine();

	    // Ask for password
	    writer.println("Enter password:");
	    String password = scanner.nextLine();

	    // Simple authentication check
	    if (username == "123" && password == "123") 
	    return true;
	    else return false;
	}
 
 private static void handleClient(Socket socket) throws IOException {
     try (
         InputStream input = socket.getInputStream();
         BufferedReader reader = new BufferedReader(new InputStreamReader(input));
         OutputStream output = socket.getOutputStream();
         PrintWriter writer = new PrintWriter(output, true);
     ) {   // bloc de code qui retourne l'inverse de chaine de caractere ecrit par l'utilisateur, quant il écrit quit il se déconnecte.
         String text;   // inspirer par un exemple dans le site codejava.net
    
         do {
             text = reader.readLine();
             String reverseText = new StringBuilder(text).reverse().toString();
             writer.println("Server: " + reverseText);

         } while (!text.equals("quit"));
     } finally {
         socket.close();
     }
 }
}
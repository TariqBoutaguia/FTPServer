package serverFTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;

public class FTPSetup {

 private int port;

 public FTPSetup(int port) {
     this.port = port;
 }

 public void startServer() {
	    try (ServerSocket serverSocket = new ServerSocket(port)) {
	        while (true) {
	            try (Socket clientSocket = serverSocket.accept()) {
	                Out("220 Server ready.\r\n", clientSocket);
	                String login = In(clientSocket);
	                
	                Out("331 enter password : \r\n", clientSocket);
	                String passwd = In(clientSocket);
	                
                    String loginParDefaut = "USER a";
                    String passParDefaut = "PASS 1";
                    
	                if (login.equals(loginParDefaut) && passwd.equals(passParDefaut)) {
	                Out("230 user logged in, proceed.\r\n", clientSocket);
	                        //handleClient(clientSocket);
	                    } else {
	                        Out("530 Not logged in.\r\n", clientSocket);
	                    }
	                }
	             catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
 }

 
	private String In(Socket socket) throws IOException {
		   InputStream in = socket.getInputStream();
	       Scanner scan = new Scanner(in);
	       String str = scan.nextLine();
	       scan.close();
	       return str;
	}

	private void Out(String str, Socket socket) throws IOException {
		OutputStream out = socket.getOutputStream();
		out.write(str.getBytes());
		out.flush();
	}
 
 /*private boolean authentification(Socket socket) throws IOException {
	 
	        Out("username :", socket);
	        String login = In(socket);
	        Out("passwd :", socket);
	        String passwd = In(socket);
		    

	        if (login.equals("tariq") && passwd.equals("12345")) {
				return true;
			} else {
				return false;
			}
	}*/

	
 
 private void handleClient(Socket socket) throws IOException {
     try{
         String text;
         //code de test
         do {
             text = In(socket);
             String reverseText = new StringBuilder(text).reverse().toString();
             Out("Server: " + reverseText, socket);

         } while (!text.equals("QUIT"));
     } finally {
         socket.close();
     }
 }
}

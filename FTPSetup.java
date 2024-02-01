package serverFTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;

public class FTPSetup {

 private int port;
 private Socket dataSocket = null; 

 public FTPSetup(int port) {
     this.port = port;
 }

 public void startServer() {
	    try (ServerSocket serverSocket = new ServerSocket(port)) {
	        while (true) {
	            try (Socket clientSocket = serverSocket.accept()) {
	                Out("220 Service ready.\r\n", clientSocket);
	                String login = In(clientSocket);
	                
	                Out("331 User name okay, need password\r\n", clientSocket);
	                String passwd = In(clientSocket);
	                //Login et password
                    String loginParDefaut = "USER a";
                    String passParDefaut = "PASS 1";
                    
	                if (login.equals(loginParDefaut) && passwd.equals(passParDefaut)) {
	                        Out("230 user logged in, proceed.\r\n", clientSocket);
	                        run(clientSocket);
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
 
 //méthode qui gere les commandes, QUIT, GET, CD
 public void run(Socket socket) {
		try {
			 String command = "";
			 String[] parts = command.split(" ", 2);
			 //String cmd = parts[0];
			 String arg = parts.length > 1 ? parts[1] : "";
			 //String userValue = null;
			 int dataPort = 0; 
			while ((command = In(socket)) != null) {
				switch (command.toUpperCase()) {
				case "QUIT":
					Out("221 closing connection\r\n", socket);
					socket.close();
					break;
				/*case "GET":
					// Retrieve the filename from the client
					String filename = In(socket);
					File file = new File(filename);
					if (file.exists()) {
						// Read the file and send it to the client
						byte[] fileBytes = Files.readAllBytes(file.toPath());
						OutputStream outputStream = socket.getOutputStream();
						outputStream.write(fileBytes);
						Out("150 File sent successfully\r\n ", socket);
					} else {
						Out("550 Error: File not found\r\n", socket);
					}
                    break;*/
				case "RETR":
					 String fileName = arg.trim();
					 Out("150 Opening data connection for " + fileName + "\r\n", socket);

					 if (transferFile(fileName, socket)) {
					      Out("226 Transfer complete\r\n", socket);
					 } else {
						  Out("550 Error: File not found\r\n", socket);
					 }
                     break;
			    case "EPRT":
					 // Port dans windows
					 if (dataSocket != null && !dataSocket.isClosed()) {
					      dataSocket.close();
					 }
					 // EPRT |<protocol>|<ip address>|<port>
					 String[] eprtParams = arg.split("\\|"); // Use arg au lieu input
					 dataPort = Integer.parseInt(eprtParams[3]);

					 // Notify the client that the command is okay

					 Out("228 ERPT Command okay.\r\n", socket);
                     //
					 System.out.println(eprtParams[0]);

					 System.out.println(eprtParams[1]);

					 System.out.println(eprtParams[2]); // Added missing semicolon

					 System.out.println("data port: " + dataPort);
                     //
					 // Data connection

					 dataSocket = new Socket("::1", dataPort); // ::1 represents the ip address

					 //System.out.println(dataSocket);
					 break;
				case "CD":
              // Change the current directory to the specified directory
         		String currentDirectory = System.getProperty("user.dir");
         		String directory = currentDirectory + "\\" + In(socket);
         		Out(directory, socket);
         		File folder = new File(directory);
         		if (folder.exists() && folder.isDirectory()) {
         			Out(System.setProperty("user.dir", directory), socket);
         			System.getProperty("user.dir");
         			Out("250 Directory changed to: " + directory + "\r\n", socket);
         			// sendCommand("Directory changed to: " + directory, socket);
         		} else {
         			Out("550 Invalid directory\r\n", socket);
         		}
				default:
					Out("500 Invalid command\r\n", socket);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

              // Lecture
	private String In(Socket socket) throws IOException {
		   InputStream in = socket.getInputStream();
	       Scanner scan = new Scanner(in);
	       String str = scan.nextLine();
	       //scan.close();
	       return str;
	}
              //Ecriture
	private void Out(String str, Socket socket) throws IOException {
		OutputStream out = socket.getOutputStream();
		out.write(str.getBytes());
		out.flush();
	}
	
	private boolean transferFile(String fileName, Socket clientSocket) throws IOException {
		 File file = new File("files", fileName);
		 if (file.exists() && file.isFile()) {
             try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream dataOutputStream = dataSocket.getOutputStream()) {
		         byte[] buffer = new byte[1024];
		         int bytesRead;
                 while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                       dataOutputStream.write(buffer, 0, bytesRead);
                 }
             return true;
             }
		 }
		     return false;
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

	
 
 /*private void handleClient(Socket socket) throws IOException {
     try{
         String text;

         do {
             text = In(socket);
             String reverseText = new StringBuilder(text).reverse().toString();
             Out("Server: " + reverseText, socket);

         } while (!text.equals("QUIT"));
     } finally {
         socket.close();
     }
 }*/
}

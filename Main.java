package serverFTP;


public class Main{


    public static void main(String[] args) {
    	FTPSetup server1 = new FTPSetup(8020);
    	server1.startServer();
    	
    }
}
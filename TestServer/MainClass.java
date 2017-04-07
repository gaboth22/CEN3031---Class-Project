import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) throws Exception {
		LocalTestServer testServer = new LocalTestServer(8000);
		testServer.handleClient();

		Scanner input = new Scanner(System.in);

		/*
			Thread needed so that we can wait for user input
			to be received without blocking our outgoing text
			scanner for outgoing messages
		 */
		new Thread(new Runnable() {
            @Override
            public void run() {
            	while(true) {
                	try {
                		System.out.println("Message from client:\n" + testServer.getMessageFromClient() + '\n');
                	}
                	catch(Exception e) {}
            	}
            }
        }).start();

		while(true) {
			testServer.sendMessageToClient(input.nextLine());
			System.out.println("");
		}
	}
}
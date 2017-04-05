import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) throws Exception {
		LocalTestServer testServer = new LocalTestServer(8000);
		testServer.handleClient();

		Scanner input = new Scanner(System.in);

		new Thread(new Runnable() {
            @Override
            public void run() {
            	while(true) {
                	try {
                		System.out.println(testServer.getMessageFromClient());
                	}
                	catch(Exception e) {}
            	}
            }
        }).start();

		while(true) {
			System.out.println("Enter string to send to client:");
			testServer.sendMessageToClient(input.nextLine());
		}
	}
}
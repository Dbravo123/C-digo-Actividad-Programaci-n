import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 5001);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			try {
				while (true) {
					String inputLine = in.readLine();
					System.out.println(inputLine);
					for (int i = 0; i < 6; i ++) {
						inputLine = in.readLine();
						System.out.println(inputLine);
					}

					while (true) {
						String response = scanner.nextLine();
						System.out.println("Enviando opción al servidor: " + response);
						out.println(response);
					
						if ("q".equals(response)) {
						break;
						}
						System.out.println("Esperando respuesta del servidor...");
						inputLine = in.readLine();
                        System.out.print("Respuesta del servidor: " + inputLine);
					}
				}	
			} finally {
			scanner.close();
			socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

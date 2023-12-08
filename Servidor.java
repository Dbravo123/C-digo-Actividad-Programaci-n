import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable{
	
	private Socket clientSocket;
	public Servidor (Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		 try {
			 serverSocket = new ServerSocket (5001);
			 System.out.println("Servidor de chat inicializado.");
			 int i = 0;
			 
			 
			 while (true) {
				
				 Socket clientSocket2 = serverSocket.accept();
				 System.out.println("Cliente conectado desde " + clientSocket2.getInetAddress());
				 i++;
				 new Thread(new Servidor(clientSocket2), "Cliente " + i).start();
				 System.out.println("Cliente número " + i);
			 
			 
			 } 
			 
		 } catch (IOException e) {
			 e.printStackTrace();
		 } finally {
			 	try {
			 		if(serverSocket != null && !serverSocket.isClosed()) {
			 			serverSocket.close();
			 		}
			 	} catch (IOException e) {
			 		e.printStackTrace();
			 	}
		 }
	}

	@Override
	public void run() {
		 
		 try {
			 PrintWriter out = new PrintWriter (clientSocket.getOutputStream(), true);
			 BufferedReader in = new BufferedReader (new InputStreamReader(clientSocket.getInputStream()));
			 out.println("Bienvenido al Servidor");
			 out.println("Qué es lo que haremos en esta ocasión? Elija una de estas opciones: ");
			 out.println("1. Deseas saber un número aleatorio entre 1 y 100?" + "\n2. Quieres cerciorarte del puerto al que te has conectado?" + "\n3. Quieres que te cuente un chiste?" + "\n4. Quieres saber una curiosidad de los pingüinos?" + "\n5. Quieres despedirte?");

			 
			 while (true) {
				 String option = null;
				 
				 try {
					 option = in.readLine();
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
				 
				 if (option != null && !option.isEmpty()) {
				 System.out.println("El cliente seleccionó la opción " + option);
				 } else {
					 System.out.println("Error al leer la opción del cliente.");
					 clientSocket.close();
					 return;
				 }
				 String response =  " ";
				 switch (option) {
				 case "1":
					 response = "El número aleatorio seleccionado es: " + (int) (Math.random()*100);
					 break;
				 case "2":
					 response = "El puerto al que está conectado es " + clientSocket.getLocalPort();
					 break;
				 case "3":
					 String [] ChisteAleatorio = {"¿Por qué los pájaros no utilizan Facebook? Porque ya tienen Twitter", "Sale el doctor después de un parto y el padre de la criatura le pregunta: '¿Doctor cómo salió todo?' El doctor le dice: 'todo salió bien, pero tuvimos que colocarle oxígeno al bebé'. El padre, horrorizado, le dice: 'pero doctor, nosotros queríamos ponerle Gabriel'", "'Me acabo de tirar un pedo de esos silenciosos, ¿qué hago?' 'Ahora nada, pero, cuando llegues a casa, cámbiale las pilas al audífono'", "Un doctor está a punto de realizar una operación y se le oye susurrar 'tú tranquilo Juan, todo saldrá bien', el paciente le mira y le dice 'Doctor, yo me llamo Luis', a lo que el doctor responde 'Eso ya lo sé hombre, Juan soy yo'."};
					 int índice = (int) (Math.random()*100);
					 if (índice < 25) {
						 response = "A ver qué te parece este chiste: " + ChisteAleatorio[0];
					 } else if (índice >= 25 && índice < 50) {
						 response = "A ver qué te parece este chiste: " + ChisteAleatorio[1];
					 } else if (índice >= 50 && índice <75) {
						 response = "A ver qué te parece este chiste: " + ChisteAleatorio[2];
					 } else {
						 response = "A ver qué te parece este chiste: " + ChisteAleatorio[3];
					 } break;
				 case "4":
					 String [] Curiosidad = {"Los pingüinos no tienen dientes sino que tienen púas serradas en la parte superior de la boca con las que descomponen alimentos", "Los pingüinos solo se pueden encontrar en el Hemisferio Sur", "Hay 18 especies distintas de pingüino, aunque se cree que pueden existir más especies o subespecies aún por determinar", "Los pingüinos son originarios de Australia, de hecho, investigaciones recientes muestran que el ancestro común de los pingüinos modernos apareció por primera vez cerca de las costas de Australia, Nueva Zelanda, y algunas otras islas del Pacífico Sur hace unos 22 millones de años.", "El pingüino más pequeño mide, tan solo, 30,48 cm de altura."};
					 int indice = (int) (Math.random()*100);
					 if (indice < 20) {
						 response = "Mira qué dato más interesante!: " + Curiosidad[0];
					 } else if (indice >= 20 && indice < 40) {
						 response = "Mira qué dato más interesante!: " + Curiosidad[1];
					 } else if (indice >= 40 && indice < 60) {
						 response = "Mira qué dato más interesante!: " + Curiosidad[2];
					 }else if (indice >= 60 && indice < 80) {
						 response = "Mira qué dato más interesante!: " + Curiosidad[3];
					 } else {
						 response = "Mira qué dato más interesante!: " + Curiosidad[4];
					 } break;
				 case "5":
					 response = "Muchas gracias por pasarte por aquí, ha sido un placer, espero verte pronto!";
					 break;
				default: 
					response = "Opción no válida, por favor elija una opción del 1 al 5";
					break;
				 } 	System.out.println("Enviando respuesta al cliente: " + response);
				 	out.println(response);
				    out.flush();

				 
				 if ("q".equals(option)) {
					 clientSocket.close();
					 break;
				 }
			 }
			 
		 	} catch (IOException e) {
		 		e.printStackTrace();
		 }
	}
}

package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import utils.EventReader;

public class ClientHandler implements Runnable {

	private final Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public ClientHandler(Socket clientSocket) throws IOException {
		this.client = clientSocket;
		this.out = new ObjectOutputStream(this.client.getOutputStream());
		this.in = new ObjectInputStream(this.client.getInputStream());
	}

	public Socket getClient() {
		return client;
	}

	@Override
	public void run() {
		String clientMsg = "";
		String sendMsg = "";
		try {
			while (true) {
				// Read the incoming data from client
				clientMsg = (String) in.readObject();
				if(clientMsg.equals("All"))
				{
					//send data event
					List<Event> events = EventReader.readEventsFromFile("src/data/events.json");
					out.writeObject(events);
					out.flush();
				}
				// Send events to the client
//                out.writeObject(this.events);
//                out.flush();
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e);
		} finally {
			try {
				System.out.println("Client " + this.client.getPort() + " disconnected!");
				this.client.close();
				this.in.close();
				this.out.close();
			} catch (IOException ex) {
				System.out.println(ex);
			}
		}
	}
}

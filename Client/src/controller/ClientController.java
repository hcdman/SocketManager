package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import model.Event;
import view.ConnectView;
import view.HomeView;

public class ClientController implements ActionListener {

	private ConnectView connect;
	private HomeView home;
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;


	public void startClientSocket(String host, int port) throws IOException {
		try {
			client = new Socket(host, port);
			client.setTcpNoDelay(true);
			this.out = new ObjectOutputStream(client.getOutputStream());
			this.in = new ObjectInputStream(client.getInputStream());
		} catch (IOException ex) {
			System.out.println(ex);
			System.out.println("Failed connect");
		}
	}

	public ClientController(ConnectView view) {
		this.connect = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Connect")) {
			// get port and address to connect
			String IP = this.connect.IPSERVER.getText();
			int port = Integer.parseInt(this.connect.PORT.getText());
			try {

				this.startClientSocket(IP, port);
				String message = "All";
				out.writeObject(message);
				out.flush();

				System.out.println(1);
				this.home = new HomeView();
				this.home.events = (List<Event>) in.readObject();
				this.connect.dispose();
				this.home.setLocationRelativeTo(null);
				this.home.setVisible(true);
				this.home.showEvents();
				
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			//error
		}
	}

}

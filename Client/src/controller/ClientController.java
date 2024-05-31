package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JComboBox;

import dto.Booked;
import model.Event;
import model.Schedule;
import utils.MessageClient;
import view.ConnectView;
import view.DetailEventView;
import view.HomeView;

public class ClientController implements ActionListener, MouseListener {

	private ConnectView connect;
	private HomeView home;
	private DetailEventView detail;
	private Socket client;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public ClientController(HomeView view, ObjectInputStream in, ObjectOutputStream out) {
		this.home = view;
		this.in = in;
		this.out = out;
	}

	public ClientController(DetailEventView view, ObjectInputStream in, ObjectOutputStream out) {
		this.detail = view;
		this.in = in;
		this.out = out;
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Connect")) {
			// get port and address to connect
			String IP = this.connect.IPSERVER.getText();
			int port = Integer.parseInt(this.connect.PORT.getText());
			try {
				this.startClientSocket(IP, port);
				String message = MessageClient.GET_DATA;
				out.writeObject(message);
				out.flush();
				this.home = new HomeView(this.in, this.out);
				this.home.events = (List<Event>) in.readObject();
				this.connect.dispose();
				this.home.setLocationRelativeTo(null);
				this.home.setVisible(true);
				this.home.showEvents();
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			// error
		}
		if (command.equals("Confirm")) {
			// get port and address to connect
			try {
				int indexSchedule = this.detail.comboBox.getSelectedIndex();
				String msg = MessageClient.BOOK + " "+ this.detail.booking.size()+" " + this.detail.event.getEventId() + " " + indexSchedule;

				for (Booked value : this.detail.booking) {
					msg += " " + value.getZoneId() + " " + value.getSeatId();
				}
				this.detail.booking.clear();
				this.detail.showSeatBooking();
				this.out.writeObject(msg);
				this.out.flush();

				// Receive data from server
				try {
					Schedule schedule = (Schedule) this.in.readObject();
					this.detail.displaySeatingChart(schedule);
				} catch (Exception e0) {
					this.detail.ShowError("Some seat have been booked!");
					msg = MessageClient.SCHEDULE + " " +this.detail.event.getEventId()+" "+ indexSchedule;
					this.out.writeObject(msg);
					this.out.flush();
					Schedule schedule;
					try {
						schedule = (Schedule) this.in.readObject();
						this.detail.displaySeatingChart(schedule);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}

				}

			} catch (IOException e1) {

				e1.printStackTrace();
			}

			// error
		}

		int index = -1;
		if (e.getSource() instanceof JComboBox) {
			JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
			index = comboBox.getSelectedIndex();
		}
		if (index != -1) {
			try {
				String msg = MessageClient.SCHEDULE + " "+this.detail.event.getEventId()+" " + index;
				this.out.writeObject(msg);
				this.out.flush();
				Schedule schedule = (Schedule) this.in.readObject();
				this.detail.displaySeatingChart(schedule);
			} catch (IOException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Check if a row is clicked
		if (e.getClickCount() == 1) {
			// Get the selected row
			int selectedRow = this.home.table.getSelectedRow();
			if (selectedRow != -1) {
				// Retrieve data from the selected row
				String message = MessageClient.GET_EVENT + " " + selectedRow;
				try {
					this.out.writeObject(message);
					this.out.flush();

					this.detail = new DetailEventView(this.in, this.out);
					this.detail.event = (Event) in.readObject();
					this.home.setEnabled(false);
					this.detail.setLocationRelativeTo(null);
					this.detail.setVisible(true);
					this.detail.showDataEvent();
					this.detail.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							home.setEnabled(true);
							home.setVisible(true);
							home.showEvents();
						}
					});
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

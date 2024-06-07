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
import java.net.SocketException;
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

	public ClientController(HomeView view, ObjectInputStream in, ObjectOutputStream out,Socket client) {
		this.home = view;
		this.in = in;
		this.out = out;
		this.client = client;
	}

	public ClientController(DetailEventView view, ObjectInputStream in, ObjectOutputStream out,Socket client) {
		this.detail = view;
		this.in = in;
		this.out = out;
		this.client=client;
	}

	public void startClientSocket(String host, int port) throws IOException {
		try {
			client = new Socket(host, port);
			client.setTcpNoDelay(true);
			this.out = new ObjectOutputStream(client.getOutputStream());
			this.in = new ObjectInputStream(client.getInputStream());
		} catch (IOException ex) {
			this.connect.ShowError("Failed to connect to server !");
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
			String IP = this.connect.IpServer.getText();
			int port = Integer.parseInt(this.connect.Port.getText());
			try {
				this.startClientSocket(IP, port);
				String message = MessageClient.GET_DATA;
				out.writeObject(message);
				out.flush();
				this.home = new HomeView(this.in, this.out,this.client);
				this.home.events = (List<Event>) in.readObject();
				this.connect.dispose();
				this.home.setLocationRelativeTo(null);
				this.home.setVisible(true);
				this.home.showEvents();
			} catch (IOException | ClassNotFoundException|NullPointerException e1) {
				e1.printStackTrace();
			}
		}
		if (command.equals("Confirm")) {
			try {
				if(this.detail.booking.size()==0)
				{
					this.detail.ShowError("Please select at least one seat to proceed!");
					return;
				}
				if(this.detail.booking.size()>4)
				{
					this.detail.ShowError("For each booking, a maximum of 4 seats can be selected!");
					return;
				}
				if(this.detail.phoneNumber.getText().isBlank())
				{
					this.detail.ShowError("You have to fill your phone number before confirm!");
					return;
				}
				if(this.detail.phoneNumber.getText().length()!=10)
				{
					this.detail.ShowError("Your phone number must have 10 numbers!");
					return;
				}
				int indexSchedule = this.detail.comboBox.getSelectedIndex();
				String phoneNumber = this.detail.phoneNumber.getText();
				String userName = this.detail.name.getText();
				String msg = MessageClient.BOOK + "-" +phoneNumber+"-"+userName+"-"+ this.detail.booking.size()+"-" + this.detail.event.getEventId() + "-" + indexSchedule;

				for (Booked value : this.detail.booking) {
					msg += "-" + value.getZoneId() + "-" + value.getSeatId();
				}
				try {
					this.out.writeObject(msg);
					this.out.flush();
				} catch (SocketException e1) {
					this.detail.ShowError("Connection to server was lost while sending data!");
					this.detail.dispose();
					return;
				}
				try {
					Schedule schedule = (Schedule) this.in.readObject();
					this.detail.displaySeatingChart(schedule);
					this.detail.ShowSuccess("Booking successful!");
					this.detail.booking.clear();
					this.detail.showSeatBooking();
					this.detail.phoneNumber.setText("");
					this.detail.name.setText("");
					
				} catch (Exception e0) {
					msg = MessageClient.SCHEDULE + "-" +this.detail.event.getEventId()+"-"+ indexSchedule;
					
					try {
						this.out.writeObject(msg);
						this.out.flush();
					} catch (SocketException e1) {
						this.detail.ShowError("Connection to server was lost while sending data!");
						this.detail.dispose();
						return;
					}
					this.detail.ShowError("Some seat have been booked!");
					this.detail.booking.clear();
					this.detail.showSeatBooking();
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
		}

		int index = -1;
		if (e.getSource() instanceof JComboBox) {
			JComboBox<?> comboBox = (JComboBox<?>) e.getSource();
			index = comboBox.getSelectedIndex();
		}
		if (index != -1) {
			try {
				String msg = MessageClient.SCHEDULE + "-"+this.detail.event.getEventId()+"-" + index;
				this.out.writeObject(msg);
				this.out.flush();
				Schedule schedule = (Schedule) this.in.readObject();
				this.detail.displaySeatingChart(schedule);
			} catch (Exception e1) {
				this.detail.ShowError("Lost connecttion with server!");
				this.detail.dispose();
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			int selectedRow = this.home.table.getSelectedRow();
			if (selectedRow != -1) {
				String message = MessageClient.GET_EVENT + "-" + selectedRow;
				try {
					
					try {
						this.out.writeObject(message);
						this.out.flush();
					} catch (SocketException e1) {
						this.home.ShowError("Lost connection with server!");
						this.home.dispose();
						return;
					}
					
					this.detail = new DetailEventView(this.in, this.out,this.client);
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

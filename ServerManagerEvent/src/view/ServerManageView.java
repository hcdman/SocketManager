package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lgooddatepicker.components.TimePickerSettings;

import controller.ManageEventController;
import model.ClientHandler;
import model.Event;
import utils.EventReader;

public class ServerManageView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameIP;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	public JTextField Port;
	public ServerSocket server;
	public JTable table;
	public JTable tableClient;
	private List<Event> events;
	private final ArrayList<ClientHandler> clients = new ArrayList<>();
	private final ExecutorService pool = Executors.newFixedThreadPool(4);

	public ServerManageView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1008, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Server");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(436, 25, 75, 51);
		contentPane.add(lblEvent);

		nameIP = new JTextField();
		nameIP.setBounds(386, 86, 187, 30);
		contentPane.add(nameIP);
		nameIP.setColumns(10);

		lblNameLabel = new JLabel("IP");
		lblNameLabel.setBounds(309, 94, 67, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("PORT");
		lblDiscription.setBounds(306, 139, 60, 13);
		contentPane.add(lblDiscription);

		Port = new JTextField();
		Port.setColumns(10);
		Port.setBounds(386, 125, 187, 30);
		contentPane.add(Port);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IP", "Port", "Status" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(37, 208, 353, 257);
		contentPane.add(scrollPane);

		// Create a TimePickerSettings instance to customize the TimePicker.
		TimePickerSettings timeSettings = new TimePickerSettings();
		// Optionally set the display format.
		timeSettings.use24HourClockFormat();

		// Zone for seating
		tableClient = new JTable();
		tableClient.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tableClient.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		tableClient.setDefaultEditor(Object.class, null);
		// set column name of table
		tableClient.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "IP", "Port", "Time", "Action" }));
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableClient.getColumnCount(); i++) {
			tableClient.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		JScrollPane scrollPane_2 = new JScrollPane(tableClient);
		scrollPane_2.setBounds(436, 208, 528, 257);
		contentPane.add(scrollPane_2);

		JLabel lblListClients = new JLabel("List clients");
		lblListClients.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListClients.setBounds(37, 160, 121, 40);
		contentPane.add(lblListClients);

		JLabel lblFollow = new JLabel("Follow");
		lblFollow.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFollow.setBounds(436, 165, 75, 40);
		contentPane.add(lblFollow);
		// action
		ActionListener action = new ManageEventController(this);
	}

	public void initServer() {
		Thread socketThread = new Thread(() -> {
			try {
				startSocket();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		socketThread.start();
	}

	public void startSocket() throws IOException {
		server = null;
		loadEventData();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			server = new ServerSocket(3000, 50, localHost);
			nameIP.setText(localHost.getHostAddress());
			this.Port.setText(3000 + "");
			while (true) {
				Socket socket = server.accept();
				ClientHandler clientThread = new ClientHandler(socket);
				clients.add(clientThread);
				pool.execute(clientThread);
				String IP = socket.getInetAddress().toString();
				String Port = socket.getPort() + "";
				this.addClientConnect(IP, Port, "Connected");
				//handleClient(socket);
			}
		} catch (IOException ex) {
			System.out.println(ex);
		} finally {
			if (server != null) {
				server.close();
			}
		}
	}

	public void endSocket() {
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadEventData() {
		try {
			this.events = EventReader.readEventsFromFile("src/data/events.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handleClient(Socket clientSocket) {
		try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
			out.writeObject(this.events);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// add row connect client
	public void addClientConnect(String ip, String port, String status) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		// Set renderer for all columns
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		model.addRow(new Object[] { ip, port, status });
	}

}

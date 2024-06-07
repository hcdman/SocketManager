package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
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
import javax.swing.table.TableColumnModel;

import model.ActionClient;
import model.ClientHandler;
import model.Event;

public class ServerManageView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameIP;
	public JTextField port;
	public ServerSocket server;
	public JTable table;
	private boolean running;
	public JTable tableClient;
	public List<Event> events;
	private final ArrayList<ClientHandler> clients = new ArrayList<>();
	public ArrayList<ActionClient> histories = new ArrayList<>();
	private final ExecutorService pool = Executors.newFixedThreadPool(4);

	public ServerManageView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		ImageIcon Icon = new ImageIcon("images\\event.png");
		this.setIconImage(Icon.getImage());

		// Label
		JLabel lblNameLabel = new JLabel("IP");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(426, 42, 67, 13);
		contentPane.add(lblNameLabel);

		JLabel lblDiscription = new JLabel("PORT");
		lblDiscription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiscription.setBounds(416, 83, 60, 13);
		contentPane.add(lblDiscription);

		JLabel lblIcon = new JLabel("");
		String path = "images\\server.png";
		ImageIcon img = new ImageIcon(path);
		int width = 112;
		int height = 112;
		Image nw = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon nc = new ImageIcon(nw);
		lblIcon.setIcon(nc);
		lblIcon.setBounds(266, 10, 112, 112);
		contentPane.add(lblIcon);

		JLabel lblListClients = new JLabel("List clients");
		lblListClients.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListClients.setBounds(37, 177, 121, 40);
		contentPane.add(lblListClients);

		JLabel lblFollow = new JLabel("Follow");
		lblFollow.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFollow.setBounds(416, 177, 75, 40);
		contentPane.add(lblFollow);

		// Text field
		nameIP = new JTextField();
		nameIP.setBounds(462, 34, 187, 30);
		contentPane.add(nameIP);
		nameIP.setColumns(10);
		nameIP.setEditable(false);

		port = new JTextField();
		port.setColumns(10);
		port.setBounds(462, 73, 187, 30);
		contentPane.add(port);
		port.setEditable(false);

		// Data table
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setDefaultEditor(Object.class, null);
		table.setRowHeight(20);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "<html><b>IP<b><html>", "<html><b>Port<b><html>", "<html><b>Status<b><html>" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(37, 227, 341, 526);
		contentPane.add(scrollPane);

		tableClient = new JTable();
		tableClient.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tableClient.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableClient.setDefaultEditor(Object.class, null);
		tableClient.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>IP<b><html>",
				"<html><b>Port<b><html>", "<html><b>Time<b><html>", "<html><b>Action<b><html>" }));
		tableClient.setRowHeight(20);
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableClient.getColumnCount(); i++) {
			tableClient.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		TableColumnModel columnModel = tableClient.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100);
		columnModel.getColumn(1).setPreferredWidth(50);
		columnModel.getColumn(2).setPreferredWidth(150);
		columnModel.getColumn(3).setPreferredWidth(200);
		JScrollPane scrollPane_2 = new JScrollPane(tableClient);
		scrollPane_2.setBounds(416, 227, 560, 526);
		contentPane.add(scrollPane_2);
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
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			server = new ServerSocket(3000, 50, localHost);
			nameIP.setText(localHost.getHostAddress());
			this.port.setText(3000 + "");
			running = true;
			while (running) {
				try {
					Socket socket = server.accept();
					ClientHandler clientThread = new ClientHandler(socket, this);
					clients.add(clientThread);
					this.UpdateClientConnect();
					pool.execute(clientThread);
				} catch (Exception e) {
					if (!running) {
						break;
					}
					e.printStackTrace();
				}
			}
		} catch (IOException ex) {
			System.out.println(ex);
		} finally {
			if (server != null && !server.isClosed()) {
				server.close();
			}
			pool.shutdown();
			for (ClientHandler client : clients) {
				client.getClient().close();
			}
			System.out.println("Close done!");
		}
	}

	public void endSocket() {
		running = false;
		if (server != null && !server.isClosed()) {
			try {
				System.out.println("Closing server");
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void UpdateClientConnect() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		for (int i = 0; i < this.clients.size(); i++) {
			model.addRow(new Object[] { this.clients.get(i).getClient().getInetAddress().toString(),
					this.clients.get(i).getClient().getPort() + "", "Connected" });
		}
	}

	public void UpdateHistory() {

		DefaultTableModel model = (DefaultTableModel) tableClient.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		for (int i = 0; i < tableClient.getColumnCount(); i++) {
			tableClient.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		for (ActionClient value : this.histories) {
			model.addRow(new Object[] { value.getIP(), value.getPort(), value.getTime().format(formatter),
					value.getAction() });
		}
	}

	public void RemoveClient(int port) {
		Iterator<ClientHandler> iterator = clients.iterator();
		while (iterator.hasNext()) {
			ClientHandler value = iterator.next();
			if (value.getClient().getPort() == port) {
				iterator.remove();
			}
		}
	}

}

package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lgooddatepicker.components.TimePickerSettings;

import controller.ManageEventController;
import model.ClientHandler;
import model.Event;
import model.ActionClient;
import utils.ObjectReader;
import java.awt.Color;

public class ServerManageView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameIP;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	public JTextField Port;
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
		nameIP = new JTextField();
		nameIP.setBounds(462, 34, 187, 30);
		contentPane.add(nameIP);
		nameIP.setColumns(10);
		nameIP.setEditable(false);
		lblNameLabel = new JLabel("IP");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(426, 42, 67, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("PORT");
		lblDiscription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiscription.setBounds(416, 83, 60, 13);
		contentPane.add(lblDiscription);

		JLabel lblIcon = new JLabel("");
		String path = "images\\server.png";
		ImageIcon img = new ImageIcon(path);
		int width = 112;
		int height =112;
		Image nw = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon nc = new ImageIcon(nw);
		lblIcon.setIcon(nc);
		lblIcon.setBounds(266, 10, 112, 112);
		contentPane.add(lblIcon);
		
		Port = new JTextField();
		Port.setColumns(10);
		Port.setBounds(462, 73, 187, 30);
		contentPane.add(Port);
		Port.setEditable(false);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>IP<b><html>", "<html><b>Port<b><html>", "<html><b>Status<b><html>" }));
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
		// Disable table's editing mode
		tableClient.setDefaultEditor(Object.class, null);
		// set column name of table
		tableClient.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>IP<b><html>", "<html><b>Port<b><html>", "<html><b>Time<b><html>", "<html><b>Action<b><html>" }));
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
		
		for (int i = 0; i < tableClient.getColumnCount(); i++) {
			tableClient.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		TableColumnModel columnModel = tableClient.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(100); // Width for "IP" column
		columnModel.getColumn(1).setPreferredWidth(50);  // Width for "Port" column
		columnModel.getColumn(2).setPreferredWidth(150); // Width for "Time" column
		columnModel.getColumn(3).setPreferredWidth(200); // Width for "Action" column
		JScrollPane scrollPane_2 = new JScrollPane(tableClient);
		//tableClient.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane_2.setBounds(416, 227, 560, 526);
		contentPane.add(scrollPane_2);

		JLabel lblListClients = new JLabel("List clients");
		lblListClients.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblListClients.setBounds(37, 177, 121, 40);
		contentPane.add(lblListClients);

		JLabel lblFollow = new JLabel("Follow");
		lblFollow.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFollow.setBounds(416, 177, 75, 40);
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
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			server = new ServerSocket(3000, 50, localHost);
			nameIP.setText(localHost.getHostAddress());
			this.Port.setText(3000 + "");
			running=true;
			while (running) {
				
				try {
					Socket socket = server.accept();
					ClientHandler clientThread = new ClientHandler(socket,this);
					clients.add(clientThread);
					this.UpdateClientConnect();
					pool.execute(clientThread);
				} catch (Exception e) {
					 if (!running) {
                        // Server was stopped, exit loop
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
            pool.shutdown(); // Properly shut down the thread pool
            for (ClientHandler client : clients) {
                client.getClient().close(); // Ensure all client handlers are closed
            }
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

	// add row connect client
	public void UpdateClientConnect() {
		// clear old data and insert again
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		// Set renderer for all columns
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		for (int i = 0; i < this.clients.size(); i++) {
			model.addRow(new Object[] {this.clients.get(i).getClient().getInetAddress().toString(),this.clients.get(i).getClient().getPort()+"","Connected" });
		}
	}
	public void UpdateHistory() {
		// clear old data and insert again
		DefaultTableModel model = (DefaultTableModel) tableClient.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		// Set renderer for all columns
		for (int i = 0; i < tableClient.getColumnCount(); i++) {
			tableClient.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		for (ActionClient value : this.histories) {
			model.addRow(new Object[] {value.getIP(),value.getPort(),value.getTime().format(formatter),value.getAction() });
		}
	}
	public void RemoveClient(int port)
	{
		Iterator<ClientHandler> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientHandler value = iterator.next();
            if (value.getClient().getPort() == port) {
                iterator.remove();
            }
        }
	}

}

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import utils.ObjectReader;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTable table;
	public List<Event> events;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Make interface more beautiful
					try {
						UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Exception e) {
						e.printStackTrace();
					}
					HomeView frame = new HomeView();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					//read and show data
					frame.events= ObjectReader.readObjectsFromFile("src/data/events.json",Event.class);
					frame.showEvents();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HomeView() {
		this.events = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon Icon = new ImageIcon("images\\event.png");
		this.setIconImage(Icon.getImage());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(200, 200, 1000, 800);
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JButton btnAddNewEvent = new JButton("Add new event");
		btnAddNewEvent.setBackground(new Color(56, 102, 65));
		btnAddNewEvent.setForeground(new Color(0, 0, 0));
		btnAddNewEvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddNewEvent.setBounds(773, 125, 150, 30);
		contentPane.add(btnAddNewEvent);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>STT</b></html>", "<html><b>Name event</b></html>", "<html><b>Description</b></html>","<html><b>Date</b></html>" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(104, 170, 819, 564);
		contentPane.add(scrollPane);
		
		JButton btnOpenServer = new JButton("Open server");
		btnOpenServer.setForeground(new Color(0, 0, 0));
		btnOpenServer.setBackground(new Color(56, 102, 65));
		btnOpenServer.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnOpenServer.setBounds(104, 125, 121, 30);
		contentPane.add(btnOpenServer);
		
		JLabel lblIcon = new JLabel("");
		String path = "images\\home.png";
		ImageIcon img = new ImageIcon(path);
		int width = 112;
		int height =112;
		Image nw = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon nc = new ImageIcon(nw);
		lblIcon.setIcon(nc);
		lblIcon.setBounds(465, 5, 112, 112);
		contentPane.add(lblIcon);
		
		JButton btnSeatBooked = new JButton("Seat booked");
		btnSeatBooked.setBackground(new Color(56, 102, 65));
		btnSeatBooked.setForeground(new Color(0, 0, 0));
		btnSeatBooked.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSeatBooked.setBounds(463, 125, 121, 30);
		contentPane.add(btnSeatBooked);
		// Actions
		ActionListener action = new ManageEventController(this);
		table.addMouseListener((MouseListener) action);
		btnAddNewEvent.addActionListener(action);
		btnOpenServer.addActionListener(action);
		btnSeatBooked.addActionListener(action);
	}
	
	public void showEvents() {
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (int i = 0; i < this.events.size(); i++) {
			model.addRow(
					new Object[] { i + 1, this.events.get(i).getName(), this.events.get(i).getDescription(), this.events.get(i).getDate().format(formatter) });
		}
	}
}

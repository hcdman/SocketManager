package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import utils.EventReader;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;

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
					frame.events= EventReader.readEventsFromFile("src/data/events.json");
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(50, 100, 891, 525);
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Events");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(397, 35, 75, 51);
		contentPane.add(lblEvent);

		JButton btnAddNewEvent = new JButton("Add new event");
		btnAddNewEvent.setBounds(641, 91, 121, 21);
		contentPane.add(btnAddNewEvent);
		// Data events
		// Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "STT", "Name event", "Discription","Date" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(104, 122, 658, 325);
		contentPane.add(scrollPane);
		// Actions
		ActionListener action = new ManageEventController(this);
		btnAddNewEvent.addActionListener(action);
	}
	public void showEvents() {
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (int i = 0; i < this.events.size(); i++) {
			model.addRow(
					new Object[] { i + 1, this.events.get(i).getName(), this.events.get(i).getDescription(), this.events.get(i).getDate().format(formatter) });
		}
	}
}

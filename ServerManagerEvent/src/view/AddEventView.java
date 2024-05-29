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
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.toedter.calendar.JDateChooser;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import model.Zone;

import javax.swing.JComboBox;

public class AddEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameEvent;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	public JTextField Discription;
	private JLabel lblDate;
	public JTable table;
	public JTable tableSeat;
	public JTextField nameZone;
	public JTextField price;
	public JTextField rows;
	public JDateChooser dateEvent;
	public JTextField seats;
	public TimePicker startTime;
	public TimePicker endTime;
	public JComboBox<String> comboBox;
	public List<Event> events;
	public List<Schedule> schedules;
	public List<Zone> zones;

	public AddEventView() {
		schedules = new ArrayList<>();
		zones = new ArrayList<>();
		events = new ArrayList<>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1008, 773);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Events");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(436, 25, 75, 51);
		contentPane.add(lblEvent);

		JButton btnSaveEvent = new JButton("Save event");
		btnSaveEvent.setBounds(824, 681, 121, 21);
		contentPane.add(btnSaveEvent);

		nameEvent = new JTextField();
		nameEvent.setBounds(386, 86, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);

		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setBounds(299, 89, 67, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("Description");
		lblDiscription.setBounds(306, 139, 60, 13);
		contentPane.add(lblDiscription);

		Discription = new JTextField();
		Discription.setColumns(10);
		Discription.setBounds(386, 125, 187, 42);
		contentPane.add(Discription);

		lblDate = new JLabel("Date");
		lblDate.setBounds(306, 183, 60, 13);
		contentPane.add(lblDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateEvent = new JDateChooser();
		dateEvent.setBounds(386, 177, 187, 30);
		dateEvent.setDateFormatString(dateFormat.toPattern());
		contentPane.add(dateEvent);
		// Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "STT", "Start time", "End time" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 266, 353, 257);
		contentPane.add(scrollPane);

		// Create a TimePickerSettings instance to customize the TimePicker.
		TimePickerSettings timeSettings = new TimePickerSettings();
		// Optionally set the display format.
		timeSettings.use24HourClockFormat();
		// Create the TimePicker with the settings.
		startTime = new TimePicker(timeSettings);
		// Set an initial time if desired.
		startTime.setTime(LocalTime.now());
		// Set bounds for the TimePicker and add it to the content pane
		startTime.setBounds(124, 545, 120, 30); // Adjusted size and position
		contentPane.add(startTime);

		JLabel lblStart = new JLabel("Start time");
		lblStart.setBounds(54, 553, 63, 13);
		contentPane.add(lblStart);

		endTime = new TimePicker(timeSettings);
		endTime.setBounds(124, 591, 120, 30);
		endTime.setTime(LocalTime.now());
		contentPane.add(endTime);

		JLabel lblEndTime = new JLabel("End time");
		lblEndTime.setBounds(54, 599, 63, 13);
		contentPane.add(lblEndTime);

		JButton btnAddSchedule = new JButton("Add schedule");
		btnAddSchedule.setBounds(108, 643, 101, 21);
		contentPane.add(btnAddSchedule);

		// Zone for seating
		tableSeat = new JTable();
		tableSeat.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tableSeat.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		tableSeat.setDefaultEditor(Object.class, null);
		// set column name of table
		tableSeat.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "STT", "Name zone", "Price ticket", "Number rows", "Seats per row" }));
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableSeat.getColumnCount(); i++) {
			tableSeat.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		JScrollPane scrollPane_2 = new JScrollPane(tableSeat);
		scrollPane_2.setBounds(436, 266, 528, 257);
		contentPane.add(scrollPane_2);

		JButton btnAddZone = new JButton("Add zone");
		btnAddZone.setBounds(598, 692, 122, 21);
		contentPane.add(btnAddZone);

		JLabel lblZone = new JLabel("Name zone");
		lblZone.setBounds(480, 545, 67, 20);
		contentPane.add(lblZone);

		nameZone = new JTextField();
		nameZone.setColumns(10);
		nameZone.setBounds(558, 536, 187, 30);
		contentPane.add(nameZone);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(480, 584, 67, 20);
		contentPane.add(lblPrice);

		price = new JTextField();
		price.setColumns(10);
		price.setBounds(558, 575, 187, 30);
		contentPane.add(price);

		JLabel lblNumberRows = new JLabel("Number rows");
		lblNumberRows.setBounds(480, 623, 67, 20);
		contentPane.add(lblNumberRows);

		rows = new JTextField();
		rows.setColumns(10);
		rows.setBounds(558, 614, 187, 30);
		contentPane.add(rows);

		JLabel lblSeatsPerRow = new JLabel("Seats per row");
		lblSeatsPerRow.setBounds(480, 661, 67, 20);
		contentPane.add(lblSeatsPerRow);

		seats = new JTextField();
		seats.setColumns(10);
		seats.setBounds(558, 652, 187, 30);
		contentPane.add(seats);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(853, 235, 111, 21);
		contentPane.add(comboBox);
		// action
		ActionListener action = new ManageEventController(this);
		btnAddSchedule.addActionListener(action);
		btnAddZone.addActionListener(action);
		btnSaveEvent.addActionListener(action);
		comboBox.addActionListener(action);
	}

	public void updateDataSchedule() {
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
		for (int i = 0; i < this.schedules.size(); i++) {
			model.addRow(
					new Object[] { i + 1, this.schedules.get(i).getStartTime(), this.schedules.get(i).getEndTime() });
		}
		this.comboBox.removeAllItems();
		for (Schedule schedule : this.schedules) {
			this.comboBox.addItem(schedule.getStartTime() + " - " + schedule.getEndTime());
		}
	}

	public void updateDataZone() {
		// clear old data and insert again
		DefaultTableModel model = (DefaultTableModel) tableSeat.getModel();
		int rowCount = model.getRowCount();
		// Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		// Set renderer for all columns
		for (int i = 0; i < tableSeat.getColumnCount(); i++) {
			tableSeat.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		if (this.zones != null) {
			for (int i = 0; i < zones.size(); i++) {
				model.addRow(new Object[] { i + 1, zones.get(i).getName(), zones.get(i).getTicketPrice(),
						zones.get(i).getRows(), zones.get(i).getColumn() });
			}
		}
	}
	public void makeEmptyScheduleForm()
	{
		this.startTime.clear();
		this.endTime.clear();
	}
	public void makeEmptyZoneForm()
	{
		this.nameZone.setText("");
		this.price.setText("");
		this.rows.setText("");
		this.seats.setText("");
	}
}

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.toedter.calendar.JDateChooser;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import model.Zone;

public class AddEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameEvent;
	public JTextArea Description;
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
	public JPopupMenu popupMenu;
	public List<Event> events;
	public List<Schedule> schedules;
	public List<Zone> zones;

	public AddEventView() {
		schedules = new ArrayList<>();
		zones = new ArrayList<>();
		events = new ArrayList<>();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1000, 800);
		contentPane = new JPanel();
		ImageIcon Icon = new ImageIcon("images\\event.png");
		this.setIconImage(Icon.getImage());
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Label
		JLabel lblEvent = new JLabel("Information of event");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(408, 0, 243, 51);
		contentPane.add(lblEvent);

		JLabel lblNameLabel = new JLabel("Name event");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(350, 63, 80, 13);
		contentPane.add(lblNameLabel);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(387, 170, 60, 13);
		contentPane.add(lblDate);

		JLabel lblStart = new JLabel("Start time");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStart.setBounds(71, 536, 80, 30);
		contentPane.add(lblStart);

		JLabel lblEndTime = new JLabel("End time");
		lblEndTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEndTime.setBounds(76, 581, 80, 30);
		contentPane.add(lblEndTime);

		JLabel lblDiscription = new JLabel("Description");
		lblDiscription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiscription.setBounds(350, 123, 80, 13);
		contentPane.add(lblDiscription);

		JLabel lblZone = new JLabel("Name zone");
		lblZone.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblZone.setBounds(565, 528, 88, 20);
		contentPane.add(lblZone);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPrice.setBounds(605, 568, 50, 20);
		contentPane.add(lblPrice);

		JLabel lblNumberRows = new JLabel("Number rows");
		lblNumberRows.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumberRows.setBounds(548, 608, 88, 20);
		contentPane.add(lblNumberRows);

		JLabel lblSeatsPerRow = new JLabel("Seats per row");
		lblSeatsPerRow.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeatsPerRow.setBounds(544, 644, 100, 20);
		contentPane.add(lblSeatsPerRow);

		// Button
		JButton btnSaveEvent = new JButton("Save event");
		btnSaveEvent.setBackground(new Color(56, 102, 65));
		btnSaveEvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSaveEvent.setBounds(408, 723, 120, 30);
		contentPane.add(btnSaveEvent);

		JButton btnAddSchedule = new JButton("Add schedule");
		btnAddSchedule.setBackground(new Color(56, 102, 65));
		btnAddSchedule.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddSchedule.setBounds(143, 639, 120, 30);
		contentPane.add(btnAddSchedule);

		JButton btnAddZone = new JButton("Add zone");
		btnAddZone.setBackground(new Color(56, 102, 65));
		btnAddZone.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddZone.setBounds(683, 680, 120, 30);
		contentPane.add(btnAddZone);

		// Text field
		nameEvent = new JTextField();
		nameEvent.setBounds(436, 56, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);

		Description = new JTextArea();
		Description.setLineWrap(true);
		Description.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(Description);
		contentPane.add(scroll);
		scroll.setBounds(436, 105, 187, 50);

		nameZone = new JTextField();
		nameZone.setColumns(10);
		nameZone.setBounds(643, 524, 187, 30);
		contentPane.add(nameZone);

		price = new JTextField();
		price.setColumns(10);
		price.setBounds(643, 563, 187, 30);
		price.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0' && c <= '9') || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == '.')) {
					e.consume();
				}
			}
		});
		contentPane.add(price);

		rows = new JTextField();
		rows.setColumns(10);
		rows.setBounds(643, 602, 187, 30);
		rows.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume(); // Ignore non-digit input
				}
			}
		});
		contentPane.add(rows);

		seats = new JTextField();
		seats.setColumns(10);
		seats.setBounds(643, 640, 187, 30);
		contentPane.add(seats);

		// Time picker
		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings.use24HourClockFormat();
		startTime = new TimePicker(timeSettings);
		startTime.setTime(LocalTime.now());
		startTime.setBounds(143, 536, 120, 33);
		contentPane.add(startTime);

		endTime = new TimePicker(timeSettings);
		endTime.setBounds(143, 582, 120, 33);
		endTime.setTime(LocalTime.now());
		contentPane.add(endTime);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateEvent = new JDateChooser();
		dateEvent.setBounds(436, 162, 187, 30);
		dateEvent.setDateFormatString(dateFormat.toPattern());
		contentPane.add(dateEvent);
		
		//Data table
		//Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setRowHeight(20);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setDefaultEditor(Object.class, null);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>STT<b><html>",
				"<html><b>Start time<b><html>", "<html><b>End time<b><html>" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(27, 257, 353, 257);
		contentPane.add(scrollPane);

		//Zone seat
		tableSeat = new JTable();
		tableSeat.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tableSeat.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableSeat.setDefaultEditor(Object.class, null);
		tableSeat.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "<html><b>STT<b><html>", "<html><b>Name zone<b><html>", "<html><b>Price ticket<b><html>",
						"<html><b>Number rows<b><html>", "<html><b>Seats per row<b><html>" }));
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableSeat.getColumnCount(); i++) {

			tableSeat.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		tableSeat.setRowHeight(20);
		JScrollPane scrollPane_2 = new JScrollPane(tableSeat);
		scrollPane_2.setBounds(436, 257, 528, 257);
		contentPane.add(scrollPane_2);

		//Combo box
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setBackground(new Color(56, 102, 65));
		comboBox.setBounds(843, 220, 120, 30);
		contentPane.add(comboBox);
		
		//Actions
		ActionListener action = new ManageEventController(this);
		tableSeat.getSelectionModel().addListSelectionListener((ListSelectionListener) action);
		table.getSelectionModel().addListSelectionListener((ListSelectionListener) action);
		btnAddSchedule.addActionListener(action);
		btnAddZone.addActionListener(action);
		btnSaveEvent.addActionListener(action);
		comboBox.addActionListener(action);
	}
	
	public void updateDataSchedule() {
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
		DefaultTableModel model = (DefaultTableModel) tableSeat.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
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

	public boolean checkTimeExisted(int index) {
		for (int i=0;i<this.schedules.size();i++) {
			Schedule schedule = this.schedules.get(i);
			if (((schedule.getStartTime().compareTo(this.startTime.getTime()) <= 0
					&& schedule.getEndTime().compareTo(this.startTime.getTime()) >= 0)
					|| (schedule.getStartTime().compareTo(this.endTime.getTime()) <= 0
							&& schedule.getEndTime().compareTo(this.endTime.getTime()) >= 0))&&(i!=index)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkLoseData() {
		for (Schedule schedule : this.schedules) {
			if (schedule.getZones().size() == 0) {
				return true;
			}
		}
		return false;
	}

	public void makeEmptyScheduleForm() {
		this.startTime.clear();
		this.endTime.clear();
	}

	public void makeEmptyZoneForm() {
		this.nameZone.setText("");
		this.price.setText("");
		this.rows.setText("");
		this.seats.setText("");
	}

	public void makeEmptyEventForm() {
		this.nameEvent.setText("");
		this.Description.setText("");
		this.dateEvent.getDateEditor().setDate(null);
	}

	public boolean checkIsBlankFormZone() {
		if (this.nameZone.getText().isBlank() || this.price.getText().isBlank() || this.rows.getText().isBlank()
				|| this.seats.getText().isBlank()) {
			return true;
		}
		return false;
	}

	public void ShowError(String error) {
		JOptionPane.showMessageDialog(contentPane, error, "Swing Tester", JOptionPane.ERROR_MESSAGE);
	}

	public void ShowSuccess(String message) {
		JOptionPane.showMessageDialog(contentPane, message, "Swing Alert", JOptionPane.INFORMATION_MESSAGE);
	}
	
}

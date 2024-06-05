package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.toedter.calendar.JDateChooser;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import model.Zone;

import javax.swing.JComboBox;
import java.awt.Color;

public class AddEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField nameEvent;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	public JTextArea Description;
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

		JLabel lblEvent = new JLabel("Information of event");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(408, 0, 243, 51);
		contentPane.add(lblEvent);

		JButton btnSaveEvent = new JButton("Save event");
		btnSaveEvent.setBackground(new Color(56, 102, 65));
		btnSaveEvent.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSaveEvent.setBounds(408, 723, 120, 30);
		contentPane.add(btnSaveEvent);

		nameEvent = new JTextField();
		nameEvent.setBounds(436, 56, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);

		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(350, 63, 80, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("Description");
		lblDiscription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDiscription.setBounds(350, 123, 80, 13);
		contentPane.add(lblDiscription);

		Description = new JTextArea();
		Description.setLineWrap(true);
		Description.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(Description);
		contentPane.add(scroll);
		scroll.setBounds(436, 105, 187, 50);
		
		lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(387, 170, 60, 13);
		contentPane.add(lblDate);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateEvent = new JDateChooser();
		dateEvent.setBounds(436, 162, 187, 30);
		dateEvent.setDateFormatString(dateFormat.toPattern());
		contentPane.add(dateEvent);
		// Schedule
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>STT<b><html>", "<html><b>Start time<b><html>", "<html><b>End time<b><html>" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(27, 257, 353, 257);
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
		startTime.setBounds(143, 536, 120, 33); // Adjusted size and position
		contentPane.add(startTime);

		JLabel lblStart = new JLabel("Start time");
		lblStart.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStart.setBounds(71, 536, 80, 30);
		contentPane.add(lblStart);

		endTime = new TimePicker(timeSettings);
		endTime.setBounds(143, 582, 120, 33);
		endTime.setTime(LocalTime.now());
		contentPane.add(endTime);

		JLabel lblEndTime = new JLabel("End time");
		lblEndTime.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEndTime.setBounds(76, 581, 80, 30);
		contentPane.add(lblEndTime);

		JButton btnAddSchedule = new JButton("Add schedule");
		btnAddSchedule.setBackground(new Color(56, 102, 65));
		btnAddSchedule.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddSchedule.setBounds(143, 639, 120, 30);
		contentPane.add(btnAddSchedule);

		// Zone for seating
		tableSeat = new JTable();
		tableSeat.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tableSeat.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		tableSeat.setDefaultEditor(Object.class, null);
		// set column name of table
		tableSeat.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "<html><b>STT<b><html>", "<html><b>Name zone<b><html>", "<html><b>Price ticket<b><html>", "<html><b>Number rows<b><html>", "<html><b>Seats per row<b><html>" }));
		DefaultTableCellRenderer centerRenderer_2 = new DefaultTableCellRenderer();
		centerRenderer_2.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableSeat.getColumnCount(); i++) {
			tableSeat.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer_2);
		}
		JScrollPane scrollPane_2 = new JScrollPane(tableSeat);
		scrollPane_2.setBounds(436, 257, 528, 257);
		contentPane.add(scrollPane_2);

		JButton btnAddZone = new JButton("Add zone");
		btnAddZone.setBackground(new Color(56, 102, 65));
		btnAddZone.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnAddZone.setBounds(683, 680, 120, 30);
		contentPane.add(btnAddZone);

		JLabel lblZone = new JLabel("Name zone");
		lblZone.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblZone.setBounds(565, 528, 88, 20);
		contentPane.add(lblZone);

		nameZone = new JTextField();
		nameZone.setColumns(10);
		nameZone.setBounds(643, 524, 187, 30);
		contentPane.add(nameZone);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPrice.setBounds(605, 568, 50, 20);
		contentPane.add(lblPrice);

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

		JLabel lblNumberRows = new JLabel("Number rows");
		lblNumberRows.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNumberRows.setBounds(548, 608, 88, 20);
		contentPane.add(lblNumberRows);

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

		JLabel lblSeatsPerRow = new JLabel("Seats per row");
		lblSeatsPerRow.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSeatsPerRow.setBounds(544, 644, 100, 20);
		contentPane.add(lblSeatsPerRow);

		seats = new JTextField();
		seats.setColumns(10);
		seats.setBounds(643, 640, 187, 30);
		contentPane.add(seats);

		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setBackground(new Color(56, 102, 65));
		comboBox.setBounds(843, 220, 120, 30);
		contentPane.add(comboBox);
		// pop up menu
		popupMenu = new JPopupMenu();
		JPopupMenu menu2 = new JPopupMenu();
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem deleteItemSeat = new JMenuItem("Delete");
		popupMenu.add(deleteItem);
		menu2.add(deleteItemSeat);
		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = table
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
						if (rowAtPoint > -1) {
							table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
						}
					}
				});
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		menu2.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = tableSeat
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), tableSeat));
						if (rowAtPoint > -1) {
							tableSeat.setRowSelectionInterval(rowAtPoint, rowAtPoint);
						}
					}
				});
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {

			}
		});
		table.setComponentPopupMenu(popupMenu);
		tableSeat.setComponentPopupMenu(menu2);
		// action
		ActionListener action = new ManageEventController(this);
		btnAddSchedule.addActionListener(action);
		btnAddZone.addActionListener(action);
		btnSaveEvent.addActionListener(action);
		deleteItem.addActionListener(action);
		deleteItemSeat.addActionListener(action);
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

	public boolean checkTimeExisted() {
		for (Schedule schedule : this.schedules) {
			if ((schedule.getStartTime().compareTo(this.startTime.getTime()) <= 0
					&& schedule.getEndTime().compareTo(this.startTime.getTime()) >= 0)
					|| (schedule.getStartTime().compareTo(this.endTime.getTime()) <= 0
							&& schedule.getEndTime().compareTo(this.endTime.getTime()) >= 0)) {
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

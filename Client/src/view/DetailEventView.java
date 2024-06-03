package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import dto.Booked;
import model.Event;
import model.Schedule;
import model.Seat;
import model.Zone;

public class DetailEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField nameEvent;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	public JTextField Discription;
	private JLabel lblDate;
	public JTable table;
	public JTextField dateEvent;
	public JComboBox<String> comboBox;
	public Event event;
	public List<Booked> booking;
	private JPanel seatingChartPanel;
	private JTextField textTotal;

	public DetailEventView(ObjectInputStream in, ObjectOutputStream out) {
		booking = new ArrayList<>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1352, 773);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Events");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(436, 25, 75, 51);
		contentPane.add(lblEvent);

		nameEvent = new JTextField();
		nameEvent.setBounds(386, 86, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);

		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setBounds(299, 89, 67, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("Discription");
		lblDiscription.setBounds(306, 139, 60, 13);
		contentPane.add(lblDiscription);

		Discription = new JTextField();
		Discription.setColumns(10);
		Discription.setBounds(386, 125, 187, 42);
		contentPane.add(Discription);

		lblDate = new JLabel("Date");
		lblDate.setBounds(306, 183, 60, 13);
		contentPane.add(lblDate);
		dateEvent = new JTextField();
		dateEvent.setColumns(10);
		dateEvent.setBounds(386, 175, 187, 30);
		contentPane.add(dateEvent);
		// display seat

		comboBox = new JComboBox<String>();
		comboBox.setBounds(50, 258, 111, 21);
		contentPane.add(comboBox);
		seatingChartPanel = new JPanel();
		JScrollPane scrollSeat = new JScrollPane(seatingChartPanel);
		scrollSeat.setBounds(50, 300, 900, 400);
		contentPane.add(scrollSeat);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(351, 260, 67, 30);

		JButton btnNotBook = new JButton("");
		btnNotBook.setBackground(Color.LIGHT_GRAY);
		btnNotBook.setBounds(548, 258, 67, 30);
		contentPane.add(btnNewButton);
		contentPane.add(btnNotBook);

		JLabel lblBooked = new JLabel("BOOKED");
		lblBooked.setBounds(281, 266, 60, 13);
		contentPane.add(lblBooked);

		JLabel lblNotBook = new JLabel("NOT BOOK");
		lblNotBook.setBounds(468, 266, 80, 13);
		contentPane.add(lblNotBook);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(1109, 615, 85, 21);
		contentPane.add(btnConfirm);

		textTotal = new JTextField();
		textTotal.setColumns(10);
		textTotal.setBounds(1069, 564, 187, 30);
		contentPane.add(textTotal);

		JLabel lblTotal = new JLabel("Total");
		lblTotal.setBounds(1005, 572, 60, 13);
		contentPane.add(lblTotal);
		// show seat booked
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "STT", "Seat Id", "Zone ID", "Name Zone", "Price" }));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(960, 300, 368, 243);
		contentPane.add(scrollPane);
		// action
		ActionListener action = new ClientController(this, in, out);
		comboBox.addActionListener(action);
		btnConfirm.addActionListener(action);

	}

	public void showDataEvent() {
		this.nameEvent.setText(event.getName());
		this.Discription.setText(event.getDescription());
		this.dateEvent.setText(event.getDate() + "");
		// show combo box
		this.comboBox.removeAllItems();
		for (Schedule schedule : event.getSchedules()) {
			this.comboBox.addItem(schedule.getStartTime() + " - " + schedule.getEndTime());
		}
	}

	public void displaySeatingChart(Schedule schedule) {
		seatingChartPanel.removeAll();
		seatingChartPanel.setLayout(new BoxLayout(seatingChartPanel, BoxLayout.PAGE_AXIS));
		for (Zone zone : schedule.getZones()) {
			JLabel lblZone = new JLabel(zone.getName());
			lblZone.setFont(new Font("Tahoma", Font.BOLD, 15));
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
				@Override
				public Dimension getMaximumSize() {
					return getPreferredSize();
				}
			};
			panel.add(lblZone);
			JPanel rowZone = new JPanel(); // Create a new panel for each row of seats
			rowZone.setLayout(new BoxLayout(rowZone, BoxLayout.LINE_AXIS));
			rowZone.add(panel);
			seatingChartPanel.add(rowZone); // Add the row panel to the overall layout
			for (int i = 0; i < zone.getRows(); i++) {
				JPanel rowPanel = new JPanel(); // Create a new panel for each row of seats
				rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.LINE_AXIS));
				for (int j = 0; j < zone.getColumn(); j++) {
					Seat seat = zone.getSeats().get(i * zone.getColumn() + j);
					JButton seatButton = new JButton();
					seatButton.setText(seat.getSeatId());
					if (seat.isBooked()) {
						seatButton.setBackground(Color.RED);
					} else {
						seatButton.setBackground(Color.LIGHT_GRAY);
						seatButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (seatButton.getBackground().equals(Color.GREEN)) {
									seatButton.setBackground(Color.LIGHT_GRAY);
									removeSeat(seat.getSeatId());
								} else {
									seatButton.setBackground(Color.GREEN);
									booking.add(new Booked(seat.getSeatId(), zone.getZoneId(), zone.getName(),
											zone.getTicketPrice()));
								}
								showSeatBooking();
							}
						});
					}
					rowPanel.add(seatButton);
				}
				seatingChartPanel.add(rowPanel); // Add the row panel to the overall layout
				seatingChartPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add some vertical space between rows
			}
		}
		seatingChartPanel.revalidate();
		seatingChartPanel.repaint();
	}

	public void removeSeat(String seadId) {
		Iterator<Booked> iterator = this.booking.iterator();
		while (iterator.hasNext()) {
			Booked value = iterator.next();
			if (value.getSeatId().equals(seadId)) {
				iterator.remove();
			}
		}
	}

	public void showSeatBooking() {
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

		double price = 0;
		for (int i = 0; i < this.booking.size(); i++) {
			price += this.booking.get(i).getPrice();
			model.addRow(new Object[] { i + 1, this.booking.get(i).getSeatId(), this.booking.get(i).getZoneId(),
					this.booking.get(i).getNameZone(), this.booking.get(i).getPrice() });
		}
		this.textTotal.setText(price + "");
	}

	public void ShowError(String error) {
		JOptionPane.showMessageDialog(contentPane, error, "Swing Tester", JOptionPane.ERROR_MESSAGE);
	}
}

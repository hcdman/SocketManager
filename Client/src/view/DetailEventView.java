package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
	public JTextArea Description;
	public JTable table;
	public JTextField dateEvent;
	public JComboBox<String> comboBox;
	public Event event;
	public List<Booked> booking;
	private JPanel seatingChartPanel;
	private JTextField textTotal;
	public JTextField phoneNumber;
	public JTextField name;

	public DetailEventView(ObjectInputStream in, ObjectOutputStream out,Socket client) {
		booking = new ArrayList<>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon Icon = new ImageIcon("images\\client.png");
		this.setIconImage(Icon.getImage());
		setBounds(200, 200, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Label
		JLabel lblEvent = new JLabel("Information of event");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(440, 10, 228, 51);
		contentPane.add(lblEvent);

		JLabel lblNameLabel = new JLabel("Name event");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(382, 89, 80, 20);
		contentPane.add(lblNameLabel);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescription.setBounds(389, 135, 80, 20);
		contentPane.add(lblDescription);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(420, 180, 60, 20);
		contentPane.add(lblDate);

		JLabel lblBooked = new JLabel("BOOKED");
		lblBooked.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBooked.setBounds(320, 265, 60, 20);
		contentPane.add(lblBooked);

		JLabel lblNotBook = new JLabel("NOT BOOK");
		lblNotBook.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNotBook.setBounds(465, 265, 80, 20);
		contentPane.add(lblNotBook);

		JLabel lblTotal = new JLabel("Total cost");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTotal.setBounds(700, 601, 80, 25);
		contentPane.add(lblTotal);

		JLabel lblSchedule = new JLabel("Schedule");
		lblSchedule.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSchedule.setBounds(50, 265, 92, 20);
		contentPane.add(lblSchedule);

		JLabel lblYourPhoneNumber = new JLabel("Your phone number");
		lblYourPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourPhoneNumber.setBounds(650, 645, 150, 25);
		contentPane.add(lblYourPhoneNumber);
		
		JLabel lblYourName = new JLabel("Your name");
		lblYourName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblYourName.setBounds(700, 685, 92, 25);
		contentPane.add(lblYourName);
		// Text field
		nameEvent = new JTextField();
		nameEvent.setBounds(469, 86, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);
		nameEvent.setEditable(false);
		
		Description = new JTextArea();
		Description.setLineWrap(true);
		Description.setWrapStyleWord(true);
		Description.setEditable(false);
		JScrollPane scroll = new JScrollPane(Description);
		scroll.setBounds(469, 124, 187, 42);
		contentPane.add(scroll);

		dateEvent = new JTextField();
		dateEvent.setColumns(10);
		dateEvent.setBounds(469, 175, 187, 30);
		dateEvent.setEditable(false);
		contentPane.add(dateEvent);

		textTotal = new JTextField();
		textTotal.setColumns(10);
		textTotal.setBounds(789, 600, 187, 30);
		textTotal.setEditable(false);
		contentPane.add(textTotal);

		phoneNumber = new JTextField();
		phoneNumber.setColumns(10);
		phoneNumber.setBounds(789, 640, 187, 30);
		phoneNumber.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		contentPane.add(phoneNumber);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(789, 680, 187, 30);
		contentPane.add(name);

		// Combo box
		comboBox = new JComboBox<String>();
		comboBox.setBackground(new Color(56, 102, 65));
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setBounds(123, 260, 120, 30);
		contentPane.add(comboBox);

		// Button
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(380, 260, 67, 30);

		JButton btnNotBook = new JButton("");
		btnNotBook.setBackground(Color.LIGHT_GRAY);
		btnNotBook.setBounds(533, 260, 67, 30);
		contentPane.add(btnNewButton);
		contentPane.add(btnNotBook);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBackground(new Color(56, 102, 65));
		btnConfirm.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConfirm.setBounds(891, 720, 85, 30);
		contentPane.add(btnConfirm);

		// Seating Panel
		seatingChartPanel = new JPanel();
		JScrollPane scrollSeat = new JScrollPane(seatingChartPanel);
		scrollSeat.setBounds(50, 300, 550, 450);
		contentPane.add(scrollSeat);

		// Data table
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setDefaultEditor(Object.class, null);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "<html><b>STT<b><html>", "<html><b>Seat Id<b><html>", "<html><b>Zone ID<b><html>",
						"<html><b>Name Zone<b><html>", "<html><b>Price<b><html>" }));
		table.setRowHeight(20);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {

			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(608, 300, 368, 277);
		contentPane.add(scrollPane);

		// Actions
		ActionListener action = new ClientController(this, in, out,client);
		comboBox.addActionListener(action);
		btnConfirm.addActionListener(action);

	}

	public void showDataEvent() {
		this.nameEvent.setText(event.getName());
		this.Description.setText(event.getDescription());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.dateEvent.setText(event.getDate().format(formatter) + "");
		this.comboBox.removeAllItems();
		for (Schedule schedule : event.getSchedules()) {
			this.comboBox.addItem(schedule.getStartTime() + " - " + schedule.getEndTime());
		}
	}

	public void displaySeatingChart(Schedule schedule) {
		seatingChartPanel.removeAll();
		seatingChartPanel.setLayout(new BoxLayout(seatingChartPanel, BoxLayout.PAGE_AXIS));
		for (Zone zone : schedule.getZones()) {
			JLabel lblZone = new JLabel(zone.getZoneId()+" - "+zone.getName());
			lblZone.setFont(new Font("Tahoma", Font.BOLD, 15));
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
				@Override
				public Dimension getMaximumSize() {
					return getPreferredSize();
				}
			};
			panel.add(lblZone);
			JPanel rowZone = new JPanel();
			rowZone.setLayout(new BoxLayout(rowZone, BoxLayout.LINE_AXIS));
			rowZone.add(panel);
			seatingChartPanel.add(rowZone);
			for (int i = 0; i < zone.getRows(); i++) {
				JPanel rowPanel = new JPanel();
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
				seatingChartPanel.add(rowPanel);
				seatingChartPanel.add(Box.createRigidArea(new Dimension(0, 5)));
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

	public void ShowSuccess(String message) {
		JOptionPane.showMessageDialog(contentPane, message, "Swing Alert", JOptionPane.INFORMATION_MESSAGE);
	}
}

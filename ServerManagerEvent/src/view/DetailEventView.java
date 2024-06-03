package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.border.EmptyBorder;

import controller.ManageEventController;
import model.Event;
import model.Schedule;
import model.Seat;
import model.Zone;

public class DetailEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField nameEvent;
	private JLabel lblNameLabel;
	private JLabel lblDescription;
	public JTextField Description;
	private JLabel lblDate;
	public JTextField dateEvent;
	public JComboBox<String> comboBox;
	public Event event;
	private JPanel seatingChartPanel;
	private JLabel lblSchedule;

	public DetailEventView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1040, 856);
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
		nameEvent.setEditable(false);
		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setBounds(299, 89, 67, 13);
		contentPane.add(lblNameLabel);

		lblDescription = new JLabel("Description");
		lblDescription.setBounds(306, 139, 60, 13);
		contentPane.add(lblDescription);

		Description = new JTextField();
		Description.setColumns(10);
		Description.setBounds(386, 125, 187, 42);
		contentPane.add(Description);
		Description.setEditable(false);
		lblDate = new JLabel("Date");
		lblDate.setBounds(306, 183, 60, 13);
		contentPane.add(lblDate);
		dateEvent = new JTextField();
		dateEvent.setColumns(10);
		dateEvent.setBounds(386, 175, 187, 30);
		contentPane.add(dateEvent);
		dateEvent.setEditable(false);
		// display seat
		comboBox = new JComboBox<String>();
		comboBox.setBounds(132, 260, 111, 21);
		contentPane.add(comboBox);
		// contentPane.add(seatingChartPanel);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(726, 258, 67, 30);

		JButton btnNotBook = new JButton("");
		btnNotBook.setBackground(Color.LIGHT_GRAY);
		btnNotBook.setBounds(883, 258, 67, 30);
		contentPane.add(btnNewButton);
		contentPane.add(btnNotBook);

		JLabel lblBooked = new JLabel("BOOKED");
		lblBooked.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblBooked.setBounds(656, 268, 60, 13);
		contentPane.add(lblBooked);

		JLabel lblNotBook = new JLabel("NOT BOOK");
		lblNotBook.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNotBook.setBounds(814, 268, 67, 13);
		contentPane.add(lblNotBook);
		
		seatingChartPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(seatingChartPanel);
		scrollPane.setBounds(50, 300, 900, 500);
		contentPane.add(scrollPane);
		
		JLabel lblSeating = new JLabel("Seating chart ");
		lblSeating.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSeating.setBounds(383, 241, 172, 51);
		contentPane.add(lblSeating);
		
		lblSchedule = new JLabel("Schedule");
		lblSchedule.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSchedule.setBounds(61, 264, 75, 13);
		contentPane.add(lblSchedule);
		// action
		ActionListener action = new ManageEventController(this);
		comboBox.addActionListener(action);
	}

	public void showDataEvent() {
		this.nameEvent.setText(event.getName());
		this.Description.setText(event.getDescription());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		this.dateEvent.setText(event.getDate().format(formatter) + "");
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
			lblZone.setFont(new Font("Tahoma", Font.BOLD, 15));JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
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
}

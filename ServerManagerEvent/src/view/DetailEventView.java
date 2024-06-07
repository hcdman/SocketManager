package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
	public JTextArea Description;
	public JTextField dateEvent;
	public JComboBox<String> comboBox;
	public Event event;
	private JPanel seatingChartPanel;

	public DetailEventView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		ImageIcon Icon = new ImageIcon("images\\event.png");
		this.setIconImage(Icon.getImage());
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		//Label
		JLabel lblEvent = new JLabel("Information of event");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(398, 20, 243, 51);
		contentPane.add(lblEvent);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDescription.setBounds(334, 137, 80, 20);
		contentPane.add(lblDescription);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDate.setBounds(357, 179, 50, 20);
		contentPane.add(lblDate);
		
		JLabel lblBooked = new JLabel("BOOKED");
		lblBooked.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBooked.setBounds(662, 264, 60, 20);
		contentPane.add(lblBooked);

		JLabel lblNotBook = new JLabel("NOT BOOK");
		lblNotBook.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNotBook.setBounds(808, 264, 80, 20);
		contentPane.add(lblNotBook);
		
		JLabel lblSeating = new JLabel("Stage");
		lblSeating.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSeating.setBounds(460, 260, 80, 40);
		contentPane.add(lblSeating);
		
		JLabel lblSchedule = new JLabel("Schedule");
		lblSchedule.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSchedule.setBounds(50, 264, 75, 20);
		contentPane.add(lblSchedule);
		//Text to fill
		nameEvent = new JTextField();
		nameEvent.setBounds(427, 84, 187, 30);
		contentPane.add(nameEvent);
		nameEvent.setColumns(10);
		nameEvent.setEditable(false);
		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNameLabel.setBounds(327, 87, 80, 20);
		contentPane.add(lblNameLabel);
		
        Description = new JTextArea();
		Description.setLineWrap(true);
		Description.setWrapStyleWord(true);
		Description.setEditable(false);
		JScrollPane scroll = new JScrollPane(Description);
		scroll.setBounds(427, 124, 187, 42);
		contentPane.add(scroll);

		dateEvent = new JTextField();
		dateEvent.setColumns(10);
		dateEvent.setBounds(427, 176, 187, 30);
		contentPane.add(dateEvent);
		dateEvent.setEditable(false);
		
		//Button
		JButton btnNewButton = new JButton("");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(726, 260, 67, 30);

		JButton btnNotBook = new JButton("");
		btnNotBook.setBackground(Color.LIGHT_GRAY);
		btnNotBook.setBounds(881, 260, 67, 30);
		contentPane.add(btnNewButton);
		contentPane.add(btnNotBook);
		
		// Combo box
		comboBox = new JComboBox<String>();
		comboBox.setBackground(new Color(56, 102, 65));
		comboBox.setBounds(121, 260, 120, 30);
		contentPane.add(comboBox);
		
		//Seating panel
		seatingChartPanel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(seatingChartPanel);
		scrollPane.setBounds(50, 300, 900, 450);
		contentPane.add(scrollPane);
		
		//Actions
		ActionListener action = new ManageEventController(this);
		comboBox.addActionListener(action);
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
			lblZone.setFont(new Font("Tahoma", Font.BOLD, 15));JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
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
}

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JLabel lblDiscription;
	public JTextField Discription;
	private JLabel lblDate;
	public JTextField dateEvent;
	public JComboBox<String> comboBox;
	public Event event;
	private JPanel seatingChartPanel;

	public DetailEventView() {
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
		seatingChartPanel.setBounds(50, 300, 900, 400);
		contentPane.add(seatingChartPanel);
		
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
		// action
		ActionListener action = new ManageEventController(this);
		comboBox.addActionListener(action);
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
		seatingChartPanel.setLayout(new GridLayout(10, 10)); // Adjust the grid size as needed
		for (Zone zone : schedule.getZones()) {
			for (Seat seat : zone.getSeats()) {
				JButton seatButton = new JButton();
				seatButton.setPreferredSize(new Dimension(30, 30));
				seatButton.setText(seat.getSeatId());
				if (seat.isBooked()) {
					seatButton.setBackground(Color.RED);
				} else {
					seatButton.setBackground(Color.LIGHT_GRAY);
				}
				seatingChartPanel.add(seatButton);
			}
		}
		seatingChartPanel.revalidate();
		seatingChartPanel.repaint();
	}
}

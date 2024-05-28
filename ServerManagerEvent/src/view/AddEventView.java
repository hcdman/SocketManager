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
import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class AddEventView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNameLabel;
	private JLabel lblDiscription;
	private JTextField textField_1;
	private JLabel lblDate;
	public JTable table;
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
					AddEventView frame = new AddEventView();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddEventView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 625, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Events");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(296, 55, 75, 51);
		contentPane.add(lblEvent);

		JButton btnAddNewSchedule = new JButton("Next");
		btnAddNewSchedule.setBounds(280, 252, 121, 21);
		contentPane.add(btnAddNewSchedule);

		textField = new JTextField();
		textField.setBounds(246, 116, 187, 30);
		contentPane.add(textField);
		textField.setColumns(10);

		lblNameLabel = new JLabel("Name event");
		lblNameLabel.setBounds(159, 119, 67, 13);
		contentPane.add(lblNameLabel);

		lblDiscription = new JLabel("Discription");
		lblDiscription.setBounds(166, 169, 60, 13);
		contentPane.add(lblDiscription);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(246, 155, 187, 42);
		contentPane.add(textField_1);

		lblDate = new JLabel("Date");
		lblDate.setBounds(166, 213, 60, 13);
		contentPane.add(lblDate);

		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(246, 206, 187, 30);
		contentPane.add(dateChooser);
	}
}

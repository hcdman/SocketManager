package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.UserBooked;

public class UserBookedView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTable table;
	public List<UserBooked> users;


	/**
	 * Create the frame.
	 */
	public UserBookedView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(50, 100, 891, 525);
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel lblEvent = new JLabel("Data of booking ticket");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEvent.setBounds(365, 61, 200, 51);
		contentPane.add(lblEvent);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		// Disable table's editing mode
		table.setDefaultEditor(Object.class, null);
		// set column name of table
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "STT", "Phone number", "Event","Schedule","Zone","Seats","Time"}));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 122, 867, 325);
		contentPane.add(scrollPane);
	}
	public void showUsers() {
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		for (int i = 0; i < this.users.size(); i++) {
			model.addRow(
					new Object[] { i + 1, this.users.get(i).getPhoneNumber(), this.users.get(i).getIdEvent(),this.users.get(i).getIdSchedule(),this.users.get(i).getIdZone(),this.users.get(i).getIdSeats(), this.users.get(i).getTime().format(formatter) });
		}
	}

}

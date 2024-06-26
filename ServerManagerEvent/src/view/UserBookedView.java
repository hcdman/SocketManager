package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

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
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(200, 200, 1000, 800);
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);
		ImageIcon Icon = new ImageIcon("images\\event.png");
		this.setIconImage(Icon.getImage());
		
		//Label
		JLabel lblEvent = new JLabel("Ticket booking records");
		lblEvent.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblEvent.setBounds(310, 39, 250, 51);
		contentPane.add(lblEvent);
		
		JLabel lblIcon = new JLabel("");
		String path = "images\\user.png";
		ImageIcon img = new ImageIcon(path);
		int width = 100;
		int height =100;
		Image nw = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon nc = new ImageIcon(nw);
		lblIcon.setIcon(nc);
		lblIcon.setBounds(558, 14, 100, 100);
		contentPane.add(lblIcon);
		
		//Data table
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setDefaultEditor(Object.class, null);
		table.setRowHeight(20);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {  "<html><b>STT<b><html>","<html><b>Name<b><html>", "<html><b>Phone number<b><html>", "<html><b>Event<b><html>","<html><b>Schedule<b><html>","<html><b>Zone<b><html>","<html><b>Seats<b><html>","<html><b>Time<b><html>"}));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(20);
		columnModel.getColumn(3).setPreferredWidth(20);
		columnModel.getColumn(5).setPreferredWidth(20);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 124, 966, 576);
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
					new Object[] {i+1, this.users.get(i).getUserName(), this.users.get(i).getPhoneNumber(), this.users.get(i).getIdEvent(),this.users.get(i).getSchedule(),this.users.get(i).getZone(),this.users.get(i).getIdSeats(), this.users.get(i).getTime().format(formatter) });
		}
	}

}

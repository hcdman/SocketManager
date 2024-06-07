package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.ClientController;
import model.Event;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTable table;
	public List<Event> events;

	public HomeView(ObjectInputStream in, ObjectOutputStream out, Socket client) {
		this.events = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon Icon = new ImageIcon("images\\client.png");
		this.setIconImage(Icon.getImage());
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(200, 200, 1000, 800);
		setResizable(false);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Label
		JLabel lblIcon = new JLabel("");
		String path = "images\\home.png";
		ImageIcon img = new ImageIcon(path);
		int width = 112;
		int height = 112;
		Image nw = img.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon nc = new ImageIcon(nw);
		lblIcon.setIcon(nc);
		lblIcon.setBounds(465, 5, 112, 112);
		contentPane.add(lblIcon);

		// Data table
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setDefaultEditor(Object.class, null);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "<html><b>STT</b></html>","<html><b>ID</b></html>",
				"<html><b>Name event</b></html>", "<html><b>Description</b></html>", "<html><b>Date</b></html>" }));
		table.setRowHeight(20);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
		}
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);
		columnModel.getColumn(1).setPreferredWidth(10);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(104, 140, 819, 564);
		contentPane.add(scrollPane);

		// Actions
		ActionListener action = new ClientController(this, in, out, client);
		table.addMouseListener((MouseListener) action);
	}

	public void showEvents() {
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		for (int i = 0; i < this.events.size(); i++) {
			model.addRow(new Object[] { i + 1,this.events.get(i).getEventId(), this.events.get(i).getName(), this.events.get(i).getDescription(),
					this.events.get(i).getDate().format(formatter) });
		}
	}

	public void ShowError(String error) {
		JOptionPane.showMessageDialog(contentPane, error, "Swing Tester", JOptionPane.ERROR_MESSAGE);
	}

}

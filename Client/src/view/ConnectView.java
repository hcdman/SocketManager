package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.ClientController;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;

public class ConnectView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField IpServer;
	public JTextField Port;
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
					ConnectView frame = new ConnectView();
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
	public ConnectView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon Icon = new ImageIcon("images\\client.png");
		this.setIconImage(Icon.getImage());
		setBounds(100, 100, 424, 264);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(167, 201, 87));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		
		//Label
		JLabel lblClient = new JLabel("CLIENT");
		lblClient.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblClient.setBounds(172, 31, 80, 25);
		contentPane.add(lblClient);
		
		JLabel lblIpServer = new JLabel("IP SERVER");
		lblIpServer.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIpServer.setBounds(99, 98, 80, 13);
		contentPane.add(lblIpServer);
		
		JLabel lblPort = new JLabel("PORT");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPort.setBounds(130, 133, 45, 13);
		contentPane.add(lblPort);
		
		//Text field
		IpServer = new JTextField();
		IpServer.setFont(new Font("Tahoma", Font.PLAIN, 13));
		IpServer.setBounds(172, 90, 96, 30);
		contentPane.add(IpServer);
		IpServer.setColumns(10);
		
		Port = new JTextField();
		Port.setFont(new Font("Tahoma", Font.PLAIN, 13));
		Port.setColumns(10);
		Port.setBounds(172, 127, 96, 30);
		contentPane.add(Port);
		try {
			IpServer.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Port.setText(3000+"");
		
		//Button
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBackground(new Color(56, 102, 65));
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConnect.setBounds(172, 176, 85, 30);
		contentPane.add(btnConnect);
		
		//Action
		ActionListener action = new ClientController(this);
		btnConnect.addActionListener(action);
	}
	public void ShowError(String error) {
		JOptionPane.showMessageDialog(contentPane, error, "Swing Tester", JOptionPane.ERROR_MESSAGE);
	}
}

package view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import controller.ClientController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ConnectView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField IPSERVER;
	public JTextField PORT;

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
		setBounds(100, 100, 424, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		JLabel lblClient = new JLabel("CLIENT");
		lblClient.setBounds(206, 29, 45, 13);
		contentPane.add(lblClient);
		
		JLabel lblIpServer = new JLabel("IP SERVER");
		lblIpServer.setBounds(99, 101, 65, 13);
		contentPane.add(lblIpServer);
		
		IPSERVER = new JTextField();
		IPSERVER.setBounds(172, 98, 96, 25);
		contentPane.add(IPSERVER);
		IPSERVER.setColumns(10);
		
		JLabel lblPort = new JLabel("PORT");
		lblPort.setBounds(99, 133, 65, 13);
		contentPane.add(lblPort);
		
		PORT = new JTextField();
		PORT.setColumns(10);
		PORT.setBounds(172, 127, 96, 25);
		contentPane.add(PORT);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(172, 176, 85, 21);
		contentPane.add(btnConnect);
		IPSERVER.setText("192.168.19.1");
		PORT.setText("3000");
		//action
		ActionListener action = new ClientController(this);
		btnConnect.addActionListener(action);
		
	}
}

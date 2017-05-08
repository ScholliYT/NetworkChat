
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Das Userinterface, um sich mit dem Server zu verbinden und sich einzuloggen
 * @author Simon
 *
 */
public class LoginUi extends JDialog {
	
	private static final long serialVersionUID = -1244164852187125922L;
	
	private JTextField txtLocalhost;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JLabel lblStatusNichtVerbunden;
	private JLabel lblUsernameOderPasswort;
	
	
	
	public LoginUi(Manager m) {
		super((JDialog) null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle("Chatlogin");
		setBounds(100, 100, 451, 253);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Mit Server verbinden", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 425, 80);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblServerip = new JLabel("Server-IP:");
		lblServerip.setBounds(10, 23, 56, 14);
		panel.add(lblServerip);
		
		txtLocalhost = new JTextField();
		txtLocalhost.setBounds(76, 20, 339, 20);
		panel.add(txtLocalhost);
		txtLocalhost.setHorizontalAlignment(SwingConstants.RIGHT);
		txtLocalhost.setText("localhost:6000");
		txtLocalhost.setColumns(10);
		
		JButton btnVerbinden = new JButton("Verbinden");
		btnVerbinden.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean success = false;
				try{
					String ip = txtLocalhost.getText().split(":")[0];
					int port = Integer.parseInt(txtLocalhost.getText().split(":")[1]);
					success = m.verbinden(ip, port);
				}catch(Exception ex){}
				
				if(success){
					lblStatusNichtVerbunden.setForeground(Color.GREEN);
					lblStatusNichtVerbunden.setText("Status: Verbunden");
				}
			}
		});
		btnVerbinden.setBounds(326, 51, 89, 23);
		btnVerbinden.setFocusPainted(false);
		panel.add(btnVerbinden);
		
		lblStatusNichtVerbunden = new JLabel("Status: Nicht verbunden");
		lblStatusNichtVerbunden.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		lblStatusNichtVerbunden.setBounds(10, 55, 306, 14);
		panel.add(lblStatusNichtVerbunden);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Im Server einloggen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 102, 425, 120);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setBounds(10, 24, 54, 14);
		panel_1.add(lblNewLabel);
		
		JLabel lblPasswort = new JLabel("Passwort:");
		lblPasswort.setBounds(10, 49, 54, 14);
		panel_1.add(lblPasswort);
		
		txtUsername = new JTextField();
		txtUsername.setText("username");
		txtUsername.setHorizontalAlignment(SwingConstants.RIGHT);
		txtUsername.setBounds(74, 21, 341, 20);
		panel_1.add(txtUsername);
		txtUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.RIGHT);
		passwordField.setBounds(74, 46, 341, 20);
		panel_1.add(passwordField);
		
		JButton btnEinloggen = new JButton("Einloggen");
		btnEinloggen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(m.einloggen(txtUsername.getText(), String.copyValueOf(passwordField.getPassword()))){
					m.chatStarten();
					dispose();
				}else{
					lblUsernameOderPasswort.setVisible(true);
				}
			}
		});
		btnEinloggen.setBounds(326, 86, 89, 23);
		btnEinloggen.setFocusPainted(false);
		panel_1.add(btnEinloggen);
		
		lblUsernameOderPasswort = new JLabel("Username oder Passwort falsch!");
		lblUsernameOderPasswort.setVisible(false);
		lblUsernameOderPasswort.setForeground(UIManager.getColor("ToolBar.dockingForeground"));
		lblUsernameOderPasswort.setBounds(10, 90, 306, 14);
		panel_1.add(lblUsernameOderPasswort);
		
	}
}

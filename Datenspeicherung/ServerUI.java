import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ServerUI extends JFrame{

	private JPanel contentPane;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<String> list;
	private JPanel panel_1;
	private JMenuBar menuBar;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
	
	private static ServerUI instance;
	private JLabel lblNewLabel;
	private JMenu mnDatei;
	private JMenuItem mntmBeenden;
	
	public ServerUI(){
		setTitle("ChatServer running @");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 832, 537);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginManager.trySave();
				System.exit(0);
			}
		});
		mntmBeenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnDatei.add(mntmBeenden);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Teilnehmer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		
		lblNewLabel = new JLabel("Total: 0");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel, BorderLayout.SOUTH);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Chatlog", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		panel_1.add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scrollPane_1.setViewportView(textArea);
		
		instance = this;
	}
	
	public JList<String> getUserList(){
		return list;
	}
	
	public JLabel getLabel(){
		return lblNewLabel;
	}
	
	public void addMessage(String message){
		textArea.append(message + "\n");
	}
	
	
	public static ServerUI getInstance(){
		return instance;
	}
	
}
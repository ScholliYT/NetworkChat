import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Übernimmt den Ablauf des Programmes
 * @author Tom
 */
public class Manager {
	
	//Kommunikation zum Kommunizieren mit dem Server
	private Kommunikation k;
	//Userinterface für den Chat
	private ClientUi ui;
	private String username;

	public Manager(){ //Zu beginn ist erstmal alles null
		this.k = null;
		this.ui = null;
		this.username = "";
	}
	
	/**
	 * Versucht, diesen Client mit dem Server zu verbinden
	 * @param ip Die Ip des Servers
	 * @param port Der Port
	 * @return true, wenn das Verbinden geklappt hat, anstonsten false
	 */
	@SuppressWarnings("resource")
	public boolean verbinden(String ip, int port){
		try{
			Socket s = new Socket(); //Ein Socket wird erstellt
			s.connect(new InetSocketAddress(InetAddress.getByName(ip), port), 2000); //Der Socket wird verbunden, Timeout in 2 Sekunden
			if(s != null){  //Ist der Socket null?
				this.k = new Kommunikation(s); //Der Socket ist nicht null, und die möglichkeit zur weiteren Kommunikation wird erstellt
				return true; //Die Verbindung wurde erfolgreich erstellt, also wird true zurückgegeben
			}
		}catch(SocketTimeoutException ex){
			//ignore
		}catch(Exception e){
			e.printStackTrace();
		}
		return false; //Die Verbindung ist warum auch immer fehlgeschlagen, es wird false zurückgegeben
	}
	
	/**
	 * Versucht sich mit gegebenen Daten am Server einzuloggen
	 * @param username Der Username
	 * @param password Das Passwort des Users
	 * @return true, wenn der Loginversuch erfolgreich war, ansonsten false
	 */
	public boolean einloggen(String username, String password){
		AccountManager am = new AccountManager(k); //Ein Accountmanager zum einloggen wird erstellt
		if(am.einloggen(username, password)){ //Einloggen erfolgreich?
			this.username = username; //Ja, der username wird gesetzt
			return true; //true zurückgeben
		}
		return false; //Der Loginbversuch war nicht erfolgreich
	}

	public void chatStarten(){
		ui = new ClientUi(k, username); //Das Userinterface wird erstellt
		ui.setVisible(true); //Das Userinterface wird angezeigt
		
		ChatReciever reciever = new ChatReciever(k, ui); //Ein Reciever zum Empfangen von Nachrichten wird erstellt
		reciever.start(); //Der Reciever wird gestartet
	}

}
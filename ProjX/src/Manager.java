import java.net.InetAddress;
import java.net.Socket;

/**
 * Übernimmt den Ablauf des Programmes
 * @author Tom
 */
public class Manager {
	
	//Kommunikation zum Kommunizieren mit dem Server
	private Kommunikation k;
	//Userinterface für den Chat
	private ClientUi ui;

	public Manager(){ //Zu beginn ist erstmal alles null
		this.k = null;
		this.ui = null;
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
			Socket s = new Socket(InetAddress.getByName(ip), port); //Ein Socket wird erstellt und dieser wird verbunden
			if(s != null){  //Ist der Socket null?
				this.k = new Kommunikation(s); //Der Socket ist nicht null, und die möglichkeit zur weiteren Kommunikation wird erstellt
				return true; //Die Verbindung wurde erfolgreich erstellt, also wird true zurückgegeben
			}
		}catch(Exception e){}
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
		return am.einloggen(username, password); //Das Ergebis des Einlogversuches wird zurückgegeben
	}

	public void chatStarten(){
		ui = new ClientUi(k); //Das Userinterface wird erstellt
		ui.setVisible(true); //Das Userinterface wird angezeigt
		
		ChatReciever reciever = new ChatReciever(k, ui); //Ein Reciever zum Empfangen von Nachrichten wird erstellt
		reciever.start(); //Der Reciever wird gestartet
	}

}
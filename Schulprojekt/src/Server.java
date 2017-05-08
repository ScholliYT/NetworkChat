import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Die Serverklasse dieses Projektes.
 * Von hier aus wird das Projekt initialisiert und gestartet
 * @author Simon
 *
 */
public class Server extends ServerSocket {

	// Liste mit allen Clients die am Server angemeldet sind
	private ArrayList<Client> clients;
	//Objekt, um mit dem UI zu interagieren
	private UIInterface uihelper;
	//Objekt, welches die Annahme von neuen Clients übernimmt
	private ClientAnnahme clientAnnahme;
	
	/**
	 * Konstruktor dieser Klasse Startet den Server. Startet auf der IPv4-Adresse des Computers mit dem Port 6000
	 * 
	 * @throws Exception
	 *             wenn ein Fehler auftritt
	 */
	public Server() throws Exception {
		this(6000);
	}
	
	/**
	 * Startet den Server auf einem angegeben Port
	 * @param port Der Port
	 * @throws Exception wenn ein Fehler auftritt
	 */
	public Server(int port) throws Exception{
		super(port, 50, InetAddress.getLocalHost());
		uihelper = new UIInterface();
		clients = new ArrayList<Client>();
		clientAnnahme = new ClientAnnahme(this);
		clientAnnahme.start();
	}
	
	/**
	 * Gibt eine Liste mit allen Clients zurück
	 * 
	 * @return <code>java.util.List</code> mit allen Clients
	 */
	public List<Client> getClients() {
		return clients;
	}

	/**
	 * Sendet eine Nachricht an alle Clients, bis auf den Sender
	 * 
	 * @param message
	 *            Die zu versendende Nachricht
	 * @param sender
	 *            Der Sender der Nachricht
	 */
	public void sendMessages(String message, Client sender) {
		String msg = sender.getUsername() + ": " + message; //Die zu versendende Nachricht wird erzeugt
		uihelper.addMessage(msg); //Die Nachricht wird im Userinterface des Servers angezeigt
		synchronized (clients) { //Damit nicht 2 Threads gleichzeitig versuchen, mit den Clients zu interagieren (schreiben), wird hier dafür gesorgt, dass dieser Thread exklusive Rechte and er Liste hat
			for (Client c : clients) { //Allen Clients die Nachricht senden
				if (c == sender)
					continue; //Ist der aktuelle Client der Sender wird diesem die Nachricht nicht geschickt
				try {
					c.writeLine(msg); //Die Nachricht wird in den Stream geschrieben und somit verschickt
				} catch (Exception ignore) {
					ignore.printStackTrace(); //Eventuelle Fehler werden in der Konsole ausgegeben
				}
			}
		}
	}
	
	/**
	 * Sendet eine Nachricht ohne Absender an alle Clients
	 * @param message Die Nachricht, die versendet werden soll
	 */
	public void sendNotification(String message) {
		uihelper.addMessage(message); //Die Nachricht wird im Userinterface des Servers angezeigt
		synchronized (clients) { //Damit nicht 2 Threads gleichzeitig versuchen, mit den Clients zu interagieren (schreiben), wird hier dafür gesorgt, dass dieser Thread exklusive Rechte and er Liste hat
			for (Client c : clients) { //Allen Clients die Nachricht senden
				try {
					c.writeLine(message); //Die Nachricht wird in den Stream geschrieben und somit verschickt
				} catch (Exception e) {
					e.printStackTrace(); //Eventuelle Fehler werden in der Konsole ausgegeben
				}
			}
		}
	}
	
	/**
	 * Fügt dem Server einen neuen Client hinzu
	 * @param client Der neue Client
	 */
	public void addClient(Client client) {
		synchronized (clients) {
			clients.add(client); //Der Liste wird der neue Client hinzugefügt
		}
		sendNotification("Der Nutzer mit dem Namen " + client.getUsername() + " hat sich angemeldet!"); //Eine Info mit namen des neuen Nutzers wird an alle Clients verschickt
		uihelper.updateList(clients); //Im UserInterface des Servers wird die Liste mit allen Clients aktualisiert
	}
	
	public void removeClient(Client client){
		synchronized (clients) {
			clients.remove(client);
		}
		sendNotification(client.getUsername() + " hat sich abgemeldet.");
		uihelper.updateList(clients);
	}
	
	/**
	 * Beendet das Programm komplett
	 */
	public void terminate() {
		LoginManager.trySave(); //Es wird versucht, die aktuelle Login-Datenbank zu speichern
		try {
			this.close(); //Es wird versucht, diesen ServerSocket zu schließen
		} catch (IOException e) {
			e.printStackTrace(); //Eventuelle Fehler werden in der Konsole ausgegeben
		}
		System.exit(0); //Die JRE wird beendet
	}

}
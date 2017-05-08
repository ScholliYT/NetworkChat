import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Diese Klasse übernimmt die Kommunikation mit den Clients
 * Sie wartet auf neue Nachrichten und erkennt, wenn ein Client die Verbindung trennt
 * @author Simon
 */
public class ClientThread extends Thread{
	
	//Aktuelle Instanz des Servers
	private Server server;
	//Client, welcher in diesem Thread behandelt wird
	private Client client;
	
	/**
	 * Erstellt einen neuen Clientthread
	 * @param server Der Server
	 * @param client Der Client
	 */
	public ClientThread(Server server, Client client){
		this.server = server;
		this.client = client;
	}
	
	/**
	 * Wird aufgerufen, sobald der Thread gestartet wird
	 */
	@Override
	public void run(){
		try{
			client.getSocket().setSoTimeout(1000); //Es wird ein Timeout gesetzt
		}catch(SocketException e){
			e.printStackTrace(); //Falls was beim Timeout setzen falsch läuft, wird der Fehler auf der Konsole ausgegeben
		}
		
		while(true){ //Solange der Thread läuft wird unteres ausgeführt
			try{
				String message = client.waitForNextInput(); //Es wird auf neuen Input vom Client gewartet, und dieser Thread damit geblockt
				server.sendMessages(message, client); //Es gibt eine Nachricht, diese wird nun versendet
			}catch(SocketTimeoutException ste){ //Der Leseversuch ist ausgetimed. Dies ist nicht schlimmes und die Schleife beginnt von vorn
				continue; //Direkt nächster Schleifendurchlauf
			}catch(Exception e){ //Ein anderer Fehler ist aufgeteten und es kann nicht mehr mit dem Client kommuniziert werden
				server.removeClient(client); //Der Client wird dadurch abgemeldet
				break; //Die Schleife wird beendet
			}
		}
	}
	
}
import java.io.*;
import java.net.*;

/**
 * Stellt einen Client als Objekt da und erm�glicht die Kommunikation mit diesem
 * 
 * @author Simon
 */
public class Client{

	// Nutzername des Clients
	private String username;

	// Reader des Clients um Daten von diesem zu empfangen
	private BufferedReader reader;
	// Writer dieses Clients, um Daten zu senden
	private BufferedWriter writer;
	// Socket dieses Clients
	private Socket socket;
	// Ip-Adresse des Clients
	private InetAddress ip;

	/**
	 * Erzeugt einen neuen Client Übergeben wird ein Socket, welcher durch die
	 * Methode server.accept() erlangt wird
	 * 
	 * @param socket
	 *            java.net.Socket der Socket des Clients, normalerweise durch Server.accept()
	 *            erlangt (Siehe ClientAnnahme)
	 * @throws Exception
	 *             Wenn ein unerwarteter Fehler auftritt
	 */
	public Client(Socket socket) throws Exception {
		this.username = "";
		this.socket = socket;
		this.ip = socket.getInetAddress();
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	/**
	 * Gibt den nächsten Input des Clients zurück oder null, wenn kein
	 * weiterer Input vorhanden ist
	 * 
	 * @throws IOException
	 *             wenn etwas beim Lesen schief l�uft
	 * @return Die n�chste Nachricht eines Clients oder null, wenn keine
	 *         Vorhanden ist
	 * @throws IOException Wenn ein unerwarteter Fehler auftritt
	 */
	public String getNextInput() throws IOException {
		if (reader.ready()) { //�berpr�ft, ob etwas zum lesen vorhanden ist
			return reader.readLine(); //Liest eine Zeile aus dem reader
		} else {
			return null;
		}
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	/**
	 * Gibt den nächsten Input zurück und blockt den aktuellen Thread solange,
	 * bis neuer input vorhanden ist
	 * 
	 * @return Die n�chste Nachricht des Clients
	 * @throws IOException
	 *             wenn ein Fehler beim Lesen auftritt
	 * @throws SocketTimeoutException wenn der Lesevorgang austimed
	 */
	public String waitForNextInput() throws SocketTimeoutException, IOException {
		return reader.readLine(); //Blockt den aufrufenden Thread, bis eine neue Zeile verf�gbar ist und ausgeslesen werden kann
	}

	/**
	 * Sendet dem Client eine Nachricht
	 * 
	 * @param line
	 *            Die zu versendende Nachricht
	 * @throws IOException wenn w�hrend des Schreibvorgangs ein Fehler auftritt
	 * 
	 */
	public void writeLine(String line) throws IOException {
		writer.write(line); //Die Daten werden geschrieben
		writer.newLine(); //Der writer "bicht die zeile um"
		writer.flush(); //Die Daten werden aus dem Buffer in den Stream geschrieben
	}

	/**
	 * Gibt die IP-Adresse des Clients zurück
	 * 
	 * @return <code>java.net.InetAddress</code> des Clients
	 */
	public InetAddress getIp() {
		return ip;
	}
	
	/**
	 * Setzt dem Nutzer einen (neuen) Username
	 * 
	 * @param username
	 *                Der neue Username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gibt den Usernamen zurück
	 * 
	 * @return Der aktuelle Benutzername des Clients
	 */
	public String getUsername() {
		return username;
	}
}
import java.net.*;

/**
 * Clientannahme nimmt neue Clients an die sich mit dem Server verbinden und
 * leitet diese in die Authentification weiter
 * 
 * @author Simon
 */
public class ClientAnnahme extends Thread {

	// Aktuelle Instanz des Servers
	private Server server;
	// Loginmanager zum Überprüfen der Daten
	private LoginManager manager;
	// Boolean, der prüft, ob der Server noch läuft oder nicht
	private boolean running;

	/**
	 * Konstruktor der Klasse
	 * 
	 * @param server
	 *            aktuelle Instanz des Server-Objektes
	 */
	public ClientAnnahme(Server server) {
		this.server = server;
		this.manager = new LoginManager();
		this.running = true;
	}

	/**
	 * Wird ausgeführt, sobald dieser Thread gestartet wird und nimmt neue
	 * Clients an
	 */
	@Override
	public void run() {
		//Solange der Thread l�uft, wird immer wieder auf neue Clients gewartet
		while (running) {
			try {
				Socket s = server.accept(); //Ein neuer Client der sich anmeldet wird als Socket angenommen
				Client client = new Client(s); //Mithilfe des Sockets wird ein Client instanziert
				ClientAuthentification auth = new ClientAuthentification(client, manager, server); //Dieser Client wird nun in die Anmeldung geschmissen
				auth.start(); //Die Anmeldung des Clients mit Nutzernamen und Passwort wird gestartet
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Bricht die Annahme von neuen Clients ab
	 */
	@Override
	public void interrupt() {
		this.running = false;
		manager.save();
	}

}
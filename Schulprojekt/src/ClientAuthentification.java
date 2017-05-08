/**
 * Ãœbernimmt die Authentification von neuen Clients am Server
 */
public class ClientAuthentification extends Thread {

	// Der Client der zu autentifizieren ist
	private Client client;
	// Loginmanager zum Ã¼berprÃ¼fen der Daten
	private LoginManager manager;
	// Der Server um den Client bei erfolg anzumelden
	private Server server;

	/**
	 * Konstruktor der Klasse Benötigt einen Client, einen Loginmanager und
	 * einen Server
	 * @param client Der Client, der mithilfe der Instanz authentifiziert werden soll
	 * @param manager Der Loginmanager zum überprüfen der Logindaten
	 * @param server Aktuelle Instanz der Server-Klasse
	 */
	public ClientAuthentification(Client client, LoginManager manager, Server server) {
		this.client = client;
		this.manager = manager;
		this.server = server;
	}

	@Override
	public void run() {
		boolean accept = false; //boolean der angibt, ob der Client akzeptuert wurde oder nicht
		try {
//			client.writeLine("login"); //Der Client bekommt die Info, dass der sich anmelden muss
//			client.writeLine("Geben Sie bitte den Nutzernamen und das Passwort mit einem Semikolon getrennt in einer Zeile an."); //Instruktionen werden dem Client gesendet
			while (!accept) { //Er hat unendlich viele Anmeldeversuche (Freut doch sicher Nutzer von Bruteforce-Angriffen^^)
				String result = client.waitForNextInput(); //Es wird auf eine Antwort des Cleints gewartet
				String[] login = result.split(";"); //Die Daten werden in Nutzernamen und Passwort aufgeteilt
				if (login.length != 2) { //Wenn es nicht 2 Datensätze (Nutzername und Passwort) gibt, kann die Anmeldung nicht erfolgreich sein
					client.writeLine("wrongLogin"); //Der Client bekommt die Info, dass der Versuch nicht geklappt hat.
					continue; //Die Schleife beginnt sofort von vorne
				}
				if (manager.validLogin(login[0], login[1])) { //Es wird auf Richigkeit der Daten geprüft
					//Die Daten sind korrekt
					client.writeLine("erfolgreich"); //Der Client bekommt die Nachricht, das er sich erfolgreich angemeldet hat
					client.setUsername(login[0]); //Dem Client wird sein Benuztername gesetzt
					server.addClient(client); //Der Server wird "benachrichtigt" das er einen neuen Client hat.
					ClientThread clientThread = new ClientThread(server, client);
					clientThread.start();
					accept = true; //accept wird auf true gesetzt, um die Schleife zu unterbrechen
				} else {
					//Der Login war nicht erfolgreich
					client.writeLine("wrongLogin"); //Der Client bekommt die Information, dass der Login nicht geklappt hat
					continue; //Die Schleife beginnt sofort von vorne
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); //Ein möglicher Fehler wird in der Konsole ausgegeben!
		}
	}

}
import java.net.*;
import java.io.*;

/**
 * Übernimmt die Kommunikation mit dem Server
 * @author Tom
 */
public class Kommunikation {
	
	//Der Server
	private Socket server;
	//Ermöglicht das Schreiben von Nachrichten
	private BufferedWriter bw;
	//Ermöglicht das Empfangen von Nachrichten
	private BufferedReader br;
	
	/**
	 * Erzeugt eine neue Instanz der Klasse
	 * @param socket Der Socket des Servers mit dem der Client verbunden ist
	 */
	public Kommunikation(Socket socket) {
		this.server = socket;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Überprüft, ob etwas im Stream gelesen werden kann
	 * @return true, wenn etwas im br gelesen werden kann, sonst false
	 */
	public boolean istwasimbuffer() {
		try {
			return br.ready();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * Liest die nächste Nachricht im Stream und blockt diesen, bis eine neue Nachricht vorhanden ist
	 * @return Die nächste Nachricht
	 */
	public String streamLesen() {
		String msg = ""; //Die Nachricht
		try {
			msg = br.readLine(); //Die Nachricht wird gelesen
		} catch (Exception e) { //Etwas ist schief gelaufen
			e.printStackTrace(); //Der Fehler wird auf der Konsole ausgegeben
		}
		return msg; //Die Nachricht wird zurückgegeben
	}
	
	/**
	 * Sendet eine Nachricht an den Server
	 * @param s Die zu sendende Nachricht
	 */
	public void streamSchreiben(String s) {
		try {
			bw.write(s); //Die Nachricht wird in den Stream geschrieben
			bw.newLine(); //Neue Zeile im Writer
			bw.flush(); //Senden der Nachricht
		} catch (Exception e) {
			e.printStackTrace(); //Eventuelle Fehler werden ausgegeben
		}

	}
}
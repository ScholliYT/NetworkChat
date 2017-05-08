
/**
 * Empfängt neue Nachrichten vom Server und gibt diese im UI aus
 * @author Tom
 */
public class ChatReciever extends Thread {
	
	//Gibt an, ob noch nachrichten empfangen werden sollen
	private boolean running = true;
	//Dient zur Kommunikation mit dem Server
	private Kommunikation k;
	//UI, auf dem die Nachricht ausgegeben wird
	private ClientUi ui;
	
	/**
	 * Erzeugt eine neue Instanz dieser Klasse
	 * @param k Die Kommunikation um mit dem Server zu kommunizieren
	 * @param ui Das Userinterface
	 */
	public ChatReciever(Kommunikation k, ClientUi ui) {
		this.k = k;
		this.ui = ui;
	}
	
	/**
	 * Wird aufgerufen, sobald der Thread startet
	 */
	@Override
	public void run() {
		while (running) { //Solange das Programm läuft wird auf neue Nachrichten gewartet
			try{
				ui.addMessage(k.streamLesen().trim()); //Eine neue Nachricht wird im UI angezeigt
			}catch(Exception ignore){} //Fehler werden ignoriert
		}
	}
}
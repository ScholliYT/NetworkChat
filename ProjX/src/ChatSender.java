import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * Übernimmt das Senden von Nachrichten
 * @author Tom
 */
public class ChatSender extends KeyAdapter {

	private Kommunikation k;
	private ClientUi ui;
	
	/**
	 * Erstellt eine neue Instanz der Klasse
	 * @param k Kommunikation, um mit dem Server zu kommunizieren
	 * @param ui ui, um Nachrichen auszugeben
	 */
	public ChatSender(Kommunikation k, ClientUi ui) {
		this.k = k;
		this.ui = ui;
	}
	
	/**
	 * Wird aufgerufen, wenn im Userinterface in dem Textfield für die Nutzereingabe eine Taste der Tastatur losgelassen wird
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { //Die losgelassene Taste ist die Entertaste
			JTextField textfield = (JTextField) e.getSource(); //Das Textfield wird "geholt" um die Nachricht auszulesen
			String message = textfield.getText(); //Die Nachricht wird aus dem Textfeld gelesen
			k.streamSchreiben(message); //Die Nachricht wird zum Server gesendet
			ui.addMessage("Du: " + message); //Die Nachricht wird im UI angezeigt
			textfield.setText(""); //Das Textfield wird zurückgesetzt
		}
	}

}
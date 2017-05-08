import java.util.List;

import javax.swing.DefaultListModel;

/**
 * Diese Klasse dient zur einfacheren Kommunikation mit dem Nutzerinterface des Servers
 * @author Simon
 *
 */
public class UIInterface{
	
	//Instanz der ServerUI-Klasse
	private ServerUI ui;
	
	/**
	 * Default-Constructor
	 */
	public UIInterface(){
	}
	
	/**
	 * Updatet die Liste mit den Clients im UserInterface
	 * @param clients Aktuelle Liste aller Clients
	 */
	public void updateList(List<Client> clients){
		this.check();
		DefaultListModel<String> newModel = new DefaultListModel<>();
		newModel.addElement("            Alle Benutzer:            ");
		for(Client c: clients) newModel.addElement(c.getUsername());
		ui.getUserList().setModel(newModel);
		ui.getLabel().setText("Total: " + clients.size());
	}
	
	/**
	 * Fügt dem Textfeld eine neue Nachricht zu
	 * @param message Die Nachricht, die dem UI hinzugefügt werden soll
	 */
	public void addMessage(String message){
		this.check();
		ui.addMessage(message);
	}
	
	/**
	 * Ändert den Titel des Frames
	 * @param title Der neue Titel
	 */
	public void setTitle(String title){
		this.check();
		ui.setTitle(title);
	}
	
	/**
	 * Überprüft, ob das UI null ist. Sollte dies der Fall sein, wird ui gesetzt.
	 */
	private void check(){
		if(ui == null){
			ui = ServerUI.getInstance();
		}
	}
	
}
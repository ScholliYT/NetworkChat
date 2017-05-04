import java.util.List;

import javax.swing.DefaultListModel;

public class UIInterface{
	
	private ServerUI ui;
	
	public UIInterface(){
		this.ui = ServerUI.getInstance();
	}
	
	public void updateList(List<Client> clients){
		DefaultListModel<String> newModel = new DefaultListModel<>();
		newModel.addElement("            Alle Benutzer:            ");
		for(Client c: clients) newModel.addElement(c.getUsername());
		ui.getUserList().setModel(newModel);
		ui.getLabel().setText("Total: " + clients.size());
	}
	
	public void addMessage(String message){
		ui.addMessage(message);
	}
	
	public void setTitle(String title){
		ui.setTitle(title);
	}
	
}
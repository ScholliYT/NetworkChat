import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClientDisconnectListener extends Thread{
	
	private Server server;
	private List<Client> clientList;
	private ArrayList<String> disconnected;
	private UIInterface uiHelper;
	private boolean running = true;
	
	public ClientDisconnectListener(Server server, UIInterface uiHelper){
		this.server = server;
		this.clientList = server.getClients();
		this.disconnected = new ArrayList<String>();
		this.uiHelper = uiHelper;
	}
	
	@Override
	public void run(){
		while(running){
			synchronized(clientList){
				Iterator<Client> it = clientList.iterator();
				while(it.hasNext()){
					Client c = it.next();
					if(!c.alive()){
						it.remove();
						disconnected.add(c.getUsername());
					}
				}
			}
			
			if(disconnected.size() > 0){
				for(String disconnect: disconnected){
					server.sendNotification("User " + disconnect + " hat die Verbindung getrennt.");
				}
				disconnected.clear();
				
				uiHelper.updateList(clientList);
			}
			try{
				Thread.sleep(2500);
			}catch(Exception ignore){}
		}
	}
	
}
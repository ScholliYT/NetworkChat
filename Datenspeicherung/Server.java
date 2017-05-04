import java.net.*;
import java.util.*;

public class Server extends ServerSocket{
    
    //Liste mit allen Clients die am Server angemeldet sind
    private ArrayList<Client> clients;
    private UIInterface uihelper;
    private ClientAnnahme clientAnnahme;
    private ClientDisconnectListener disconnectListener;
    private ServerThread serverThread;
    
    /**
     * Konstruktor dieser Klasse
     * Startet den Server
     * @throws Exception wenn ein Fehler auftritt
     */
    public Server() throws Exception{
        super(6000, 50, /**InetAddress.getLocalHost()**/InetAddress.getByName("localhost"));
        uihelper = new UIInterface();
        clients = new ArrayList<Client>();
        clientAnnahme = new ClientAnnahme(this);
        disconnectListener = new ClientDisconnectListener(this, uihelper);
        serverThread = new ServerThread(this, 100);
        
        clientAnnahme.start();
        disconnectListener.start();
        serverThread.start();
        
        uihelper.setTitle("ChatServer running @ " + getInetAddress().getHostAddress() + ":" + getLocalPort());
        uihelper.updateList(clients);
    }
    
    /**
     * Gibt eine Liste mit allen Clients zurück
     * @return
     */
    public List<Client> getClients(){
        return clients;
    }
    
    /**
     * Sendet eine Nachricht an alle Clients, bis auf den Sender
     * @param message
     * @param sender
     */
    public void sendMessages(String message, Client sender){
    	String msg = sender.getUsername() + ": " + message;
    	uihelper.addMessage(msg);
    	synchronized(clients){
    		for(Client c: clients){
    			if(c == sender) continue;
                try{
                    c.writeLine(msg);
                }catch(Exception ignore){
                	ignore.printStackTrace();
                }
            }
    	}
    }
    
    public void sendNotification(String message){
    	uihelper.addMessage(message);
    	synchronized(clients){
    		for(Client c: clients){
                try{
                    c.writeLine(message);
                }catch(Exception ignore){
                	ignore.printStackTrace();
                }
            }
    	}
    }
    
    /**
     * Fügt dem Server einen neuen Client hinzu
     */
    public void addClient(Client client){
        clients.add(client);
        uihelper.addMessage("Der Nutzer mit dem Namen " + client.getUsername() + " hat sich angemeldet!");
        uihelper.updateList(clients);
    }
    
    /**
     * Beendet das Programm komplett
     */
    public void terminate(){
    	LoginManager.trySave();
        System.exit(0);
    }
    
}
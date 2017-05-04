import java.net.*;

/**
 * Clientannahme nimmt neue Clients an die sich mit dem Server verbinden und leitet diese in die 
 * Authentification weiter
 */
public class ClientAnnahme extends Thread{
    
    //Aktuelle Instanz des Servers
    private Server server;
    //Loginmanager zum Überprüfen der Daten
    private LoginManager manager;
    //Boolean, der prüft, ob der Server noch läuft oder nicht
    private boolean running;
    
    /**
     * Konstruktor der Klasse
     */
    public ClientAnnahme(Server server){
        this.server = server;
        this.manager = new LoginManager();
        this.running = true;
    }
    
    /**
     * Wird ausgeführt, sobald dieser Thread gestartet wird und nimmt neue Clients an
     */
    @Override
    public void run(){
        while(running){
            try{
                Socket s = server.accept();
                Client client = new Client(s);
                ClientAuthentification auth = new ClientAuthentification(client, manager, server);
                auth.start();
            }catch(Exception e){
               e.printStackTrace();
            }
        }
        
    }
    
    /**
     * Bricht die Annahme von neuen Clients ab
     */
    @Override
    public void interrupt(){
        this.running = false;
        manager.save();
    }
    
}
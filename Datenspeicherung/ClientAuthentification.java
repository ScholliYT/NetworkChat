/**
 * Übernimmt die Authentification von neuen Clients am Server
 */
public class ClientAuthentification extends Thread{

    //Der Client der zu autentifizieren ist
    private Client client;
    //Loginmanager zum überprüfen der Daten
    private LoginManager manager;
    //Der Server um den Client bei erfolg anzumelden
    private Server server;

    /**
     * Konstruktor der Klasse
     * Benötigt einen Client, einen Loginmanager und einen Server
     */
    public ClientAuthentification(Client client, LoginManager manager, Server server){
        this.client = client;
        this.manager = manager;
        this.server = server;
    }

    @Override
    public void run(){
        boolean accept = false;
        try{
            client.writeLine("login");
            client.writeLine("Geben Sie bitte den Nutzernamen und das Passwort mit einem Semikolon getrennt in einer Zeile an.");
            while(!accept){
                String result = client.waitForNextInput();
                System.out.println("Daten empfangen: " + result);
                String[] login = result.split(";");
                if(login.length != 2){
                    client.writeLine("Das war nicht korrekt.");
                    continue;
                }
                if(manager.validLogin(login[0], login[1])){
                    client.writeLine("erfolgreich");
                    client.setUsername(login[0]);
                    server.addClient(client);
                    accept = true;
                }else{
                    client.writeLine("irgendwasanderes");
                    continue;
                }
            }
        }catch(Exception e){
           e.printStackTrace();
        }
    }

}
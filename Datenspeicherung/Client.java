import java.io.*;
import java.net.*;

/**
 * Stellt einen Client als Objekt da und ermöglicht die Kommunikation mit diesem
 */
public class Client{

    //Nutzername des Clients
    private String username;
    
    //Reader des Clients um Daten von diesem zu empfangen
    private BufferedReader reader;
    //Writer dieses Clients, um Daten zu senden
    private BufferedWriter writer;
    
    //Socket dieses Clients
    private Socket socket;
    //Ip-Adresse des Clients
    private InetAddress ip;
    
    /**
     * Erzeugt einen neuen Client
     * Übergeben wird ein Socket, welcher durch die Methode server.accept() erlangt wird
     */
    public Client(Socket socket) throws Exception{
        this.username = "";
        this.socket = socket;
        this.ip = socket.getInetAddress();
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    /**
     * Gibt den nächsten Input des Clients zurück oder null, wenn kein weiterer Input vorhanden ist
     */
    public String getNextInput() throws IOException{
        if(reader.ready()){
            return reader.readLine();
        }else{
            return null;
        }
    }
    
    /**
     * Gibt den nächsten Input zurück und blockt den aktuellen Thread solange, bis neuer input vorhanden ist
     */
    public String waitForNextInput() throws IOException{
        return reader.readLine();
    }
    
    /**
     * überprüft, ob der Client noch mit dem Server verbunden ist, und gibt das Ergebnis als boolean zur�ck
     */
    public boolean alive(){
    	try{
    		socket.getOutputStream().write(" ".getBytes());
    		socket.getOutputStream().flush();
    		return true;
    	}catch(IOException e){
    		return false;
    	}
    }
    
    /**
     * Sendet dem Client eine Nachricht
     */
    public void writeLine(String line) throws IOException{
        writer.write(line);
        writer.newLine();
        writer.flush();
    }
    
    /**
     * Gibt die IP-Adresse des Clients zurück
     */
    public InetAddress getIp(){
        return ip;
    }
    
    /**
     * Setzt dem Nutzer einen (neuen) Username
     */
    public void setUsername(String username){
           this.username = username;
    }
    
    /**
     * Gibt den Usernamen zurück
     */
    public String getUsername(){
           return username;
    }
}
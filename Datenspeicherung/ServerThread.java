import java.util.List;

public class ServerThread extends Thread{
    
    private Server server;
    private long wait;
    private boolean running;
    
    public ServerThread(Server server, int tickrate){
        this.server = server;
        this.wait = 1000 / tickrate;
        this.running = true;
    }
    
    @Override
    public void run(){
        while(running){
        	List<Client> clients = server.getClients();
        	synchronized(clients){
	            for(Client c: server.getClients()){
	               try{
	                    String message = c.getNextInput();
	                    if(message != null){
	                        server.sendMessages(message, c);
	                    }
	                }catch(Exception ignore){}
	            }
	            
	            try{
	                Thread.sleep(wait);
	            }catch(Exception interrupt){
	                interrupt.printStackTrace();
	            }
	        }
        }
    }
    
    @Override
    public void interrupt(){
        this.running = false;
    }
    
}
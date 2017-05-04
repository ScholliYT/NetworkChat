import java.awt.EventQueue;
import java.util.*;

public class Manager
{
    private Kommunikation k;
    private Scanner sc;
    private ClientUi ui;
    
    public Manager(String ip, int port) {
        this.k = new Kommunikation(ip, port);
        this.sc = new Scanner(System.in);
        einloggen();
        chatStarten();
    }
    
    private void einloggen() {
        AccountManager am = new AccountManager(k, sc);
        am.einloggen();
    }
    
    private void chatStarten() {
    	try{
    		 EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run(){
					ui = new ClientUi(k);
		            ui.setVisible(true);
				}
			});
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	//Auf die Eventqueue warten
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
        ChatReciever reciever = new ChatReciever(k, ui);
        reciever.start();
    }
   
}

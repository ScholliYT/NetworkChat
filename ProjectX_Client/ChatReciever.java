
public class ChatReciever extends Thread{
	
    private boolean running = true;
    private Kommunikation k;
    private ClientUi ui;
    
    public ChatReciever(Kommunikation k, ClientUi ui) {
        this.k = k;
        this.ui = ui;
    }
    
    @Override
    public void run() {
        while(running) {
            ui.addMessage(k.streamLesen().trim());
        }
    }
}

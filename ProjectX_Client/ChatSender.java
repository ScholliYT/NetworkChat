import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class ChatSender extends KeyAdapter{
	
    private Kommunikation k;
    private ClientUi ui;
    
    public ChatSender(Kommunikation k, ClientUi ui) {
        this.k = k;
        this.ui = ui;
    }
    
    @Override
    public void keyReleased(KeyEvent e){
    	if(e.getKeyCode() == KeyEvent.VK_ENTER){
    		JTextField textfield = (JTextField) e.getSource();
    		String message = textfield.getText();
    		k.streamSchreiben(message);
    		ui.addMessage("Du: " + message);
    		textfield.setText("");
    	}
    }
    
}
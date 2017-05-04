import java.awt.EventQueue;

import javax.swing.UIManager;

public class ServerMain{
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run(){
					ServerUI ui = new ServerUI();
					ui.setVisible(true);
				}
			});
			Thread.sleep(100);
			Server s = new Server();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
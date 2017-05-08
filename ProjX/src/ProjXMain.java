import java.awt.EventQueue;

import javax.swing.UIManager;

/**
 * 
 * @author Simon
 */
public class ProjXMain{
	
	public static void main(String[] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Das UI soll wie normale Windows (oder BS-Programme) aussehen
		}catch(Exception e){
			e.printStackTrace();
		}
		Manager m = new Manager(); //Ein Programmmanager wird gestartet
		
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				LoginUi login = new LoginUi(m); //Das Loginui wird erstellt und angezeigt
				login.setVisible(true);
			}
		});
	}
	
}
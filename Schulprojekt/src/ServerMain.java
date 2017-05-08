import java.awt.EventQueue;

import javax.swing.UIManager;

/**
 * Klasse mit der main-Methode
 * @author Simon
 *
 */
public class ServerMain{
	
	/**
	 * Mainmethode, von welcher aus das Projekt gestartet wird
	 * @param args Die Startargumente
	 */
	public static void main(String[] args) { //Wird von Java bei dem Start des Programmes ausgeführt
		try {
			Server s = null;
			if(args.length == 1){
				try{
					int port = Integer.parseInt(args[0]);
					if(port >= 0 && port <= 65535){
						s = new Server(port);
					}
				}catch (Exception e){}
			}
			
			if(s == null){
				try {
					s = new Server();
				} catch (Exception e){
					e.printStackTrace();
					return;
				}
			}
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Sorgt dafür, dass das Nutzerinterface wie alle Windowsprogramme ausehen (Zumindest auf einem Windows-BS)
			Server server = s; //Nötig, da sonst nicht kompilierbar!
			//Das Userinterface wird in einem separaten Thread gestartet
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run(){
					ServerUI ui = new ServerUI(server);
					ui.setVisible(true);
				}
			});
		} catch (Exception e) {
			e.printStackTrace(); //Eventuelle Fehler werden in der Konsole ausgegeben
		}
	}
	
}
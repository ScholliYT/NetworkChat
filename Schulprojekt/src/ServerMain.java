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
			if(args.length == 1){ //Wurde ein Port mit übergeben?
				try{
					int port = Integer.parseInt(args[0]); //Die Eingabe wird, falls möglich, in eine Zahl umgewandelt (String to int)
					if(port >= 0 && port <= 65535){ //ist die Zahl ok?
						s = new Server(port); //Zahl ok, der Server wird auf dem übergebenen Port gestartet
					}
				}catch (Exception e){}
			}
			
			if(s == null){ //Wurde schon ein Server mit Port instanziert?
				try {
					s = new Server(); //Nein, ein normaler Server mit Port 6000 wird erstellt
				} catch (Exception e){
					e.printStackTrace();
					return; //Etwas lief schief, s ist null und das Programm wird abstürzen, deshalb wird es beendet
				}
			}
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Sorgt dafür, dass das Nutzerinterface wie alle Windowsprogramme ausehen (Zumindest auf einem Windows-BS)
			Server server = s; //Nötig, da sonst nicht kompilierbar!
			//Das Userinterface wird in einem separaten Thread gestartet
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run(){
					ServerUI ui = new ServerUI(server); //UI erstellen
					ui.setVisible(true); //UI anzeigen
				}
			});
		} catch (Exception e) {
			e.printStackTrace(); //Eventuelle Fehler werden in der Konsole ausgegeben
		}
	}
	
}
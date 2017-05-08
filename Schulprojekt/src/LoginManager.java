import java.util.*;
import java.io.*;

/**
 * Eine Klasse die das Laden und Speichern der Userdaten (also Nutzername und Passwort)
 * �bernimmt
 * @author Simon
 */
public class LoginManager {
	
	//Alle Nutzerdaten werden in einer Hashmap gespeichert
	private HashMap<String, String> logins;
	//Die Datei auf der Festplatte
	private File f;
	
	//statische Instanz dieser Klasse
	private static LoginManager instance;
	
	/**
	 * Erzeugt eine neue Instanz der LoginManager-Klasse
	 */
	public LoginManager() {
		f = new File("E:\\Informatikprojekt\\logins.dat"); //Eine Datei, in welcher die Datenbank gespeichert wird, wird erstellt
		load(); //Die Datenbank wird geladen
		instance = this;
	}
	
	/**
	 * Versucht, die aktuelle Hashmap mit allen Daten zu speichern
	 */
	public static void trySave() {
		if (instance != null) {
			instance.save();
		}
	}
	
	/**
	 * Speichert die HashMap mit allen Eintr�gen auf der Festplatte
	 */
	public void save() {
		try {
			if (!f.exists()) { //Wenn die Datei nicht existiert soll sie erstellt werden
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)); //Es wird ein Stream instanziert, mit dem ich Objekte, die Serialisierbar sind, in eine Datei schreiben kann
			oos.writeObject(logins); //Die Datenbank (Hashmap) wird gespeichert
			oos.flush(); //Der buffer wird auf die Platte geschrieben
			oos.close(); //Der Stream wird geschlossen
		} catch (Exception e) {
			e.printStackTrace(); //Eventuelle Fehler werden ausgegeben!
		}
	}
	
	/**
	 * Versucht, eine bereits gespeicherte Datenbank mit anmeldedaten von der Festplatte zu laden
	 * Sollte keine vorhanden sein, wird eine neue erstellt. Diese enth�lt 2 Standardeintr�ge:<br>
	 * 1. Max, Passwort: 123<br>
	 * 2. Franz Passwort: 456
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			if (!f.exists()) { //Wenn die Datei nicht existiert, wird eine neue erstellt
				f.createNewFile();
				createLoginDb(); //Eine HashMap mit 2 Standardeintr�gen wird erstellt
				return;
			}
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)); //Ein Stream, mit welchem serialisierte Objekte von der Festplatte gelesen werden k�nnen, wird erstellt
			Object result = ois.readObject(); //Das Objekt wird von der Platte gelesen
			ois.close(); //Der Stream wird geschlossen
			if (result instanceof HashMap) { //Es wird �berpr�ft, ob das gelesene Objekt auch eine HashMap ist
				logins = (HashMap<String, String>) result; //Das gelesene Objekt "wird in die Form einer HashMap gezwungen"
				return;
			} else {
				createLoginDb(); //Eine HashMap mit 2 Standardeintr�gen wird erstellt
				return;
			}
		} catch (Exception e) {
			e.printStackTrace(); //Eventuelle Fehler werden ausgegeben
		}
	}
	
	/**
	 * F�gt der Datenbank einen neuen Nutzer hinzu
	 * @param username Der Nutzername des neuen Nutzers
	 * @param password Das Passwort des neuen Nutzers
	 */
	public void addUser(String username, String password) {
		logins.put(username, password); //Die neuen Daten werden zun�chst ohne �berpr�fung hinzugef�gt
	}
	
	/**
	 * Erstellt eine neue HashMap mit den 2 Standardeintr�gen
	 */
	private void createLoginDb() {
		logins = new HashMap<String, String>(); //Eine Instanz der Klasse HashMap wird erzeugt
		
		//2 Standardeintr�ge werden erzeugt
		logins.put("Max", "123");
		logins.put("Franz", "456");
	}
	
	/**
	 * Gibt zur�ck, ob ein Loginversuch mit den �bergeben Daten g�ltig ist, oder nicht
	 * @param user Der Nutzername des Loginversuches
	 * @param pw Das Passwort des Loginversuches
	 * @return true, wenn ein Nutzer mit dem Nutzernamen existiert, und das Passwort �bereinstimmt
	 * Ansonsten false
	 */
	public boolean validLogin(String user, String pw) {
		if (!logins.containsKey(user)) //Wenn der Schl�ssel in der Hashmap nicht existiert, kann auch der Login nicht valid sein
			return false;
		return pw.equals(logins.get(user)); //Sollte das Passwort, was dem Schl�ssel der Hashmap zugeordnet ist mit dem �bergebenen �bereinstimmen ist der Login valid
	}
}
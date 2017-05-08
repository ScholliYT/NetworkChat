/**
 * �bernimmt das Einloggen am Server
 * @author Tom
 */
public class AccountManager {
	private Kommunikation k;

	/**
	 * Erzeugt eine neue Instanz dieser Klasse
	 * @param ko Kommunikation, um mit dem Server zu kommunizieren, darf nicht null sein
	 */
	public AccountManager(Kommunikation ko) {
		k = ko;
	}
	
	/**
	 * �bernimmt das Einloggen am Server
	 * @param username Der Username
	 * @param password Das Passwort des Users
	 * @return true, wenn der Login geklappt hat, false wenn nicht
	 */
	public boolean einloggen(String username, String password){
		if(k == null) return false; //Wenn keine M�glichkeit zur Kommunikation besteht, wird false zur�ckgegeben
		k.streamSchreiben(username + ";" + password); //Es werden die Daten an den Server f�r diesen Verst�ndlich gesendet
		String read = k.streamLesen(); //Die Antwort des Servers wird erwartet und gelesen
		
		if(read.equalsIgnoreCase("erfolgreich")){ //War der Loginversuch erfolgreich?
			return true; //Der Loginversuch war erfolgreich
		}
		return false; //Der Loginversuch ist fehlgeschlagen
	}
}
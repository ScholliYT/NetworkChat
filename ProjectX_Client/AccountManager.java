import java.util.*;
public class AccountManager
{
    private Kommunikation k;
    private Scanner scanner;
    /*
     * Instanziert ein neues Accountmanager objekt
     */
    public AccountManager(Kommunikation ko, Scanner sc) {
        k = ko;
        scanner = sc;
    }

    public boolean einloggen() {
        System.out.println("Loginprozess begonnen1");
        if(!k.streamLesen().equals("login")) {
            return false;
        }
        System.out.println("Loginprozess begonnen2");
        //loginname & passwort
        //Stream lesen & Ausgeben
        System.out.println(k.streamLesen());
        
        boolean eingeloggt = false;
        while(!eingeloggt){

            //Auslesen
            String input = scanner.nextLine();
            //In Stream Schreiben
            if(input != null && !input.equals("") && input.contains(";")) {
                k.streamSchreiben(input);
            } else {
                System.out.println("Falsche Daten / Keine / Falsche Formatierung!");
                continue; 
            }

            //Server überprüft die Daten

            String s = k.streamLesen();
            if(s.equals("erfolgreich"))
            {
                eingeloggt = true;
            }
        }
        return true;
    }
}

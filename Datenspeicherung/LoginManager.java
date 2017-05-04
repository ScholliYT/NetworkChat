import java.util.*;
import java.io.*;

public class LoginManager{
    
    private HashMap<String, String> logins;
    private File f;
    
    private static LoginManager instance;
    
    public LoginManager(){
        f = new File("E:\\OneDrive\\Schule\\Informatik\\NetworkChat\\users.dat");
        load();
        instance = this;
    }
    
    
    public static void trySave(){
    	if(instance != null){
    		instance.save();
    	}
    }
    
    public void save(){
        try{
            if(!f.exists()){
                f.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(logins);
            oos.flush();
            oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void load(){
        try{
            if(!f.exists()){
                f.createNewFile();
                createLoginDb();
                return;
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object result = ois.readObject();
            ois.close();
            if(result instanceof HashMap){
                logins = (HashMap<String, String>) result;
                return;
            }else{
                createLoginDb();
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addUser(String username, String password){
    	logins.put(username, password);
    }
    
    private void createLoginDb(){
        logins = new HashMap<String, String>();
        logins.put("Max", "123");
        logins.put("Franz", "456");
    }
    
    public boolean validLogin(String user, String pw){
        if(!logins.containsKey(user)) return false;
        return pw.equals(logins.get(user));
    }   
}
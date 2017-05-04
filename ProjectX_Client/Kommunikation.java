import java.net.*;
import java.io.*;
import java.util.*;

public class Kommunikation{
	
    private Socket server;
    private BufferedWriter bw;
    private BufferedReader br;
    
    public Kommunikation(String ip, int port) {
        server = verbindungAufbauen(ip, port);
        try {
            OutputStream os = server.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);

            InputStream is = server.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Socket verbindungAufbauen(String ip, int port) {
        try {
            Socket socket = new Socket(InetAddress.getByName(ip), port);
            System.out.println("[Kommunikation] Socket Status: " + socket.isConnected());
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean istwasimbuffer() {
        try{
            return br.ready();
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        return false;
    }
    
    public String streamLesen() {
        String msg = "";
        try {
            msg = br.readLine();
//             if(warten) {
//                 while(!br.ready()) {
//                     System.out.print(".");
//                     Thread.sleep(1000);
//                 }
//                 System.out.println();
//             }
//             while(br.ready()) {
//                 char c = (char)br.read();
//                 if(c == '\n') {
//                     break;
//                 } else {
//                    msg += br.read(); 
//                 }
//             }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(msg);
        return msg;
    }

    public void streamSchreiben(String s) {
        try {
            bw.write(s);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}

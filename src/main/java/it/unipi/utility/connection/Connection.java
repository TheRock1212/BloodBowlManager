package it.unipi.utility.connection;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Classe con metodi statici per effettuare connessioni al server e ricevere e inviare dati
 * @author aleroc
 */
public class Connection {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";

    public static HashMap<String, Object> params = new HashMap<>();

    /**
     * Stabilisce una connessione HTTP di metodo <code>method</code> con il Server al relativo <code>url</code>.
     * @param url Parte dell'URL interessata.
     * @param method Il metodo da utilizzare.
     * @param data I dati da inviare al Server
     * @throws IOException
     * @throws MalformedURLException
     * @throws ProtocolException
     */
    public static String getConnection(String url, String method, String data) throws IOException, MalformedURLException, ProtocolException {
        String param = "";
        if(params != null) {
            int i = 0;
            for(String key : params.keySet()) {
                if(i == 0)
                    param += "?";
                param += key + "=" + params.get(key);
                if(i < params.size() - 1)
                    param += "&";
                i++;
            }
            params.clear();
        }
        URL u = new URL("http://localhost:8080" + url + param);  //MalformedURLExeption
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod(method);
        if(data != null && !data.isEmpty()) {
            //Stabilire una connessione HTTP con il metodo POST per inviare dati e ricevere la risposta
                //ProtocolException
            connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
            //Si applica il tipo di contenuto per il trasferimento
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Inviare i dati al Server
            Connection.sendData(data, connection);
        }
        //In entrambi i metodi POST o GET si riceve la risposta che viene elaborata
        return Connection.receiveData(connection);
    }
    /**
     * Invia i dati <code>data</code> alla seguente connessione <code>con</code>.
     * @param data Dati da inviare.
     * @param con Connessione stabilita da <code>getConnection</code>
     * @throws IOException
     */
    public static void sendData(String data, HttpURLConnection con) throws IOException{
        DataOutputStream wr = new DataOutputStream (con.getOutputStream());
        wr.writeBytes(data);
        wr.close();
    }
    /**
     * Riceve i dati dalla connessione <code>con</code>.
     * @param con Connessione stabilita da <code>getConnection</code>
     * @return Il json ricevuto
     * @throws IOException
     */
    public static String receiveData(HttpURLConnection con) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}

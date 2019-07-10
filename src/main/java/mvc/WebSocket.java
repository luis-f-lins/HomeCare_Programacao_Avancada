package mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import mvc.dto.Medidas;
import mvc.modelo.AcessoBD;
import mvc.pagehandlers.Tratador_pagina2_jsp;

@ServerEndpoint("/websocket")
public class WebSocket {
    
    private static HashMap<String, HashMap> connections = new HashMap<String, HashMap>();
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
        HashMap<String,Object> connection = new HashMap<String,Object>();
        connection.put("session", session);
        connections.put(session.getId(), connection);
    }

    @OnMessage
    public void onReceive(String message, Session session) throws IOException{
        HashMap result =
        new ObjectMapper().readValue(message, HashMap.class);
        HashMap connection = connections.get(session.getId());
        connection.put("searchFilters", result);
        sendUpdate(connection);
        System.out.println(connection.toString());
        
    }

    @OnError
    public void onError(Session session,java.lang.Throwable throwable) {
//        System.out.println("--- OnError: "+throwable);
    }

    @OnClose
    public void onClose(Session session,CloseReason reason) {
        connections.remove(session.getId());
    }
    
    public static void broadcastUpdate() 
      throws IOException {
        for (String key : connections.keySet()) {
            sendUpdate(connections.get(key));
        }
    }
    
    private static void sendUpdate(HashMap connection){
        HashMap<String,String> search = (HashMap) connection.get("searchFilters");  
        
        String datetime = search.get("datetime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(datetime);
        } catch (ParseException ex) {
            Logger.getLogger(Tratador_pagina2_jsp.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    
        String medidor = search.get("medidor");
        String periodo = search.get("periodo");
        
        
        ArrayList<Medidas> listadeMedidas = new AcessoBD().getMedidas(medidor, parsedDate, periodo);
        ArrayList<JsonObject> MedidasObject = new ArrayList<>();
        
        for(Medidas elemento : listadeMedidas){
            try{
            JsonObject objetoJSON = Json.createObjectBuilder()
                .add("serial", elemento.getSerial())
                .add("medidor", elemento.getMedidor())
                .add("temperatura", elemento.getTemperatura())
                .add("umidade", elemento.getUmidade())
                .add("datahora", elemento.getDatahora())
      
                .build();
                MedidasObject.add(objetoJSON);
            }
            catch(Exception e){
                System.out.println(e);
            }  
        }
        String result = MedidasObject.toString();
        
        
        try {
            ((Session) connection.get("session")).getBasicRemote().sendText(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
 
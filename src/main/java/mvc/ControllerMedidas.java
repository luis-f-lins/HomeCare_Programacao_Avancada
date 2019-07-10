package mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import static java.lang.System.console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.dto.Medidas;
import mvc.modelo.AcessoBD;
import mvc.pagehandlers.Tratador_pagina2_jsp;

public class ControllerMedidas extends HttpServlet {

    // Este controlador pode chamar vários handlers (tratadores de páginas)
    // Todos os hamdlers implementam a mesma interface.
    protected void processRequest(  HttpServletRequest request,
                                    HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Não é um conjunto de pares nome-valor,
        // então tem que ler como se fosse um upload de arquivo...
        BufferedReader br = new BufferedReader(
                                  new  InputStreamReader(
                                           request.getInputStream(),"UTF-8"));
        String textoDoJson = br.readLine();

        // Ler e fazer o parsing do String para o "objeto json" java
        JsonObject jsonObjectDeJava = null;
        try (   
            //Converte o string em "objeto json" java
            // Criar um JsonReader.
            JsonReader readerDoTextoDoJson = 
                    Json.createReader(new StringReader(textoDoJson))) {
            // Ler e fazer o parsing do String para o "objeto json" java
            jsonObjectDeJava = readerDoTextoDoJson.readObject();
            // Acabou, então fechar o reader.
            readerDoTextoDoJson.close();
            
        }catch(Exception e){
            e.printStackTrace();
            out.print("{\"textoResposta\":\"Exceção no controller...\"}");
            out.flush();
            out.close();
        }
        
        
        String datetime = jsonObjectDeJava.getString("datetime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(datetime);
        } catch (ParseException ex) {
            Logger.getLogger(Tratador_pagina2_jsp.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    
        String medidor = jsonObjectDeJava.getString("medidor");
        String periodo = jsonObjectDeJava.getString("periodo");
        
        
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
        
        out.print(MedidasObject);
        out.flush();
        out.close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

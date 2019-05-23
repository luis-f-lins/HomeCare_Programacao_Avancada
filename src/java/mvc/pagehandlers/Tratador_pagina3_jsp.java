/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.pagehandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.IFTratadorDePaginas;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import mvc.modelo.AcessoBD;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.dto.Medidas;
import mvc.dto.Medidores;

/**
 *
 * @author luisfernandolins
 */
public class Tratador_pagina3_jsp implements IFTratadorDePaginas{
    
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
    String form = request.getParameter("form");
    ArrayList<Medidores> listadeMedidores = new AcessoBD().getMedidor();
    if (form.equals("form1")){
        String datetime = request.getParameter("datetime");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(datetime);
        } catch (ParseException ex) {
            Logger.getLogger(Tratador_pagina2_jsp.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        String medidor = request.getParameter("medidor");
        String periodo = request.getParameter("periodo");
        
        ArrayList<Medidas> listadeMedidas = new AcessoBD().getMedidas(medidor, parsedDate, periodo);
        
        request.setAttribute("LISTA_DE_MEDIDAS",listadeMedidas);
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        
        return "pagina3.jsp";
    }
    
    
    if (form.equals("form2")){
        return "pagina1.jsp";
    }
    
    if (form.equals("form3")){
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        return "pagina4.jsp";
    }
    
        return "pagina3.jsp";
    
    }
}

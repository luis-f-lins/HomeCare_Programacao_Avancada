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

public class Tratador_pagina2_jsp implements IFTratadorDePaginas{
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
        String form = request.getParameter("form");
        response.setCharacterEncoding("UTF-8");
    
    if (form.equals("form2")){
        return "pagina1.html";
    }

        return "pagina2.html";
    
    }
}

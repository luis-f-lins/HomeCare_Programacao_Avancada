package mvc.pagehandlers;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.IFTratadorDePaginas;
import mvc.dto.Medidores;
import mvc.modelo.AcessoBD;

public class Tratador_pagina1_jsp implements IFTratadorDePaginas{
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
        String form = request.getParameter("form");
        response.setCharacterEncoding("UTF-8");
        if (form.equals("form1")){

        return "pagina2.html";
        }
        
        if (form.equals("form2")){
        return "pagina1.html";
        }
        return "pagina1.html";
    }
}


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
        if (form.equals("form1")){
        
        ArrayList<Medidores> listadeMedidores = new AcessoBD().getMedidor();
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);

        return "pagina2.jsp";
        }
        
        if (form.equals("form2")){
        return "pagina1.jsp";
        }
        return "pagina1.jsp";
    }
}


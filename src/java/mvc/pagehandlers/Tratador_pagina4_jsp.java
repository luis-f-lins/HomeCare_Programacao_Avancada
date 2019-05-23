/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.pagehandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.IFTratadorDePaginas;

import java.util.ArrayList;
import mvc.modelo.AcessoBD;
import mvc.dto.Medidores;

/**
 *
 * @author luisfernandolins
 */
public class Tratador_pagina4_jsp implements IFTratadorDePaginas{
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
        ArrayList<Medidores> listadeMedidores = new AcessoBD().getMedidor();
        String form = request.getParameter("form");
        
        if (form.equals("form1")){
        Medidores medidor = new Medidores();
        listadeMedidores.add(medidor);
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        }
        
        if (form.equals("form2")){
        
        String nome = request.getParameter("nome");
        
        listadeMedidores = new AcessoBD().criarMedidor(nome);
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        
        return "pagina4.jsp";
    }
        
        if (form.equals("form3")){
        
        String elemNome = request.getParameter("elemNome");
        for(Medidores elemento : listadeMedidores){
                    if(elemNome.equals(elemento.getNome())){
                        elemento.editing = true;
                    }
        }
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        
        
        return "pagina4.jsp";
    }
        
        if (form.equals("form4")){

        String novoNome = request.getParameter("novoNome");
        String tabela = request.getParameter("tabela");
        String opcao = request.getParameter("opcao");
        
        for(Medidores elemento : listadeMedidores){
                    if(tabela.equals(elemento.getTabela())){
                        elemento.editing = false;
                    }
        }
        
        if(opcao.equals("Salvar")){
        listadeMedidores = new AcessoBD().editarMedidor(novoNome, tabela);
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        }
        if(opcao.equals("Excluir")){
        listadeMedidores = new AcessoBD().excluirMedidor(tabela);
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);
        }
        
        
        return "pagina4.jsp";
    }
        
        if (form.equals("form10")){

        listadeMedidores = new AcessoBD().getMedidor();
        request.setAttribute("LISTA_DE_MEDIDORES",listadeMedidores);

        return "pagina2.jsp";
    }
        
        return "pagina4.jsp";
    }
}


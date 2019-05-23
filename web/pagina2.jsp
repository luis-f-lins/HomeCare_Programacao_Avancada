<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="mvc.dto.Medidores"%>
<%@page import="java.sql.SQLException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="mvc.modelo.AcessoBD"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temperatura e Umidade</title>
        <link rel="stylesheet" type="text/css" href="css/pagina2.css">
        
    </head>
    
    <body>
        <center>
        <br>
        <br>
        <h1 style="font-size:48px;">Tabela de Temperatura e Umidade</h1>
        <br>
        

        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina2_jsp" />
            <input type="hidden"
                   name="form"
                   value="form1" />
            <table>
                <tr>
                    <td>
            Medidor:
            <select name="medidor">
            <%
                ArrayList<Medidores> listadeMedidores = (ArrayList<Medidores>)request.getAttribute("LISTA_DE_MEDIDORES");
                for(Medidores elemento : listadeMedidores){
                %>
                <option value=<%= elemento.getTabela() %>> <%= elemento.getNome() %></option>
            <%    
            }
            %>
            </select>
                    </td>
<!--            <BR>-->
            <td>Data final:
                <%
              SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
              String strDate = dateFormat.format(System.currentTimeMillis()); 
                    %>
            <input type="text" name="datetime" value=<%=strDate%>>
<!--            <br>-->
            </td>
            <td>
            Período:
            <select name="periodo">
                <option value="year">Ano</option>
                <option value="month">Mês</option>
                <option value="week">Semana</option>
                <option value="day">Dia</option>
            </select>
            </td>
            
            <td>
            <input type="Radio" name="datatype" value="table" checked>Tabela
            <br>
            <input type="Radio" name="datatype" value="graphics">Gráfico
<!--            <br>-->
            </td>
            
                </tr>
            </table>
            <br>
            <INPUT TYPE="SUBMIT" value="EXIBIR">
        </FORM>
        <BR>
        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina2_jsp" />
            <input type="hidden"
                   name="form"
                   value="form2" />
            
            <input type="Submit" value="VOLTAR">
        </form>
        <br>
        
        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina2_jsp" />
            <input type="hidden"
                   name="form"
                   value="form3" />
            
            <input type="Submit" value="EDITAR MEDIDORES">
        </form>

        </center>
    </body>
</html>

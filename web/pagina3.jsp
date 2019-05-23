<%@page import="mvc.dto.Medidores"%>
<%@page import="mvc.dto.Medidas"%>
<%@page import="mvc.pagehandlers.Tratador_pagina2_jsp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.ArrayList"%>
<%@page import="mvc.modelo.AcessoBD"%>
<%@page import="java.sql.ResultSet"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Temperatura e Umidade</title>
        <link rel="stylesheet" type="text/css" href="css/pagina3.css">
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
                   value="mvc.pagehandlers.Tratador_pagina3_jsp" />
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
                    String selected = "";
                    if(request.getParameter("medidor").equals(elemento.getTabela())){
                        selected = "selected";
                    }
                %>
                
                <option value=<%= elemento.getTabela() %> <%=selected%>> <%= elemento.getNome() %></option>
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
            <input type="text" name="datetime" value=<%=request.getParameter("datetime")%>>
<!--            <br>-->
            </td>
            <td>
            Período:
            <select name="periodo">
                <%
                    String periodoselected = request.getParameter("periodo");
                    String yearselected = "";
                    String monthselected = "";
                    String weekselected = "";
                    String dayselected = "";
                    
                    if (periodoselected.equals("year")){
                            yearselected="selected";
                            monthselected = "";
                            weekselected = "";
                            dayselected = "";
                    }
                    else if (periodoselected.equals("month")){
                            yearselected="";
                            monthselected = "selected";
                            weekselected = "";
                            dayselected = "";
                    }
                    else if (periodoselected.equals("week")){
                        System.out.println('3');
                            yearselected="";
                            monthselected = "";
                            weekselected = "selected";
                            dayselected = "";
                    }
                    else if (periodoselected.equals("day")){
                            yearselected="";
                            monthselected = "";
                            weekselected = "";
                            dayselected = "selected";
                    }
                    else{
                            yearselected="";
                            monthselected = "";
                            weekselected = "";
                            dayselected = "";
                    }

                    %>
                <option value="year" <%=yearselected%> >Ano</option>
                <option value="month" <%=monthselected%>>Mês</option>
                <option value="week" <%=weekselected%>>Semana</option>
                <option value="day" <%=dayselected%>>Dia</option>
            </select>
            </td>
            
            <td>
                <%
                    String datatype = request.getParameter("datatype");
                    String tableselected = "";
                    String graphicsselected = "";
                    
                    if (datatype.equals("table")){
                            tableselected="checked";
                            graphicsselected = "";
                    }
                    else if (datatype.equals("graphics")){
                            tableselected="";
                            graphicsselected = "checked";
                    }
                    
                    %>
            <input type="Radio" name="datatype" value="table" <%= tableselected%>>Tabela
            <br>
            <input type="Radio" name="datatype" value="graphics" <%= graphicsselected%>>Gráfico
<!--            <br>-->
            </td>
            
                </tr>
            </table>
            <br>
            <INPUT TYPE="SUBMIT" value="EXIBIR">
        </FORM>
        
        <br>
        <br>


<table align="center" cellpadding="5" cellspacing="5" border="1">
<tr>

</tr>
<tr style="background-color: rgba(0,0,100,0.8);">
<td><b>Serial</b></td>
<td><b>Medidor</b></td>
<td><b>Temperatura</b></td>
<td><b>Umidade</b></td>
<td><b>Data e Hora</b></td>
</tr>


                <%
                ArrayList<Medidas> listadeMedidas = (ArrayList<Medidas>)request.getAttribute("LISTA_DE_MEDIDAS");
                if (!listadeMedidas.isEmpty()){
                for(Medidas elemento : listadeMedidas){
                %>    
                    <tr style="background-color: rgba(0,0,50,0.8);">
                        <td>
                            <%= elemento.getSerial() %>
                        </td>
                        <td>
                            <%= elemento.getMedidor() %>
                        </td>
                        <td>
                            <%= elemento.getTemperatura() %>
                        </td>
                        <td>
                            <%= elemento.getUmidade() %>
                        </td>
                        <td>
                            <%= elemento.getDatahora() %>
                        </td>

                    </tr>
                <% }
                }
        else{
%>
<p>Não foram encontradas medidas para estes parâmetros</p>
<%

        }
                
                %>
            </table>
            
            <br>    
            <br>    
            
            <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina3_jsp" />
            <input type="hidden"
                   name="form"
                   value="form2" />
            <input type="submit" name="botaoSubmit" value="VOLTAR"/>
            
            
            
        </form>
            <br>
            <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina3_jsp" />
            <input type="hidden"
                   name="form"
                   value="form3" />
            
            <input type="Submit" value="EDITAR MEDIDORES">
        </form>
            
        </center>
    </body>
</html>

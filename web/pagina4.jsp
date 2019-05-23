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
        <link rel="stylesheet" type="text/css" href="css/pagina4.css">
    </head>
    
    
    <body>
        <center>
        <br>
        <br>
        <h1 style="font-size:48px;">Tabela de Temperatura e Umidade</h1>
        <h3>Medidores cadastrados</h3>

        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina4_jsp" />
            <input type="submit" value="Cadastrar novo medidor">
            <input type="hidden"
                   name="form"
                   value="form1" />
        </form>
        <br>
            
            <table id="tabelamedidores">
                <tr>
                    <td>Nome</td>
                    <td>Tabela</td>
                    <td>&nbsp;</td>
                </tr>
                
                <%
                ArrayList<Medidores> listadeMedidores = (ArrayList<Medidores>)request.getAttribute("LISTA_DE_MEDIDORES");
                for(Medidores elemento : listadeMedidores){
                    if(elemento.getNome()==null){
                 %>       
                 <form method="GET" action="controller">
                    <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina4_jsp" />
                <input type="hidden"
                   name="form"
                   value="form2" />
                 
                 <tr>
                <td><input type="text" name="nome"></td>
                <td>???</td>
                <td><input type="submit" value="Salvar"></td>
                </tr> 
                </form>
                 <%
                    }
                }

                for(Medidores elemento : listadeMedidores){
                    if(elemento.getNome()!=null && elemento.editing==false){
                %>
                <form method="GET" action="controller">
                    <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina4_jsp" />
                <input type="hidden"
                   name="form"
                   value="form3" />
                <input type="hidden"
                   name="elemNome"
                   value=<%= elemento.getNome() %> />
                <tr>
                <td><%= elemento.getNome() %></td>
                <td><%= elemento.getTabela() %></td>
                <td><input type="submit" value="Editar">
                </td>
                </tr>
                </form>
                <%    
                }

                if(elemento.getNome()!=null && elemento.editing==true){
                %>
                <form method="GET" action="controller">
                    <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina4_jsp" />
                <input type="hidden"
                   name="form"
                   value="form4" />
                <input type="hidden"
                   name="tabela"
                   value=<%= elemento.getTabela() %> />
                <tr>
                <td><input type="text" name="novoNome"></td>
                <td><%= elemento.getTabela() %></td>
                <td><input type="submit" name="opcao" value="Salvar">
                <input type="submit" name="opcao" value="Excluir">
                </td>
                </tr>
                </form>
                <%    
                }

            }

                %>
            
            </table>
        <BR>
        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina4_jsp" />
             <input type="hidden"
                   name="form"
                   value="form10" />
            
            <input type="Submit" value="VOLTAR">
        </form>

        </center>
    </body>
</html>

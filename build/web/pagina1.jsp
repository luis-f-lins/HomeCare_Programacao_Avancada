<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HomeCare</title>
        <link rel="stylesheet" type="text/css" href="css/pagina1.css">
    </head>
    
    <!-- ========================================= -->
    <!-- Exemplo feito SEM TAGS, só com scriplets. -->
    <!-- ========================================= -->
    
    <body background ="https://static1.squarespace.com/static/5b899f9f12b13f368e13e6d5/t/5b89ae4b70a6ad81f19142b3/1535749709731/1*aEvXFlMOEqz_8Gh34nJC2A.jpg?format=2500w">
        <center>
        <br>
        <br>
        <h1 style="font-size:48px; font-family: Futura; color: white;" >HomeCare</h1>
        <br>
        <br>
        <form method="GET" action="controller">
            
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina1_jsp" />
            <input type="hidden"
                   name="form"
                   value="form1" />
            <input type="submit" value ="Temperatura e Umidade" class="block" />
        </form>
        <form method="GET" action="controller">
            <input type="hidden"
                   name="nomeDoTratadorDePagina"
                   value="mvc.pagehandlers.Tratador_pagina1_jsp" />
            <input type="hidden"
                   name="form"
                   value="form2" />
            <input type="submit" value ="Controle do Ar-Condicionado" class="block" />
        </form>
            <br>
            <br>
            <br>
            <br>
            <br>
            <%
                String erro = (String)request.getAttribute("MENSAGEM_DE_ERRO");
                if(erro==null) erro = "";
            %>
            <font color="red"><%= erro %></font>
            <br>
            </h2>
        </form>
        </center>
    </body>
    
</html>

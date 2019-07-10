
let dataglobal = null;

function fazerPedidoAJAXMedidas(datetime,medidor,periodo){
//    var url = "http://localhost:8080/HomeCare_Maven/controllerMedidas?" +
//         
//              "datetime=" + datetime.value + "&medidor=" + medidor.value + "&periodo=" + periodo.value;
//    
    var data = {};
    data.datetime = datetime.value;
    data.medidor = medidor.value;
    data.periodo = periodo.value;
    
    document.getElementById("tabelamedidores").innerHTML="";
      
    document.getElementById("botaomedidores").innerHTML="";
    
    fazerPedidoPostAJAXcomPromise(data,'controllerMedidas')
	    .then(function(xhr){
                        let table = document.getElementById("medidas");
                        data = xhr.response;
                        let tabela = document.getElementById("tabela");
                        let grafico = document.getElementById("grafico");
                        if(tabela.checked){
                            generateTableMedidas(table,data);
                        }
                        if(grafico.checked){
                            generateGraph(data);
                        }
                       
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
}

function fazerPedidoPostAJAXcomPromise(sendData,destino){
        return new Promise(function (resolve,reject) {
            var data = JSON.stringify(sendData);
            var xhr = new XMLHttpRequest();
            xhr.open('POST', destino);
            xhr.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
            xhr.responseType = 'json';
            xhr.onload = function(){
                if (xhr.readyState===4 && xhr.status === 200) {
                    resolve(xhr);
                } else {
                    reject(xhr);
                }
            };
            xhr.onerror = function() {
                reject(xhr);
            };
            xhr.send(data);
        });
}



function fazerPedidoAJAXMedidores(){
    var data = {};
    data.modo = "exibir";
    document.getElementById("medidas").innerHTML="";
        
    fazerPedidoPostAJAXcomPromise(data,'controllerMedidores')
	    .then(function(xhr){
                        let table = document.getElementById("tabelamedidores");
                        data = xhr.response;
                        dataglobal = xhr.response;
                        generateTableMedidores(table,data);
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
}

function fazerPedidoAJAXEditarMedidor(tabela, novonome){
    var data = {};
    data.modo = "editar";
    data.tabela = tabela;
    data.novonome = novonome;
        
    fazerPedidoPostAJAXcomPromise(data,'controllerMedidores')
	    .then(function(xhr){
                        let table = document.getElementById("tabelamedidores");
                        data = xhr.response;
                        console.log(data);
                        generateTableMedidores(table,data);
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
}

function fazerPedidoAJAXRemoverTabela(tabela){

    var data = {};
    data.modo = "remover";
    data.tabela = tabela;
    
    fazerPedidoPostAJAXcomPromise(data,'controllerMedidores')
	    .then(function(xhr){
                        let table = document.getElementById("tabelamedidores");
                        data = xhr.response;
                        console.log(data);
                        generateTableMedidores(table,data);
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
}

function fazerPedidoAJAXCriarMedidor(nome){
    var data = {};
    data.modo = "criar";
    data.nome = nome;
    
    fazerPedidoPostAJAXcomPromise(data,'controllerMedidores')
	    .then(function(xhr){
                        let table = document.getElementById("tabelamedidores");
                        data = xhr.response;
                        console.log(data);
                        generateTableMedidores(table,data);
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
}

function generateTableMedidas(table, data) {
    table.innerHTML="";
    let grafico = document.getElementById("graficomedidas");
    grafico.innerHTML="";
    console.log(data);
    
    let tableHead = ['Serial','Medidor','Temperatura', 'Umidade','Data e Hora'];
    let tH = table.insertRow();
    tH.id = "tablehead";
    for (var i = 0; i < tableHead.length; i++) {
      let cell = tH.insertCell();
      let text = document.createTextNode(tableHead[i]);
      cell.appendChild(text);
    }
   
  for (let element of data) {
    let row = table.insertRow();
    row.className="tablebody";
    for (key in element) {
      let cell = row.insertCell();
      let text = document.createTextNode(element[key]);
      cell.appendChild(text);
    }
  }
}



function generateTableMedidores(table,data) {
        var length = data.length;
        var htmltext = "";
        document.getElementById("botaomedidores").innerHTML="<button onclick='add(dataglobal);'>CADASTRAR NOVO MEDIDOR</button>";
        table.innerHTML="";
        
        let grafico = document.getElementById("graficomedidas");
        grafico.innerHTML="";

    
      htmltext += "<tr id='tablehead'><td>Nome</td><td>Tabela</td><td></td></tr>";

        for (var i = 0; i < length; i++) {
            console.log(data[i]);
            htmltext += "<tr class='tablebody' id='table"+i+"'><td>"
            +data[i].nome+
            "</td><td>"
            +data[i].tabela+
            "</td><td><button onclick='edit("+i+")'>Editar</button><button onclick='remover(\""+data[i].tabela+"\")'>Remover</button></td></tr>";
        }
        document.getElementById("tabelamedidores").innerHTML = htmltext;
    }

    function edit(indice) {
        htmltext = "<tr class='tablebody'><td><input id='inputname"+indice+"' type='text' value='"
            +dataglobal[indice].nome+
            "'></td><td>"
            +dataglobal[indice].tabela+
            "</td><td><button onclick='save("+indice+")'>Salvar</button><button onclick='fazerPedidoAJAXMedidores()'>Cancelar</button></td></tr>";
        document.getElementById("table"+indice).innerHTML = htmltext; 
    }


    function save(indice) {
        dataglobal[indice].nome = document.getElementById("inputname"+indice).value;
        var htmltext = "<tr id='table"+indice+"'><td>"
            +dataglobal[indice].nome+
            "</td><td>"
            +dataglobal[indice].tabela+
            "</td><td><button onclick='edit("
            +indice+")'>Editar</button><button onclick='remover("
            +indice+")'>Remover</button></td></tr>";
    
        fazerPedidoAJAXEditarMedidor(dataglobal[indice].tabela, dataglobal[indice].nome);    
        document.getElementById("table"+indice).innerHTML = htmltext;
        setTimeout(start, 200);
        
        
        
    }

    function remover(tabela) {
        console.log(tabela);
        fazerPedidoAJAXRemoverTabela(tabela);
        setTimeout(fazerPedidoAJAXMedidores, 200);
        setTimeout(start, 200);
    }
    
    function add(data) {
        var ultimoindice = data.length;
        var htmltext = "<tr id='tablehead'><td>Nome</td><td>Tabela</td><td></td></tr>";
        
        for (var i = 0; i < ultimoindice; i++) {
            console.log(data[i]);
            htmltext += "<tr class='tablebody' id='table"+i+"'><td>"
            +data[i].nome+
            "</td><td>"
            +data[i].tabela+
            "</td><td><button onclick='edit("+i+")'>Edit</button><button onclick='remove("+i+")'>Remove</button></td></tr>";
        }
        htmltext+= "<tr class='tablebody'><td><input id='inputname"+ultimoindice+"' type='text'></td><td>"
            +"???"+
            "</td><td><button onclick='saveadd()'>Save</button><button onclick='fazerPedidoAJAXMedidores()'>Cancelar</button></td></tr>";
        
        document.getElementById("tabelamedidores").innerHTML = htmltext;
    }
    
    function saveadd() {
        var ultimoindice = dataglobal.length;
        nome = document.getElementById("inputname"+ultimoindice).value;
        fazerPedidoAJAXCriarMedidor(nome);
        setTimeout(fazerPedidoAJAXMedidores, 200);
        setTimeout(start, 200);
    }
    
    function atualizarMedidores(data){
        
        htmltext="";
        
        for (var i = 0; i < data.length; i++) {
            htmltext+= "<option value=\""+data[i].tabela+"\">" +data[i].nome + "</option>";
        }
        document.getElementById("listamedidores").innerHTML=htmltext;
        
    }
         
    function start(){
        var data={};
        data.modo = "exibir";
        
        fazerPedidoPostAJAXcomPromise(data,'controllerMedidores')
	    .then(function(xhr){
                        atualizarMedidores(xhr.response);
	    })
	    .catch(function(xhr){
			console.log('Resposta:'+JSON.stringify(xhr.response));
			console.log('Status:'+JSON.stringify(xhr.status));
			console.log('Erro:'+JSON.stringify(xhr.err));
	    });
        
        var today = new Date();
        var date = ("0" + today.getDate()).slice(-2) + '/' + ("0" + (today.getMonth() + 1)).slice(-2) + '/' + today.getFullYear();
        
        var inputdata = document.getElementById("datafinal");
        inputdata.innerHTML = "Data final: <input type='text' name='datetime' value='" + date.toString() + "'>";
        
        let radios = document.getElementsByName('tipoleitura');
        for (var i = 0; i < radios.length; i++) {
            radios[i].addEventListener('change',changeReadingMode);
        }
        
        document.getElementsByName("medidor")[0].addEventListener('change',changingParams);
        document.getElementsByName("periodo")[0].addEventListener('change',changingParams);
        document.getElementsByName("datatype")[0].addEventListener('change',changingParams);
        document.getElementsByName("datatype")[1].addEventListener('change',changingParams);
        
    }

function changeReadingMode(){
    let botaoexibir = document.getElementById("exibir");
    let table = document.getElementById("medidas");
    if(this.value === "AUT"){
        openReceiveSocket();
        var today = new Date();
        var date = ("0" + today.getDate()).slice(-2) + '/' + ("0" + (today.getMonth() + 1)).slice(-2) + '/' + today.getFullYear();
        
        var inputdata = document.getElementById("datafinal");
        inputdata.innerHTML = "Data final: <input type='text' name='datetime' value='" + date.toString() + "'>";
        table.innerHTML="";
        botaoexibir.disabled =true;
        document.getElementsByName("datetime")[0].readOnly = true;
    } else {
        disconnectWebSocket();
        botaoexibir.disabled=false;
        document.getElementsByName("datetime")[0].readOnly = false;
        table.innerHTML="";
    }
}

function changingParams(){
    if (document.getElementById("aut").checked){
        websocket.send(JSON.stringify(sendParams()));
    }
}

websocket = null;

function openReceiveSocket(){
    try {
        websocket = new WebSocket("ws://localhost:8080/HomeCare_Maven/websocket");
    } catch (erro) {
        alert('Error on websocket connection!');
        return;
    }
    
    websocket.binaryType = "arraybuffer";

    websocket.onopen = function(ev){
        console.log('=== Connected');
        websocket.send(JSON.stringify(sendParams()));
        console.log(JSON.stringify(sendParams()));
    };
    
    websocket.onmessage = receiveMessage;
    
    websocket.onerror = function (evt) {
        alert('Error on websocket: ' + evt);
    };
}

function receiveMessage(evt) {
    console.log('Worked so far!');
    var json = JSON.parse(evt.data);
    if (typeof evt.data === "string") {
        let table = document.getElementById("medidas");
        let tabela = document.getElementById("tabela");
        let grafico = document.getElementById("grafico");
                        if(tabela.checked){
                            generateTableMedidas(table, json);
                        }
                        if(grafico.checked){
                            generateGraph(json);
                        }
    } else {
        console.log('Recebeu dados binários! E agora?');
    }
};

function disconnectWebSocket() {
    if(!websocket){
        alert('Error: websocket should be open!');
        return;
    }
    websocket.close();
    websocket = null;
    console.log('Websocket disconnected');
}

function sendParams(){
    return {
        "medidor" : document.getElementsByName('medidor')[0].value,
        "periodo" : document.getElementsByName('periodo')[0].value,
        "datetime" : document.getElementsByName('datetime')[0].value,
        "action": "search"
    };
}

function generateGraph(data){
    let table = document.getElementById("medidas");
    table.innerHTML="";
    let grafico = document.getElementById("graficomedidas");
    grafico.innerHTML="";
    let medidores = document.getElementById("tabelamedidores");
    medidores.innerHTML="";
    
    // set the dimensions and margins of the graph
    var margin = {top: 20, right: 20, bottom: 30, left: 50},
        width = 960 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    // parse the date / time "2019-07-07 07:43:59-03"
    var parseTime = d3.timeParse("%Y-%m-%d %H:%M:%S%Z");

    // set the ranges
    var x = d3.scaleTime().range([0, width]);
    var y = d3.scaleLinear().range([height, 0]);

    // define the 1st line
    var valueline = d3.line()
        .x(function(d) { return x(d.datahora); })
        .y(function(d) { return y(d.temperatura); });

    // define the 2nd line
    var valueline2 = d3.line()
        .x(function(d) { return x(d.datahora); })
        .y(function(d) { return y(d.umidade); });

    // append the svg obgect to the body of the page
    // appends a 'group' element to 'svg'
    // moves the 'group' element to the top left margin
    var svg = d3.select("#graficomedidas").append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .attr("class", "grafico")
      .append("g")
        .attr("transform",
              "translate(" + margin.left + "," + margin.top + ")");
         
    console.log(svg);
      // format the data
      data.forEach(function(d) {
          d.datahora = parseTime(d.datahora);
          d.umidade = +d.umidade;
          d.temperatura = +d.temperatura;
      });

      // Scale the range of the data
      x.domain(d3.extent(data, function(d) { return d.datahora; }));
      y.domain([0, d3.max(data, function(d) {
              return Math.max(d.temperatura, d.umidade); })]);

      // Add the valueline path.
      svg.append("path")
          .data([data])
          .attr("class", "line")
          .style("stroke", "blue")
          .style("fill", "none")
          .attr("d", valueline);

      // Add the valueline2 path.
      svg.append("path")
          .data([data])
          .attr("class", "line")
          .style("stroke", "red")
          .style("fill", "none")
          .attr("d", valueline2);

      // Add the X Axis
      svg.append("g")
          .attr("transform", "translate(0," + height + ")")
          .call(d3.axisBottom(x));

      // Add the Y Axis
      svg.append("g")
          .call(d3.axisLeft(y));

}

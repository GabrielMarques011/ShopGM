const cep = document.querySelector("#cep")

function limpaCampos() {
    // Limpa valores do formulário de cep.
    $("#logradouro").val("");
    $("#bairro").val("");
    $("#localidade").val("");
    $("#uf").val("");
}

const showData = (result)=>{
    //fazendo ele trazer os campos (trazendo no formato dos nomes dos id)
    for(const campo in result){
        //vendo se existe o id
        if(document.querySelector("#"+campo)){
            
            //preenchendo os campos
            //result é onde estou puxando os ids e trazendo as informações
            document.querySelector("#"+campo).value = result[campo]

            console.log(campo)
            
        }
    }
}

cep.addEventListener("blur", ()=>{

    let busca = cep.value.replace("-","")

    const opcoes = {
        method: 'GET',
        mode: 'cors',
        cache: 'default'
    }

    //serve para fazer a busca direto da url
    fetch(`https://viacep.com.br/ws/${busca}/json/`, opcoes)
    
    //se der certo
    .then(response => { response.json()
        .then( data => showData(data))
    })
    //se der errado
    .catch(e => console.log('Deu Erro: '+ e,message))

})

//criando uma ação no botão
// preciso de um evento (no caso o click)
/* $("#btn").on("click", function(){
    var numCep = $("#cep").val();
    var url = "https://viacep.com.br/ws/"+numCep+"/json";

    $.ajax({
        url: url,
        type: "get",
        dataType: "json",

        success:function(dados){
            console.log(dados);
            $("#uf").val(dados.uf);
            $("#cidade").val(dados.localidade);
            $("#logradouro").val(dados.logradouro);
            $("#bairro").val(dados.bairro);
        }
    })

   
}) */

// =======================================================
// Função para iniciar/reinicializar a DataTable
// =======================================================

function inicializaDataTable() {
    $('#tabela-escolas').DataTable({
        "language": {
            "url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Portuguese-Brasil.json"
        }
    });
}

$(document).ready(function() {

    // Preencher o SELECT do Estado
    $.ajax({
        url: 'RecuperarEstados',
        method: 'POST',
        data: {
            recuperar: "sim"
        },
        datatype: 'html',
        success: function(retorno) {
            // Preenche o SELECT
            $('#select-estado').html(retorno);
        },
        error: function(retorno) {
            console.log('Error');
            console.log(retorno);
        }
    });
    
    // Preencher o SELECT do Município
    $('#select-estado').change(function () {
        
        // Recupera os dados do Estado selecionado
        var dados_estado = $(this).select2('data');
        var estado = dados_estado[0].id;
        var nome_estado = dados_estado[0].text;


        // Recuperar os Municípios
        $.ajax({
            url: 'RecuperarMunicipios',
            method: 'POST',
            data: {
                estado: estado
            },
            datatype: 'html',
            success: function(retorno) {

                // Preenche o SELECT
                $('#select-municipio').html(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
            }
        });

        // Atualizar o nome na view
        $('.nome-estado').text(nome_estado);

        // Zera o nome do Municipio
        $('.nome-municipio').html('');

        // =======================================================
        // Preencher a tabela
        // =======================================================

        // Limpa e destrói a tabela
        $("#tabela-escolas").DataTable().clear().destroy();

        $('#tabela-escolas').DataTable({
            "language": {
                "url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Portuguese-Brasil.json"
            },
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": 'RecuperarEscolasTabela',
                "type": 'GET',
                "data": {
                    "estado": estado
                }
            },
            "columns": [
                {
                    "name": "co_escola",
                    "className": "t-codigo-escola"
                },
                {
                    "name": "nome_escola",
                    "className": "t-nome-escola"
                },
                {
                    "name": "situacao_funcionamento",                
                    "className": "t-situacao-escola"
                },
                {
                    "name": "dependencia_adm",
                    "className": "t-dep-adm"
                },
                {
                    "name": "ofertas",
                    "className": "t-ofertas"
                }
            ],
            createdRow: function (row, data, index) {
                var ofertas = data[4];
    
                var div = document.createElement('div');
                div.className = "ofertas";
    
                for (let i = 0; i < ofertas.length; i++) {
                    const oferta = ofertas[i];
                    
                    if (oferta) {
                        var span = document.createElement('span');
                        span.className = "badge badge-pill badge-light";
                        span.textContent = oferta;
                        div.append(span);
                    }
                    
                }
    
                $(row).children("td:last-child").html(div);
            }
        });

    });

    // Preencher o SELECT da Escola
    $('#select-municipio').change(function () {
    
        // Recupera os dados do Municipio selecionado
        var dados_municipio = $(this).select2('data');
        var municipio = dados_municipio[0].id;
        var nome_municipio = dados_municipio[0].text;
        
        var estado = $('#select-estado').val();
        
        $.ajax({
            url: 'RecuperarEscolas',
            method: 'POST',
            data: {
                municipio: municipio
            },
            datatype: 'html',
            success: function(retorno) {

                // Preenche o SELECT
                $('#select-escola').html(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
            }
        });

        // Atualizar o nome na view
        $('.nome-municipio').text(nome_municipio);

        
        // =======================================================
        // Preencher a tabela
        // =======================================================

        // Limpa e destrói a tabela
        $("#tabela-escolas").DataTable().clear().destroy();

        $('#tabela-escolas').DataTable({
            "language": {
                "url": "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Portuguese-Brasil.json"
            },
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": 'RecuperarEscolasTabela',
                "type": 'GET',
                "data": {
                    "estado": estado,
                    "municipio": municipio
                }
            },
            "columns": [
                {
                    "name": "co_escola",
                    "className": "t-codigo-escola"
                },
                {
                    "name": "nome_escola",
                    "className": "t-nome-escola"
                },
                {
                    "name": "situacao_funcionamento",                
                    "className": "t-situacao-escola"
                },
                {
                    "name": "dependencia_adm",
                    "className": "t-dep-escola"
                },
                {
                    "name": "ofertas",
                    "className": "t-ofertas"
                }
            ],
            createdRow: function (row, data, index) {
                var ofertas = data[4];
    
                var div = document.createElement('div');
                div.className = "ofertas";
    
                for (let i = 0; i < ofertas.length; i++) {
                    const oferta = ofertas[i];
                    
                    if (oferta) {
                        var span = document.createElement('span');
                        span.className = "badge badge-pill badge-light";
                        span.textContent = oferta;
                        div.append(span);
                    }
                    
                }
    
                $(row).children("td:last-child").html(div);
            }
        });

    });

    // Criação dos selects
    $("#select-estado").select2({
        placeholder: "Estado"
    });
    $("#select-municipio").select2({
        placeholder: "Município"
    });


    inicializaDataTable(); 

});

// =======================================================
// Controle Cartão Escolas
// =======================================================

$(document).on('click', '#tabela-escolas tbody tr', function(){

    var co_escola = $(this).children("td.t-codigo-escola").text();

    $.ajax({
        url: 'RecuperarInformacoesEscola',
        method: 'POST',
        data: {
            codigoEscola: co_escola        
        },
        datatype: 'html',
        success: function(retorno) {
            $("#modal-escola").html(retorno);
            $("#modal-escola").modal("show");
        },
        error: function(retorno) {
            console.log(retorno);
        }
    })

})


// =======================================================
// Controle dos dropdowns de filtros
// =======================================================

$(document).click(function() {
    $('.filtros-dropdown').addClass('closed');
    $('.filtros-dropdown').removeClass('opened');    
});

// Para a propagação do clique dentro do dropdown
$(document).on('click', '.filtros-dropdown', function(e) {
    e.stopPropagation();
});

$(document).on("click", ".btn-filtro", function(e) {
    e.stopPropagation();
    e.preventDefault();

    // Abre o que clicou
    var dropdown = $(this).parent().children('.filtros-dropdown');
    if (dropdown.hasClass('opened')) {        
        dropdown.removeClass('opened');
        dropdown.addClass('closed');
        return;
    }

    // Fecha todos os drop
    $('.filtros-dropdown').addClass('closed');
    $('.filtros-dropdown').removeClass('opened');
    
    dropdown.removeClass('closed');
    dropdown.addClass('opened');
});
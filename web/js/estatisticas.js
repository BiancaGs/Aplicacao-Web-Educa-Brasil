/// <reference path='../lib/jquery/jquery-3.3.1.min.js' />


$(document).ready(function() {

    // Ao abrir a página, carregar as regiões
    $.ajax({
        url: 'RecuperarRegioes',
        method: 'POST',
        data: {
            recuperarRegioes: "sim"
        },
        datatype: 'html',
        success: function(retorno) {
            
            $('#select-regiao').html(retorno);
            
        },
        error: function(retorno) {
            console.log('Error');
            console.log(retorno);
        }
    });


    // =======================================================
    // Recuperar os Estados à partir de uma Região
    // =======================================================

    $('#select-regiao').change(function() {
        
        var dados_regiao = $(this).select2('data');
        var regiao = dados_regiao[0].id;
        var nome_regiao = dados_regiao[0].text;

        $.ajax({
            url: 'RecuperarEstados',
            method: 'POST',
            data: {
                regiao: regiao
            },
            datatype: 'html',
            success: function(retorno) {
                console.log('Success');
                console.log(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
            }
        });

        // Atualizar o nome na view
        // TODO

    });

    // =======================================================
    // Recuperar os Municipios à partir de um Estado
    // =======================================================

    $('#select-estado').change(function() {
        
        var dados_estado = $(this).select2('data');
        var estado = dados_estado[0].id;
        var nome_estado = dados_estado[0].text;

        $.ajax({
            url: 'RecuperarMunicipios',
            method: 'POST',
            data: {
                estado: estado
            },
            datatype: 'html',
            success: function(retorno) {
                console.log('Success');
                console.log(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
            }
        });

        // Atualizar o nome na view
        // TODO

    });


    // Criação dos selects
    $("#select-regiao").select2({
        placeholder: "Região"
    });
    $("#select-estado").select2({
        placeholder: "Estado"
    });
    $("#select-municipio").select2({
        placeholder: "Município"
    });

});
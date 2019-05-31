
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
        
        var estado = $(this).val();
        
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
                    "className": "t-dep-escola"
                }
            ]
        });

    });

    // Preencher o SELECT da Escola
    $('#select-municipio').change(function () {
    
        var municipio = $(this).val();
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
        
        // Preencher a tabela
        // TODO

    });

    // Criação dos selects
    $("#select-estado").select2({
        placeholder: "Estado"
    });
    $("#select-municipio").select2({
        placeholder: "Município"
    });
    $("#select-escola").select2({
        placeholder: "Escola"
    });


    inicializaDataTable(); 

});


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
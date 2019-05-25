
// =======================================================
// Função para iniciar/reinicializar a DataTable
// =======================================================

function inicializaDataTable() {
    $('#tabela-escolas').DataTable({
        "language": {
            "lengthMenu": "Mostrar _MENU_ itens por página",
            "zeroRecords": "Nenhum item encontrado",
            "info": "Mostrando página _PAGE_ de _PAGES_",
            "infoEmpty": "Nenhum item encontrado",
            "infoFiltered": "(filtrado a partir de _MAX_ itens)",
            "search": "Buscar:",
            "emptyTable":     "Nenhum dado disponível na tabela",
            "loadingRecords": "Carregando...",
            "processing":     "Processando...",
            "paginate": {
                "first":      "Primeiro",
                "last":       "Último",
                "next":       "Próximo",
                "previous":   "Anterior"
            }
        },
        "order": [[ 1, "asc" ]]    // Ordena por Nome
    });
    $('.dataTables_length').addClass('bs-select');
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
    
    // Preencher o SELECT do Estado
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

        // Preencher a tabela
        $.ajax({
            url: 'recuperar-escolas.jsp',
            method: 'POST',
            data: {
                estado: estado
            },
            datatype: '',
            success: function(retorno) {
                console.log('Success');
                console.log(retorno);

                $('#resultado-busca').html(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
            }
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
        $.ajax({
            url: 'recuperar-escolas.jsp',
            method: 'POST',
            data: {
                municipio: municipio,
                estado: estado
            },
            datatype: '',
            success: function(retorno) {
                console.log('Success');
                console.log(retorno);

                $('#resultado-busca').html(retorno);
            },
            error: function(retorno) {
                console.log('Error');
                console.log(retorno);
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
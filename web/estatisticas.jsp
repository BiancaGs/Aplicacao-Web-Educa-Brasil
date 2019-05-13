<%-- 
    Document   : estatisticas
    Created on : 13/05/2019, 10:03:56
    Author     : Pietro
    Author     : Bianca
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="cabecalho.html"%>

    <!-- Header -->
    <header id="header-principal">

        <!-- Barra de Navegação de Busca -->
        <div id="busca">

            <div class="container">
                <ul class="list-inline navbar-busca">
                    <li class="list-inline-item">Brasil</li>
                    <li class="list-inline-item">Região</li>
                    <li class="list-inline-item">Estado</li>
                    <li class="list-inline-item">Município</li>
                </ul>
            </div>

        </div>

    </header>

    <!-- Main -->
    <main id="main-principal">

        <div class="container mt-4">
            
            <h2 class="verde-escuro-text">Brasil</h2>

            <div class="container">
                <div class="row">
                    <div class="col-sm-6 mapa-wrapper">
                        <div id="mapa"></div>
                    </div>
                    <div class="col-sm-6 barras-wrapper">
                        
                    </div>
                </div>
            </div>

        </div>

        <div class="container" id="estatisticas-gerais">
            <div class="row">
                <div class="col-sm-6">
                    <h5 class="verde-escuro-text">Situação de Funcionamento</h5>
                    <div class="grafico-est" id="grafico-situacao"></div>
                </div>
                <div class="col-sm-6">
                    <h5 class="verde-escuro-text">Dependência Administrativa</h5>
                    <div class="grafico-est" id="grafico-dep-adm"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <h5 class="verde-escuro-text">Localização</h5>
                    <div class="grafico-est" id="grafico-localizacao"></div>
                </div>
                <div class="col-sm-6">
                    <h5 class="verde-escuro-text">Oferta de Matrícula</h5>
                    <div class="grafico-est" id="grafico-oferta"></div>
                </div>
            </div>
        </div>

    </main>

<%@include file="rodape.html"%>

<!-- Amcharts v4.0 -->
<script src="lib/amchats4/core.js"></script>
<script src="lib/amchats4/charts.js"></script>
<script src="lib/amchats4/animated.js"></script>
<script src="js/estatisticas_graficos.js"></script>
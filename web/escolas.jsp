<%-- 
    Document   : escolas
    Created on : 13/05/2019, 10:03:21
    Author     : Pietro
    Author     : Bianca
--%>

<%@page import="model.Estado"%>
<%@page import="persistence.EstadoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="cabecalho.html"%>

    <!-- Header -->
    <header id="header-principal">

        <!-- Barra de Navegação de Busca -->
        <section id="busca">

            <div class="container">
                <ul class="list-inline navbar-busca">
                    <li class="list-inline-item">Buscar</li>
                    <li class="list-inline-item">
                        <select name="select-estado" id="select-estado">
                            <!-- Preenchido com AJAX -->
                        </select>
                    </li>
                    <li class="list-inline-item">
                        <select name="select-municipio" id="select-municipio">
                            <option></option>
                            <!-- Preenchido com AJAX -->
                        </select>
                    </li>
                </ul>
            </div>

        </section>

        <section id="secao-filtros">

            <div class="container">

                <ul class="filtros d-flex flex-row">

                    <li class="filtro">
                        <form action="" class="form-filtro">
                            <button type="button" class="btn-filtro">
                                <span class="filtro-nome">Situação de Funcionamento</span>
                                <li-icon>
                                    <i class="fas fa-caret-down fa-lg"></i>
                                </li-icon>
                            </button>
                            <div class="filtros-dropdown closed">
                                <fieldset>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="em-atividade">
                                        <label class="custom-control-label" for="em-atividade">Em Atividade</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="paralisada">
                                        <label class="custom-control-label" for="paralisada">Paralisada</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="extinta">
                                        <label class="custom-control-label" for="extinta">Extinta</label>
                                    </div>
                                    <div class="filtros-botoes">
                                        <button class="filtros-btn-cancelar">Cancelar</button>
                                        <button class="filtros-btn-aplicar">Aplicar</button>
                                    </div>
                                </fieldset>
                            </div>
                        </form>
                    </li>

                    <li class="filtro">
                        <form action="" class="form-filtro">
                            <button type="button" class="btn-filtro">
                                <span class="filtro-nome">Dependência Administrativa</span>
                                <li-icon>
                                    <i class="fas fa-caret-down fa-lg"></i>
                                </li-icon>
                            </button>
                            <div class="filtros-dropdown closed">
                                <fieldset>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="federal">
                                        <label class="custom-control-label" for="federal">Federal</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="estadual">
                                        <label class="custom-control-label" for="estadual">Estadual</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="municipal">
                                        <label class="custom-control-label" for="municipal">Municipal</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="particular">
                                        <label class="custom-control-label" for="particular">Particular</label>
                                    </div>
                                    <div class="filtros-botoes">
                                        <button class="filtros-btn-cancelar">Cancelar</button>
                                        <button class="filtros-btn-aplicar">Aplicar</button>
                                    </div>
                                </fieldset>
                            </div>
                        </form>
                    </li>

                    <li class="filtro">
                        <form action="" class="form-filtro">
                            <button type="button" class="btn-filtro">
                                <span class="filtro-nome">Ofertas de Matrícula</span>
                                <li-icon>
                                    <i class="fas fa-caret-down fa-lg"></i>
                                </li-icon>
                            </button>
                            <div class="filtros-dropdown closed">
                                <fieldset>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="bercario">
                                        <label class="custom-control-label" for="bercario">Bercário</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="creche">
                                        <label class="custom-control-label" for="creche">Creche</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="pre-escola">
                                        <label class="custom-control-label" for="pre-escola">Pré Escola</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="ef-1">
                                        <label class="custom-control-label" for="ef-1">Ensino Fundamental - 1º ao 4º</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="ef-2">
                                        <label class="custom-control-label" for="ef-2">Ensino Fundamental - 5º ao 8º</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="ensino-medio">
                                        <label class="custom-control-label" for="ensino-medio">Ensino Médio Normal</label>
                                    </div>
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input" id="ensino-medio-int">
                                        <label class="custom-control-label" for="ensino-medio-int">Ensino Médio
                                            Integrado</label>
                                    </div>
                                    <div class="filtros-botoes">
                                        <button class="filtros-btn-cancelar">Cancelar</button>
                                        <button class="filtros-btn-aplicar">Aplicar</button>
                                    </div>
                                </fieldset>
                            </div>
                        </form>
                    </li>

                </ul>

            </div>

        </section>

    </header>

    <!-- Main -->
    <main id="main-principal">

        <div class="container mt-5" id="resultado-busca">

            <h2 class="verde-escuro-text titulo-resultado-busca">Resultado da Busca:</h2>
            <h3 class="verde-claro-text nome-estado"></h3>
            <h4 class="verde-claro-text mt-4 nome-municipio"></h4>
            
            
            <!-- Escolas -->
            <section id="resultado-escolas" class="mt-4">

                <!-- Tabela de Escolas -->
                <table class="table table-hover" id="tabela-escolas">
                    
                    <thead class="thead-light">
                        <tr>
                            <th class="t-codigo">Código</th>
                            <th class="t-nome">Nome</th>
                            <th class="t-situacao">Situação</th>
                            <th class="t-dep-adm">Dep.Adm.</th>
                            <th class="t-ofertas">Ofertas</th>
                        </tr>
                    </thead>

                </table>
                <!-- Fim da Tabela de Escolas -->
                
            </section>
            <!-- Fim Escolas -->

            <!-- Modal Informações Escolas -->
            <div class="modal fade" id="modal-escola" tabindex="-1" role="dialog" aria-labelledby="modal-escola" aria-hidden="true">
                <!-- Completado com requisição -->
            </div>
            <!-- Modal Informações Escolas -->

        </div>
        <!-- Fim do Container -->

        <!-- Seção de Filtros -->
        <section id="filtros-wrapper" class="d-none">

            <h2 class="verde-escuro-text">Filtros:</h2>

            <!-- Filtros -->
            <article id="filtros">

                <div class="filtros-funcionamento">
                    <p class="font-weight-bold">Situação de Funcionamento</p>

                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="em-atividade">
                        <label class="custom-control-label" for="em-atividade">Em Atividade</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="paralisada">
                        <label class="custom-control-label" for="paralisada">Paralisada</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="extinta">
                        <label class="custom-control-label" for="extinta">Extinta</label>
                    </div>

                </div>

                <div class="filtros-dependencia-adm">
                    <p class="font-weight-bold">Dependência Administrativa</p>

                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="federal">
                        <label class="custom-control-label" for="federal">Federal</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="estadual">
                        <label class="custom-control-label" for="estadual">Estadual</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="municipal">
                        <label class="custom-control-label" for="municipal">Municipal</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="particular">
                        <label class="custom-control-label" for="particular">Particular</label>
                    </div>
                </div>

                <div class="filtros-ofertas">
                    <p class="font-weight-bold">Ofertas de Matrícula</p>

                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="creche">
                        <label class="custom-control-label" for="creche">Creche</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="pre-escola">
                        <label class="custom-control-label" for="pre-escola">Pré Escola</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="ef-1">
                        <label class="custom-control-label" for="ef-1">Ensino Fundamental - 1º ao 4º</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="ef-2">
                        <label class="custom-control-label" for="ef-2">Ensino Fundamental - 5º ao 8º</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="ensino-medio">
                        <label class="custom-control-label" for="ensino-medio">Ensino Médio Normal</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="ensino-medio-int">
                        <label class="custom-control-label" for="ensino-medio-int">Ensino Médio
                            Integrado</label>
                    </div>
                </div>

                <div class="filtros-dependencias d-flex flex-row flex-wrap">
                    <p class="font-weight-bold">Dependências da Escola</p>

                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="agua-filtrada">
                        <label class="custom-control-label" for="agua-filtrada">Água Filtrada</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="esgoto">
                        <label class="custom-control-label" for="esgoto">Esgoto</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="coleta-lixo">
                        <label class="custom-control-label" for="coleta-lixo">Coleta de Lixo</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="reciclagem">
                        <label class="custom-control-label" for="reciclagem">Reciclagem</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="lab-info">
                        <label class="custom-control-label" for="lab-info">Laboratório de
                            Informática</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="lab-ciencias">
                        <label class="custom-control-label" for="lab-ciencias">Laboratório de
                            Ciências</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="quadra">
                        <label class="custom-control-label" for="quadra">Quadra de Esportes</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="cozinha">
                        <label class="custom-control-label" for="cozinha">Cozinha</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="biblioteca">
                        <label class="custom-control-label" for="biblioteca">Biblioteca</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="sala-leitura">
                        <label class="custom-control-label" for="sala-leitura">Sala de Leitura</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="parque-infantil">
                        <label class="custom-control-label" for="parque-infantil">Parque Infantil</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="bercario">
                        <label class="custom-control-label" for="bercario">Bercário</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="auditorio">
                        <label class="custom-control-label" for="auditorio">Auditório</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="refeitorio">
                        <label class="custom-control-label" for="refeitorio">Refeitório</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="aloj-alunos">
                        <label class="custom-control-label" for="aloj-alunos">Alojamento para Alunos</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="aloj-prof">
                        <label class="custom-control-label" for="aloj-prof">Alojamento para
                            Professores</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="area-verde">
                        <label class="custom-control-label" for="area-verde">Área Verde</label>
                    </div>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="internet">
                        <label class="custom-control-label" for="internet">Internet</label>
                    </div>
                </div>

                <!-- Botão para aplicar os filtros -->
                <button class="text-uppercase w-100 p-1 mt-4 bg-verde-claro white-text"
                    id="aplicar-filtros">aplicar filtros</button>

            </article>
            <!-- Fim dos filtros -->
        </section>
        <!-- Fim da seção de filtros -->

    </main>

<%@include file="rodape.html"%>

<script src="lib/mdbootstrap/js/datatables.js"></script>
<script src="lib/select2/js/select2.js"></script>
<script src="js/escolas.js"></script>
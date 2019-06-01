<%-- 
    Document   : modal-escola
    Created on : 31/05/2019, 21:09:04
    Author     : Pietro
    Author     : Bianca
--%>

<%@page import="model.EscolaOfertas"%>
<%@page import="model.EscolaDependenciasGerais"%>
<%@page import="model.EscolaDependenciasFisicas"%>
<%@page import="model.Escola"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Escola e = (Escola) request.getAttribute("escola");
    EscolaDependenciasFisicas edf = e.getEdf();
    EscolaDependenciasGerais edg = e.getEdg();
    EscolaOfertas eo = e.getEo();
%>

<div class="modal-dialog modal-xl" role="document">

    <div class="modal-content">
        <div class="modal-header">
            <div class="d-flex flex-column">
                <h5 class="verde-escuro-text" id="modal-escola-label"><%= e.getCodigo() %> - <%= e.getNome() %></h5>
                <h6>Nordeste - Maranhão - Leste Maranhanse - Chapada do Alto Itapecuru - Jenipapo dos Vieiras - Jenipapo dos Vieiras</h6>
            </div>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="modal-body">
            <div class="container">

                <div class="row">
                    <div class="col-sm-7">

                        <div class="info-card">
                            <h6 class="info-card-title">informações de localização</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Região</b>:</div>
                                    <div class="col">Nordeste</div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Estado</b>:</div>
                                    <div class="col">Maranhão - MA</div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Mesorregião</b>:</div>
                                    <div class="col">Leste Maranhense</div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Microrregião</b>:</div>
                                    <div class="col">Chapadas do Alto do Itaperucu</div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Município</b>:</div>
                                    <div class="col">Jenipapo das Vieiras</div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Distrito</b>:</div>
                                    <div class="col">Jenipapo das Vieiras</div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Localização</b>:</div>
                                    <div class="col"><%= e.getLocalizacao() %></div>
                                </li>
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">ofertas de matrícula</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Berçário</b>:</div>
                                    <div class="col"><% out.print( eo.getBercario()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Creche</b>:</div>
                                    <div class="col"><% out.print( eo.getCreche() ? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Pré-Escola</b>:</div>
                                    <div class="col"><% out.print( eo.getPreEscola()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Ensino Fundamental - 1º ao 4º</b>:</div>
                                    <div class="col"><% out.print( eo.getEFI()? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Ensino Fundamental - 5º ao 8º</b>:</div>
                                    <div class="col"><% out.print( eo.getEFII()? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Ensino Médio Normal</b>:</div>
                                    <div class="col"><% out.print( eo.getEMN()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Ensino Média Integrado</b>:</div>
                                    <div class="col"><% out.print( eo.getEMI()? "Sim" : "Não"); %></div>
                                </li>
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">dependências da escola</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Sala de Diretoria:</b></div>
                                    <div class="col"><% out.print( edf.getSalaDiretoria()? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Sala de Professores:</b></div>
                                    <div class="col"><% out.print( edf.getSalaProfessor()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Laboratório de Informática:</b></div>
                                    <div class="col"><% out.print( edf.getLaboratorioInformatica()? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Laboratório de Ciências:</b></div>
                                    <div class="col"><% out.print( edf.getLaboratorioCiencias()? "Sim" : "Não"); %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Quadra de Esportes:</b></div>
                                    <div class="col"><% out.print( edf.getQuadraEsportes()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Biblioteca:</b></div>
                                    <div class="col"><% out.print( edf.getBiblioteca()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Sala de Leitura:</b></div>
                                    <div class="col"><% out.print( edf.getSalaLeitura()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Parque Infantil:</b></div>
                                    <div class="col"><% out.print( edf.getParqueInfantil()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Secretaria:</b></div>
                                    <div class="col"><% out.print( edf.getSecretaria()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Auditório:</b></div>
                                    <div class="col"><% out.print( edf.getAuditorio()? "Sim" : "Não"); %></div>
                                </li> 
                                <li class="row">
                                    <div class="col"><b>Área Verde:</b></div>
                                    <div class="col"><% out.print( edg.getAreaVerde()? "Sim" : "Não"); %></div>
                                </li>
                            </ul>

                        </div>

                    </div>
                    <!-- Fim col -->

                    <div class="col-sm-5">

                        <div class="info-card">
                            <h6 class="info-card-title">informações gerais</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Situação de Funcionamento:</b></div>
                                    <div class="col"><%= e.getSituacaoFuncionamento() %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Início do Ano Letivo:</b></div>
                                    <div class="col"><%= e.getInicioAnoLetivo() %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Término do Ano Letivo:</b></div>
                                    <div class="col"><%= e.getTerminoAnoLetivo() %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Dep. Administrativa:</b></div>
                                    <div class="col"><%= e.getDependenciaAdm() %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Qtd. de Salas:</b></div>
                                    <div class="col"><%= e.getQtdSalasExistentes() %></div>
                                </li>                                                      
                                <li class="row">
                                    <div class="col"><b>Qtd. de Funcionários:</b></div>
                                    <div class="col"><%= e.getQtdFuncionarios() %></div>
                                </li>
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">saneamento básico</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Água Filtrada:</b></div>
                                    <div class="col"><% out.print( edg.getAguaFiltrada()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Esgoto:</b></div>
                                    <div class="col"><% out.print( edg.getEsgoto()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Coleta de Lixo:</b></div>
                                    <div class="col"><% out.print( edg.getColetaLixo()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Reciclagem:</b></div>
                                    <div class="col"><% out.print( edg.getRecliclagem()? "Sim" : "Não"); %></div>
                                </li>
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">alimentação</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Alimentação:</b></div>
                                    <div class="col"><% out.print( edg.getAlimentacao()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Cozinha:</b></div>
                                    <div class="col"><% out.print( edf.getCozinha()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Refeitório:</b></div>
                                    <div class="col"><% out.print( edf.getRefeitorio()? "Sim" : "Não"); %></div>
                                </li>                                                      
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">moradia</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Alojamento para Alunos:</b></div>
                                    <div class="col"><% out.print( edg.getAlojamentoAlunos()? "Sim" : "Não"); %></div>
                                </li>
                                <li class="row">
                                    <div class="col"><b>Alojamento para Professores:</b></div>
                                    <div class="col"><% out.print( edg.getAlojamentoProfessores()? "Sim" : "Não"); %></div>
                                </li>                                                    
                            </ul>
                        </div>

                        <div class="info-card">
                            <h6 class="info-card-title">acesso à internet</h6>
                            <ul class="info">
                                <li class="row">
                                    <div class="col"><b>Internet:</b></div>
                                    <div class="col"><% out.print( edg.getInternet()? "Sim" : "Não"); %></div>
                                </li>
                            </ul>
                        </div>

                    </div>
                    <!-- Fim col -->

                </div>
                <!-- Fim row -->
            </div>
            <!-- Fim container -->
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-light" data-dismiss="modal">fechar</button>
        </div>
    </div>
</div>
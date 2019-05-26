<%-- 
    Document   : recuperar-escolas
    Created on : 25/05/2019, 17:41:01
    Author     : Pietro
    Author     : Bianca
--%>

<%@page import="model.EscolaOfertas"%>
<%@page import="model.Escola"%>
<%@page import="java.util.List"%>
<%@page import="persistence.EscolaDAO"%>
<%@page import="model.Municipio"%>
<%@page import="persistence.MunicipioDAO"%>
<%@page import="model.Estado"%>
<%@page import="persistence.EstadoDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<h2 class="verde-escuro-text">Resultado da Busca:</h2>
<h3 class="verde-claro-text">
    <%
        if (request.getParameter("estado") != null) {
            EstadoDAO edao = new EstadoDAO();
            Estado e = new Estado();
            e = edao.buscar(request.getParameter("estado"));
            out.print(e.getNome());
        }
    %>
</h3>
<h4 class="verde-claro-text mt-4">
    <%
        if (request.getParameter("municipio") != null) {
            MunicipioDAO mdao = new MunicipioDAO();
            Municipio m = new Municipio();
            m = mdao.buscar(request.getParameter("municipio"));
            out.print(m.getNome());
        }
        
    %>
</h4>

<!-- Escolas -->
<section id="resultado-escolas" class="mt-4">
    
    <%
        EscolaDAO esdao = new EscolaDAO();
        List<Escola> escolas = null;
        
        if (request.getParameter("estado") != null) {
            System.out.println("Nao");
            escolas = esdao.listarPorEstado(request.getParameter("estado"));
        }
            
        //else if (request.getParameter("municipio") != null)
            //List<Escola> escolas = esdao.listarPorMunicipio(request.getParameter("municipio"));
    %>

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

        <tbody id="escolas-datatable">
            <%
            System.out.println(escolas);
            for (final Escola escola : escolas) {
            %>
                <tr class="escola" codigo-escola="21114978" data-toggle="modal" data-target="#modal-escola">
                    <td class="t-codigo"><%= escola.getCodigo() %></td>
                    <td class="t-nome"><%= escola.getNome() %></td>
                    <td class="t-situacao"><%= escola.getSituacaoFuncionamento() %></td>
                    <td class="t-dep-adm"><%= escola.getDependenciaAdm() %></td>
                    <td class="t-ofertas">
                        <%
                        EscolaOfertas eo = escola.getEo();
                        if (eo.getBercario())
                            out.print("<span class='badge badge-pill badge-light'>B</span>");
                        if (eo.getCreche())
                            out.print("<span class='badge badge-pill badge-light'>C</span>");
                        if (eo.getPreEscola())
                            out.print("<span class='badge badge-pill badge-light'>PE</span>");
                        if (eo.getEFI())
                            out.print("<span class='badge badge-pill badge-light'>EFI</span>");
                        if (eo.getEFII())
                            out.print("<span class='badge badge-pill badge-light'>EFII</span>");
                        if (eo.getEMN())
                            out.print("<span class='badge badge-pill badge-light'>EMN</span>");
                        if (eo.getEMI())
                            out.print("<span class='badge badge-pill badge-light'>EMI</span>");
                        %>
                    </td>
                </tr>
            <%
            }
            %>
        </tbody>

    </table>
    <!-- Fim da Tabela de Escolas -->

</section>
<!-- Fim Escolas -->

<!-- Modal Informações Escolas -->
<div class="modal fade" id="modal-escola" tabindex="-1" role="dialog" aria-labelledby="modal-escola" aria-hidden="true">

</div>
<!-- Modal Informações Escolas -->
<%-- 
    Document   : cadastrarPerfil
    Created on : 6 de set. de 2022, 15:28:38
    Author     : guilherme
--%>

<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css"/>
        <link rel="stylesheet" href="css/menu.css" type="text/css"/>
        <link rel="stylesheet" href="css/styles.css" type="text/css"/>

        <title>Cadastro de Cliente</title>
    </head>
    <body>
        
        
        <% 
           String msg = (String) request.getAttribute("msg");
           
           if (msg != null) { 
                out.println(
                "<script type='text/javascript'>" +
                "alert('" + msg + "'); " +
                "</script>");
            }
        %>
        <div id="container"> <!-- Actions da JSP de include -->

            <div id="header">
                <jsp:include page="template/banner.jsp"></jsp:include>
                </div>

                <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
                </div>

                <div id="conteudo" class="bg-background">
                    <h1>FORMULÁRIO DE CADASTRO DE CLIENTE</h1>

           <!-- Os expression language usados abaixo no formulário funcionam na lógica do método get() -->
                    <form action="gerenciarCliente" method="POST" 
                          accept-charset="iso-8859-1, utf-8">
                        <h3 class="text-center mt-5">Cadastro de Cliente</h3>

                        <input type="hidden" id="idcliente" name="idCliente" 
                               value="${cliente.idCliente}">

                        <div class="form-group row offset-md-2 mt-3">
                            <label for="idnome" class="col-sm-2 form-label">Nome</label>
                            <div class="col-md-5">
                                <!-- Propriedade idnome pode ser usado como referência
                                para a utiização de alguma implementação de JavaScript 
                                sm , md, lg = tamanhos e mt = margin top -->
                                <input type="text" name="nome" id="idnome" 
                                       class="form-control" placeholder="Digite nome conforme CPF " value="${cliente.nome}">
                            </div>
                        </div>
                            
                            <div class="form-group row offset-md-2 mt-3">
                            <label for="idnome" class="col-sm-2 form-label ">CPF</label>
                            <div class="col-md-5">
                                <!-- Propriedade idnome pode ser usado como referência
                                para a utiização de alguma implementação de JavaScript 
                                sm , md, lg = tamanhos e mt = margin top -->
                                <input type="text" name="cpf" id="idcpf" 
                                       class="form-control" placeholder="Digite seu CPF" value="${cliente.cpf}">
                            </div>
                        </div>
                            
                            
                            

                        <div class="form-group row offset-md-2 mt-3">
                            <label for="iddata" class="col-sm-2 form-label">Data de Cadastro</label>
                            <div class="col-md-2">
                                <input type="date" name="dataCadastro" id="iddata" 
                                       class="form-control"  value="${cliente.dataCadastro}">
                            </div>
                        </div>

                        <div class="form-group row offset-md-2 mt-3">
                            <label for="idstatus" class="col-sm-2 form-label">Status</label>
                            <div class="col-md-1">
                                <select id="idstatus" name="status"
                                        class="form-control-sm">
                                    <option value="">Escolha uma opção</option>
                                    
                                    <!-- Expression Language EL -->
                                    
                                    <option value="1"
                                    <c:if test="${cliente.status == 1}"> selected</c:if>>Ativado</option>

                                    <option value="0"
                                    <c:if test="${cliente.status == 0}"> selected</c:if>>Desativado</option>

                                </select>
                        </div>
                    </div>
                        <!-- Faz-se necessário aqui ajustar o botão de salvar/adicionar/gravar
                        para que não salve um perfil com dados nulos/em branco.
                        -->
                        <div class="d-md-flex justify-content-md-center mr-3">
                            <button class="btn btn-primary btn-md mr-2">
                                Salvar Cliente&nbsp;
                                <i class="fa-solid fa-cloud-arrow-up"></i>
                            </button>
                            <a href="gerenciarCliente?acao=listar"
                               class="btn btn-info btn-md" role="button">
                                Voltar&nbsp;
                                <i class="fa-solid fa-circle-arrow-left"></i>                                                               
                            </a>                            
                        </div>                       
                </form>
            </div>
        </div>
        <!-- Espaço abaixo para teste de bootstrap -->


        <!-- tem que ser na sequência abaixo para carregar com êxito -->
        <!-- JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!-- Popper.js via cdn -->
        <script src="
                https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" 
                integrity="
                sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" 
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>

    </body>
</html>


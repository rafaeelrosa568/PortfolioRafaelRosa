
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- essa biblioteca fmt é utilizada para formatação de dados. Date, Money etc -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, 
              shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css"/>
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css"/>
        <link rel="stylesheet" href="css/menu.css" type="text/css"/>
        <link rel="stylesheet" href="css/styles.css" type="text/css"/>
        <link rel="stylesheet" href="datatables/css/dataTables.bootstrap4.min.css" type="text/css"/>
        <link rel="stylesheet" href="datatables/css/jquery.dataTables.min.css" type="text/css"/>

        <title>Registro de Clientes</title>
    </head>
    <body>
        
        <div id="container"> <!-- Actions da JSP de include -->

            <div id="header">
                <jsp:include page="template/banner.jsp"></jsp:include>
                </div>

                <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
            </div>

            <div id="conteudo" class="bg-background">
                <h1>Lista de Clientes Cadastrados no Sistema</h1>
                <div class="h-100 justify-content-center align-items-center">
                    <div class="col-12">
                        
                        <div class="col-sm-12" style="padding-bottom: 15px">
                            <a href="cadastrarCliente.jsp" class="btn btn-primary btn-md"
                               role="button">Cadastrar um Cliente&nbsp;
                                <i class="fa-solid fa-boxes-packing"></i>

                            </a>
                            <a href="#" class="btn btn-primary btn-md"
                               role="button"> Imprimir PDF&nbsp;
                                <i class="fa-solid fa-boxes-packing"></i>
                            </a>
                        </div>
                        <div class="table-responsive ">
                            <table class="table table-striped table-hover table-bordered responsive" 
                                   id="listarClientes">
                                <thead class="bg-primary  ">
                                    <tr class="text-white">
                                        <th class="text-center">Código</th>
                                        <th class="text-center">Nome</th>
                                        <th class="text-center">CPF</th>
                                        <th class="text-center">Data de Cadastro</th>
                                        <th class="text-center">Status</th>
                                        <th class="text-center">Ação</th>
                                                                             
                                    </tr>
                                </thead>
                                <tbody>
                               
                                <c:forEach items="${clientes}" var="cliente">
                                                                    
                                    <tr>
                                        <td>${cliente.idCliente}</td>
                                        <td>${cliente.nome}</td>
                                        <td>${cliente.cpf}</td>
                                        <td>
                                        <fmt:formatDate pattern = "dd/MM/yyyy" value = "${cliente.dataCadastro}" />
                                        </td>
                                        <td>
                                            
                                            <c:choose>                                             
                                                <c:when test="${cliente.status == 1}">
                                                    Ativado                                                    
                                                </c:when>
                                                
                                                <c:otherwise>
                                                    Desativado
                                                </c:otherwise>
                                                    
                                            </c:choose>    
                                                    
                                        </td>
                                        <td>
                                            <script type="text/javascript">
                                                function confirmDesativar(id, nome) {
                                                    if(confirm('Deseja desativar o cliente ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarCliente?acao=desativar&idCliente="+id;
                                                    }
                                                }
                                                
                                                function confirmAtivar(id, nome) {
                                                    if(confirm('Deseja ativar o cliente ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarCliente?acao=ativar&idCliente="+id;
                                                    }
                                                }
                                                
                                                function confirmDeletar(id, nome) {
                                                    if(confirm('Deseja deletar o cliente ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarCliente?acao=deletar&idCliente="+id;
                                                    }
                                                }
                                                
                                                function confirmAlterar(id, nome) {
                                                    if(confirm('Na proxima pagina aperte Ctrl + P Para imprimir! ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarCliente?acao=Alterar&idCliente="+id;
                                                    }
                                                }
                                                
                                            </script>
                                            <a href=
                                               "gerenciarCliente?acao=alterar&idCliente=${cliente.idCliente}"
                                               class="btn btn-primary btn-sm" role="button">
                                                Alterar&nbsp;<i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                               <c:choose>
                                                   <c:when test="${cliente.status == 1}">
                                                       <button class="btn btn-danger btn-sm"
                                                            onclick="confirmDesativar('${cliente.idCliente}','${cliente.nome}')">                                                                
                                                            Desativar&nbsp;
                                                            <i class="fa-solid fa-user-slash"></i>                                                           
                                                       </button>
                                                       
                                                   </c:when>
                                                   <c:otherwise>
                                                       <button class="btn btn-success btn-sm"
                                                            onclick="confirmAtivar('${cliente.idCliente}','${cliente.nome}')">
                                                            Ativar&nbsp;
                                                            <i class="fa-solid fa-user-check"></i>                                                            
                                                       </button>
                                                            
                                                   </c:otherwise>
                                                   
                                                   
                                                   
                                               </c:choose>
                                               
                                                <a href=
                                               "gerenciarCliente?acao=deletar&idCliente=${cliente.idCliente}"
                                               class="btn btn-primary btn-sm" role="button">
                                                Deletar&nbsp;<i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                               
                                               <a href=
                                               "gerenciarCliente?acao=listarUmaColunaTabelaCliente&idCliente=${cliente.idCliente}"
                                               class="btn btn-primary btn-sm" role="button">
                                                GerarPDF&nbsp;<i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                               
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            
                        </div>
                    </div> <!-- fim da div col-12 -->

                </div> <!-- fim da div h-100 justify -->

            </div> <!-- fim da div content/conteudo -->

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
        
        <!-- DataTables.js sempre o último a ser invocado -->
        <script src="datatables/js/jquery.dataTables.min.js"></script>
        <script src="datatables/js/dataTables.bootstrap4.min.js"></script>
        
        <script type="text/javascript">
             $(document).ready(function () {
                $("#listarClientes").DataTable({
                    "bJQueryUI": true,
                    "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
                        "oLanguage": {
                            "sProcessing": "Processando..",
                            "sLengthMenu": "Mostrar _MENU_ registros",
                            "sZeroRecords": "Não foram encontrados resultados",
                            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                            "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                            "sInfoFiltered": "",
                            "sInfoPostFix": "",
                            "sSearch": "Pesquisar",
                            "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext": "Próximo",
                            "sLast": "Último"
                            }
                        }
                    });
                }); 
        </script>

        
    </body>
</html>



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

        <title>Registro de Produtos</title>
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
                <h1>Lista de Produtos Cadastrados no Sistema</h1>
                <div class="h-100 justify-content-center align-items-center">
                    <div class="col-12">
                        
                        <div class="col-sm-12" style="padding-bottom: 15px">
                            <a href="cadastrarProduto.jsp" class="btn btn-primary btn-md"
                               role="button">Cadastrar um Produto&nbsp;
                                <i class="fa-solid fa-boxes-packing"></i>

                            </a>
                            <a href="report.jsp" class="btn btn-primary btn-md"
                               role="button"> Imprimir PDF&nbsp;
                                <i class="fa-solid fa-boxes-packing"></i>
                            </a>
                        </div>
                        <div class="table-responsive ">
                            <table class="table table-striped table-hover table-bordered responsive" 
                                   id="listarProdutos">
                                <thead class="bg-primary  ">
                                    <tr class="text-white">
                                        <th class="text-center">Código</th>
                                        <th class="text-center">Nome</th>
                                        <th class="text-center">Descricao</th>
                                        <th class="text-center">Data de Cadastro</th>
                                        <th class="text-center">Status</th>
                                        <th class="text-center">Ação</th>
                                                                             
                                    </tr>
                                </thead>
                                <tbody>
                                <!-- Aqui fica o objeto perfil do array 
                                e a string do request.setAttribute, que é um arraylist.
                                    Como também utiliza-se o expression languages como
                                espécie de "get" para os objetos perfil da classe Perfil
                                    Tag da expression language dentro das <td></td> -->
                                <c:forEach items="${produtos}" var="produto">
                                                                    
                                    <tr>
                                        <td>${produto.idProduto}</td>
                                        <td>${produto.nome}</td>
                                        <td>${produto.descricao}</td>
                                        <td>
                                        <fmt:formatDate pattern = "dd/MM/yyyy" value = "${produto.dataCadastro}" />
                                        </td>
                                        <td>
                                            <!-- WHEN mesma coisa que perfil.getStatus() -->
                                            <!-- OTHERWISE o que seria o }else{ -->
                                            <c:choose>                                             
                                                <c:when test="${produto.status == 1}">
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
                                                    if(confirm('Deseja desativar o produto ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarProduto?acao=desativar&idProduto="+id;
                                                    }
                                                }
                                                
                                                function confirmAtivar(id, nome) {
                                                    if(confirm('Deseja ativar o produto ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarProduto?acao=ativar&idProduto="+id;
                                                    }
                                                }
                                                
                                                function confirmDeletar(id, nome) {
                                                    if(confirm('Deseja deletar o produto ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarProduto?acao=deletar&idProduto="+id;
                                                    }
                                                }
                                                
                                                function confirmAlterar(id, nome) {
                                                    if(confirm('Na proxima pagina aperte Ctrl + P Para imprimir! ' +
                                                            nome + '?')) {
                                                        location.href="gerenciarProduto?acao=Alterar&idProduto="+id;
                                                    }
                                                }
                                                
                                            </script>
                                            <a href=
                                               "gerenciarProduto?acao=alterar&idProduto=${produto.idProduto}"
                                               class="btn btn-primary btn-sm" role="button">
                                                Alterar&nbsp;<i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                               <c:choose>
                                                   <c:when test="${produto.status == 1}">
                                                       <button class="btn btn-danger btn-sm"
                                                            onclick="confirmDesativar('${produto.idProduto}','${produto.nome}')">                                                                
                                                            Desativar&nbsp;
                                                            <i class="fa-solid fa-user-slash"></i>                                                           
                                                       </button>
                                                       
                                                   </c:when>
                                                   <c:otherwise>
                                                       <button class="btn btn-success btn-sm"
                                                            onclick="confirmAtivar('${produto.idProduto}','${produto.nome}')">
                                                            Ativar&nbsp;
                                                            <i class="fa-solid fa-user-check"></i>                                                            
                                                       </button>
                                                            
                                                   </c:otherwise>
                                                   
                                                   
                                                   
                                               </c:choose>
                                               
                                                <a href=
                                               "gerenciarProduto?acao=deletar&idProduto=${produto.idProduto}"
                                               class="btn btn-primary btn-sm" role="button">
                                                Deletar&nbsp;<i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                               
                                               <a href=
                                               "gerenciarProduto?acao=gerarPdf&idProduto=${produto.idProduto}"
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
                $("#listarProdutos").DataTable({
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


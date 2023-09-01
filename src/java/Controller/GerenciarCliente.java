package Controller;

import Model.Cliente;
import Model.ClienteDAO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.io.font.TrueTypeFont;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.DocumentException;

import java.io.FileOutputStream;
import java.io.IOException;



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente", "/report"})
public class GerenciarCliente extends HttpServlet {

   
 @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String mensagem = "";
        
        
        Cliente cliente = new Cliente();
    
        ClienteDAO daoCliente = new ClienteDAO();
        
        try {
            if(acao.equals("listar")){
                
                ArrayList<Cliente> clientes = new ArrayList<>();
                
                clientes = daoCliente.getLista();
                

                
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarClientes.jsp");
                request.setAttribute("clientes", clientes);
                dispatcher.forward(request, response);
                
            }else if(acao.equals("alterar")){
//                                  Buscar por id
                cliente = daoCliente.getCarregarPorId(Integer.parseInt(idCliente));
                
//                
                if(cliente.getIdCliente() > 0 ){
                    
                    RequestDispatcher dispatcher =
                        getServletContext().
                            getRequestDispatcher("/cadastrarCliente.jsp");
                    request.setAttribute("cliente", cliente);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Cliente não encontrado na base de dados!";
                }
               
//                  ATIVAR E DESATIVAR SERVLET
            }else if(acao.equals("desativar")){
                
                cliente.setIdCliente(Integer.parseInt(idCliente));
                
                if(daoCliente.desativar(cliente)){
                    mensagem = "Cliente desativado com sucesso!";
                    
                }else{
                    mensagem = "Falha ao desativar o cliente!";
                }
          
               
            }else if(acao.equals("ativar")){
                
                cliente.setIdCliente(Integer.parseInt(idCliente));
                
                if(daoCliente.ativar(cliente)){
                     mensagem = "Cliente ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o cliente!";
                }
            
            
            }else if(acao.equals("deletar")){
                
                cliente.setIdCliente(Integer.parseInt(idCliente));
                
                if(daoCliente.deletar(cliente)){
                    mensagem = "Cliente Deletado!";
                }else{
                    mensagem = "Falha ao deletar Cliente!";
                }
            
             
            }else if(acao.equals("listarUmaColunaTabelaCliente")){
                
                cliente.setIdCliente(Integer.parseInt(idCliente));
                
                if(daoCliente.listarUmaColunaTabelaCliente(cliente)){
                    
                    
                    mensagem = "PAGINA EM DESENVOLVIMENTO!";
                }else{
                    mensagem = "Falha ao gerar coluna!";
                }
                
            }else{
                
                
                response.sendRedirect("/index.jsp");
            }
            
        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            
            ex.printStackTrace();
        }
        
        out.println(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarCliente?acao=listar';" +
            "</script>" );
      
    }

  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idCliente = request.getParameter("idCliente");
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String dataCadastro = request.getParameter("dataCadastro");
        String status = request.getParameter("status");
        //String gerarPDF = request.getParameter("report");
        String mensagem = "";
        
       Cliente cliente = new Cliente();
        ClienteDAO daoCliente = new ClienteDAO();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        try {            
            if(!idCliente.isEmpty()){
                cliente.setIdCliente(Integer.parseInt(idCliente));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "O campo NOME precisa ser preenchido!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                            forward(request, response);
               
            }else{
                cliente.setNome(nome);
            }
            
            if (cpf.isEmpty() || cpf.equals("")){
                request.setAttribute("msg", "O campo CPF precisa ser preenchido!");
                getServletContext().getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
                
            }else{
                cliente.setCpf(cpf);
            }
            
            if(dataCadastro.isEmpty() || dataCadastro.equals("")){
                request.setAttribute("msg", "Informe a DATA DE CADASTRO!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
            }else{
                cliente.setDataCadastro(df.parse(dataCadastro));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Um STATUS precisa ser selecionado!");
                getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                        forward(request, response);
            }else{
                cliente.setStatus(Integer.parseInt(status));
            }
            
            if(daoCliente.salvar(cliente)){
                mensagem = "Cliente salvo na base de dados";
            }
        } catch (ParseException pe) {
            mensagem = "Erro: " + pe.getMessage();
        } catch (SQLException ex){
            mensagem = "Erro: " + ex.getMessage();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarCliente?acao=listar';" +
                "</script>"
        
        );

    }
    
  
    /*
    protected void gerarPDF(HttpServletRequest request,
            HttpServletResponse response, PdfDocument pdfDoc) throws ServletException, IOException{
     
            Document documento = new Document(pdfDoc);
            
            try {
            
                response.setContentType("apllication/pdf");
                
                response.addHeader("Content-Disposition", "inline; falename="
                        + "cliente.pdf");
                com.lowagie.text.Document Documento = null;
                com.lowagie.text.pdf.PdfWriter.getInstance(Documento, response.getOutputStream());
                
                documento.add(new Paragraph("Lista de teste Clientes"));
                documento.close();
        } catch (Exception e) {
                System.out.println(e);
                documento.close();
        }
    }
*/
}

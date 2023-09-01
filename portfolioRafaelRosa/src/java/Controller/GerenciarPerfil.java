package Controller;

import Model.Perfil;
import Model.PerfilDAO;
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

@WebServlet(name = "GerenciarPerfil", urlPatterns = {"/gerenciarPerfil"})
public class GerenciarPerfil extends HttpServlet {

   
 @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";
        
       
        Perfil perfil = new Perfil();
    
        PerfilDAO daoPerfil = new PerfilDAO();
        
        try {
            if(acao.equals("listar")){
                
                ArrayList<Perfil> perfis = new ArrayList<>();
                
                perfis = daoPerfil.getLista();
                

                
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarPerfis.jsp");
                request.setAttribute("perfis", perfis);
                dispatcher.forward(request, response);
                
            }else if(acao.equals("alterar")){
//                                  Buscar por id
                perfil = daoPerfil.getCarregarPorId(Integer.parseInt(idPerfil));
                
  
                if(perfil.getIdPerfil() > 0 ){
                    
                    RequestDispatcher dispatcher =
                        getServletContext().
                            getRequestDispatcher("/cadastrarPerfil.jsp");
                    request.setAttribute("perfil", perfil);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Perfil não encontrado na base de dados!";
                }
               
//                  ATIVAR E DESATIVAR SERVLET
            }else if(acao.equals("desativar")){
                
                perfil.setIdPerfil(Integer.parseInt(idPerfil));
                
                if(daoPerfil.desativar(perfil)){
                    mensagem = "Perfil desativado com sucesso!";
                    
                }else{
                    mensagem = "Falha ao desativar o perfil!";
                }
          
               
            }else if(acao.equals("ativar")){
                
                perfil.setIdPerfil(Integer.parseInt(idPerfil));
                
                if(daoPerfil.ativar(perfil)){
                     mensagem = "Perfil ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o perfil!";
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
            "location.href='gerenciarPerfil?acao=listar';" +
            "</script>" );
      
    }

  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idPerfil = request.getParameter("idPerfil");
        String nome = request.getParameter("nome");
        String dataCadastro = request.getParameter("dataCadastro");
        String status = request.getParameter("status");
        String mensagem = "";
        
        Perfil perfil = new Perfil();
        PerfilDAO daoPerfil = new PerfilDAO();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        try {            
            if(!idPerfil.isEmpty()){
                perfil.setIdPerfil(Integer.parseInt(idPerfil));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "O campo NOME precisa ser preenchido!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                            forward(request, response);
               
            }else{
                perfil.setNome(nome);
            }
            
            if(dataCadastro.isEmpty() || dataCadastro.equals("")){
                request.setAttribute("msg", "Informe a DATA DE CADASTRO!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                        forward(request, response);
            }else{
                perfil.setDataCadastro(df.parse(dataCadastro));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Um STATUS precisa ser selecionado!");
                getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                        forward(request, response);
            }else{
                perfil.setStatus(Integer.parseInt(status));
            }
            
            if(daoPerfil.salvar(perfil)){
                mensagem = "Perfil salvo na base de dados";
            }
        } catch (ParseException pe) {
            mensagem = "Erro: " + pe.getMessage();
        } catch (SQLException ex){
            mensagem = "Erro: " + ex.getMessage();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarPerfil?acao=listar';" +
                "</script>"
        
        );

    }

}

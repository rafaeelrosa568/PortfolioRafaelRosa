package Controller;

import Model.Menu;
import Model.Usuario;
import Model.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GerenciarLogin", urlPatterns = {"/gerenciarLogin"})
public class GerenciarLogin extends HttpServlet {

    private static HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        sessao.removeAttribute("usuariologado");
        sessao.invalidate();
        response.sendRedirect("index.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        GerenciarLogin.response = response;

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String mensagem = "";

        Usuario usuario = new Usuario();
        UsuarioDAO daoUsuario = new UsuarioDAO();

        if (login.equals("") || login.isBlank() || login.isEmpty()) {
            request.setAttribute("msg", "Informe o login de usuário!");
            despacharRequisicao(request, response);
        } else {
            usuario.setLogin(login);
        }

        if (senha.equals("") || senha.isBlank() || senha.isEmpty()) {
            request.setAttribute("msg", "Informe a senha do usuário!");
            despacharRequisicao(request, response);
        } else {
            usuario.setSenha(senha);
        }

        try {
            usuario = daoUsuario.getRecuperarUsuario(login);
            if ((usuario.getIdUsuario() > 0) && (usuario.getSenha().equals(senha))) {
//          Sessão = momento que o usuário se autentica no sistema, até quando ele realiza o logoff                
                HttpSession sessao = request.getSession();
                sessao.setAttribute("usuariologado", usuario);
                response.sendRedirect("index.jsp");

            } else {
                exibirMensagem("Login ou Senha inválidos!");
            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }

    }

    private void despacharRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/formLogin.jsp").
                forward(request, response);

    }

    private static void exibirMensagem(String mensagem) {
        try {
            PrintWriter out = response.getWriter();
            out.print(
                    "<script type='text/javascript'>"
                    + "alert('" + mensagem + "');"
                    + "history.back();"
                    + "</script>");
            out.close();

        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public static Usuario verificarAcesso(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        GerenciarLogin.response = response;
        Usuario usuario = null;
        try {
            HttpSession sessao = request.getSession();
            if (sessao.getAttribute("usuariologado") == null) {
                response.sendRedirect("formLogin.jsp");

            } else {
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();

                if (queryString != null) {
                    uri += "?" + queryString;
                }

                usuario = (Usuario) request.getSession().getAttribute("usuariologado");

                if (usuario == null) {
                    sessao.setAttribute("msg", "Usuário não autenticado no sistema!");
                    response.sendRedirect("formLogin.jsp");
                } else {
                    boolean possuiAcesso = false;
                    for (Menu menu : usuario.getPerfil().getMenus()) {
                        if (uri.contains(menu.getLink())) {
                            possuiAcesso = true;
                            break;
                        }
                    }
                    if (!possuiAcesso) {
                        exibirMensagem("O usúario não autorizado!");
                    }
                }

            }
        } catch (Exception e) {
            exibirMensagem("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        return usuario;
    }

     public static boolean verificarPermissao(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException{
        GerenciarLogin.response = response;
        Usuario usuario = null;
        boolean possuiAcesso = false;
        try {
            HttpSession sessao = request.getSession();
            if(sessao.getAttribute("usuariologado") == null){
                response.sendRedirect("formLogin.jsp");
                
            }else{
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                
                if(queryString != null){
                    uri += "?" + queryString;
                }
              
                usuario = (Usuario) request.getSession().getAttribute("usuariologado");
                
                if(usuario == null){
                    sessao.setAttribute("msg", "Usuário não autenticado no sistema!");
                    response.sendRedirect("formLogin.jsp");
                }else{
                    
                    for(Menu menu : usuario.getPerfil().getMenus()){
                        if(uri.contains(menu.getLink())){
                            possuiAcesso = true;
                            break;
                        }
                        
                    }
                  
                }
                  
            }
        } catch (Exception ex) {
            exibirMensagem("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return possuiAcesso;
    }

}

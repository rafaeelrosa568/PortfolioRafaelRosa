package Controller;

import Model.Perfil;
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
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebServlet(name = "GerenciarUsuario", urlPatterns = {"/gerenciarUsuario"})
public class GerenciarUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");

        String acao = request.getParameter("acao");
        String idUsuario = request.getParameter("idUsuario");
        String mensagem = "";

        Usuario usuario = new Usuario();
        UsuarioDAO daoUsuario = new UsuarioDAO();

        try {

            if (acao.equals("listar")) {

                ArrayList<Usuario> listaDeUsuarios = new ArrayList<>();
                listaDeUsuarios = daoUsuario.getLista();

                RequestDispatcher dispatcher = getServletContext().
                        getRequestDispatcher("/listarUsuario.jsp");

                request.setAttribute("usuarios", listaDeUsuarios);
                dispatcher.forward(request, response);

            } else if (acao.equals("alterar")) {

                if (GerenciarLogin.verificarPermissao(request, response)) {

                    usuario = daoUsuario.getCarregarPorId(Integer.parseInt(idUsuario));

                    if (usuario.getIdUsuario() > 0) {
                        RequestDispatcher dispatcher = getServletContext().
                                getRequestDispatcher("/cadastrarUsuario.jsp");
                        request.setAttribute("usuario", usuario);
                        dispatcher.forward(request, response);

                    } else {
                        mensagem = "Usuário não encontrado na base de dados!";
                    }
                } else {
                    mensagem = "Acesso não Concedido!";
                }

            } else if (acao.equals("ativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    usuario.setIdUsuario(Integer.parseInt(idUsuario));

                    if (daoUsuario.ativar(usuario)) {
                        mensagem = "Usuário ATIVADO com sucesso!";
                    } else {
                        mensagem = "ERRO ao ATIVAR o usuário.";
                    }
                } else {
                    mensagem = "Acesso não Concedido!";
                }

            } else if (acao.equals("desativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    usuario.setIdUsuario(Integer.parseInt(idUsuario));

                    if (daoUsuario.desativar(usuario)) {
                        usuario = daoUsuario.getCarregarPorId(Integer.parseInt(idUsuario));
                        mensagem = "Usuário " + usuario.getNome() + "DESATIVADO com sucesso!";

                    } else {
                        mensagem = "ERRO ao desativar o usuário: " + usuario.getNome();
                    }
                    
                } else {
                    mensagem = "Acesso não Concedido!";
                }
                
            } else {
                response.sendRedirect("/index.jsp");

            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String idUsuario = request.getParameter("idUsuario");
        String nome = request.getParameter("nome");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        String idPerfil = request.getParameter("idPerfil");

        String mensagem = "";

        Usuario usuario = new Usuario();

        if (!idUsuario.isEmpty()) {
            usuario.setIdUsuario(Integer.parseInt(idUsuario));
        }

        if (nome.equals("") || nome.isEmpty() || nome.isBlank()) {
            request.setAttribute("msg", "Informe o nome do usuário!");
            despacharRequisicao(request, response);

        } else {
            usuario.setNome(nome);
        }

        if (login.equals("") || login.isEmpty() || login.isBlank()) {
            request.setAttribute("msg", "Informe o nome de login do usuário!");
            despacharRequisicao(request, response);

        } else {
            usuario.setLogin(login);
        }

        if (senha.equals("") || senha.isEmpty() || senha.isBlank()) {
            request.setAttribute("msg", "Informe a senha do usuário!");

            despacharRequisicao(request, response);

        } else {
            usuario.setSenha(senha);
        }

        if (email.equals("") || email.isEmpty() || email.isBlank()) {
            request.setAttribute("msg", "Informe o email do usuário!");
            despacharRequisicao(request, response);
        } else {
            usuario.setEmail(email);
        }

        if (status.equals("") || status.isEmpty() || status.isBlank() || status.matches("Escolha uma opção")) {
            request.setAttribute("msg", "Selecione o STATUS do usuário!");

            despacharRequisicao(request, response);

        } else {
            usuario.setStatus(Integer.parseInt(status));
        }

        Perfil perfil = new Perfil();

        if (idPerfil.equals("") || idPerfil.isEmpty() || idPerfil.isBlank()) {
            request.setAttribute("msg", "Um tipo de perfil precisa ser selecionado!");
            despacharRequisicao(request, response);

        } else {
            perfil.setIdPerfil(Integer.parseInt(idPerfil));
            usuario.setPerfil(perfil);
        }

        UsuarioDAO daoUsuario = new UsuarioDAO();

        try {
            if (daoUsuario.Salvar(usuario)) {
                mensagem = "Usuário salvo com sucesso na base de dados do sistema!";
            } else {
                mensagem = "Ocorreu uma falha ao realizar o cadastro do usuário!";
            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();

        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarUsuario?acao=listar';"
                + "</script>");

    }

    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    private void despacharRequisicao(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/cadastrarUsuario.jsp").
                forward(request, response);

    }

}

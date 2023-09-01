package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.Menu;
import Model.MenuDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@WebServlet(name = "GerenciarMenu", urlPatterns = {"/gerenciarMenu"})
public class GerenciarMenu extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html charset=utf-8");

        String acao = request.getParameter("acao");
        String idMenu = request.getParameter("idMenu");
        String mensagem = "";

        Menu menu = new Menu();
        MenuDAO daoMenu = new MenuDAO();

        try {

            if (acao.equals("listar")) {

                ArrayList<Menu> menus = new ArrayList<>();

                menus = daoMenu.getLista();

                RequestDispatcher dispatcher
                        = getServletContext().
                                getRequestDispatcher("/listarMenus.jsp");
                request.setAttribute("menus", menus);
                dispatcher.forward(request, response);

            } else if (acao.equals("alterar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    menu = daoMenu.getCarregarPorId(Integer.parseInt(idMenu));

                    if (menu.getIdMenu() > 0) {
                        RequestDispatcher dispatcher
                                = getServletContext().
                                        getRequestDispatcher("/cadastrarMenu.jsp");
                        request.setAttribute("menu", menu);
                        dispatcher.forward(request, response);

                    } else {
                        mensagem = "Menu não encontrado na base de dados!";
                    }
                } else {
                    mensagem = "Acesso não Concedido!";
                }

            } else if (acao.equals("desativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    menu.setIdMenu(Integer.parseInt(idMenu));

                    if (daoMenu.desativar(menu)) {
                        mensagem = "Menu desativado com sucesso na base de dados!";

                    } else {
                        mensagem = "Falha ao desativar o menu da base de dados!";
                    }
                } else {
                    mensagem = "Acesso não Concedido!";
                }

            } else if (acao.equals("ativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    menu.setIdMenu(Integer.parseInt(idMenu));

                    if (daoMenu.ativar(menu)) {
                        mensagem = "Menu ativado com sucesso na base de dados!";
                    } else {
                        mensagem = "Falha ao desativar o menu da base de dados!";
                    }
                } else {
                    mensagem = "Acesso não Concedido!";
                }

            } else {
                response.sendRedirect("index.jsp");
            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }

        out.print(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenu?acao=listar';"
                + "</script>");

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String idMenu = request.getParameter("idMenu");
        String nome = request.getParameter("nome");
        String link = request.getParameter("link");
        String icone = request.getParameter("icone");
        String exibir = request.getParameter("exibir");
        String status = request.getParameter("status");
        String mensagem = "";

        Menu menu = new Menu();

        if (!idMenu.isEmpty()) {
            menu.setIdMenu(Integer.parseInt(idMenu));
        }

        menu.setIcone(icone);

        if (nome.equals("") || nome.isEmpty()) {
            request.setAttribute("msg", "O campo NOME precisa ser preenchido!");
            despacharRequisicao(request, response);

        } else {
            menu.setNome(nome);
        }

        if (link.equals("") || link.isEmpty()) {
            request.setAttribute("msg", "Um endereço de LINK precisa ser informado!");
            despacharRequisicao(request, response);

        } else {
            menu.setLink(link);
        }

        if (exibir.equals("") || exibir.isEmpty() || exibir.isBlank() || exibir.matches("Escolha uma opção")) {
            request.setAttribute("msg", "Uma opção de exibição precisa ser selecionada!");
            despacharRequisicao(request, response);

        } else {

            try {
                menu.setExibir(Integer.parseInt(exibir));
            } catch (NumberFormatException ex) {
                mensagem = "Erro: " + ex.getMessage();
                ex.printStackTrace();
            }

        }

        if (status.equals("") || status.isEmpty() || status.isBlank() || status.matches("Escolha uma opção")) {
            request.setAttribute("msg", "Uma opção de STATUS precisa ser selecionada!");
            despacharRequisicao(request, response);

        } else {
            menu.setStatus(Integer.parseInt(status));
        }

        MenuDAO daoMenu = new MenuDAO();

        try {

            if (daoMenu.salvar(menu)) {
                mensagem = "Menu cadastrado com sucesso na base de dados!";

            } else {
                mensagem = "Falha ao cadastrar o menu na base dados!";
            }

        } catch (SQLException ex) {
            mensagem = "Erro: " + ex.getMessage();
            ex.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarMenu?acao=listar';"
                + "</script>");

    }

    private void despacharRequisicao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().
                getRequestDispatcher("/cadastrarMenu.jsp").
                forward(request, response);

    }

    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

}

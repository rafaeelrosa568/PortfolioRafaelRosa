package Model;

import Factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class UsuarioDAO {

    Connection conexao;
    PreparedStatement pstm;
    ResultSet rs;
    String sqlUsuario = "";

    public ArrayList<Usuario> getLista() throws SQLException {

        ArrayList<Usuario> usuarios = new ArrayList<>();

        sqlUsuario = "SELECT p.idPerfil, p.nome, u.idUsuario, "
                + "u.nome, u.login, u.senha, u.email, u.status, u.idPerfil "
                + "FROM perfil p "
                + "INNER JOIN usuario u "
                + "ON p.idPerfil = u.idPerfil ";

        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlUsuario);
        rs = pstm.executeQuery();

        while (rs.next()) {

            Usuario usuario = new Usuario();

            usuario.setIdUsuario(rs.getInt("u.idUsuario"));
            usuario.setNome(rs.getString("u.nome"));
            usuario.setLogin(rs.getString("u.login"));
            usuario.setSenha(rs.getString("u.senha"));
            usuario.setEmail(rs.getString("u.email"));
            usuario.setStatus(rs.getInt("u.status"));

            Perfil perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt("p.idPerfil"));
            perfil.setNome(rs.getString("p.nome"));

            // Associação entre usuário e perfil, settando um perfil para usuário
            usuario.setPerfil(perfil);

            // O nome da lista instanciada aqui é: usuarios
            usuarios.add(usuario);
        }
        ConexaoFactory.close(conexao);
        return usuarios;

    }

    public boolean Salvar(Usuario usuario) throws SQLException {

        conexao = ConexaoFactory.conectar();

        if (usuario.getIdUsuario() == 0) {

            sqlUsuario = "INSERT INTO USUARIO "
                    + "(nome, login, senha, email, status, idPerfil) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            pstm = conexao.prepareStatement(sqlUsuario);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getLogin());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getEmail());
            pstm.setInt(5, usuario.getStatus());
            pstm.setInt(6, usuario.getPerfil().getIdPerfil());

        } else {

            sqlUsuario = "UPDATE USUARIO SET "
                    + "nome = ?, "
                    + "login = ?, "
                    + "senha = ?, "
                    + "email = ?, "
                    + "status = ?, "
                    + "idPerfil = ? "
                    + "WHERE idUsuario = ?";

            pstm = conexao.prepareStatement(sqlUsuario);

            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getLogin());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getEmail());
            pstm.setInt(5, usuario.getStatus());
            pstm.setInt(6, usuario.getPerfil().getIdPerfil());
            // Na string UPDATE faz-se necessário informar também o id da entidade
            pstm.setInt(7, usuario.getIdUsuario());

        }
        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        return true;

    }

    public Usuario getCarregarPorId(int idUsuario) throws SQLException {

        /* Aqui deixei os objetos instânciados com a mesma nomenclatura das Classes
           e os alias(apelidos) com a letra inicial dos objetos, logo: "u" e "p"
         */
        Usuario usuario = new Usuario();

        sqlUsuario = "SELECT p.nome, p.idPerfil, u.idUsuario, "
                + "u.nome, u.login, u.senha, u.email, u.status, u.idPerfil "
                + "FROM USUARIO u "
                + "INNER JOIN PERFIL p "
                + "ON p.idPerfil = u.idPerfil "
                + "WHERE u.idUsuario = ?";

        conexao = ConexaoFactory.conectar();

        pstm = conexao.prepareStatement(sqlUsuario);
        pstm.setInt(1, idUsuario);
        rs = pstm.executeQuery();

        if (rs.next()) {

            usuario.setIdUsuario(rs.getInt("u.idUsuario"));
            usuario.setNome(rs.getString("u.nome"));
            usuario.setLogin(rs.getString("u.login"));
            usuario.setSenha(rs.getString("u.senha"));
            usuario.setEmail(rs.getString("u.email"));
            usuario.setStatus(rs.getInt("u.status"));

            Perfil perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt("p.idPerfil"));
            perfil.setNome(rs.getString("p.nome"));

            // Relacionamento por associação entre os objetos da classe Usuário e Perfil
            usuario.setPerfil(perfil);
        }

        ConexaoFactory.close(conexao);
        return usuario;
    }

    public boolean desativar(Usuario usuario) throws SQLException {

        sqlUsuario = "UPDATE usuario SET status = 0 "
                + "WHERE idUsuario = ?";

        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlUsuario);
        pstm.setInt(1, usuario.getIdUsuario());

        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        return true;

    }

    public boolean ativar(Usuario usuario) throws SQLException {

        sqlUsuario = "UPDATE usuario SET status = 1 "
                + "WHERE idUsuario = ?";

        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlUsuario);
        pstm.setInt(1, usuario.getIdUsuario());

        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        return true;

    }

    public Usuario getRecuperarUsuario(String login) throws SQLException {
        Usuario usuario = new Usuario();

        sqlUsuario = "SELECT idUsuario, nome, login, senha, email, status, idPerfil "
                + "FROM USUARIO WHERE login = ?";

        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlUsuario);

        pstm.setString(1, login);
        rs = pstm.executeQuery();

        if (rs.next()) {
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setLogin(rs.getString("login"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setEmail(rs.getString("email"));
            usuario.setStatus(rs.getInt("status"));
            
            PerfilDAO daoPerfil = new PerfilDAO();
            
            usuario.setPerfil(daoPerfil.getCarregarPorId(rs.getInt("idPerfil")));
        }

        ConexaoFactory.close(conexao);
        return usuario;
    }

}

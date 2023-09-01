package Model;

import Factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuDAO extends ConexaoFactory {

    Connection conexao;
    PreparedStatement pstm;
    ResultSet rs;
    String sqlMenu = "";

    public ArrayList<Menu> getLista() throws SQLException {

        ArrayList<Menu> menus = new ArrayList<>();

        sqlMenu = "SELECT idMenu, nome, link, icone, exibir, status "
                + "FROM MENU ";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlMenu);
        rs = pstm.executeQuery();

        while (rs.next()) {

            Menu menu = new Menu();
            
            menu.setIdMenu(rs.getInt("idMenu"));
            menu.setNome(rs.getString("nome"));
            menu.setLink(rs.getString("link"));
            menu.setIcone(rs.getString("icone"));
            menu.setExibir(rs.getInt("exibir"));
            menu.setStatus(rs.getInt("status"));
            
            menus.add(menu);
        }
        ConexaoFactory.close(conexao);
        return menus;
    }
    
    public boolean salvar(Menu menu) throws SQLException {
        
        conexao = ConexaoFactory.conectar();
        
        if(menu.getIdMenu() == 0) {
            sqlMenu = "INSERT INTO menu "
                    + "(nome, link, icone, exibir, status) "
                    + "VALUES (?, ?, ?, ?, ?)";
            
            pstm = conexao.prepareStatement(sqlMenu);
            pstm.setString(1, menu.getNome());
            pstm.setString(2, menu.getLink());
            pstm.setString(3, menu.getIcone());
            pstm.setInt(4, menu.getExibir());
            pstm.setInt(5, menu.getStatus());
        
        } else {
            sqlMenu = "UPDATE menu SET "
                    + "nome = ?, "
                    + "link = ?, "
                    + "icone = ?, "
                    + "exibir = ?, "
                    + "status= ? "
                    + "WHERE idMenu= ?";
            
            pstm = conexao.prepareStatement(sqlMenu);
            pstm.setString(1, menu.getNome());
            pstm.setString(2, menu.getLink());
            pstm.setString(3, menu.getIcone());
            pstm.setInt(4, menu.getExibir());
            pstm.setInt(5, menu.getStatus());
        }
        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        return true;
    }
    
    public Menu getCarregarPorId(int idMenu) throws SQLException {
        
        Menu menu = new Menu();
        
        sqlMenu = "SELECT * FROM menu "
                + "WHERE idMenu = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlMenu);
        pstm.setInt(1, idMenu);
        rs = pstm.executeQuery();
        
        if(rs.next()) {
            menu.setIdMenu(rs.getInt("idMenu"));
            menu.setNome(rs.getString("nome"));
            menu.setLink(rs.getString("link"));
            menu.setExibir(rs.getInt("exibir"));
            menu.setStatus(rs.getInt("status"));
        }
        ConexaoFactory.close(conexao);
        return menu;
    }
    
    public boolean desativar(Menu menu) throws SQLException {
        
        sqlMenu = "UPDATE menu SET status = 0 "
                + "WHERE idMenu = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlMenu);
        pstm.setInt(1, menu.getIdMenu());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
          
    public boolean ativar(Menu menu) throws SQLException {
        
        sqlMenu = "UPDATE menu SET status = 1 "
                + "WHERE idMenu = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlMenu);
        pstm.setInt(1, menu.getIdMenu());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    

}

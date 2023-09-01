package Model;

import Factory.ConexaoFactory;
import Util.DataUtility;
import java.sql.*;
import java.util.ArrayList;

public class PerfilDAO extends ConexaoFactory {

    Connection conexao;
    PreparedStatement pstm;
//  ResultSet = conjunto de resultados da requisição Query/SQL "resultado da pesquisa"
    ResultSet rs;
    String sqlPerfil = "";

    public ArrayList<Perfil> getLista() throws SQLException {

        ArrayList<Perfil> perfis = new ArrayList<>();

        sqlPerfil = "SELECT idPerfil, nome, dataCadastro, status "
                + "FROM PERFIL";


        conexao = ConexaoFactory.conectar();
//      pstm prepara a instrução
        pstm = conexao.prepareStatement(sqlPerfil);
        rs = pstm.executeQuery();

//      next é um método booleano
        while (rs.next()) {

            Perfil perfil = new Perfil();

            perfil.setIdPerfil(rs.getInt("idPerfil"));
            perfil.setNome(rs.getString("nome"));
            perfil.setDataCadastro(rs.getDate("dataCadastro"));
            perfil.setStatus(rs.getInt("status"));
            perfis.add(perfil);

        }
        ConexaoFactory.close(conexao); 
        return perfis;

    }

    public boolean salvar(Perfil perfil) throws SQLException {
        
        conexao = ConexaoFactory.conectar();

        if (perfil.getIdPerfil() == 0) {
            sqlPerfil = "INSERT INTO perfil "
                    + "(nome, dataCadastro, status) "
                    + "VALUES (?, ?, ?)";

            pstm = conexao.prepareStatement(sqlPerfil);
            pstm.setString(1, perfil.getNome());
            pstm.setDate(2, DataUtility.dateParaDateSql(perfil.getDataCadastro()));
            pstm.setInt(3, perfil.getStatus());

        } else {
            sqlPerfil = "UPDATE perfil SET "
                    + "nome = ?, "
                    + "dataCadastro = ?, "
                    + "status = ? "
                    + "WHERE idPerfil = ?";
            
            pstm = conexao.prepareStatement(sqlPerfil);
            pstm.setString(1, perfil.getNome());
            pstm.setDate(2, DataUtility.dateParaDateSql(perfil.getDataCadastro()));
            pstm.setInt(3, perfil.getStatus());
            pstm.setInt(4, perfil.getIdPerfil());
            
        }
        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        
        return true;
    }
    
    /*
        Método que busca por id
    */
    public Perfil getCarregarPorId(int idPerfil) throws SQLException {
        
        Perfil perfil = new Perfil();
        
        sqlPerfil = "SELECT * FROM perfil "
                + "WHERE idPerfil = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlPerfil);
        pstm.setInt(1, idPerfil);
        rs = pstm.executeQuery();
        
        if(rs.next()) {
            perfil.setIdPerfil(rs.getInt("idPerfil"));
            perfil.setNome(rs.getString("nome"));
            perfil.setDataCadastro(rs.getDate("dataCadastro"));
            perfil.setStatus(rs.getInt("status"));
        }
        
        ConexaoFactory.close(conexao);
        return perfil;
    }
    
    public boolean desativar(Perfil perfil) throws SQLException {
        
        sqlPerfil = "UPDATE perfil SET status = 0 "
                + "WHERE idPerfil = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlPerfil);
        pstm.setInt(1, perfil.getIdPerfil());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    public boolean ativar(Perfil perfil) throws SQLException {
        
        sqlPerfil = "UPDATE perfil SET status = 1 "
                + "WHERE idPerfil = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlPerfil);
        pstm.setInt(1, perfil.getIdPerfil());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
}

package Model;

import Factory.ConexaoFactory;
import Util.DataUtility;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.sql.*;
import java.util.ArrayList;


public class ProdutoDAO extends ConexaoFactory {

    Connection conexao;
    PreparedStatement pstm;
//  ResultSet = conjunto de resultados da requisição Query/SQL "resultado da pesquisa"
    ResultSet rs;
    String sqlProduto = "";

    public ArrayList<Produto> getLista() throws SQLException {

        ArrayList<Produto> produtos = new ArrayList<>();

        sqlProduto = "SELECT idProduto, nome, descricao, dataCadastro, status "
                + "FROM PRODUTO";


        conexao = ConexaoFactory.conectar();
//      pstm prepara a instrução
        pstm = conexao.prepareStatement(sqlProduto);
        rs = pstm.executeQuery();

//      next é um método booleano
        while (rs.next()) {

            Produto produto = new Produto();

            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setDataCadastro(rs.getDate("dataCadastro"));
            produto.setStatus(rs.getInt("status"));
            produtos.add(produto);

        }
        ConexaoFactory.close(conexao); 
        return produtos;

    }

    public boolean salvar(Produto produto) throws SQLException {
        
        conexao = ConexaoFactory.conectar();

        if (produto.getIdProduto() == 0) {
            sqlProduto = "INSERT INTO produto "
                    + "(nome, descricao, dataCadastro, status) "
                    + "VALUES (?, ?, ?, ?)";

            pstm = conexao.prepareStatement(sqlProduto);
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.setDate(3, DataUtility.dateParaDateSql(produto.getDataCadastro()));
            pstm.setInt(4, produto.getStatus());

        } else {
            sqlProduto = "UPDATE produto SET "
                    + "nome = ?, "
                    + "descricao = ?, "
                    + "dataCadastro = ?, "
                    + "status = ? "
                    + "WHERE idProduto = ?";
            
            pstm = conexao.prepareStatement(sqlProduto);
            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.setDate(3, DataUtility.dateParaDateSql(produto.getDataCadastro()));
            pstm.setInt(4, produto.getStatus());
            pstm.setInt(5, produto.getIdProduto());
            
        }
        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        
        return true;
    }
    
    /*
        Método que busca por id
    */
    public Produto getCarregarPorId(int idProduto) throws SQLException {
        
        Produto produto = new Produto();
        
        sqlProduto = "SELECT * FROM produto "
                + "WHERE idProduto = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlProduto);
        pstm.setInt(1, idProduto);
        rs = pstm.executeQuery();
        
        if(rs.next()) {
            produto.setIdProduto(rs.getInt("idProduto"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setDataCadastro(rs.getDate("dataCadastro"));
            produto.setStatus(rs.getInt("status"));
        }
        
        ConexaoFactory.close(conexao);
        return produto;
    }
    
    public boolean desativar(Produto produto) throws SQLException {
        
        sqlProduto = "UPDATE produto SET status = 0 "
                + "WHERE idProduto = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlProduto);
        pstm.setInt(1, produto.getIdProduto());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    public boolean ativar(Produto produto) throws SQLException {
        
        sqlProduto = "UPDATE produto SET status = 1 "
                + "WHERE idProduto = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlProduto);
        pstm.setInt(1, produto.getIdProduto());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
    
    public boolean deletar (Produto produto) throws SQLException {
        
        sqlProduto = "DELETE FROM produto "
                + "WHERE idProduto = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlProduto);
        pstm.setInt(1, produto.getIdProduto());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    
    
    
    public boolean listarUmaColunaTabelaProduto (Produto produto) throws SQLException {
        
        sqlProduto = "SELECT idProduto FROM produto "
                + " WHERE idProduto = ? ";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlProduto);
        pstm.setInt(1, produto.getIdProduto());
        rs = pstm.executeQuery();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
    
}

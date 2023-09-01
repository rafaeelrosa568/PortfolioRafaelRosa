package Model;

import Factory.ConexaoFactory;
import Util.DataUtility;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import java.sql.*;
import java.util.ArrayList;


public class ClienteDAO extends ConexaoFactory {

    Connection conexao;
    PreparedStatement pstm;
//  ResultSet = conjunto de resultados da requisição Query/SQL "resultado da pesquisa"
    ResultSet rs;
    String sqlCliente = "";

    public ArrayList<Cliente> getLista() throws SQLException {

        ArrayList<Cliente> clientes = new ArrayList<>();

        sqlCliente = "SELECT idCliente, nome, cpf, dataCadastro, status "
                + "FROM CLIENTE";


        conexao = ConexaoFactory.conectar();
//      pstm prepara a instrução
        pstm = conexao.prepareStatement(sqlCliente);
        rs = pstm.executeQuery();

//      next é um método booleano
        while (rs.next()) {

            Cliente cliente = new Cliente();

            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setDataCadastro(rs.getDate("dataCadastro"));
            cliente.setStatus(rs.getInt("status"));
            clientes.add(cliente);

        }
        ConexaoFactory.close(conexao); 
        return clientes;

    }

    public boolean salvar(Cliente cliente) throws SQLException {
        
        conexao = ConexaoFactory.conectar();

        if (cliente.getIdCliente() == 0) {
            sqlCliente = "INSERT INTO cliente "
                    + "(nome, cpf, dataCadastro, status) "
                    + "VALUES (?, ?, ?, ?)";

            pstm = conexao.prepareStatement(sqlCliente);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setDate(3, DataUtility.dateParaDateSql(cliente.getDataCadastro()));
            pstm.setInt(4, cliente.getStatus());

        } else {
            sqlCliente = "UPDATE cliente SET "
                    + "nome = ?, "
                    + "cpf = ?, "
                    + "dataCadastro = ?, "
                    + "status = ? "
                    + "WHERE idCliente = ?";
            
            pstm = conexao.prepareStatement(sqlCliente);
            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setDate(3, DataUtility.dateParaDateSql(cliente.getDataCadastro()));
            pstm.setInt(4, cliente.getStatus());
            pstm.setInt(5, cliente.getIdCliente());
            
        }
        pstm.executeUpdate();
        ConexaoFactory.close(conexao);
        
        return true;
    }
    
    /*
        Método que busca por id
    */
    public Cliente getCarregarPorId(int idCliente) throws SQLException {
        
        Cliente cliente = new Cliente();
        
        sqlCliente = "SELECT * FROM cliente "
                + "WHERE idCliente = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, idCliente);
        rs = pstm.executeQuery();
        
        if(rs.next()) {
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNome(rs.getString("nome"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setDataCadastro(rs.getDate("dataCadastro"));
            cliente.setStatus(rs.getInt("status"));
        }
        
        ConexaoFactory.close(conexao);
        return cliente;
    }
    
    public boolean desativar(Cliente cliente) throws SQLException {
        
        sqlCliente = "UPDATE cliente SET status = 0 "
                + "WHERE idCliente = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, cliente.getIdCliente());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    public boolean ativar(Cliente cliente) throws SQLException {
        
        sqlCliente = "UPDATE cliente SET status = 1 "
                + "WHERE idCliente = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, cliente.getIdCliente());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
    
    public boolean deletar (Cliente cliente) throws SQLException {
        
        sqlCliente = "DELETE FROM cliente "
                + "WHERE idCliente = ?";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, cliente.getIdCliente());
        pstm.executeUpdate();
        
        ConexaoFactory.close(conexao);
        return true;
    }
    
    
    public boolean gerarPDF (Cliente cliente) throws SQLException {
        
        
        
        sqlCliente = "SELECT * FROM cliente "
                + "WHERE idCliente = ?";
        
        Document documento = new Document(sqlCliente);
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, cliente.getIdCliente());
        pstm.executeQuery();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
    
    public boolean listarUmaColunaTabelaCliente (Cliente cliente) throws SQLException {
        
        sqlCliente = "SELECT idCliente FROM cliente "
                + " WHERE idCliente = ? ";
        
        conexao = ConexaoFactory.conectar();
        pstm = conexao.prepareStatement(sqlCliente);
        pstm.setInt(1, cliente.getIdCliente());
        rs = pstm.executeQuery();
        
        ConexaoFactory.close(conexao);
        return true;
        
    }
    
}

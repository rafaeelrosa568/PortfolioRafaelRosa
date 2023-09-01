package TesteConexao;

import Factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {
    public static void main(String[] args) {
        
        Connection conexao = null;
        
        try {
            conexao = ConexaoFactory.conectar();
            System.out.println("Conexão ao banco de dados concluída com êxito!");
            conexao.close();
        } catch (SQLException ex) {
            System.out.println("Falha na comunicação com o banco de dados."
            + ex.getMessage());
        }
    }
}

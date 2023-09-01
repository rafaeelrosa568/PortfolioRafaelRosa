package Factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConexaoFactory {

    private static final String URLDOBANCO
            = "jdbc:mysql://localhost:3306/rafaelbd?useTimezone=true&serverTimezone=UTC&useSSL=false";
    private static final String USUARIODOBANCO = "root";
    private static final String SENHADOBANCO = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    /* versão inferior de mysql 
    = private static final String DRIVER = "com.mysql.jdbc.Driver"; */
    //useTimeZone=true serverTimeZone=UTC useSSL=false
//    Connection conexao;
//    
//    public static Connection conectar() {
//        try {
//            conexao = DriverManager.getConnection(urlDoBanco, usuarioDoBanco, senhaDoBanco);
//        } catch (Exception ex) {
//            System.out.println("");
//        }
//        return conexao;
//    }
//    
//}
    public static Connection conectar() throws SQLException {

        Connection conexao = null;

        try {
            Class.forName(DRIVER);
            conexao = 
                    DriverManager.getConnection(URLDOBANCO, USUARIODOBANCO, SENHADOBANCO);
        } catch (ClassNotFoundException ex) {
            System.out.println("Falha ao registrar o Driver: "
                    + ex.getMessage());
        }

        return conexao;

    }

    public static void close(Connection conexao) throws SQLException {
        if (conexao != null) {
            conexao.close();
        }
    }

}

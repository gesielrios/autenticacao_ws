package br.com.grios.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDao {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/autenticacao";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    private Connection con;
    private PreparedStatement stmt;


    /**
     * Método para criar a conexao com o bando de dados.
     *
     */
    private void conection() throws ClassNotFoundException, SQLException {
    	Class.forName(DRIVER);
		con = DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     *Método para fechar os recursos da conexão com o banco de dados
     *
     */
    private void closeConection() throws SQLException {
    	stmt.close();
    	con.close();
    }

    /**
     * Método utilizado para efetuar a autenticação pelo WS.
     * @param login
     * @param senha
     * @return true se o login e senha estiver cadastrado e false caso contrário.
     * @throws SQLException
     */
    public Boolean autenticado(String login, String senha) throws SQLException {

        Boolean result = Boolean.FALSE;

        try {
            conection();

            String sql = "SELECT * FROM usuarios WHERE login = ? and senha = ?";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = Boolean.TRUE;
            }
            
        } catch (ClassNotFoundException e) {
        	throw new SQLException();
        } finally {
            closeConection();
        }

        return result;
    }

    public Boolean autorizado(String login, String role) throws SQLException {

        Boolean result = Boolean.FALSE;

        try {
            conection();

            String sql = "select * from usuarios us " +
                            " inner join sistemas si on si.usuario_id = us.id " +
                            " where us.login = ? and si.role = ? ";

            stmt = con.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, role);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = Boolean.TRUE;
            }
            
        } catch (ClassNotFoundException e) {
			throw new SQLException();
        } finally {
            closeConection();
        }

        return result;
    }

}

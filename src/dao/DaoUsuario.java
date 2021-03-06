package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.BeanFerramenta;
import connection.SingleConnection;

public class DaoUsuario {
	
	private Connection connection;
	
	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(BeanFerramenta usuario) {
		
		try {
		String sql = "insert into usuario(login, senha) values (?, ?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, usuario.getLogin());
		insert.setString(2, usuario.getSenha());
		insert.execute();
		connection.commit();
		}catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
				
	}
	
	public boolean validarLogin(String login) throws Exception{
		String sql = "select count(1) as qtd from usuario where login  = '" + login + "'";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet =  preparedStatement.executeQuery();
		
		if(resultSet.next()) {					
			
			return resultSet.getInt("qtd") <=0;
		}
		
		return false;
	}

}

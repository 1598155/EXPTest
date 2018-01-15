package com.nupt.EXPTest;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCUtlTool {
	public static Connection getConnection(){
		String driver="com.mysql.jdbc.Driver";   //��ȡmysql���ݿ��������
		String url="jdbc:mysql://10.128.0.120:3306/streams"; //�������ݿ⣨kucun�����ݿ�����
		String name="root";//����mysql���û���
		String pwd="123456";//����mysql������
		try{
			Class.forName(driver);
			Connection conn=DriverManager.getConnection(url,name,pwd);//��ȡ���Ӷ���
			return conn;
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			return null;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			if(ps!=null){
				ps.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		try{
			if(conn!=null){
				conn.close();
			}
		}catch(SQLException e){
			e.printStackTrace();	
		}
	}
	
	public static void main(String[] args) throws SQLException
	{
		
		Connection cc=JDBCUtlTool.getConnection();
		
		if(!cc.isClosed())

		System.out.println("Succeeded connecting to the Database!");
		Statement statement = cc.createStatement();
		String sql = "select * from statusinf";
		ResultSet rs = statement.executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString("id")+"");
		}


	}
}

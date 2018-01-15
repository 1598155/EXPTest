package com.nupt.EXPTest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TestMycon {
	static String sql = null;
	static MyCon db1 = null;

	public static void main(String[] args) {
		sql = "insert into statusinf (car_id,tmp,hum,time) values (?,?,?,?)";// SQL���
		db1 = new MyCon(sql);// ����DBHelper����

		try {
			PreparedStatement pst = db1.pst;
			
			while(true){
			for (int i = 0; i < 50; i++) {
				pst.setInt(1, 2);
				pst.setInt(2,(int)(Math.random()*100));
				pst.setInt(3,(int)(Math.random()*100));
				pst.setTimestamp(4, new Timestamp((new java.util.Date()).getTime()));
				pst.addBatch();
				System.out.println("存储");
			}
			pst.executeBatch();
			}
		}// ��ʾ����

		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db1.close();

		}
	}

}

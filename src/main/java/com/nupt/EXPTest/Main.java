package com.nupt.EXPTest;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;

public class Main{

    private String sql = "insert into statusinf (car_id,tmp,hum,time) values (?,?,?,?)";
    private String connectStr = "jdbc:mysql://10.128.0.120:3306/test?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=false&rewriteBatchedStatements=true";
    private String username = "root";
    private String password = "123456";

    private void doStore() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = (Connection) DriverManager.getConnection(connectStr, username, password);
        conn.setAutoCommit(false);
        int count = 0;
        PreparedStatement psts = conn.prepareStatement(sql);
        long start = System.currentTimeMillis();
        
        while(true){
        for (int i = 1; i <= 250; i++) {
            psts.setInt(1,(int)(Math.random()*10));
            psts.setInt(2,(int)(Math.random()*100));
            psts.setInt(3,(int)(Math.random()*100));
            psts.setTimestamp(4, new Timestamp((new java.util.Date()).getTime()));
            psts.addBatch();   // 加入批量处理
            count++;
        }
        psts.executeBatch(); // 执行批量处理
        conn.commit(); // 提交
       
        long end = System.currentTimeMillis();
        System.out.println("数量=" + count);
        System.out.println("运行时间=" + (end - start));
        }
       
    }

    public static void main(String[] args) {
        try {
            new Main().doStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


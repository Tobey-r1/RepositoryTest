package com.example.mysqlconnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {
   private static String diver = "com.mysql.jdbc.Driver";
   //加入utf-8是为了后面往表中输入中文，表中不会出现乱码的情况
   private static String url = "jdbc:mysql://172.20.10.3:3306/test?characterEncoding=utf-8";
   private static String user = "liu";//用户名
   private static String password = "031122";//密码
    /*
    * 连接数据库
    * */
   public static Connection getConn(){
       Connection conn = null;
       try {
           Class.forName(diver);
           conn = (Connection) DriverManager.getConnection(url,user,password);//获取连接
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return conn;
   }
}

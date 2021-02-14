package com.flash.sale.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

  public static Connection getConn() throws Exception {
    String url = "jdbc:mysql://127.0.0.1:3306/miaosha?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false";
    String username = "root";
    String password = "123456";
    String driver = "com.mysql.jdbc.Driver";
    Class.forName(driver);
    return DriverManager.getConnection(url, username, password);
  }
}

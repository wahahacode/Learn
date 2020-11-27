package saooperation.dao.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private static DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            // 使用连接池技术
            JDBCUtil.dataSource = DruidDataSourceFactory.createDataSource(properties);
            //Class.forName(properties.getProperty("driverName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JDBCUtil(){};

    public static Connection getConnection(){
/*        try {
            return DriverManager.getConnection(properties.getProperty("url") , properties.getProperty("username") , properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;*/
        try {
            return JDBCUtil.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(AutoCloseable... acarr){
        for (AutoCloseable autoCloseable : acarr) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

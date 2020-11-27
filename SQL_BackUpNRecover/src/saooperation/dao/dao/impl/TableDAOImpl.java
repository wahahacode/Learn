package saooperation.dao.dao.impl;

import saooperation.dao.dao.ITableDAO;
import saooperation.dao.util.JDBCUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableDAOImpl<T> implements ITableDAO<T> {

    @Override
    public void insert(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("insert into ").append(tableName + " values(");
        // 拼接每个字段
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Object value = null;
            try {
                value = declaredField.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null != value && value.getClass() == String.class) {
                sql.append("'" + value + "'" + ",");
            } else {
                sql.append(value + ",");
            }
        }
        // 多的逗号去掉 , 再拼个半括号
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        sql.append(")");
        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sqlOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("update ").append(tableName + " set ");
        // 拼接每个字段
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            String name = declaredField.getName();
            Object value = null;
            try {
                value = declaredField.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            // set 属性名 = 值
            sql.append(name + " = ");

            if (null != value && value.getClass() == String.class) {
                // 字符串需要''包围否则sql语句出错
                sql.append("'" + value + "'" + ",");
            } else {
                sql.append(value + ",");
            }
        }
        // 多的逗号去掉
        sql = new StringBuilder(sql.substring(0, sql.length() - 1));
        // where XX = xx , 默认先使用id吧
        try {
            Field id = clz.getDeclaredField("id");
            id.setAccessible(true);
            sql.append("where id = " + id.get(obj) );
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sqlOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delete(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("delete from ").append(tableName);

        // where XX = xx , 默认先使用id吧
        try {
            Field id = clz.getDeclaredField("id");
            id.setAccessible(true);
            sql.append(" where id = " + id.get(obj) );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sqlOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public T selectById(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("select * from ").append(tableName);
        // where XX = xx , 默认先使用id吧
        try {
            Field id = clz.getDeclaredField("id");
            id.setAccessible(true);
            sql.append(" where id = " + id.get(obj) );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sqlOutput);
            // 封装对象并返回
            if (resultSet.next()) {
                for (Field declaredField : clz.getDeclaredFields()) {
                    // 解锁
                    declaredField.setAccessible(true);
                    // 获取字段名
                    String name = declaredField.getName();
                    // 获取字段在数据库的值
                    Object value = resultSet.getObject(name);
                    // 装载值到对象
                    declaredField.set(obj , value);
                }
                return obj;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> selectAll(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("select * from ").append(tableName);

        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(sqlOutput);
            // 封装对象并返回
            ArrayList<T> arrayList = new ArrayList<>();
            // 获取domain构造方法 : 无参
            Constructor constructor = clz.getConstructor();
            // 打开访问权限
            constructor.setAccessible(true);
            while (resultSet.next()) {
                Object o = constructor.newInstance();
                for (Field declaredField : clz.getDeclaredFields()) {
                    // 解锁
                    declaredField.setAccessible(true);
                    // 获取字段名
                    String name = declaredField.getName();
                    // 获取字段在数据库的值
                    Object value = resultSet.getObject(name);
                    // 装载值到对象
                    declaredField.set(o , value);
                }
                // 装载进arrayList
                arrayList.add((T) o);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void truncate(T obj) {
        Class clz = obj.getClass();
        // 获取表名 , 要求封装类名必须与表名完全一致
        String tableName = clz.getSimpleName();
        // 获取表字段
        Field[] declaredFields = clz.getDeclaredFields();
        // 拼接字段
        // 总字段
        StringBuilder sql = new StringBuilder();
        // sql语句头输入
        sql.append("truncate table ").append(tableName);

        String sqlOutput = sql.toString();

        // 开始数据库操作
        try (
                Connection connection = JDBCUtil.getConnection();
                // statement 性能更好
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sqlOutput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recover(List<T> obj) {
        // 判断空输入
        if (null == obj) {
            System.out.println("恢复对象数组不能为空");
            return;
        }
        // 截断表
        truncate(obj.get(0));
        // 插入数据
        for (T t : obj) {
            insert(t);
        }
    }
}

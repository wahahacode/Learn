package saooperation.service.impl;

import saooperation.dao.dao.impl.TableDAOImpl;
import saooperation.service.ITableService;

import java.io.*;
import java.util.List;

public class TableServiceImpl implements ITableService {
    @Override
    public void backup(Object domain) {
        // 开始使用查询全部的DAO 进行对象备份
        TableDAOImpl tableDAO = new TableDAOImpl();
        // 获取全表数据
        List list = tableDAO.selectAll(domain);
        // 定义备份文件名
        String name = domain.getClass().getSimpleName();
        // 丢resources文档里面
        File backupFile = new File("resources/" + name + ".dbObjBackup");
        // 输出对象流
        try (
                // 创建文件
                FileOutputStream fos = new FileOutputStream(backupFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                ) {
            // 如果备份文件不存在则 创建
            if(!backupFile.exists()) {
                fos.flush();
            }
            oos.writeObject(list);
            System.out.println(name + "备份成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void recover(Object domain) {
        // 开始使用查询全部的DAO 进行对象备份
        TableDAOImpl tableDAO = new TableDAOImpl();
        // 获取全表数据
        List list = tableDAO.selectAll(domain);
        // 定义备份文件名
        Class clz = domain.getClass();
        String name = clz.getSimpleName();
        // 丢resources文档里面
        File backupFile = new File("resources/" + name + ".dbObjBackup");
        // 输出对象流
        try (
                // 创建文件
                FileInputStream fis = new FileInputStream(backupFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            // 如果备份文件不存在则 中止
            if(!backupFile.exists()) {
                System.out.println("备份文件不存在,恢复中止");
                return;
            }
            // 有则读
            List o = (List)ois.readObject();
            tableDAO.recover(o);
            System.out.println(name + "恢复成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

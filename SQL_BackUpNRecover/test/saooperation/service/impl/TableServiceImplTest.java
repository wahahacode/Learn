package saooperation.service.impl;

import org.junit.Test;
import saooperation.dao.domain.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import static org.junit.Assert.*;

public class TableServiceImplTest {

    @Test
    public void backup() {
        TableServiceImpl tableService = new TableServiceImpl();
        tableService.backup(new User());
    }

    @Test
    public void recover() {
        TableServiceImpl tableService = new TableServiceImpl();
        tableService.recover(new User());
    }
}
package saooperation.service;

public interface ITableService {
    void backup(Object domain);
    void recover(Object domain);
}

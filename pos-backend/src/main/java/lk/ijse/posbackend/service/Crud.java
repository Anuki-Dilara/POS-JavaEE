package lk.ijse.posbackend.service;

import lk.ijse.posbackend.entity.Customer;
import org.hibernate.Session;

import java.util.List;

public interface Crud<T> extends SuperService{

    List<T> getAll(Session session) throws Exception;
    boolean save(T t , Session session) throws Exception;
    boolean Update(T t, Session session) throws Exception;
    boolean delete(String id, Session session) throws Exception;
}

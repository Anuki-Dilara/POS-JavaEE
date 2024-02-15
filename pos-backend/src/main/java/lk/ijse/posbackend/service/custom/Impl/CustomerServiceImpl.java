package lk.ijse.posbackend.service.custom.Impl;

import lk.ijse.posbackend.entity.Customer;
import lk.ijse.posbackend.service.custom.CustomerService;
import org.hibernate.Session;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    @Override
    public List<Customer> getAll(Session session) throws Exception {
        return session.createQuery("FROM Customer", Customer.class).getResultList();
    }

    @Override
    public boolean save(Customer customer, Session session) throws Exception {
        session.persist(customer);
        return true;
    }

    @Override
    public boolean Update(Customer customer, Session session) throws Exception {
        session.update(customer);
        return true;
    }

    @Override
    public boolean delete(String id, Session session) throws Exception {
       session.remove(session.get(Customer.class, id));
        return true;
    }
}

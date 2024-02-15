package lk.ijse.posbackend.service.custom.Impl;

import lk.ijse.posbackend.entity.Item;
import lk.ijse.posbackend.entity.User;
import lk.ijse.posbackend.service.custom.UserService;
import org.hibernate.Session;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> getAll(Session session) throws Exception {
        return null;
    }

    @Override
    public boolean save(User user, Session session) throws Exception {
        session.persist(user);
        return true;
    }

    @Override
    public boolean Update(User user, Session session) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id, Session session) throws Exception {
        return false;
    }


    @Override
    public List<User> getAuth(Session session) throws Exception {
        return session.createQuery("FROM User", User.class).getResultList();
    }
}

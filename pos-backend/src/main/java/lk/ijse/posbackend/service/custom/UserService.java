package lk.ijse.posbackend.service.custom;

import lk.ijse.posbackend.entity.User;
import lk.ijse.posbackend.service.Crud;
import org.hibernate.Session;

import java.util.List;

public interface UserService extends Crud<User> {

    List<User> getAuth(Session session) throws Exception;
}

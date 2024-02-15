package lk.ijse.posbackend.bo.custom.impl;

import lk.ijse.posbackend.bo.custom.UserBO;
import lk.ijse.posbackend.dto.ItemDTO;
import lk.ijse.posbackend.dto.UserDTO;
import lk.ijse.posbackend.entity.Item;
import lk.ijse.posbackend.entity.User;
import lk.ijse.posbackend.service.ServiceFactory;
import lk.ijse.posbackend.service.custom.OrderService;
import lk.ijse.posbackend.service.custom.UserService;
import lk.ijse.posbackend.utill.Convert;
import lk.ijse.posbackend.utill.HibernateFactoryConfig;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBO {
    private UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.Servicetypes.USER);

    @Override
    public boolean saveUser(UserDTO dto) throws Exception {
        Session session = HibernateFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        if (userService.save(Convert.userDTOToEntity(dto), session)){
            transaction.commit();
            session.close();
            return true;
        }
        session.close();
        return false;
    }

    @Override
    public List<UserDTO> getAuth() throws Exception {
        List<User> all = userService.getAuth(HibernateFactoryConfig.getInstance().getSession());
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : all){
            userDTOS.add(Convert.userEntityToDto(user));
        }
        return userDTOS;
    }


}
